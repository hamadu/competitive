package topcoder.srm5xx.srm558;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by hama_du on 15/09/02.
 */
public class Ear {

    private static final int INF = 1145141919;

    public long getCount(String[] redX, String[] blueX, String[] blueY) {
        int[] red = build(redX);
        int rn = red.length;
        int[] bx = build(blueX);
        int[] by = build(blueY);
        int bn = bx.length;
        int[][] blue = new int[bn][2];
        for (int i = 0; i < bn ; i++) {
            blue[i][0] = by[i];
            blue[i][1] = bx[i];
        }
        Arrays.sort(red);
        Arrays.sort(blue, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[0] - o1[0];
            }
        });

        long ans = 0;
        for (int b1 = 0; b1 < bn ; b1++) {
            for (int b2 = b1 + 1; b2 < bn ; b2++) {
                if (blue[b1][0] <= blue[b2][0]) {
                    continue;
                }

                int dy = blue[b1][0] - blue[b2][0];
                int dx = Math.abs(blue[b1][1] - blue[b2][1]);

                // left
                long leftPattern = 0;
                {
                    int limit = INF;
                    if (blue[b1][1] > blue[b2][1]) {
                        limit = dy * blue[b1][1] - blue[b1][0] * dx;
                    }
                    for (int ri = 0; ri < rn ; ri++) {
                        if (red[ri] >= Math.min(blue[b2][1], blue[b1][1]) || red[ri] * dy >= limit) {
                            continue;
                        }
                        int min = red[ri]+1;
                        int max = blue[b2][1]-1;
                        leftPattern += count(red, min, max);
                    }
                }

                // right
                long rightPattern = 0;
                {
                    int limit = -INF;
                    if (blue[b1][1] < blue[b2][1]) {
                        limit = dy * blue[b1][1] + blue[b1][0] * dx;
                    }
                    for (int ri = rn-1 ; ri >= 0 ; ri--) {
                        if (red[ri] <= Math.max(blue[b2][1], blue[b1][1]) || red[ri] * dy <= limit) {
                            continue;
                        }
                        int min = blue[b2][1]+1;
                        int max = red[ri]-1;
                        rightPattern += count(red, min, max);
                    }
                }
                ans += leftPattern * rightPattern;
            }
        }
        return ans;
    }

    private static int count(int[] red, int min, int max) {
        return Math.max(0, count(red, max) - count(red, min-1));
    }

    private static int count(int[] red, int lim) {
        int idx = Arrays.binarySearch(red, lim);
        if (idx < 0) {
            return (-idx-1);
        }
        return idx+1;
    }

    public int[] build(String[] line) {
        StringBuilder sb = new StringBuilder();
        for (String l : line) {
            sb.append(l);
        }
        String[] part = sb.toString().split(" ");
        int[] ret = new int[part.length];
        for (int i = 0; i < part.length; i++) {
            ret[i] = Integer.valueOf(part[i]);
        }
        return ret;
    }
}
