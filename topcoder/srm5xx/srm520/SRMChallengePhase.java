package topcoder.srm5xx.srm520;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/16.
 */
public class SRMChallengePhase {
    private static final long MOD = 1000000007;

    public int countWays(String[] codersAttempted, String[] codersChallenged) {
        char[] attempted = build(codersAttempted);
        char[] challenged = build(codersChallenged);
        int n = attempted.length;

        int a = 0;
        int b = 0;
        int x = 0;
        for (int i = 0; i < n ; i++) {
            if (attempted[i] == 'Y') {
                if (challenged[i] == 'Y') {
                    x++;
                } else {
                    a++;
                }
            } else if (challenged[i] == 'Y') {
                b++;
            }
        }

        long[][] dp = new long[a+x+1][x+1];
        dp[0][0] = 1;
        for (int i = 1 ; i <= a+x; i++) {
            for (int j = 0; j <= x ; j++) {
                dp[i][j] += dp[i-1][j];
                if (j >= 1) {
                    dp[i][j] += dp[i-1][j-1] * (i - j) % MOD;
                }
                dp[i][j] %= MOD;
            }
        }

        prec(10000);


        long ret = dp[a+x][x] * comb(a,b) % MOD;
        for (int i = 0; i < a-b ; i++) {
            ret *= (n-1);
            ret %= MOD;
        }
        ret *= _fact[x];
        ret %= MOD;
        ret *= _fact[a];
        ret %= MOD;
        ret *= _fact[b];
        ret %= MOD;

        return (int)ret;
    }

    private char[] build(String[] strs) {
        StringBuilder line = new StringBuilder();
        for (String s : strs) {
            line.append(s);
        }
        return line.toString().toCharArray();
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    public static void main(String[] args) {
        SRMChallengePhase challengePhase = new SRMChallengePhase();
        debug(challengePhase.countWays(new String[]{"YYY"}, new String[]{"NNY"}));
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

    // nもrもそこそこ大きい時。prec(n)を呼んで前計算する。この時、計算量がO(nlogn)かかるが、クエリに対しO(1)で答えられる
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
