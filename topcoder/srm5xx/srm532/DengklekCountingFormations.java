package topcoder.srm5xx.srm532;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/23.
 */
public class DengklekCountingFormations {
    public int numFormations(int N, int M, int K) {
        prec(10000);

        memo = new long[N+1][M+1][K+1][M+1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= M; j++) {
                for (int k = 0; k <= K; k++) {
                    Arrays.fill(memo[i][j][k], -1);
                }
            }
        }
        return (int)dfs(N, M, K, 0);
    }

    static long[][][][] memo;

    static long dfs(int row, int col, int k, int last) {
        if (row == 0) {
            return 1;
        }
        if (col < last) {
            return 0;
        }
        if (memo[row][col][k][last] != -1) {
            return memo[row][col][k][last];
        }
        long ret = 0;
        if (col == 0) {
            ret = (row <= 1) ? 1 : 0;
        } else if (k == 0) {
            ret = 0;
        } else {
            long cold = comb(col, last);
            long z = 1;
            for (int i = 0; i <= row; i++) {
                long ncr = comb(row, i);
                long upper = dfs(i, col-last, k-1, 0);
                long downer = dfs(row-i, col, k, last+1);
                ret += ncr * upper % MOD * downer % MOD * z % MOD;
                z *= cold;
                z %= MOD;
            }
        }
        ret %= MOD;
        memo[row][col][k][last] = ret;
        return ret;
    }

    public static void main(String[] args) {
        DengklekCountingFormations formations = new DengklekCountingFormations();
        debug(formations.numFormations(1, 1, 58));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
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
