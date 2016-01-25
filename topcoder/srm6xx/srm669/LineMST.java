package topcoder.srm6xx.srm669;

import java.util.Arrays;

/**
 * Created by hama_du on 15/10/04.
 */
public class LineMST {
    private static final long MOD = 1000000007;

    public int count(int N, int L) {
        long perm = 1;
        for (int i = 3 ; i <= N ; i++) {
            perm *= i;
            perm %= MOD;
        }

        long[][] ncr = new long[300][300];
        for (int i = 0; i < ncr.length ; i++) {
            ncr[i][0] = ncr[i][i] = 1;
            for (int j = 1 ; j < i ; j++) {
                ncr[i][j] = (ncr[i-1][j-1] + ncr[i-1][j]) % MOD;
            }
        }

        long[][] dp = new long[N][L+1];
        dp[0][L] = 1;
        for (int i = 0; i < N-1 ; i++) {
            for (int j = L ; j >= 1; j--) {
                if (dp[i][j] == 0) {
                    continue;
                }
                long base = dp[i][j];
                long x = 0;
                for (int t = i; t <= N-1; t++) {
                    int len = t-i;
                    long ptn = ncr[N-1-i][len];
                    long edge = pow((L-j)+1, x);
                    dp[t][j-1] += base * ptn % MOD * edge % MOD;
                    dp[t][j-1] %= MOD;
                    x += Math.max(0, N-2-t);
                }
            }
        }


        long sum = 0;
        for (int i = 0 ; i <= L; i++) {
            sum += dp[N-1][i];
        }
        sum %= MOD;
        return (int)((sum * perm) % MOD);
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

    public static void main(String[] args) {
        LineMST mst = new LineMST();
        debug(mst.count(4, 1));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    // 8,41
    // 655468587

    // 200,200
    // 152699064
}
