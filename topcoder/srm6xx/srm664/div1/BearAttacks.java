package topcoder.srm6xx.srm664.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/03.
 */
public class BearAttacks {
    public int expectedValue(int N, int R0, int A, int B, int M, int LOW, int HIGH) {
        int[][] edges = buildEdges(N, R0, A, B, M, LOW, HIGH);
        int n = edges.length+1;

        long[] inv = new long[n];
        for (int i = 0; i < n; i++) {
            inv[i] = inv(i+1);
        }

        long[] dp = new long[n];
        for (int i = 0; i < n ; i++) {
            dp[i] = inv[i];
        }
        for (int i = n-1; i >= 1; i--) {
            int par = edges[i-1][1];
            dp[par] = (dp[par] + dp[i] * inv[par] % MOD) % MOD;
        }
        
        long ans = 0;

        // self
        for (int i = 0; i < n ; i++) {
            ans += inv[i];
            ans %= MOD;
        }

        // subtree
        for (int i = n-1 ; i >= 1 ; i--) {
            int par = edges[i-1][1];
            ans += dp[i] * inv[par] % MOD;
            ans += (dp[i] * (MOD + dp[par] - dp[i] * inv[par] % MOD)) % MOD;
            ans %= MOD;
        }

        // N!
        for (int i = 1; i <= n; i++) {
            ans *= i;
            ans %= MOD;
        }

        return (int)ans;
    }

    static final long MOD = 1000000007;

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

    static int[][] buildEdges(int n, int r0, long a, long b, long m, long low, long high) {
        long r = r0;
        int BILLION = 1000000000;

        int[][] edges = new int[n-1][2];
        for (int i = 0; i < n-1 ; i++) {
            r = (a * r + b) % m;
            long min = (i*low) / BILLION;
            long max = (i*high) / BILLION;
            edges[i][0] = i+1;
            edges[i][1] = (int)(min + r % (max-min+1));
        }
        return edges;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
