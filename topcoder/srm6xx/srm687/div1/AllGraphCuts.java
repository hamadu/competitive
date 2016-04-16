package topcoder.srm6xx.srm687.div1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by hama_du on 4/11/16.
 */
public class AllGraphCuts {
    private static final int INF = 100000000;

    public int[] findGraph(int[] x) {
        int[][] graph = decode(x);

        List<int[]> edges = solve(graph);
        if (edges == null) {
            return new int[]{-1};
        }

        int[] ans = new int[edges.size()];
        int n = graph.length;
        for (int ei = 0; ei < edges.size() ; ei++) {
            int[] ijw = edges.get(ei);
            int i = ijw[0];
            int j = ijw[1];
            int w = ijw[2];
            ans[ei] = w*n*n+i*n+j;
        }
        return ans;
    }

    private List<int[]> solve(int[][] mincuts) {
        int n = mincuts.length;

        // sanity check 1
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (i == j) {
                    if (mincuts[i][j] != 0) {
                        return null;
                    }
                } else {
                    if (mincuts[i][j] != mincuts[j][i]) {
                        return null;
                    }
                }
            }
        }

        UnionFind uf = new UnionFind(n);
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                edges.add(new int[]{i, j, mincuts[i][j]});
            }
        }
        Collections.sort(edges, (e1, e2) -> e2[2] - e1[2]);

        int[][] tree = new int[n][n];
        List<int[]> response = new ArrayList<>();
        for (int i = 0; i < n ; i++) {
            Arrays.fill(tree[i], INF);
            tree[i][i] = 0;
        }
        for (int[] e : edges) {
            if (!uf.issame(e[0], e[1])) {
                response.add(e);
                uf.unite(e[0], e[1]);
                tree[e[0]][e[1]] = tree[e[1]][e[0]] = e[2];
            }
        }

        // sanity check 2
        for (int i = 0; i < n ; i++) {
            int[] path = new int[n];
            dfs(i, -1, INF, tree, path);
            for (int j = 0; j < n ; j++) {
                if (mincuts[i][j] != path[j]) {
                    return null;
                }
            }
        }
        return response;
    }

    private void dfs(int now, int par, int w, int[][] tree, int[] path) {
        path[now] = (w == INF) ? 0 : w;
        for (int i = 0; i < tree.length ; i++) {
            if (i != par && i != now && tree[now][i] < INF) {
                dfs(i, now, Math.min(w, tree[now][i]), tree, path);
            }
        }
    }

    static class UnionFind {
        int[] rank;
        int[] parent;
        int[] cnt;

        public UnionFind(int n) {
            rank = new int[n];
            parent = new int[n];
            cnt = new int[n];
            for (int i = 0; i < n ; i++) {
                parent[i] = i;
                cnt[i] = 1;
            }
        }

        public int find(int a) {
            if (parent[a] == a) {
                return a;
            }
            parent[a] = find(parent[a]);
            return parent[a];
        }

        public void unite(int a, int b) {
            a = find(a);
            b = find(b);
            if (a == b) {
                return;
            }
            if (rank[a] < rank[b]) {
                parent[a] = b;
                cnt[b] += cnt[a];
                cnt[a] = cnt[b];
            } else {
                parent[b] = a;
                cnt[a] += cnt[b];
                cnt[b] = cnt[a];
                if (rank[a] == rank[b]) {
                    rank[a]++;
                }
            }
        }

        public int groupCount(int a) {
            return cnt[find(a)];
        }

        private boolean issame(int a, int b) {
            return find(a) == find(b);
        }
    }

    public int[][] decode(int[] in) {
        int nn = in.length;
        int n = (int)Math.sqrt(nn);
        int[][] g = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n ; j++) {
                g[i][j] = in[i*n+j];
            }
        }
        return g;
    }

    public static void main(String[] args) {
        AllGraphCuts solution = new AllGraphCuts();
        debug(solution.findGraph(new int[]
                {0,2,2, 2,0,2, 2,2,0}
        ));
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
