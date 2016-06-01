package topcoder.srm6xx.srm663.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/05/30.
 */
public class ChangingChange {
    public int[] countWays(int[] ways, int[] valueRemoved, int[] numRemoved) {
        prec(2000100);

        int q = numRemoved.length;
        int[] ret = new int[q];
        for (int i = 0; i < q ; i++) {
            ret[i] = solve(ways, valueRemoved[i], numRemoved[i]);
        }
        return ret;
    }

    private int solve(int[] ways, int val, int num) {
        long res = 0;
        int n = ways.length;
        for (int idx = n-1, l = 0 ; idx >= 0 ; idx -= val, l++) {
            long pl = comb(num-1+l, l);
            if ((l & 1) == 1) {
                pl = (MOD - pl) % MOD;
            }
            res += pl * ways[idx] % MOD;
        }
        return (int)(res % MOD);
    }

    static final int MOD = 1000000007;

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
