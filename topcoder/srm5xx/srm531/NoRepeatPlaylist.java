package topcoder.srm5xx.srm531;

/**
 * Created by hama_du on 15/08/20.
 */
public class NoRepeatPlaylist {
    private static final long MOD = 1000000007;

    public int numPlaylists(int N, int M, int P) {
        long[][] dp = new long[P+1][N+1];
        dp[0][N] = 1;
        for (int i = 0; i < P ; i++) {
            for (int j = 0; j <= N; j++) {
                if (dp[i][j] == 0) {
                    continue;
                }
                long base = dp[i][j];
                if (j >= 1) {
                    dp[i+1][j-1] += (base * j) % MOD;
                    dp[i+1][j-1] %= MOD;
                }
                int used = N - j;
                if (used > M) {
                    dp[i+1][j] += (base * (used - M)) % MOD;
                    dp[i+1][j] %= MOD;
                }
            }
        }
        return (int)(dp[P][0] % MOD);
    }
}
