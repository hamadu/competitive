package topcoder.srm5xx.srm552;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/30.
 */
public class FoxPaintingBalls {
    public long theMax(long R, long G, long B, int N) {
        if (N == 1) {
            return R + G + B;
        }

        long per = N*(N+1L)/2;
        if (per % 3 != 0) {
            long min = -1;
            long two = per / 3;
            long max = R + G + B + 10;
            while (max - min > 1) {
                long Z = (max + min) / 2;
                long sum = R + G + B - Z * two * 3;
                if (Math.min(R, Math.min(G, B)) / two >= Z && sum >= Z) {
                    min = Z;
                } else {
                    max = Z;
                }
            }
            return min;
        } else {
            long use = per / 3;
            return Math.min(R, Math.min(G, B)) / use;
        }
    }
}
