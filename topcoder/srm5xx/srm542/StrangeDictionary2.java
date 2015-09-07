package topcoder.srm5xx.srm542;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/25.
 */
public class StrangeDictionary2 {
    public double[] getProbabilities(String[] words) {
        int n = words.length;
        int m = words[0].length();
        char[][] table = new char[n][];
        for (int i = 0; i < n ; i++) {
            table[i] = words[i].toCharArray();
        }

        long[] stat = new long[1<<n];
        for (int i = 1; i < (1<<n) ; i++) {
            int cnt = Integer.bitCount(i);
            int[] ord = new int[cnt];
            int oi = 0;
            for (int j = 0; j < n ; j++) {
                if ((i & (1<<j)) >= 1) {
                    ord[oi++] = j;
                }
            }
            for (int j = 0; j < m ; j++) {
                char c = table[ord[0]][j];
                boolean isSame = true;
                for (int k = 1 ; k < cnt; k++) {
                    if (table[ord[k]][j] != c) {
                        isSame = false;
                        break;
                    }
                }
                if (isSame) {
                    stat[i] |= 1L << j;
                }
            }
        }

        double[] dp = new double[1<<n];
        dp[(1<<n)-1] = 1.0d;

        for (int d = (1<<n)-1 ; d >= 1 ; d--) {
            if (Integer.bitCount(d) == 1) {
                continue;
            }
            long column = stat[d];
            int bo = 0;
            for (int ch = 0; ch < m ; ch++) {
                if ((column & (1L << ch)) == 0) {
                    bo++;
                }
            }

            for (int ch = 0; ch < m ; ch++) {
                if ((column & (1L<<ch)) == 0) {
                    int best = 'z';
                    for (int i = 0; i < n ; i++) {
                        if ((d & (1<<i)) >= 1) {
                            best = Math.min(best, table[i][ch]);
                        }
                    }
                    int td = 0;
                    for (int i = 0; i < n ; i++) {
                        if ((d & (1<<i)) >= 1 && table[i][ch] == best) {
                            td |= 1<<i;
                        }
                    }
                    dp[td] += dp[d] / bo;
                }
            }
        }

        double[] ret = new double[n];
        for (int i = 0; i < n ; i++) {
            ret[i] = dp[1<<i];
        }
        return ret;
    }

    public static void main(String[] args) {
        StrangeDictionary2 dictionary2 = new StrangeDictionary2();
        debug(dictionary2.getProbabilities(new String[]{"ab", "ba"}));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
