package topcoder.srm5xx.srm551;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/30.
 */
public class ColorfulWolves {
    private static final int INF = 114514;

    public int getmin(String[] colormap) {
        int n = colormap.length;
        int[][] cost = new int[n][n];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(cost[i], INF);
            int del = 0;
            for (int j = 0; j < n ; j++) {
                if (colormap[i].charAt(j) == 'Y') {
                    cost[i][j] = del;
                    del++;
                }
            }
        }

        for (int k = 0; k < n ; k++) {
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < n ; j++) {
                    cost[i][j] = Math.min(cost[i][j], cost[i][k] + cost[k][j]);
                }
            }
        }
        return cost[0][n-1] >= INF ? -1 : cost[0][n-1];
    }
}
