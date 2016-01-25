package topcoder.srm6xx.srm672;

/**
 * Created by hama_du on 15/10/22.
 */
public class AlmostEulerianGraph {
    public static final long MOD = 1000000007;

    public int calculateGraphs(int n) {
        long[][] dp = new long[n+1][n+1];
        dp[1][0] = 1;
        for (int i = 2 ; i <= n; i++) {
            for (int k = 0; k <= i; k++) {
                dp[i][k] = 0;


            }
        }
        return 0;
    }
}
