package topcoder.srm6xx.srm658.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/06/16.
 */
public class Mutalisk {
    private static final int INF = 100000;

    public int minimalAttacks(int[] x) {
        int n = x.length;
        int min = -1;
        int max = 100;
        dp = new int[n+1][210][210];
        while (max - min > 1) {
            int med = (max + min) / 2;
            if (isOK(med, x)) {
                max = med;
            } else {
                min = med;
            }
        }
        return max;
    }

    int[][][] dp;

    private boolean isOK(int upto, int[] x) {
        int n = x.length;
        int GETA = upto+10;
        int MAX = GETA*2;
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < MAX ; j++) {
                Arrays.fill(dp[i][j], INF);
            }
        }
        dp[0][GETA][GETA] = 0;

        for (int i = 0; i < n ; i++) {
            for (int l3 = 0 ; l3 < MAX ; l3++) {
                for (int l1 = 0; l1 < MAX; l1++) {
                    if (dp[i][l3][l1] == INF) {
                        continue;
                    }
                    int base = dp[i][l3][l1];
                    for (int nine = 0 ; nine <= 7 ; nine++) {
                        for (int three = 0 ; three < GETA ; three++) {
                            int one = Math.max(0, x[i] - (nine*9+three*3));
                            if (upto - nine < three + one) {
                                continue;
                            }
                            int tl3 = l3+nine-three;
                            int tl1 = l1+nine-one;
                            if (tl3 < 0 || tl1 < 0 || tl3 >= MAX || tl1 >= MAX) {
                                continue;
                            }
                            dp[i+1][tl3][tl1] = Math.min(dp[i+1][tl3][tl1], base+nine);
                            if (one <= 0) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        int best = INF;
        for (int l1 = GETA ; l1 < MAX; l1++) {
            for (int l3 = GETA ; l3 < MAX ; l3++) {
                best = Math.min(best, dp[n][l3][l1]);
            }
        }
        return best <= upto;
    }
}