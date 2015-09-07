package topcoder.srm5xx.srm573;

/**
 * Created by hama_du on 15/09/05.
 */
public class WolfPack {
    private static final int INF = 1145141919;

    public int calc(int[] x, int[] y, int m) {
        prec(m+10);

        int n = x.length;

        int[] xy = new int[n];
        int[] yx = new int[n];
        for (int i = 0; i < n ; i++) {
            xy[i] = x[i]+y[i];
            yx[i] = x[i]-y[i];
        }
        long ptnXY = solve(xy, m);
        long ptnYX = solve(yx, m);
        return (int)(ptnXY * ptnYX % MOD);
    }

    private long solve(int[] pos, int m) {
        int n = pos.length;
        int min = INF;
        int max = -INF;

        for (int i = 0; i < n ; i++) {
            min = Math.min(min, pos[i]-m);
            max = Math.max(max, pos[i]+m);
        }
        long ret = 0;
        for (int p = min ; p <= max; p++) {
            long ptn = 1;
            for (int i = 0; i < n ; i++) {
                int diff = Math.abs(pos[i] - p);
                if ((m - diff) % 2 == 1 || diff > m) {
                    ptn = 0;
                    break;
                }
                int free = (m - diff) / 2;
                ptn *= comb(m, free);
                ptn %= MOD;
            }
            ret += ptn;
            if (ret >= MOD) {
                ret -= MOD;
            }
        }
        return ret;
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
