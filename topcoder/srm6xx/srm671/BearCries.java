package topcoder.srm6xx.srm671;

/**
 * Created by hama_du on 15/10/15.
 */
public class BearCries {
    private static final long MOD = 1000000007;

    public int count(String message) {
        char[] ch = message.toCharArray();
        int n = ch.length;
        int cnt = 0;
        for (int i = 0; i < n ; i++) {
            if (ch[i] == ';') {
                cnt++;
            }
        }
        if (cnt % 2 == 1) {
            return 0;
        }

        cnt /= 2;

        long[][][] dp = new long[n+1][cnt+5][cnt+5];
        dp[0][0][0] = 1;
        for (int i = 0; i < n ; i++) {
            for (int d = 0; d <= cnt ; d++) {
                for (int u = 0; u <= cnt ; u++) {
                    if (dp[i][d][u] == 0) {
                        continue;
                    }
                    long base = dp[i][d][u];
                    if (ch[i] == ';') {
                        // open
                        dp[i+1][d][u+1] += base;
                        dp[i+1][d][u+1] %= MOD;

                        // close
                        if (d >= 1) {
                            dp[i+1][d-1][u] += (base * d) % MOD;
                            dp[i+1][d-1][u] %= MOD;
                        }
                    } else {
                        // _(under)

                        // give to not yet have
                        if (u >= 1) {
                            dp[i+1][d+1][u-1] += (base * u) % MOD;
                            dp[i+1][d+1][u-1] %= MOD;
                        }

                        // give to already have
                        if (d >= 1) {
                            dp[i+1][d][u] += (base * d) % MOD;
                            dp[i+1][d][u] %= MOD;
                        }
                    }
                }
            }
        }
        return (int)dp[n][0][0];
    }
}
