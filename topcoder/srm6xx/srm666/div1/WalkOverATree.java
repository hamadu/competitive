package topcoder.srm6xx.srm666.div1;

/**
 * Created by hama_du on 2016/05/27.
 */
public class WalkOverATree {
    public int maxNodesVisited(int[] parent, int L) {
        int n = parent.length+1;
        int[][] edge = new int[n-1][2];
        for (int i = 0; i < n-1 ; i++) {
            edge[i][0] = i+1;
            edge[i][1] = parent[i];
        }
        graph = buildGraph(n, edge);
        depth = new int[n];
        dfs(0, -1, 0);

        int maxD = 0;
        for (int i = 0; i < n ; i++) {
            if (maxD < depth[i] ) {
                maxD = depth[i];
            }
        }
        int left = n - (maxD + 1);
        int ans = 1;
        int one = Math.min(L, maxD);
        ans += one;
        L -= one;
        int two = Math.min(L / 2, left);
        ans += two;
        return ans;
    }

    int[][] graph;
    int[] depth;
    int dfs(int now, int par, int d) {
        depth[now] = d;
        for (int to : graph[now]) {
            if (to == par) {
                continue;
            }
            dfs(to, now, d+1);
        }
        return 0;
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
}
