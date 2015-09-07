package topcoder.srm5xx.srm561;

import java.util.*;

/**
 * Created by hama_du on 15/09/02.
 */
public class CirclesGame {
    public String whoCanWin(int[] x, int[] y, int[] r) {
        n = x.length;
        parent = new int[n];
        Arrays.fill(parent, -1);

        graph = new boolean[n][n];
        for (int i = 0; i < n ; i++) {
            int smallest = Integer.MAX_VALUE;
            for (int j = 0 ; j < n ; j++) {
                if (i == j || r[j] < r[i]) {
                    continue;
                }
                int centerDistance2 = (x[i]-x[j])*(x[i]-x[j])+(y[i]-y[j])*(y[i]-y[j]);
                int radius2 = (r[i]-r[j])*(r[i]-r[j]);
                if (centerDistance2 < radius2) {
                    // circle I is inside circle J
                    graph[j][i] = true;
                    if (smallest > r[j]) {
                        smallest = r[j];
                        parent[i] = j;
                    }
                }
            }
        }
        for (int i = 0; i < n ; i++) {
            graph[i][i] = true;
        }

        debug(parent);


        memo = new int[n];
        Arrays.fill(memo, -1);

        int xor = 0;
        for (int i = 0; i < n ; i++) {
            if (parent[i] == -1) {
                xor ^= dfs(i);
            }
        }
        return xor == 0 ? "Bob" : "Alice";
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }


    int n;
    int[] memo;
    boolean[][] graph;
    int[] parent;

    private int dfs(int now) {
        if (memo[now] != -1) {
            return memo[now];
        }

        Set<Integer> have = new HashSet<>();
        for (int i = 0; i < n ; i++) {
            if (graph[now][i]) {
                int tail = i;
                int last = i;
                List<Integer> heads = new ArrayList<>();
                while (true) {
                    for (int to = 0; to < n ; to++) {
                        if (parent[to] == tail && to != last) {
                            heads.add(to);
                        }
                    }
                    if (tail == now) {
                        break;
                    }
                    last = tail;
                    tail = parent[tail];
                }
                int result = 0;
                for (int sub : heads) {
                    result ^= dfs(sub);
                }
                have.add(result);
            }
        }
        for (int x = 0; x <= 255 ; x++) {
            if (!have.contains(x)) {
                memo[now] = x;
                return x;
            }
        }
        throw new RuntimeException("oh my god");
    }
}
