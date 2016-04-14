package topcoder.tco2016.round1b;

import java.util.Arrays;

/**
 * Created by hama_du on 4/13/16.
 */
public class ReplacingDigit {
    public int getMaximumStockWorth(int[] A, int[] D) {
        int sum = 0;

        int n = A.length;
        char[][] prices = new char[n][];
        boolean[][] used = new boolean[n][];
        for (int i = 0; i < n ; i++) {
            prices[i] = String.valueOf(A[i]).toCharArray();
            used[i] = new boolean[prices[i].length];
        }

        int[] keta = new int[7];
        keta[0] = 1;
        for (int i = 1 ; i < 7 ; i++) {
            keta[i] = keta[i-1] * 10;
        }

        while (true) {
            int bestI = -1;
            int bestJ = -1;
            int bestD = -1;
            int bestDiff = -1;
            for (int d = 1 ; d <= 9 ; d++) {
                if (D[d-1] <= 0) {
                    continue;
                }
                for (int i = 0 ; i < n ; i++) {
                    for (int j = 0; j < prices[i].length ; j++) {
                        if (used[i][j] || d <= (prices[i][j] - '0')) {
                            continue;
                        }
                        int diff = Math.abs(d - (prices[i][j] - '0')) * keta[prices[i].length-1-j];
                        if (bestDiff < diff) {
                            bestDiff = diff;
                            bestI = i;
                            bestJ = j;
                            bestD = d;
                        }
                    }
                }
            }
            if (bestDiff == -1) {
                break;
            }
            prices[bestI][bestJ] = (char)('0' + bestD);
            used[bestI][bestJ] = true;
            D[bestD-1]--;
        }

        int total = 0;
        for (int i = 0; i < n ; i++) {
            total += Integer.valueOf(String.valueOf(prices[i]));
        }
        return total;
    }

    public static void main(String[] args) {
        ReplacingDigit solution = new ReplacingDigit();
        // debug(solution.someMethod());
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
