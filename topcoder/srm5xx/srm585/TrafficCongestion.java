package topcoder.srm5xx.srm585;

/**
 * Created by hama_du on 15/09/16.
 */
public class TrafficCongestion {
    long MOD = 1000000007;

    public int theMinCars(int n) {
        if (n == 0) {
            return 1;
        }
        long[] dp = new long[n+1];
        long[] imos = new long[n+2];
        dp[0] = dp[1] = 1;
        imos[1] = 1;
        imos[2] = 2;
        for (int i = 2 ; i <= n ; i++) {
            dp[i] = (1 + imos[i-1] * 2) % MOD;
            imos[i+1] = (imos[i] + dp[i]) % MOD;
        }
        return (int)dp[n];
    }
}
