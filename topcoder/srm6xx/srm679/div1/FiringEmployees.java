package topcoder.srm6xx.srm679.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/01/20.
 */
public class FiringEmployees {

    int[] profit;
    int[][] graph;

    public int fire(int[] manager, int[] salary, int[] productivity) {
        int n = manager.length;
        int[][] edge = new int[n][2];
        for (int i = 0; i < n ; i++) {
            edge[i][0] = manager[i];
            edge[i][1] = i+1;
        }
        graph = buildGraph(n+1, edge);
        profit = new int[n+1];
        for (int i = 0; i < n; i++) {
            profit[i+1] = productivity[i] - salary[i];
        }
        return dfs(0, -1);
    }

    private int dfs(int now, int par) {
        int maxProfit = profit[now];
        for (int to : graph[now]) {
            if (to == par) {
                continue;
            }
            maxProfit += dfs(to, now);
        }
        if (maxProfit < 0) {
            maxProfit = 0;
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        FiringEmployees solution = new FiringEmployees();

        debug(solution.fire(
            new int[]{0,1,2,3},
            new int[]{4,3,2,1},
            new int[]{2,3,4,5}
        ));
        // debug(solution.someMethod());
    }


    static int[][] buildGraph(int n, int[][] edges) {
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        int m = edges.length;
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

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
