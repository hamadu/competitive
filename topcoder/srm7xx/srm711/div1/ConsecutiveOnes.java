package topcoder.srm7xx.srm711.div1;

import java.util.Arrays;

public class ConsecutiveOnes {
    public long get(long n, int k) {
        long min = Long.MAX_VALUE;
        for (int len = k ; len <= 50 ; len++) {
            for (int at = 0 ; at <= len-k ; at++) {
                long ho = ((1L<<k)-1)<<at;
                if (ho < n) {
                    for (int l = 50 ; l >= 0 ; l--) {
                        int h = (int)((ho >> l) & 1);
                        int v = (int)((n >> l) & 1);
                        if (h < v) {
                            ho |= 1L<<l;
                            if (ho >= n) {
                                break;
                            }
                        }
                    }
                }
                if (ho >= n) {
                    min = Math.min(min, ho);
                }
            }
        }
        return min;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
