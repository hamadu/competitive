package topcoder.srm5xx.srm518;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/14.
 */
public class Nim {
    public int count(int K, int L) {
        int[] pr = generatePrimes(L);
        int k = K % 2;

        long ret = 0;
        while (k <= pr.length) {
            //
            k += 2;
        }

        return 0;
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

    static long comb(long n, long k) {
        long upper = 1;
        for (long nl = 0; nl < k; nl++) {
            upper = (upper * ((n - nl) % MOD)) % MOD;
        }

        long downer = 1;
        for (int nl = 1; nl <= k; nl++) {
            downer = (downer * nl) % MOD;
        }

        return (upper * inv(downer)) % MOD;
    }

    static long[] _fact;
    static long[] _invfact;

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

    static int[] generatePrimes(int upto) {
        boolean[] isp = new boolean[upto];
        Arrays.fill(isp, true);
        isp[0] = isp[1] = false;

        int pi = 0;
        for (int i = 2; i < upto ; i++) {
            if (isp[i]) {
                pi++;
                for (int j = i * 2; j < upto; j += i) {
                    isp[j] = false;
                }
            }
        }

        int[] ret = new int[pi];
        int ri = 0;
        for (int i = 2 ; i < upto ; i++) {
            if (isp[i]) {
                ret[ri++] = i;
            }
        }
        return ret;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    public static void main(String[] args) {
        Nim nim = new Nim();
        debug(nim.count(1000000000, 50000));
    }
}
