package topcoder.srm6xx.srm654.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/06/20.
 */
public class SuccessiveSubtraction2 {

    public int INF = 100000000;

    public int[] calc(int[] a, int[] p, int[] v) {
        int n = a.length;
        int q = p.length;

        dp = new int[n+1][5];
        int[] ret = new int[q];
        for (int i = 0; i < q ; i++) {
            a[p[i]] = v[i];
            ret[i] = solve(a);
        }
        return ret;
    }

    int[][] dp;

    private int solve(int[] a) {
        int n = a.length;
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], -INF);
        }
        dp[1][0] = a[0];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 5 ; j++) {
                if (dp[i][j] == -INF) {
                    continue;
                }
                int base = dp[i][j];
                if (j % 2 == 0) {
                    if (j != 4) {
                        dp[i+1][j+1] = Math.max(dp[i+1][j+1], base-a[i]);
                    }
                    dp[i+1][j] = Math.max(dp[i+1][j], base-a[i]);
                } else {
                    dp[i+1][j+1] = Math.max(dp[i+1][j+1], base+a[i]);
                    dp[i+1][j] = Math.max(dp[i+1][j], base+a[i]);
                }
            }
        }
        int max = -INF;
        for (int i = 0; i <= 4 ; i += 2) {
            max = Math.max(max, dp[n][i]);
        }
        return max;
    }
}
