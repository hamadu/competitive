package topcoder.srm6xx.srm668.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/05/25.
 */
public class WalkingToSchool {
    public String canWalkExactly(int N, int[] from, int[] to) {
        return solve(N, from, to) ? "Freedom" : "Chores";
    }

    private boolean solve(int n, int[] from, int[] to) {
        int m = from.length;
        int[][] edges = new int[m][2];
        for (int i = 0; i < m ; i++) {
            edges[i][0] = from[i];
            edges[i][1] = to[i];
        }
        int[][] graph = buildDirectedGraph(n, edges);
        boolean[] zeroOne = doit(graph, 0, 1);
        boolean[] oneZero = doit(graph, 1, 0);

        boolean[] flg = new boolean[4*n+1];
        for (int i = 0; i <= 2*n ; i++) {
            for (int j = 0; j <= 2*n ; j++) {
                if (zeroOne[i] && oneZero[j]) {
                    flg[i+j] = true;
                }
            }
        }

        long g = -1;
        for (int i = 0; i <= 4*n ; i++) {
            if (flg[i]) {
                if (g == -1) {
                    g = i;
                } else {
                    g = gcd(g, i);
                }
            }
        }
        return g == 1;
    }

    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a%b);
    }

    private boolean[] doit(int[][] graph, int start, int goal) {
        int n = graph.length;
        boolean[][] dp = new boolean[n][2*n+1];
        dp[start][0] = true;
        int[] que = new int[n*(2*n+1)*2];
        int qh = 0;
        int qt = 0;
        que[qh++] = start;
        que[qh++] = 0;
        while (qt < qh) {
            int now = que[qt++];
            int time = que[qt++];
            for (int to : graph[now]) {
                if (time+1 <= 2*n && !dp[to][time+1]) {
                    dp[to][time+1] = true;
                    que[qh++] = to;
                    que[qh++] = time+1;
                }
            }
        }
        return dp[goal];
    }


    static int[][] buildDirectedGraph(int n, int[][] edges) {
        int m = edges.length;
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            deg[a]++;
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
        }
        return graph;
    }


    public static void main(String[] args) {
        WalkingToSchool school = new WalkingToSchool();
        debug(school.canWalkExactly(3, new int[]{0, 0, 1}, new int[]{1, 2, 2}));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
