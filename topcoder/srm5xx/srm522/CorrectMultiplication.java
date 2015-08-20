package topcoder.srm5xx.srm522;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/17.
 */
public class CorrectMultiplication {
    public long getMinimum(int a, int b, int c) {
        return Math.min(doit(a, b, c), doit(b, a, c));
    }

    public long doit(long a, long b, long c) {
        long limit = Math.min(a-1 + Math.abs(c - b), b-1 + Math.abs(c - a));
        for (long A = 1 ; A <= 1000000 ; A++) {
            for (int d = -1 ; d <= 1; d++) {
                long B = c / A + d;
                if (B >= 1) {
                    limit = Math.min(limit, Math.abs(a - A) + Math.abs(B - b) + Math.abs(c - A * B));
                }
            }
        }
        return limit;
    }

    public static void main(String[] args) {
        CorrectMultiplication multiplication = new CorrectMultiplication();
        debug(multiplication.getMinimum(1000000000, 1000000000, 1000000000));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
