package topcoder.srm5xx.srm557;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/01.
 */
public class XorAndSum {
    public void reverseSort(long[] a, int from, int to) {
        Arrays.sort(a, from, to);
        for (int i = from ; i < to ; i++) {
            int j = to-(i-from+1);
            if (j <= i) {
                break;
            }
            long tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    public long maxSum(long[] number) {
        int n = number.length;
        long[] bits = new long[61];
        int rank = n;
        for (int p = 0 ; p < n ; p++) {
            reverseSort(number, p, n);
            if (number[p] == 0) {
                rank = p;
                break;
            }
            long high = Long.highestOneBit(number[p]);
            bits[p] = high;
            for (int i = p+1 ; i < n ; i++) {
                if ((number[i] & high) >= 1) {
                    number[i] ^= number[p];
                }
            }
        }

        for (int i = rank-1; i >= 0; i--) {
            for (int j = 0 ; j < i ; j++) {
                if ((number[j] & bits[i]) >= 1) {
                   number[j] ^= number[i];
                }
            }
        }
        for (int i = 1 ; i < rank ; i++) {
            number[0] ^= number[i];
        }
        for (int i = 1; i < n ; i++) {
            number[i] ^= number[0];
        }

        long ret = 0;
        for (int i = 0; i < n ; i++) {
            ret += number[i];
        }
        return ret;
    }


    public static void main(String[] args) {
        XorAndSum sum = new XorAndSum();
        sum.maxSum(new long[]{5,6,3});
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
