package topcoder.srm5xx.srm586;

/**
 * Created by hama_du on 15/09/16.
 */
public class PiecewiseLinearFunction {
    public int maximumSolutions(int[] Y) {
        int n = Y.length;
        for (int i = 0; i < n-1; i++) {
            if (Y[i] == Y[i+1]) {
                return -1;
            }
        }
        for (int i = 0; i < n; i++) {
            Y[i] *= 2;
        }

        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int d = -1 ; d <= 1 ; d++) {
                int cnt = 0;
                int y = Y[i]+d;
                for (int j = 0; j < n-1 ; j++) {
                    if (Math.min(Y[j], Y[j+1]) < y && y < Math.max(Y[j], Y[j+1])) {
                        cnt++;
                    }
                }
                for (int j = 0; j < n ; j++) {
                    if (y == Y[j]) {
                        cnt++;
                    }
                }
                max = Math.max(max, cnt);
            }
        }
        return max;
    }
}
