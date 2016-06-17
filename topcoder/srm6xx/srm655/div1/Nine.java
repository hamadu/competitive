package topcoder.srm6xx.srm655.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/06/17.
 */
public class Nine {
    public int count(int N, int[] d) {
        long[] foe = new long[1<<N];
        for (int i = 0; i < d.length ; i++) {
            foe[d[i]] *= 10;
            foe[d[i]]++;
            foe[d[i]] %= MOD;
        }

        int max = (int)Math.pow(10, N);
        int[][] dp = new int[2][max];
        dp[0][0] = 1;
        for (int pt = 0 ; pt < (1<<N) ; pt++) {
            int fr = pt % 2;
            int to = 1 - fr;
            Arrays.fill(dp[to], 0);
            int[] arr = new int[N];
            for (int p = 0 ; p < max ; p++) {
                if (dp[fr][p] == 0) {
                    continue;
                }
                int pp = p;
                for (int i = 0; i < N ; i++) {
                    arr[i] = pp % 10;
                    pp /= 10;
                }
                long base = dp[fr][p];
                for (int t = 0 ; t <= 8 ; t++) {
                    long mul = base * (foe[pt] + ((t == 0) ? 1 : 0)) % MOD;
                    int tp = 0;
                    int b = 1;
                    for (int j = 0 ; j < N ; j++) {
                        if ((pt & (1<<j)) >= 1) {
                            tp += b * ((arr[j] + t) % 9);
                        } else {
                            tp += b * arr[j];
                        }
                        b *= 10;
                    }
                    dp[to][tp] += (int)mul;
                    dp[to][tp] %= MOD;
                }
            }
        }
        return dp[0][0];
    }

    static final int MOD = 1000000007;
}
