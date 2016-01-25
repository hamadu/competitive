package topcoder.srm6xx.srm676;

import java.util.Arrays;

/**
 * Created by hama_du on 2015/12/19.
 */
public class WaterTank {
    public double minOutputRate(int[] t, int[] x, int C) {
        double max = 1e18;
        double min = 0;
        for (int z = 0; z < 500 ; z++) {
            double med = (max + min) / 2;
            if (isOK(t, x, C, med)) {
                max = med;
            } else {
                min = med;
            }
        }
        return max;
    }

    private boolean isOK(int[] t, int[] x, int c, double med) {
        int n = t.length;
        double current = 0;
        boolean over = false;
        for (int i = 0 ; i < n ; i++) {
            double first = current;
            double diff = (x[i] - med) * t[i];
            double next = first + diff;
            next = Math.max(0, next);
            if (next > c) {
                over = true;
            }
            current = next;
        }
        return !over;
    }


    private boolean isOK2(int[] t, int[] x, int c, double med) {
        int n = t.length;
        double current = 0;
        boolean over = false;
        for (int i = 0 ; i < n ; i++) {
            double first = current;
            double diff = (x[i] - med) * t[i];
            diff = Math.max(diff, 0);
            double next = first + diff;
            if (next > c) {
                over = true;
            }
            current = next;
        }
        return !over;
    }


    public static void main(String[] args) {
        WaterTank solution = new WaterTank();
        debug(solution.minOutputRate(
                new int[]{105,4,103,2,101} ,
                new int[]{3,30,3,300,1000} ,
        1000));

        // debug(solution.someMethod());
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
