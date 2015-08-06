package topcoder.srm5xx.srm506;

import java.util.*;

/**
 * Created by hama_du on 15/08/05.
 */
public class SlimeXSlimeRancher {
    private static final long INF = (long)1e16;

    public long train(String[] first_slime, String[] second_slime, String[] third_slime) {
        long[] s1 = attrs(first_slime);
        long[] s2 = attrs(second_slime);
        long[] s3 = attrs(third_slime);

        int n = s1.length;
        long[][] attr = new long[n][3];
        for (int i = 0; i < n ; i++) {
            attr[i][0] = s1[i];
            attr[i][1] = s2[i];
            attr[i][2] = s3[i];
        }

        Arrays.sort(s1);
        Arrays.sort(s2);
        Arrays.sort(s3);

        int[][] idToAttr = new int[n][3];
        boolean[][] visited = new boolean[n][3];
        for (int i = 0; i < n ; i++) {
            long x = s1[i];
            long y = s2[i];
            long z = s3[i];
            for (int k = 0; k < 3 ; k++) {
                long val = new long[]{x, y, z}[k];
                for (int j = 0; j < n ; j++) {
                    if (attr[j][k] == val && !visited[j][k]) {
                        idToAttr[i][k] = j;
                        visited[j][k] = true;
                        break;
                    }
                }
            }
        }

        long[][][] dp = new long[n+1][n+1][n+1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                Arrays.fill(dp[i][j], INF);
            }
        }
        dp[0][0][0] = 0;

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                for (int k = 0; k <= n; k++) {
                    if (dp[i][j][k] == INF) {
                        continue;
                    }
                    long base = dp[i][j][k];
                    if (i < n) {
                        int aid = idToAttr[i][0];
                        long y = attr[aid][1];
                        long z = attr[aid][2];
                        long cost = 0;
                        if (j >= 1 && y <= s2[j-1] && k >= 1 && z <= s3[k-1]) {
                            cost = (s2[j-1] - y) + (s3[k-1] - z);
                        }
                        dp[i+1][j][k] = Math.min(dp[i+1][j][k], base+cost);
                    }
                    if (j < n) {
                        int aid = idToAttr[j][1];
                        long cost = 0;
                        long x = attr[aid][0];
                        long z = attr[aid][2];
                        if (i >= 1 && x <= s1[i-1] && k >= 1 && z <= s3[k-1]) {
                            cost = (s1[i-1] - x) + (s3[k-1] - z);
                        }
                        dp[i][j+1][k] = Math.min(dp[i][j+1][k], base+cost);
                    }
                    if (k < n) {
                        int aid = idToAttr[k][2];
                        long cost = 0;
                        long x = attr[aid][0];
                        long y = attr[aid][1];
                        if (i >= 1 && x <= s1[i-1] && j >= 1 && y <= s2[j-1]) {
                            cost = (s1[i-1] - x) + (s2[j-1] - y);
                        }
                        dp[i][j][k+1] = Math.min(dp[i][j][k+1], base+cost);
                    }
                }
            }
        }
        return dp[n][n][n];
    }

    public long[] attrs(String[] slime) {
        StringBuilder line = new StringBuilder();
        for (String s : slime) {
            line.append(s);
        }
        String[] attrs = line.toString().split(" ");
        long[] ret = new long[attrs.length];
        for (int i = 0; i < attrs.length ; i++) {
            ret[i] = Long.valueOf(attrs[i]);
        }
        return ret;
    }
}
