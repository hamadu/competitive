package topcoder.srm5xx.srm577;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/13.
 */
public class EllysRoomAssignmentsDiv1 {
    public double getAverage(String[] ratings) {
        int[] a = rate(ratings);
        int n = a.length;
        int R = (n + 19) / 20;
        int[][] contestants = new int[n][2];
        for (int i = 0; i < n ; i++) {
            contestants[i][0] = i;
            contestants[i][1] = a[i];
        }
        Arrays.sort(contestants, (c1, c2) -> c2[1] - c1[1]);
        double ret = 0;
        int nin = 1;
        int ellySetID = -1;
        for (int i = 0; i < n ; i++) {
            if (contestants[i][0] == 0) {
                ellySetID = i / R;
                ret += contestants[i][1];
            }
        }

        double sub = 0.0d;
        double subRate = 0.0d;
        int sid = 0;
        for (int si = 0; si < n ; si += R) {
            if (sid == ellySetID) {
                sid++;
                continue;
            }
            int sum = 0;
            int cnt = 0;
            for (int v = si ; v < Math.min(n, si+R) ; v++) {
                sum += contestants[v][1];
                cnt++;
            }
            if (cnt == R) {
                ret += sum * 1.0d / R;
                nin++;
            } else {
                sub = sum * 1.0d / cnt;
                subRate = cnt * 1.0d / R;
            }
            sid++;
        }

        return ((1.0d - subRate) * ret / nin) + (subRate * (ret + sub) / (nin + 1));
    }


    public int[] rate(String[] ratings) {
        StringBuilder line = new StringBuilder();
        for (String i : ratings) {
            line.append(i);
        }
        String[] part = line.toString().split(" ");
        int[] ret = new int[part.length];
        for (int i = 0; i < part.length ; i++) {
            ret[i] = Integer.valueOf(part[i]);
        }
        return ret;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }


}
