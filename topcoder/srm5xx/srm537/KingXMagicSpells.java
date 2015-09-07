package topcoder.srm5xx.srm537;

/**
 * Created by hama_du on 15/08/24.
 */
public class KingXMagicSpells {
    public double expectedNumber(int[] ducks, int[] spellOne, int[] spellTwo, int K) {
        int n = ducks.length;
        double ans = 0;
        for (int q = 0; q <= 31 ; q++) {
            int mask = 1<<q;
            double[][] dp = new double[K+1][n];
            for (int i = 0; i < n ; i++) {
                dp[0][i] = (ducks[i] & mask) >= 1 ? 1.0d : 0;
            }
            for (int k = 0; k < K ; k++) {
                // spell One
                for (int i = 0; i < n ; i++) {
                    if ((spellOne[i] & mask) >= 1) {
                        dp[k+1][i] += 0.5 * (1.0 - dp[k][i]);
                    } else {
                        dp[k+1][i] += 0.5 * dp[k][i];
                    }
                }
                // spell Two
                for (int i = 0; i < n ; i++) {
                    int to = spellTwo[i];
                    dp[k+1][to] += 0.5 * dp[k][i];
                }
            }
            ans += dp[K][0] * mask;
        }
        return ans;
    }
}
