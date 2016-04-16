package topcoder.srm6xx.srm687.div1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hama_du on 4/11/16.
 */
public class AllGraphCuts {
    public int[] findGraph(int[] x) {
        int[][] graph = decode(x);

        List<Integer> edges = solve(graph);
        if (edges == null) {
            return new int[]{-1};
        }

        debug(edges);

        int[] ans = new int[edges.size() / 3];
        int n = graph.length;
        for (int ei = 0; ei < edges.size() ; ei += 3) {
            int i = edges.get(ei);
            int j = edges.get(ei+1);
            int w = edges.get(ei+2);
            ans[ei/3] = w*n*n+i*n+j;
        }
        return ans;
    }

    private List<Integer> solve(int[][] graph) {
        int n = graph.length;

        // sanity check
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (i == j) {
                    if (graph[i][j] != 0) {
                        return null;
                    }
                } else {
                    if (graph[i][j] != graph[j][i]) {
                        return null;
                    }
                }
            }
        }

        // sanity check 2
        for (int i = 0; i < n ; i++) {
            for (int j = i+1; j < n ; j++) {
                for (int k = j+1; k < n ; k++) {
                    if (i == j || j == k || k == i) {
                        continue;
                    }
                    int no = 0;
                    if (Math.min(graph[i][k], graph[k][j]) != graph[i][j]) {
                        no++;
                    }
                    if (Math.min(graph[i][j], graph[j][k]) != graph[i][k]) {
                        no++;
                    }
                    if (Math.min(graph[j][i], graph[i][k]) != graph[j][k]) {
                        no++;
                    }
                    if (no == 3) {
                        return null;
                    }
                }
            }
        }


        List<Integer> response = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        int minI = -1;
        int minJ = -1;
        for (int i = 0 ; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                if (min > graph[i][j]) {
                    min = graph[i][j];
                    minI = i;
                    minJ = j;
                }
            }
        }

        response.add(minI);
        response.add(minJ);
        response.add(min);
        for (int x = 0; x < n ; x++) {
            if (x != minI && x != minJ) {
                if (graph[x][minI] > min && graph[x][minJ] > min) {
                    return null;
                } else if (graph[x][minJ] > min) {
                    response.add(x);
                    response.add(minJ);
                    response.add(graph[x][minJ]);
                } else {
                    response.add(x);
                    response.add(minI);
                    response.add(graph[x][minI]);
                }
            }
        }
        return response;
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
