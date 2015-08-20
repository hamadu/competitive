package topcoder.srm5xx.srm520;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/16.
 */
public class SRMIntermissionPhase {
    private static final int MOD = 1000000007;

    public int countWays(int[] points, String[] description) {
        int[][] prec = new int[8][200010];
        int[] imos = new int[200010];
        for (int p = 0; p <= 7; p++) {
            prec[p][0] = 1;
            for (int c = 0; c < 3 ; c++) {
                if ((p & (1<<c)) == 0) {
                    continue;
                }
                int il = imos.length-1;
                for (int l = 0; l < il ; l++) {
                    imos[l+1] = imos[l] + prec[p][l];
                    imos[l+1] -= (imos[l+1] >= MOD) ? MOD : 0;
                }

                int max = prec[p].length;
                for (int t = 0 ; t < max ; t++) {
                    int to = t;
                    int fr = Math.max(0, t - points[c]);
                    prec[p][t] = imos[to] - imos[fr] + MOD;
                    prec[p][t] -= (prec[p][t] >= MOD) ? MOD : 0;
                }
            }
        }

        int n = description.length;
        int[][] dp = new int[2][200010];
        dp[0][dp[0].length-1] = 1;
        for (int i = 0; i < n ; i++) {
            int flg = 0;
            for (int j = 0; j < 3 ; j++) {
                if (description[i].charAt(j) == 'Y') {
                    flg |= 1<<j;
                }
            }

            int fr = i % 2;
            int to = 1 - fr;
            Arrays.fill(dp[to], 0);
            long sum = 0;
            for (int ts = dp[0].length-1 ; ts >= 0 ; ts--) {
                long tv = sum * prec[flg][ts] % MOD;
                dp[to][ts] = (int)tv;
                sum = (sum + dp[fr][ts]) % MOD;
            }
        }

        long ret = 0;
        for (int i = 0; i < dp[0].length ; i++) {
            ret += dp[n%2][i];
            ret %= MOD;
        }
        return (int)ret;
    }
}
