package topcoder.srm6xx.srm693.div1;

/**
 * Created by hama_du
 */
import java.io.*;
import java.util.*;

public class BiconnectedDiv1 {
    private static final int INF = (int)1e8;

    public int minimize(int[] w1, int[] w2) {
        int n = w1.length+1;
        int[][] dp = new int[n][2];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[0][0] = 0;
        for (int i = 0; i < n-1 ; i++) {
            for (int j = 0; j <= 1 ; j++) {
                if (dp[i][j] == INF) {
                    continue;
                }
                int now = dp[i][j];
                if (j == 0) {
                    if (i < w2.length) {
                        dp[i+1][1] = Math.min(dp[i+1][1], now+w1[i]+w2[i]);
                    }
                } else {
                    dp[i+1][0] = Math.min(dp[i+1][0], now + w1[i]);
                    if (i < w2.length) {
                        dp[i+1][1] = Math.min(dp[i+1][1], now+w2[i]);
                    }
                }
            }
        }
        return dp[n-1][0];
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
