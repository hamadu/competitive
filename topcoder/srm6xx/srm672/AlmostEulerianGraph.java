package topcoder.srm6xx.srm672;

import java.util.Arrays;

/**
 * Created by hama_du on 15/10/22.
 */
public class AlmostEulerianGraph {
    public static final long MOD = (long)1e9+7;

    public int calculateGraphs(int n) {
        if (n <= 2) {
            return 0;
        }
        prec(2015);
        even = evenGraphOfSizeN(n);
        memo = new long[n+1];
        Arrays.fill(memo, -1);
        memo[0] = 1;
        memo[1] = 0;

        long base = (even[n] + MOD - dfs(n)) % MOD;
        long edge = n * (n - 1) / 2 + 1;

        return (int)(base * edge % MOD);
    }

    public static void main(String[] args) {
        AlmostEulerianGraph graph = new AlmostEulerianGraph();
        debug(graph.calculateGraphs(2000));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }


    public long dfs(int n) {
        if (memo[n] != -1) {
            return memo[n];
        }
        // [connected component of size k that contains vertex 0] and [the other]
        long ans = 0;
        for (int k = 1 ; k <= n-1 ; k++) {
            long comp = comb(n-1, k-1) * (even[k] - dfs(k)) % MOD;
            ans += comp * even[n-k] % MOD;
        }
        ans %= MOD;
        memo[n] = ans;
        return ans;
    }

    long[] memo;
    long[] even;

    public long[] evenGraphOfSizeN(int n) {
        // [size][ok or ng]
        long[][] dp = new long[n+1][2];
        dp[0][0] = 1;
        for (int i = 1 ; i <= n ; i++) {
            // ok
            dp[i][0] = (dp[i-1][0] + dp[i-1][1]) % MOD;

            // ng
            long wrongEdge = (pow(2, i-1) - 1 + MOD) % MOD;
            dp[i][1] = (dp[i-1][0] + dp[i-1][1]) * wrongEdge % MOD;
        }

        long[] ret = new long[n+1];
        for (int i = 0; i <= n; i++) {
            ret[i] = dp[i][0];
        }
        return ret;
    }

    static long pow(long a, long x) {
        long res = 1;
        while (x > 0) {
            if (x % 2 != 0) {
            res = (res * a) % MOD;
            }
            a = (a * a) % MOD;
            x /= 2;
        }
        return res;
    }

    static long inv(long a) {
        return pow(a, MOD - 2) % MOD;
    }

    static long[] _fact;
    static long[] _invfact;
    static long comb(long ln, long lr) {
        int n = (int)ln;
        int r = (int)lr;
        if (n < 0 || r < 0 || r > n) {
            return 0;
        }
        if (r > n / 2) {
            r = n - r;
        }
        return (((_fact[n] * _invfact[n - r]) % MOD) * _invfact[r]) % MOD;
    }

    static void prec(int n) {
        _fact = new long[n + 1];
        _invfact = new long[n + 1];
        _fact[0] = 1;
        _invfact[0] = 1;
        for (int i = 1; i <= n; i++) {
            _fact[i] = _fact[i - 1] * i % MOD;
            _invfact[i] = inv(_fact[i]);
        }
    }

}
