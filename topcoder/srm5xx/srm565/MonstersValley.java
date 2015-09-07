package topcoder.srm5xx.srm565;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/26.
 */
public class MonstersValley {
    public int minimumPrice(long[] dread, int[] price) {
        int n = price.length;
        long[][] dp = new long[n+1][200];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], -1);
        }
        dp[0][0] = 0;
        for (int i = 0; i < n ; i++) {
            for (int p = 0; p < 200 ; p++) {
                long party = dp[i][p];
                if (party <= -1) {
                    continue;
                }

                if (dread[i] <= party) {
                    dp[i+1][p] = Math.max(dp[i+1][p], party);
                }
                dp[i+1][p+price[i]] = Math.max(dp[i+1][p+price[i]], party + dread[i]);
            }
        }

        for (int p = 0; p < 200 ; p++) {
            if (dp[n][p] >= 0) {
                return p;
            }
        }
        return 0;
    }
}
