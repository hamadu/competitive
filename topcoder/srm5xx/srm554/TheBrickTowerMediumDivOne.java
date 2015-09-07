package topcoder.srm5xx.srm554;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by hama_du on 15/08/31.
 */
public class TheBrickTowerMediumDivOne {
    public int[] find(int[] heights) {
        int n = heights.length;
        int[] ret = new int[n];
        Arrays.fill(ret, -1);

        boolean[] used = new boolean[n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (!used[j]) {
                    ret[i] = j;
                    used[j] = true;
                    if (canBuild(ret, used, heights)) {
                        break;
                    }
                    used[j] = false;
                    ret[i] = -1;
                }
            }
        }
        debug(ret);
        return ret;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }


    private boolean canBuild(int[] ret, boolean[] used, int[] heights) {
        int n = ret.length;
        int hn = -1;

        for (int i = 0; i < n ; i++) {
            if (ret[i] == -1) {
                hn = i;
                break;
            }
        }
        boolean up = false;
        for (int i = 1 ; i < hn ; i++) {
            if (heights[ret[i-1]] < heights[ret[i]]) {
                up = true;
            } else if (up && heights[ret[i-1]] > heights[ret[i]]) {
                return false;
            }
        }
        if (!up) {
            return true;
        }
        int last = heights[ret[hn-1]];

        List<Integer> left = new ArrayList<>();
        for (int i = 0; i < n ; i++) {
            if (!used[i]) {
                left.add(heights[i]);
            }
        }
        Collections.sort(left);
        if (left.size() == 0 || last <= left.get(0)) {
            return true;
        }
        return false;
    }
}
