package topcoder.srm5xx.srm535;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/23.
 */
public class FoxAndBusiness {
    public double minimumCost(int K, int totalWork, int[] a, int[] p) {
        double min = 0;
        double max = 1e16;
        for (int cur = 0; cur < 1000 ; cur++) {
            double med = (min + max) / 2;
            if (isValid(med, K, totalWork, a, p)) {
                max = med;
            } else {
                min = med;
            }
        }
        return max;
    }

    private boolean isValid(double C, int K, int W, int[] a, int[] p) {
        // condition below must be hold
        // C*sigma(a_i) - W*sigma(a_i*p_i) >= 3600*W*K
        int n = a.length;
        double[] worker = new double[n];
        for (int i = 0; i < n ; i++) {
            worker[i] = C * a[i] - 1.0d * W * a[i] * p[i];
        }
        Arrays.sort(worker);
        double sum = 0;
        for (int i = 0; i < K ; i++) {
            sum += worker[n-1-i];
        }
        return sum >= 3600L * W * K;
    }
}
