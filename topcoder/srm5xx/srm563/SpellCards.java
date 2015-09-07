package topcoder.srm5xx.srm563;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/03.
 */
public class SpellCards {

    public int maxDamage(int[] l, int[] d) {
        int n = l.length;
        int max = 0;
        for (int i = 0; i < n ; i++) {
            int from = i;
            int to = i+n;
            int[][] dp = new int[2*n+1][n+1];
            for (int j = 0; j <= 2*n; j++) {
                Arrays.fill(dp[j], -1);
            }
            dp[from][0] = 0;
            for (int k = from ; k < to; k++) {
                for (int s = 0; s <= n; s++) {
                    if (dp[k][s] == -1) {
                        continue;
                    }
                    int base = dp[k][s];
                    dp[k+1][s+1] = Math.max(dp[k+1][s+1], base);
                    if (s >= l[k%n]-1) {
                        int ts = s-l[k%n]+1;
                        dp[k+1][ts] = Math.max(dp[k+1][ts], base+d[k%n]);
                    }
                }
            }
            for (int s = 0; s <= n; s++) {
                max = Math.max(max, dp[to][s]);
            }
        }
        return max;
    }
}
