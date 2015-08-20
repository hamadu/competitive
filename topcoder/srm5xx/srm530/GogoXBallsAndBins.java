package topcoder.srm5xx.srm530;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/19.
 */
public class GogoXBallsAndBins {
    private static final long MOD = 1000000009;

    public int solve(int[] T, int moves) {
        int n = T.length;
        int MAX = 10000;
        int GETA = MAX / 2;
        long[][][] dp = new long[2][n+1][MAX];
        dp[0][0][GETA] = 1;
        for (int i = 0; i < n ; i++) {
            int fr = i % 2;
            int to = 1 - fr;
            for (int j = 0; j <= n ; j++) {
                Arrays.fill(dp[to][j], 0);
            }
            for (int j = 0; j <= n ; j++) {
                for (int k = 0; k < MAX ; k++) {
                    if (dp[fr][j][k] == 0) {
                        continue;
                    }
                    long base = dp[fr][j][k];

                    // match same
                    dp[to][j][k] += base;
                    dp[to][j][k] %= MOD;

                    // match upper to j-downer
                    // match downer to j-upper
                    dp[to][j][k] += (2 * j * base) % MOD;
                    dp[to][j][k] %= MOD;

                    // match upper/downer to (j,j)
                    if (j >= 1) {
                        dp[to][j-1][k+2*T[i]] += ((j * j) % MOD * base) % MOD;
                        dp[to][j-1][k+2*T[i]] %= MOD;
                    }

                    // left
                    dp[to][j+1][k-2*T[i]] += base;
                    dp[to][j+1][k-2*T[i]] %= MOD;
                }
            }
        }
        return (int)dp[n%2][0][GETA+moves*2];
    }

    public static void main(String[] args) {
        GogoXBallsAndBins bins = new GogoXBallsAndBins();
        debug(bins.solve(new int[]{1,2,3}, 1));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
