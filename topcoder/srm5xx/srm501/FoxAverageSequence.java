package topcoder.srm5xx.srm501;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/04.
 */
public class FoxAverageSequence {
    private static final int MOD = 1000000007;

    public int theCount(int[] seq) {
        int n = seq.length;
        int[][][][] dp = new int[n+1][2][41][1601];
        dp[0][0][0][0] = 1;
        for (int i = 0; i < n ; i++) {
            for (int t = 0; t <= i*40; t++) {
                for (int p = 0; p <= 40; p++) {
                    for (int flg = 0; flg <= 1; flg++) {
                        if (dp[i][flg][p][t] == 0) {
                            continue;
                        }

                        int base = dp[i][flg][p][t];

                        int lb = (flg == 1) ? p : 0;
                        int ub = (i == 0) ? 40 : t / i;
                        for (int d = lb; d <= ub; d++) {
                            if (seq[i] != -1 && seq[i] != d) {
                                continue;
                            }
                            int tflg = (p > d) ? 1 : 0;
                            dp[i+1][tflg][d][t+d] += base;
                            dp[i+1][tflg][d][t+d] -= (dp[i+1][tflg][d][t+d] >= MOD) ? MOD : 0;
                        }
                    }
                }
            }
        }

        long ans = 0;
        for (int t = 0; t <= 1600; t++) {
            for (int p = 0; p <= 40; p++) {
                for (int flg = 0; flg <= 1; flg++) {
                    ans += dp[n][flg][p][t];
                }
            }
        }
        return (int)(ans % MOD);

    }

    public static void main(String[] args) {
        FoxAverageSequence fas = new FoxAverageSequence();
        int[] worst = new int[40];
        Arrays.fill(worst, -1);
        debug(fas.theCount(worst));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
