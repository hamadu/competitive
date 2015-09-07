package topcoder.srm5xx.srm568;

/**
 * Created by hama_du on 15/09/04.
 */
public class BallsSeparating {
    public int minOperations(int[] red, int[] green, int[] blue) {
        if (red.length <= 2) {
            return -1;
        }

        int min = Integer.MAX_VALUE;
        int n = red.length;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                for (int k = 0; k < n ; k++) {
                    if (i == j || j == k || i == k) {
                        continue;
                    }
                    int cost = 0;
                    for (int l = 0; l < n ; l++) {
                        if (l == i) {
                            cost += green[l] + blue[l];
                        } else if (l == j) {
                            cost += red[l] + blue[l];
                        } else if (l == k) {
                            cost += red[l] + green[l];
                        } else {
                            int max = Math.max(red[l], Math.max(green[l], blue[l]));
                            cost += red[l] + green[l] + blue[l] - max;
                        }
                    }
                    min = Math.min(min, cost);
                }
            }
        }
        return min;
    }
}
