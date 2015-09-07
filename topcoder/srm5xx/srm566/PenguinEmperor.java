package topcoder.srm5xx.srm566;

/**
 * Created by hama_du on 15/09/03.
 */
public class PenguinEmperor {
    private static final long MOD = 1000000007;

    public int countJourneys(int n, long days) {
        long[][] dp = new long[n+1][n];
        dp[0][0] = 1;
        for (int d = 0; d < n ; d++) {
            for (int j = 0; j < n ; j++) {
                if (dp[d][j] == 0) {
                    continue;
                }
                long base = dp[d][j];
                int back = (j-(d+1)+2*n)%n;
                int next = (j+(d+1))%n;
                dp[d+1][back] += base;
                if (dp[d+1][back] >= MOD) {
                    dp[d+1][back] -= MOD;
                }
                if (back != next) {
                    dp[d+1][next] += base;
                    if (dp[d+1][next] >= MOD) {
                        dp[d+1][next] -= MOD;
                    }
                }
            }
        }

        long[][] A = new long[n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                A[i][j] = dp[n][(j+i)%n];
            }
        }
        long[][] X = pow(A, days / n, MOD);
        long ret = 0;
        for (int i = 0; i < n ; i++) {
            ret += dp[(int)(days % n)][i] * X[0][i] % MOD;
            ret %= MOD;
        }
        return (int)ret;
    }

    public static long[][] pow(long[][] a, long n, long mod) {
        long i = 1;
        long[][] res = E(a.length);
        long[][] ap = mul(E(a.length), a, mod);
        while (i <= n) {
            if ((n & i) >= 1) {
                res = mul(res, ap, mod);
            }
            i *= 2;
            ap = mul(ap, ap, mod);
        }
        return res;
    }

    public static long[][] E(int n) {
        long[][] a = new long[n][n];
        for (int i = 0 ; i < n ; i++) {
            a[i][i] = 1;
        }
        return a;
    }

    public static long[][] mul(long[][] a, long[][] b, long mod) {
        int n = a.length;
        long[][] c = new long[n][n];
        for (int i = 0 ; i < 1 ; i++) {
            for (int j = 0 ; j < n ; j++) {
                long sum = 0;
                for (int k = 0 ; k < n ; k++) {
                    sum = (sum + a[i][k] * b[k][j]) % mod;
                }
                c[i][j] = sum;
            }
        }
        for (int i = 1 ; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                c[i][j] = c[0][(j-i+n)%n];
            }
        }
        return c;
    }
}
