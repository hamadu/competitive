package topcoder.srm5xx.srm523;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/17.
 */
public class BricksN {
    private static final long MOD = 1000000007;

    public int countStructures(int w, int h, int k) {
        memo = new long[w+1][h+1];
        for (int i = 0; i <= w; i++) {
            Arrays.fill(memo[i], -1);
        }
        K = k;
        prec = new long[w+1];
        prec[0] = 1;
        for (int i = 1 ; i <= w; i++) {
            for (int u = 1 ; u <= k; u++) {
                if (i-u >= 0) {
                    prec[i] += prec[i-u];
                    prec[i] %= MOD;
                }
            }
        }
        return (int)dfs(w, h);
    }

    public int K;
    public long[][] memo;
    public long[] prec;

    public long dfs(int L, int H) {
        if (H == 0) {
            return 1;
        }
        if (memo[L][H] != -1) {
            return memo[L][H];
        }
        long ptn = 0;
        long[] dp = new long[L+2];
        dp[0] = 1;
        for (int i = 0; i <= L ; i++) {
            long base = dp[i];
            for (int k = 0; k <= L ; k++) {
                int ti = i+k+1;
                if (ti >= dp.length) {
                    break;
                }
                long add = (base * prec[k]) % MOD;
                if (k >= 1) {
                    add *= dfs(k, H-1);
                    add %= MOD;
                }
                dp[ti] += add;
                dp[ti] %= MOD;
            }
        }
        memo[L][H] = dp[L+1] % MOD;
        return memo[L][H];
    }
}
