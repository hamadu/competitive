package topcoder.srm5xx.srm564;

/**
 * Created by hama_du on 15/08/11.
 */
public class AlternateColors2 {
    int[][] ord = {
            {1, 2, 0},
            {2, 0, 1},
            {0, 1, 2}
    };

    public long countWays(int n, int k) {
        long[][][] dp = new long[3][8][n+1];
        for (int i = 1; i <= 7 ; i++) {
            dp[2][i][0] = 1;
        }
        for (int i = 0; i < n ; i++) {
            for (int last = 0; last <= 2; last++) {
                for (int p = 0; p < 8 ; p++) {
                    if (dp[last][p][i] == 0) {
                        continue;
                    }
                    long base = dp[last][p][i];
                    int tl = -1;
                    for (int o = 0; o <= 2; o++) {
                        if ((p & (1<<ord[last][o])) >= 1) {
                            tl = ord[last][o];
                            break;
                        }
                    }
                    if (tl != -1) {
                        if (i+1 == k && tl != 0) {
                            continue;
                        }
                        dp[tl][p][i+1] += base;
                        dp[tl][p^(1<<tl)][i+1] += base;
                    }
                }
            }
        }
        return dp[0][0][n] + dp[1][0][n] + dp[2][0][n];
    }
}
