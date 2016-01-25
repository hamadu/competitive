package topcoder.srm6xx.srm670;

import java.util.Arrays;

/**
 * Created by hama_du on 15/10/11.
 */
public class Treestrat {
    private static final int INF = 10000;

    public int roundcnt(int[] tree, int[] A, int[] B) {
        int n = tree.length+1;
        int[][] edge = new int[n-1][2];
        for (int i = 0; i < n-1; i++) {
            edge[i][0] = i+1;
            edge[i][1] = tree[i];
        }
        int[][] dist = new int[n][n];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(dist[i], INF);
        }
        for (int i = 0; i < n ; i++) {
            dist[i][i] = 0;
        }
        for (int i = 0; i < n-1 ; i++) {
            dist[edge[i][0]][edge[i][1]] = 1;
            dist[edge[i][1]][edge[i][0]] = 1;
        }
        for (int k = 0; k < n ; k++) {
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < n ; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        boolean[] isA = new boolean[n];
        boolean[] isB = new boolean[n];
        for (int ai : A) {
            isA[ai] = true;
        }
        for (int bi : B) {
            isB[bi] = true;
        }

        int[][] graph = buildGraph(n, edge);

        int[] distB = new int[n];
        Arrays.fill(distB, INF);
        for (int i = 0; i < n ; i++) {
            if (isB[i]) {
                dfs(graph, distB, i, 0);
            }
        }

        int best = INF;
        for (int i = 0; i < n ; i++) {
            if (!isA[i]) {
                continue;
            }
            int[] distA = new int[n];
            Arrays.fill(distA, INF);
            dfs(graph, distA, i, 0);

            int max = 0;
            for (int j = 0; j < n ; j++) {
                if (distA[j] < distB[j]) {
                    max = Math.max(max, distB[j]);
                }
            }
            best = Math.min(best, max);
        }
        return best;
    }

    static void dfs(int[][] graph, int[] dist, int now, int d) {
        if (dist[now] <= d) {
            return;
        }
        dist[now] = d;
        for (int to : graph[now]) {
            dfs(graph, dist, to, d+1);
        }
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

    public static void main(String[] args) {
        Treestrat treestrat = new Treestrat();
        treestrat.roundcnt(new int[]{0,0,0,3,4,2},
        new int[]{1},
        new int[]{6});
    }
}
