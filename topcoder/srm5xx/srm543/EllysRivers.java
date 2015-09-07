package topcoder.srm5xx.srm543;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/25.
 */
public class EllysRivers {
    public double getMin(int length, int walk, int[] width, int[] speed) {
        int n = width.length;
        double[] rivers = new double[n];
        int[] dy = new int[n];
        for (int i = 0; i < n ; i++) {
            rivers[i] += 1.0d * width[i] / speed[i];
        }
        double current = 0;
        for (int i = 0; i < n ; i++) {
            current += rivers[i];
        }
        double best = current + 1.0d * length / walk;
        for (int d = 0; d < length; d++) {
            double bestDiff = Double.MAX_VALUE;
            int bestRiver = -1;
            for (int i = 0; i < n ; i++) {
                double diff = Math.sqrt(1.0d * width[i] * width[i] + 1.0 * (dy[i] + 1) * (dy[i] + 1)) / speed[i] - rivers[i];
                if (bestDiff > diff) {
                    bestDiff = diff;
                    bestRiver = i;
                }
            }
            current += bestDiff;
            rivers[bestRiver] += bestDiff;
            dy[bestRiver]++;
            best = Math.min(best, current + 1.0d * (length - d - 1) / walk);
        }
        return best;
    }
}
