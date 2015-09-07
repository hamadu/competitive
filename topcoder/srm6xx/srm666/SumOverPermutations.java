package topcoder.srm6xx.srm666;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/26.
 */
public class SumOverPermutations {
    private static final int MOD = 1000000007;

    public int findSum(int n) {
        int[][] ncr = new int[n+1][n+1];
        for (int i = 0; i < n ; i++) {
            ncr[i][0] = ncr[i][i] = 1;
            for (int j = 1 ; j <= i-1 ; j++) {
                ncr[i][j] = (ncr[i-1][j-1] + ncr[i-1][j]);
                if (ncr[i][j] >= MOD) {
                    ncr[i][j] -= MOD;
                }
            }
        }

        long[] dp = new long[n+1];
        dp[0] = 1;
        dp[1] = n;
        for (int range = 2 ; range <= n ; range++) {
            long ret = 0;
            long edge = 2L * (n-1) * dp[range-1] % MOD;
            for (int c = 1 ; c <= (range-2+1)/2 ; c++) {
                long mul = (c == range - c - 1) ? 1 : 2;
                long add =  mul * (n-2) * dp[c] % MOD * dp[range-c-1] % MOD;
                add *= ncr[range-1][c];
                add %= MOD;
                ret += add;
                if (ret >= MOD) {
                    ret -= MOD;
                }
            }
            ret += edge;
            if (ret >= MOD) {
                ret -= MOD;
            }
            dp[range] = ret;
        }
        return (int)dp[n];
    }

    public static void main(String[] args) {
        SumOverPermutations permutations = new SumOverPermutations();
        debug(permutations.findSum(3995));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
