package gcj.gcj2015.round3;

import java.io.PrintWriter;
import java.util.*;

public class A {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            int n = in.nextInt();
            int d = in.nextInt();
            int[][] f = new int[2][4];
            for (int i = 0; i < 2 ; i++) {
                for (int j = 0; j < 4 ; j++) {
                    f[i][j] = in.nextInt();
                }
            }

            int[] s = make(f[0], n);
            int[] mg = make(f[1], n);
            int[][] edge = new int[n-1][2];
            for (int i = 0 ; i < n-1 ; i++) {
                edge[i][0] = i+1;
                edge[i][1] = mg[i+1] % (i+1);
            }
            int[][] graph = buildGraph(n, n-1, edge);

            int ret = solve(graph, s, d);
            out.println(String.format("Case #%d: %s", cs, ret));
        }
        out.flush();
    }

    private static int solve(int[][] g, int[] s, int d) {
        graph = g;
        salary = s;
        D = d;

        int n = g.length;
        minmax = new int[n][2];
        for (int i = 0 ; i < n ; i++) {
            minmax[i][0] = -1;
            minmax[i][1] = -1;
        }

        dfs(0, -1, salary[0], salary[0]);

        Map<Integer,List<Integer>> eventL = new HashMap<>();
        Map<Integer,List<Integer>> eventR = new HashMap<>();
        for (int i = 0 ; i < n ; i++) {
            if (minmax[i][0] == -1) {
                continue;
            }
            if (!eventL.containsKey(minmax[i][0])) {
                eventL.put(minmax[i][0], new ArrayList<>());
            }
            if (!eventR.containsKey(minmax[i][1])) {
                eventR.put(minmax[i][1], new ArrayList<>());
            }
            eventL.get(minmax[i][0]).add(i);
            eventR.get(minmax[i][1]).add(i);
        }

        boolean[] end = new boolean[n+1];

        int ans = 0;
        Set<Integer> have = new HashSet<>();
        for (int r = 0 ; r < 3000000 ; r++) {
            if (eventR.containsKey(r)) {
                for (int l : eventR.get(r)) {
                    if (!end[l]) {
                        have.add(l);
                    }
                }
            }
            if (r - D - 1 >= 0 && eventL.containsKey(r-D-1)) {
                for (int l : eventL.get(r-D-1)) {
                    have.remove(l);
                    end[l] = true;
                }
            }
            if (r-D-1 <= s[0] && s[0] <= r) {
                ans = Math.max(ans, have.size());
            }
        }
        return ans;
    }

    static int n;
    static int D;
    static int[] salary;
    static int[][] graph;
    static int[][] minmax;

    private static void dfs(int now, int par, int min, int max) {
        if (max - min > D) {
            return;
        }
        minmax[now][0] = min;
        minmax[now][1] = max;

        for (int to : graph[now]) {
            if (to != par) {
                dfs(to, now, Math.min(min, salary[to]), Math.max(max, salary[to]));
            }
        }
    }


    private static int[] make(int[] ints, int n) {
        int[] ret = new int[n];
        ret[0] = ints[0];
        for (int i = 1 ; i < n ; i++) {
            long v = (1L * ret[i-1] * ints[1] + ints[2]) % ints[3];
            ret[i] = (int)v;
        }
        return ret;
    }

    static int[][] buildGraph(int n, int m, int[][] _edges) {
        int[][] edges = new int[m][];
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = _edges[i][0];
            int b = _edges[i][1];
            deg[a]++;
            deg[b]++;
            edges[i] = new int[]{a, b};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
            graph[b][--deg[b]] = a;
        }
        return graph;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



