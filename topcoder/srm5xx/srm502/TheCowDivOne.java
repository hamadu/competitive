package topcoder.srm5xx.srm502;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/04.
 */
public class TheCowDivOne {
    private static final long MOD = 1000000007;

    public int find(int n, int k) {
        N = n;
        K = k;

        int d = gcd(n, k);
        long ret = doit(n, k, d, 1) * inv(n / d) % MOD;
        for (int i = 1 ; i <= k; i++) {
            ret *= inv(i);
            ret %= MOD;
        }

        return (int)ret;
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

    static long N;
    static int K;

    static long doit(int N, int K, int D, int A) {
        long[][][] dp = new long[2][D+1][D+1];
        for (int d = 1 ; d <= D; d++) {
            if (D % d == 0) {
                for (int a = 0; a < d ; a++) {
                    dp[1][d][a] = N / (d / gcd(d, a));
                }
            }
        }
        for (int k = 2 ; k <= K ; k++) {
            int prev = (k-1) % 2;
            int cur = 1 - prev;
            for (int d = 1 ; d <= D; d++) {
                if (D % d == 0) {
                    for (int a = 0; a < d ; a++) {
                        int g2 = gcd(d, a);
                        long mul = (dp[prev][g2][1%g2] * (N / d * g2)) % MOD;
                        long sub = (dp[prev][d][(a+1)%d] * (k-1)) % MOD;
                        dp[cur][d][a] = mul - sub + MOD;
                        dp[cur][d][a] %= MOD;
                    }
                }
            }
        }
        return dp[K%2][D][A%D] % MOD;
    }

    static int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a%b);
    }

    public static void main(String[] args) {
        TheCowDivOne cow = new TheCowDivOne();
        debug(cow.find(7, 4));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
