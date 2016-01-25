package topcoder.srm5xx.srm581;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/13.
 */
public class TreeUnion {
    public double expectedCycles(String[] tree1, String[] tree2, int K) {
        int[][] g1 = buildTree(tree1);
        int[][] g2 = buildTree(tree2);
        int n = g1.length;

        LCA l1 = new LCA(g1);
        LCA l2 = new LCA(g2);

        int[][] distDegreeForG2 = new int[n][K+1];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                int dist = l2.dist(i, j);
                if (dist <= K) {
                    distDegreeForG2[i][dist]++;
                }
            }
        }


        double ex = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0 ; j < n ; j++) {
                if (i == j) {
                    continue;
                }
                int d1 = l1.dist(i, j);
                int d2 = K - d1 - 2;
                if (d2 >= 1) {
                    for (int k = 0; k < n ; k++) {
                        // i-k, j-?
                        int ptn = distDegreeForG2[k][d2];
                        ex += (1.0d / n) * (ptn * 1.0d) / (n-1);
                    }
                }
            }
        }
        return ex / 2.0d;
    }

    private int[][] buildTree(String[] tree1) {
        StringBuilder line = new StringBuilder();
        for (String l : tree1) {
            line.append(l);
        }
        String[] part = line.toString().split(" ");
        int n = part.length+1;
        int[][] edges = new int[n-1][2];
        int[] deg = new int[n];
        for (int i = 0; i < n-1; i++) {
            int a = Integer.valueOf(part[i]);
            int b = i+1;
            edges[i][0] = a;
            edges[i][1] = b;
            deg[a]++;
            deg[b]++;
        }
        int[][] graph = new int[n][];
        for (int i = 0; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0; i < n-1 ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
            graph[b][--deg[b]] = a;
        }
        return graph;
    }

    static class LCA {
        int[][] graph;
        int[][] parent;
        int[] depth;

        public LCA(int[][] graph) {
            int n = graph.length;
            this.graph = graph;
            init(n);
        }

        void dfs(int now, int from, int dep) {
            parent[0][now] = from;
            depth[now] = dep;
            for (int to : graph[now]) {
                if (to != from) {
                    dfs(to, now, dep+1);
                }
            }
        }

        void init(int n) {
            int log = 1;
            int nn = n;
            while (nn >= 1) {
                nn /= 2;
                log++;
            }
            parent = new int[log+1][n];
            for (int i = 0 ; i <= log ; i++) {
                Arrays.fill(parent[i], -1);
            }
            depth = new int[n];

            dfs(0, -1, 0);

            for (int k = 0 ; k < log ; k++) {
                for (int v = 0 ; v < n ; v++) {
                    if (parent[k][v] < 0) {
                        parent[k+1][v] = -1;
                    } else {
                        parent[k+1][v] = parent[k][parent[k][v]];
                    }
                }
            }
        }

        int lca(int u, int v) {
            int loglen = parent.length;
            if (depth[u] > depth[v]) {
                int tmp = u;
                u = v;
                v = tmp;
            }
            for (int k = 0 ; k < loglen ; k++) {
                if (((depth[v] - depth[u]) >> k) % 2 == 1) {
                    v = parent[k][v];
                }
            }
            if (u == v) {
                return u;
            }

            for (int k = loglen-1 ; k >= 0 ; k--) {
                if (parent[k][u] != parent[k][v]) {
                    u = parent[k][u];
                    v = parent[k][v];
                }
            }
            return parent[0][u];
        }

        int dist(int x, int y) {
            int l = lca(x, y);
            return depth[x] + depth[y] - depth[l] * 2;
        }
    }
}
