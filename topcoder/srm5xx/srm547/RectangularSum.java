package topcoder.srm5xx.srm547;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/28.
 */
public class RectangularSum {
    public long minimalArea(int height, int width, long S) {
        long s2 = S*2;
        long min = Long.MAX_VALUE;
        for (long area = 1 ; area <= 1500000 ; area++) {
            if (s2 % area != 0) {
                continue;
            }
            long rev = s2 / area;
            if (area < min && isPossible(area, rev, width, height)) {
                min = area;
            }
            if (rev < min && isPossible(rev, area, width, height)) {
                min = rev;
            }
        }
        return min == Long.MAX_VALUE ? -1 : min;
    }

    private boolean isPossible(long area, long rev, int width, int height) {
        for (long x = 1 ; x <= 1000 ; x++) {
            if (area % x != 0) {
                continue;
            }
            long y = area / x;
            if (canMake(x, y, rev, width, height) || canMake(y, x, rev, width, height)) {
                return true;
            }
        }
        return false;
    }

    private boolean canMake(long x, long y, long rev, long width, long height) {
        long right = rev + x - 1 + y * width - width;
        if (right % 2 == 1) {
            return false;
        }
        right /= 2;

        long j = right % width;
        long i = j - x + 1;
        if (0 <= i && i <= j && j <= width-1) {
        } else {
            return false;
        }

        long l = (right - j);
        if (l % width != 0) {
            return false;
        }
        l /= width;
        long k = l - y + 1;
        if (0 <= k && k <= l && l <= height-1) {
        } else {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        RectangularSum sum = new RectangularSum();
        debug(sum.minimalArea(2, 3, 8));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
