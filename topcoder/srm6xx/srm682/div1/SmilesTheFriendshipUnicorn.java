package topcoder.srm6xx.srm682.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/05/15.
 */
public class SmilesTheFriendshipUnicorn {

    public String hasFriendshipChain(int N, int[] A, int[] B) {
        int m = A.length;
        int[][] edges = new int[m][2];
        for (int i = 0; i < m ; i++) {
            edges[i][0] = A[i];
            edges[i][1] = B[i];
        }
        return solve(buildGraph(N, edges)) ? "Yay!" : ":(";
    }

    private boolean solve(int[][] graph) {
        int n = graph.length;
        boolean[][] has = new boolean[n][n];
        for (int i = 0; i < n ; i++) {
            for (int to : graph[i]) {
                has[to][i] = true;
            }
        }

        long[][] bits = new long[n][32];
        for (int i = 0; i < n ; i++) {
            for (int to : graph[i]) {
                int ti = to / 64;
                int tj = to & 63;
                bits[i][ti] |= 1L<<tj;
            }
        }

        int K = bits[0].length;
        for (int i = 0; i < n ; i++) {
            int k = graph[i].length;
            for (int u = 0 ; u < k ; u++) {
                for (int v = u+1 ; v < k ; v++) {
                    int uu = graph[i][u];
                    int vv = graph[i][v];
                    int atLeast = has[uu][vv] ? 2 : 1;
                    if (graph[uu].length <= atLeast) {
                        continue;
                    }
                    if (graph[vv].length <= atLeast) {
                        continue;
                    }

                    int ct = 0;
                    for (int j = 0; j < K ; j++) {
                         ct += Long.bitCount(bits[uu][j] & bits[vv][j]);
                    }
                    if (ct < graph[uu].length || ct < graph[vv].length) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static int[][] buildGraph(int n, int[][] edges) {
        int m = edges.length;
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            deg[a]++;
            deg[b]++;
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

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
