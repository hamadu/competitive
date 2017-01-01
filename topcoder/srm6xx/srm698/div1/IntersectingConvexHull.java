package topcoder.srm6xx.srm698.div1;

import java.io.*;
import java.util.*;

public class IntersectingConvexHull {
    static final long MOD = 1000000007;

    public int count(int[] x, int[] y) {
        prec(1000);

        int n = x.length;
        long ng = 0;
        for (int i = 0 ; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                long dx1 = x[j] - x[i];
                long dy1 = y[j] - y[i];

                // i -> j
                int left = 0;
                int right = 0;
                for (int k = 0 ; k < n ; k++) {
                    if (k == i || k == j) {
                        continue;
                    }
                    long dx2 = x[k] - x[i];
                    long dy2 = y[k] - y[i];
                    if (dx1 * dy2 - dx2 * dy1 < 0) {
                        left++;
                    } else {
                        right++;
                    }
                }
                long ptnLeft = pow(2, left)-left-1;
                long ptnRight = pow(2, right)-right-1;
                ng += ptnLeft * ptnRight % MOD;
            }
        }

        ng %= MOD;

        long all = 0;
        for (int l = 3 ; l <= n ; l++) {
            for (int r = 3 ; l+r <= n ; r++) {
                all += comb(n, l) * comb(n-l, r) % MOD;
            }
        }
        return (int)((all + 2 * MOD - ng - ng) % MOD);
    }

    static long pow(long a, long x) {
        long res = 1;
        while (x > 0) {
            if (x%2 != 0) {
                res = (res*a)%MOD;
            }
            a = (a*a)%MOD;
            x /= 2;
        }
        return res;
    }

    static long inv(long a) {
        return pow(a, MOD-2)%MOD;
    }

    static long[] _fact;
    static long[] _invfact;

    static long comb(long ln, long lr) {
        int n = (int) ln;
        int r = (int) lr;
        if (n < 0 || r < 0 || r > n) {
            return 0;
        }
        if (r > n/2) {
            r = n-r;
        }
        return (((_fact[n]*_invfact[n-r])%MOD)*_invfact[r])%MOD;
    }

    static void prec(int n) {
        _fact = new long[n+1];
        _invfact = new long[n+1];
        _fact[0] = 1;
        _invfact[0] = 1;
        for (int i = 1; i <= n; i++) {
            _fact[i] = _fact[i-1]*i%MOD;
            _invfact[i] = inv(_fact[i]);
        }
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
