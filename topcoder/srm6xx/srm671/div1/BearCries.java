package topcoder.srm6xx.srm671.div1;

/**
 * Created by hama_du on 2016/05/23.
 */
public class BearCries {
    private static final long MOD = (long) 1e9 + 7;

    public int count(String message) {
        int n = message.length();
        char[] c = message.toCharArray();

        // [pos][zero '-'][one '-']
        long[][][] dp = new long[n+1][n+5][n+5];
        dp[0][0][0] = 1;

        for (int i = 0; i < n ; i++) {
            for (int j = 0; j <= n ; j++) {
                for (int k = 0; k <= n ; k++) {
                    if (dp[i][j][k] == 0) {
                        continue;
                    }
                    long base = dp[i][j][k];
                    if (c[i] == ';') {
                        // open
                        if (j+1 <= n) {
                            dp[i+1][j+1][k] += base;
                            dp[i+1][j+1][k] %= MOD;
                        }

                        // close
                        if (k >= 1) {
                            dp[i+1][j][k-1] += base * k % MOD;
                            dp[i+1][j][k-1] %= MOD;
                        }
                    } else {
                        // strech zero
                        if (j >= 1 && k+1 <= n) {
                            dp[i+1][j-1][k+1] += base * j % MOD;
                            dp[i+1][j-1][k+1] %= MOD;
                        }

                        // strech one
                        dp[i+1][j][k] += base * k % MOD;
                        dp[i+1][j][k] %= MOD;
                    }
                }
            }
        }
        return (int)dp[n][0][0];
    }
}
