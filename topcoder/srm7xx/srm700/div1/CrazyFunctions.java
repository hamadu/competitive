package topcoder.srm7xx.srm700.div1;

import java.util.Arrays;

/**
 * Created by hamada on 2016/10/13.
 */
public class CrazyFunctions {
    public int count(int n, int k) {
        prec(100000);

        long answer = 1;
        for (int i = 2 ; i <= k ; i++) {
            answer = answer * i;
            answer %= MOD;
        }
        answer *= comb(n, k);
        answer %= MOD;

        long[] dp = new long[n+1];
        dp[k] = 1;
        for (int num = k+1 ; num <= n ; num++) {
            int wantToPlace = num-k;
            for (int leaf = 1 ; leaf <= wantToPlace ; leaf++) {
                int sign = (leaf % 2) * 2 - 1;
                dp[num] += (sign * dp[num-leaf] + MOD) % MOD * comb(wantToPlace, leaf) % MOD * pow(num-leaf, leaf) % MOD;
            }
            dp[num] %= MOD;
        }

        answer *= dp[n];
        answer %= MOD;

        return (int)answer;
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

    public static void main(String[] args) {
        CrazyFunctions functions = new CrazyFunctions();
        debug(functions.count(5000, 2));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
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