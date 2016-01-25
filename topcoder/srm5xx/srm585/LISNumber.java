package topcoder.srm5xx.srm585;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/16.
 */
public class LISNumber {
    long MOD = 1000000007;

    public int count(int[] cardsnum, int K) {
        int[][] ncr = new int[1500][1500];
        for (int i = 0; i < 1500 ; i++) {
            ncr[i][0] = ncr[i][i] = 1;
            for (int j = 1; j < i; j++) {
                ncr[i][j] = (int)((ncr[i-1][j] + ncr[i-1][j-1]) % MOD);
            }
        }

        int n = cardsnum.length;
        long[][] dp = new long[n+1][1500];
        dp[0][0] = 1;
        int total = 0;
        for (int i = 0; i < n ; i++) {
            if (i == 0) {
                total += cardsnum[0];
                dp[i+1][total] = 1;
                continue;
            }
            for (int j = 0; j <= K; j++) {
                if (dp[i][j] == 0) {
                    continue;
                }
                long base = dp[i][j];
                for (int a = 0; a <= cardsnum[i]; a++) {
                    for (int ua = 0; ua <= j; ua++) {
                        int fa = a - ua;
                        if (fa < 0) {
                            break;
                        }
                        long ptnA = (ua >= 1) ? ncr[fa+ua-1][ua-1] : 1;
                        ptnA *= ncr[j][ua];
                        ptnA %= MOD;
                        ptnA *= base;
                        ptnA %= MOD;
                        if (ua == 0) {
                            if (a != 0) {
                                // no way!
                                continue;
                            }
                        }

                        int b = cardsnum[i]-a;
                        int tj = j+cardsnum[i]-ua;
                        int left = total+1-j;
                        long ptnB = ncr[b+left-1][left-1];
                        long add = ptnA * ptnB % MOD;
                        if (tj <= K) {
                            dp[i+1][tj] += add;
                            dp[i+1][tj] %= MOD;
                        }
                    }
                }
            }
            total += cardsnum[i];
        }
        return (int)dp[n][K];
    }

    public static void main(String[] args) {
        LISNumber lisNumber = new LISNumber();
        debug(lisNumber.count(new int[]{31, 4, 15, 9, 26, 5, 35, 8, 9, 7, 9, 32, 3, 8, 4, 6, 26}, 58));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }


    // [---]-3 [---]-3 [---]-3 [---]-3
    //

}
