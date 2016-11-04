package topcoder.srm6xx.srm699.div1;

import java.io.*;
import java.util.*;

public class FromToDivisible {
    private static final int INF = 114514;

    public int shortest(int N, int S, int T, int[] a, int[] b) {
        List<int[]> edges = new ArrayList<>();

        // S -> a[i] ?
        int n = a.length;
        for (int i = 0; i < n; i++) {
            if (S % a[i] == 0) {
                edges.add(new int[]{n, i});
            }
            if (T % b[i] == 0) {
                edges.add(new int[]{i, n+1});
            }
        }

        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                long ab = lcm(b[i], a[j]);
                if (ab <= N && i != j) {
                    edges.add(new int[]{i, j});
                }
            }
        }
        int[][] graph = buildGraph(n+2, edges);
        int ret = cost(n, n+1, graph);

        return ret >= INF ? -1 : ret-1;
    }

    static int cost(int start, int goal, int[][] graph) {
        int n = graph.length;
        int[] dp = new int[n];
        Arrays.fill(dp, INF);
        dp[start] = 0;
        int[] que = new int[2*n];
        int qh = 0;
        int qt = 0;
        que[qh++] = start;
        que[qh++] = 0;
        while (qt < qh) {
            int now = que[qt++];
            int tim = que[qt++]+1;
            for (int to : graph[now]) {
                if (dp[to] > tim) {
                    dp[to] = tim;
                    que[qh++] = to;
                    que[qh++] = tim;
                }
            }
        }
        return dp[goal];
    }

    static int[][] buildGraph(int n, List<int[]> edges) {
        int m = edges.size();
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = edges.get(i)[0];
            deg[a]++;
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges.get(i)[0];
            int b = edges.get(i)[1];
            graph[a][--deg[a]] = b;
        }
        return graph;
    }


    public static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a%b);
    }

    public static long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
