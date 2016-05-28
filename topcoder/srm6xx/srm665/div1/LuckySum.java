package topcoder.srm6xx.srm665.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/11.
 */
public class LuckySum {

    public long doit(char[] digits) {
        int n = digits.length;
        long[][][] dp = new long[n+1][2][2];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= 1; j++) {
                Arrays.fill(dp[i][j], Long.MAX_VALUE);
            }
        }
        long[] pw10 = new long[n+1];
        pw10[0] = 1;
        for (int i = 1; i < n ; i++) {
            pw10[i] = pw10[i-1] * 10;
        }

        dp[0][0][0] = 0;
        for (int i = 0; i < n ; i++) {
            for (int c = 0; c <= 1; c++) {
                for (int k = 0; k <= 1; k++) {
                    if (dp[i][c][k] == Long.MAX_VALUE) {
                        continue;
                    }
                    long base = dp[i][c][k];
                    for (int c1 : new int[]{0, 4, 7}) {
                        if (i >= 1 && c1 == 0) {
                            continue;
                        }
                        if (n == 1 && c1 == 0) {
                            continue;
                        }
                        for (int c2 : new int[]{0, 4, 7}) {
                            if (k == 1 && c2 == 0) {
                                continue;
                            }
                            for (int tc = 0; tc <= 1 ; tc++) {
                                int tv = c1 + c2 + tc;
                                if (tv >= 10 && c == 0) {
                                    continue;
                                } else if (tv <= 9 && c == 1) {
                                    continue;
                                }
                                int td = tv % 10;
                                if (digits[i] == '?' || td == (digits[i] - '0')) {
                                    int tk = (k == 1 || c2 != 0) ? 1 : 0;
                                    if (i == 0 && td == 0) {
                                        continue;
                                    }
                                    dp[i+1][tc][tk] = Math.min(dp[i+1][tc][tk], base + pw10[n-i-1] * (c1 + c2));
                                }
                            }
                        }
                    }
                }
            }
        }
        return dp[n][0][1];
    }

    public long construct(String note) {
        char[] c = note.toCharArray();
        long f = doit(c);
        return f == Long.MAX_VALUE ? -1 : f;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    public static void main(String[] args) {
        LuckySum ls = new LuckySum();
        debug(ls.construct("??"));
    }

}
