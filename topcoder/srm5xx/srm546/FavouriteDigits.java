package topcoder.srm5xx.srm546;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/27.
 */
public class FavouriteDigits {
    public long findNext(long N, int digit1, int count1, int digit2, int count2) {
        char[] c = String.valueOf(N).toCharArray();
        int keta = Math.max(count1+count2, c.length);
        long res = doit(keta, c, digit1, count1, digit2, count2);
        if (res == -1) {
            return doit(keta+1, c, digit1, count1, digit2, count2);
        }
        return res;
    }

    private long doit(int K, char[] c, int d1, int c1, int d2, int c2) {
        long[] res = new long[K];
        long[] grt = new long[K];
        Arrays.fill(res, -1);
        Arrays.fill(grt, -1);
        for (int i = 0; i < c.length ; i++) {
            grt[K-1-i] = c[c.length-1-i]-'0';
        }

        for (int i = 0; i < K ; i++) {
            for (int d = (i == 0) ? 1 : 0 ; d <= 9; d++) {
                res[i] = d;
                if (isOK(res, grt, d1, c1, d2, c2)) {
                    break;
                }
                res[i] = -1;
            }
            if (res[i] == -1) {
                return -1;
            }
        }

        long ret = 0;
        for (int i = 0; i < K ; i++) {
            ret *= 10;
            ret += res[i];
        }
        return ret;
    }

    private boolean isOK(long[] res, long[] base, int d1, int c1, int d2, int c2) {
        long[] tr = res.clone();

        int pos = tr.length;
        for (int i = 0; i < tr.length ; i++) {
            if (tr[i] == -1) {
                pos = i;
                break;
            } else if (tr[i] == d1) {
                c1 = Math.max(0, c1-1);
            } else if (tr[i] == d2) {
                c2 = Math.max(0, c2-1);
            }
        }
        int left = tr.length - pos;
        if (left < c1 + c2) {
            return false;
        }
        int[][] digits = new int[][] {
                {d1, c1},
                {d2, c2},
                {9, left - c1 - c2}
        };
        Arrays.sort(digits, (a, b) -> b[0] - a[0]);
        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < digits[i][1]; j++) {
                tr[pos++] = digits[i][0];
            }
        }
        for (int i = 0; i < tr.length; i++) {
            if (tr[i] < base[i]) {
                return false;
            } else if (tr[i] > base[i]) {
                break;
            }
        }
        return true;
    }

}
