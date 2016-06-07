package topcoder.srm6xx.srm662.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/06/07.
 */
public class FoxesOfTheRoundTable {
    public int[] minimalDifference(int[] h) {
        int n = h.length;
        int[][] hx = new int[n][2];
        for (int i = 0; i < n ; i++) {
            hx[i][0] = h[i];
            hx[i][1] = i;
        }
        Arrays.sort(hx, (o1, o2) -> o1[0] - o2[0]);
        for (int i = 0; i < n ; i += 2) {
            hx[i][0] *= -1;
        }
        Arrays.sort(hx, (o1, o2) -> o1[0] - o2[0]);

        return Arrays.stream(hx).mapToInt(o -> o[1]).toArray();
    }
}
