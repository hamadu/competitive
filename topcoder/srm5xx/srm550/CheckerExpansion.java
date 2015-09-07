package topcoder.srm5xx.srm550;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/30.
 */
public class CheckerExpansion {
    public String[] resultAfter(long t, long x0, long y0, int w, int h) {
        char[][] ret = new char[h][w];
        for (int i = 0; i < h ; i++) {
            for (int j = 0; j < w ; j++) {
                long x = x0 + j;
                long y = y0 + i;
                if ((x + y) % 2 == 1) {
                    ret[i][j] = '.';
                } else {
                    long N = (x + y) / 2;
                    if (N >= t) {
                        ret[i][j] = '.';
                    } else {
                        long K = y;
                        if (N >= K && isOdd(N, K)) {
                            ret[i][j] = (N % 2 == 0) ? 'A' : 'B';
                        } else {
                            ret[i][j] = '.';
                        }
                    }
                }
            }
        }



        String[] ans = new String[h];
        for (int i = 0; i < h ; i++) {
            ans[i] = String.valueOf(ret[h-i-1]);
        }
        return ans;
    }

    // determine nCk is Odd number
    private boolean isOdd(long n, long k) {
        return twos(n) == twos(n-k) + twos(k);
    }

    // how many twos in X!
    private long twos(long X) {
        long sum = 0;
        while (X >= 2) {
            sum += X / 2;
            X /= 2;
        }
        return sum;
    }

    public static void main(String[] args) {
        CheckerExpansion checkerExpansion = new CheckerExpansion();
        checkerExpansion.resultAfter(4, 0 ,0 ,10,10);

    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
