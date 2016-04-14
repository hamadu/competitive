package topcoder.tco2016.round1b;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by hama_du on 4/13/16.
 */
public class SettingShield {
    public long getOptimalCost(int n, int h, int t, int[] val0, int[] a, int[] b, int[] m) {
        long[] protection = new long[n];
        protection[0] = val0[0];
        for (int i = 1 ; i < n ; i++) {
            protection[i] = (a[0] * protection[i-1] + b[0]) % m[0];
        }

        int[] left = new int[h];
        int[] right = new int[h];
        left[0] = val0[1];
        right[0] = val0[2];
        for (int i = 1 ; i < h; i++) {
            left[i] = (int)Math.min(n-1, ((1L * a[1] * left[i-1] + b[1]) % m[1]));
            long dist = right[i-1] - left[i-1];
            right[i] = (int)Math.min(n-1, left[i] + ((1L * a[2] * dist + b[2]) % m[2]));
        }

        return solve(protection, left, right, t);
    }

    private long solve(long[] protection, int[] left, int[] right, long t) {
        int n = protection.length;
        int m = left.length;

        int[][] lr = new int[m][2];
        for (int i = 0; i < m ; i++) {
            lr[i][0] = left[i];
            lr[i][1] = right[i];
        }
        Arrays.sort(lr, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);

        long cost = Long.MAX_VALUE;
        long min = 0;
        long max = 10000000;
        for (int c = 0 ; c < 40 ; c++) {
            long m1 = (min * 2 + max) / 3;
            long m2 = (min + max * 2) / 3;
            long c1 = solveSub(protection, lr, t, m1);
            long c2 = solveSub(protection, lr, t, m2);
            if (c1 < c2) {
                max = m2;
            } else {
                min = m1;
            }
            cost = Math.min(cost, c1);
            cost = Math.min(cost, c2);
        }
        for (long d = -1 ; d <= 1 ; d++) {
            long f = (min + d);
            if (f >= 0) {
                long c = solveSub(protection, lr, t, f);
                cost = Math.min(cost, c);
            }
        }
        return cost;
    }

    long[] dectbl = new long[100000];
    long[] pp = new long[100000];

    public long solveSub(long[] protection, int[][] lr, long t, long special) {
        int n = protection.length;
        int m = lr.length;
        for (int i = 0; i < n ; i++) {
            pp[i] = protection[i] - special;
        }
        Arrays.fill(dectbl, 0);
        long cost = 0;
        int maxCov = -1;
        int li = 0;
        long have = 0;
        for (int i = 0 ; i < n ; i++) {
            while (li < m && lr[li][0] <= i) {
                maxCov = Math.max(maxCov, lr[li][1]);
                li++;
            }
            if (have < pp[i]) {
                if (i > maxCov) {
                    return Long.MAX_VALUE;
                }
                cost += pp[i] - have;
                dectbl[maxCov] += pp[i] - have;
                have = pp[i];
            }
            have -= dectbl[i];
        }
        return cost + t * special;
    }

    public static void main(String[] args) {
        SettingShield solution = new SettingShield();
        debug(solution.getOptimalCost(
                12,
                6,
                4,
                new int[]{4, 3, 7},
                new int[]{2, 4, 5},
                new int[]{3, 8, 7},
                new int[]{40, 23, 13}
        ));
        // debug(solution.someMethod());
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
