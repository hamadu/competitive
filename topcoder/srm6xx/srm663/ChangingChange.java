package topcoder.srm6xx.srm663;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/12.
 */
public class ChangingChange {

    public int[] countWays(int[] ways, int[] valueRemoved, int[] numRemoved) {
        prec(2000010);

        int q = valueRemoved.length;
        int[] res = new int[q];
        for (int i = 0; i < q ; i++) {
            res[i] = (int)solve(ways, valueRemoved[i], numRemoved[i]);
        }
        return res;
    }

    private long solve(int[] ways, int v, int c) {
        int now = ways.length-1;
        long ret = 0;
        int cur = 0;
        while (now >= 0) {
            long pl = (ways[now] * comb(c-1+cur, cur)) % MOD;
            if ((cur & 1) == 0) {
                ret += pl;
            } else {
                ret += MOD - pl;
            }
            now -= v;
            cur++;
        }
        return (int)(ret % MOD);
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

    public static void main(String[] args) {
        ChangingChange cc = new ChangingChange();
        debug(cc.countWays(
                new int[]{1, 2, 3, 6, 9, 14},
                new int[]{1, 2, 3, 4, 5, 1, 2, 3, 4, 5},
                new int[]{1, 1, 1, 1, 1, 2, 2, 2, 2, 2}
        ));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
