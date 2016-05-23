package topcoder.srm6xx.srm670.div1;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 2016/05/23.
 */
public class Treestrat {
    public int roundcnt(int[] tree, int[] A, int[] B) {
        int n = tree.length + 1;
        int[][] edges = new int[n-1][2];
        for (int i = 0; i < n-1 ; i++) {
            edges[i][0] = i+1;
            edges[i][1] = tree[i];
        }
        int[][] graph = buildGraph(n, edges);
        int min = Integer.MAX_VALUE;
        int an = A.length;
        int bn = B.length;

        int[] btbl = new int[n];
        Arrays.fill(btbl, n);
        for (int i = 0 ; i < bn ; i++) {
            int[] bt = go(graph, B[i]);
            for (int j = 0; j < n ; j++) {
                btbl[j] = Math.min(btbl[j], bt[j]);
            }
        }

        for (int i = 0; i < an ; i++) {
            int best = dfs(A[i], -1, 0, btbl, graph);
            min = Math.min(min, best);
        }
        return min;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }


    private int dfs(int now, int par, int dep, int[] btbl, int[][] graph) {
        int ret = btbl[now];
        if (btbl[now] > dep) {
            for (int to : graph[now]) {
                if (to == par) {
                    continue;
                }
                ret = Math.max(ret, dfs(to, now, dep+1, btbl, graph));
            }
        }
        return ret;
    }

    private int[] go(int[][] graph, int from) {
        int n = graph.length;
        int[] ret = new int[n];
        Arrays.fill(ret, n);
        ret[from] = 0;
        Queue<Integer> q = new ArrayBlockingQueue<>(4*n);
        q.add(from);
        q.add(0);
        while (q.size() >= 1) {
            int now = q.poll();
            int tim = q.poll();
            for (int to : graph[now]) {
                if (ret[to] > tim+1) {
                    ret[to] = tim+1;
                    q.add(to);
                    q.add(tim+1);
                }
            }
        }
        return ret;
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
