package topcoder.srm6xx.srm656.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/06/17.
 */
public class PermutationCounts {
    public int countPermutations(int N, int[] pos) {
        prec(1000010);

        int n = pos.length+1;
        Arrays.sort(pos);
        int[] g = new int[n];
        {
            int last = 0;
            for (int i = 0; i < n-1; i++) {
                g[i] = pos[i]-last;
                last = pos[i];
            }
            g[n-1] = N-last;
        }

        int[] imos = new int[n+1];
        for (int i = 0; i < n ; i++) {
            imos[i+1] = imos[i] + g[i];
        }



        int[][][] dp = new int[2][n+1][n+1];
        dp[0][0][0] = 1;
        for (int last = 0; last <= n ; last++) {
            for (int now = last ; now <= n ; now++) {
                for (int flg = 0 ; flg <= 1 ; flg++) {
                    if (dp[flg][last][now] == 0) {
                        continue;
                    }
                    long base = dp[flg][last][now];

                    // step one together
                    if (last == now && last < n) {
                        long pl = base * comb(N - imos[last], g[now]) % MOD;
                        dp[flg][last+1][now+1] += pl;
                        dp[flg][last+1][now+1] %= MOD;
                    }

                    // step one
                    if (now < n) {
                        dp[1-flg][last][now+1] += base;
                        dp[1-flg][last][now+1] %= MOD;
                    }

                    // connect to now
                    if (last < now && now < n) {
                        long pl = base * comb(N - imos[last], imos[now+1] - imos[last]) % MOD;
                        dp[flg][now+1][now+1] += pl;
                        dp[flg][now+1][now+1] %= MOD;
                    }
                }
            }
        }

        // debug(dp[0][n][n], dp[1][n][n]);
        return (dp[0][n][n] - dp[1][n][n] + MOD) % MOD;
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
