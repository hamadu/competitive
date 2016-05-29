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

    private static int[] make(int[] ints, int n) {
        int[] ret = new int[n];
        ret[0] = ints[0];
        for (int i = 1; i < n; i++) {
            long v = (1L*ret[i-1]*ints[1]+ints[2])%ints[3];
            ret[i] = (int) v;
        }
        return ret;
    }

    private static int solve(int[][] g, int[] s, int d) {
        int n = g.length;
        salary = s;
        graph = g;
        range = new int[n][2];
        for (int i = 0; i < n ; i++) {
            range[i][0] = 1;
            range[i][1] = 0;
        }

        dfs(0, -1, d);

        int[] imos = new int[2000010];
        for (int i = 0; i < n ; i++) {
            if (range[i][0] > range[i][1]) {
                continue;
            }
            int fr = Math.max(range[i][0], 0);
            int to = range[i][1];
            imos[fr]++;
            imos[to+1]--;
        }
        int val = 0;
        int max = 0;
        for (int i = 0; i < imos.length ; i++) {
            val += imos[i];
            max = Math.max(max, val);
        }
        return max;
    }

    static int[] salary;
    static int[][] graph;
    static int[][] range;

    static void dfs(int now, int par, int D) {
        if (par == -1) {
            range[now][0] = salary[now];
            range[now][1] = salary[now] + D;
        } else {
            range[now][0] = Math.max(salary[now], range[par][0]);
            range[now][1] = Math.min(salary[now] + D, range[par][1]);
        }
        for (int to : graph[now]) {
            if (to == par) {
                continue;
            }
            dfs(to, now, D);
        }
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



