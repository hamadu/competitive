package topcoder.srm6xx.srm658.div1;

/**
 * Created by hama_du on 2016/06/16.
 */
public class OddEvenTree {
    private static final int[] NG = new int[]{-1};

    public int[] getTree(String[] x) {
        int n = x.length;
        int[][] oe = new int[n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                oe[i][j] = x[i].charAt(j) == 'O' ? 1 : 0;
            }
        }
        return solve(oe);
    }

    private int[] solve(int[][] graph) {
        int n = graph.length;
        for (int i = 0; i < n ; i++) {
            if (graph[i][i] == 1) {
                return NG;
            }
        }
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (graph[i][j] != graph[j][i]) {
                    return NG;
                }
            }
        }

        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                for (int k = 0; k < n ; k++) {
                    if ((graph[i][k] + graph[k][j]) % 2 != graph[i][j]) {
                        return NG;
                    }
                }
            }
        }
        for (int i = 0; i < n ; i++) {
            int odddeg = 0;
            for (int j = 0; j < n ; j++) {
                odddeg += graph[i][j];
            }
            if (odddeg == 0) {
                return NG;
            }
        }

        int[] edges = new int[2*n-2];
        int ei = 0;
        int odd = -1;
        for (int i = 1 ; i < n ; i++) {
            if (graph[0][i] == 1) {
                edges[ei++] = 0;
                edges[ei++] = i;
                odd = i;
            }
        }
        for (int i = 1 ; i < n ; i++) {
            if (graph[0][i] == 0) {
                edges[ei++] = odd;
                edges[ei++] = i;
            }
        }
        return edges;
    }
}