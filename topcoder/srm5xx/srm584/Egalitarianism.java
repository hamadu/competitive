package topcoder.srm5xx.srm584;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/15.
 */
public class Egalitarianism {
    private static final int INF = 100000000;

    public int maxDifference(String[] isFriend, int d) {
        int n = isFriend.length;
        int[][] graph = new int[n][n];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(graph[i], INF);
        }
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (i == j) {
                    graph[i][i] = 0;
                } else {
                    graph[i][j] = (isFriend[i].charAt(j) == 'Y') ? 1 : INF;
                }
            }
        }
        for (int k = 0; k < n ; k++) {
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < n ; j++) {
                    graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
                }
            }
        }

        int max = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                max = Math.max(max, graph[i][j]);
            }
        }
        if (max >= INF) {
            return -1;
        }
        return max * d;
    }
}
