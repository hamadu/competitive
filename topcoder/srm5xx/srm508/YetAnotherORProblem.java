package topcoder.srm5xx.srm508;

/**
 * Created by hama_du on 15/08/05.
 */
public class YetAnotherORProblem {
    private static final long MOD = 1000000009;

    public int countSequences(long[] R) {
        int n = R.length;
        long[][] dp = new long[62][1<<n];
        dp[61][0] = 1;
        for (int i = 61 ; i >= 1 ; i--) {
            for (int ptn = 0; ptn < 1<<n ; ptn++) {
                if (dp[i][ptn] == 0) {
                    continue;
                }
                long base = dp[i][ptn];
                long bit = 1L<<(i-1);

                // j < n : pick one
                // j = n : pick none
                for (int j = 0; j <= n ; j++) {
                    int tptn = ptn;
                    for (int k = 0; k < n ; k++) {
                        if (j != k && (ptn & (1<<k)) == 0) {
                            if ((R[k] & bit) >= 1) {
                                tptn |= 1<<k;
                            }
                        }
                    }
                    if (j < n) {
                        if ((R[j] & bit) >= 1 || (ptn & (1<<j)) >= 1) {
                            dp[i-1][tptn] += base;
                            dp[i-1][tptn] %= MOD;
                        }
                    } else {
                        dp[i-1][tptn] += base;
                        dp[i-1][tptn] %= MOD;
                    }
                }
            }
        }

        long res = 0;
        for (int ptn = 0; ptn < 1<<n ; ptn++) {
            res += dp[0][ptn];
        }
        return (int)(res % MOD);
    }
}
