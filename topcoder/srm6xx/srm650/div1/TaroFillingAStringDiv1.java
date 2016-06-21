package topcoder.srm6xx.srm650.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/06/21.
 */
public class TaroFillingAStringDiv1 {
    int MOD = (int)1e9+7;

    public int getNumber(int N, int[] position, String value) {
        int n = position.length;
        int[][] pos = new int[n][2];
        for (int i = 0; i < n ; i++) {
            pos[i][0] = position[i]-1;
            pos[i][1] = value.charAt(i) == 'A' ? 0 : 1;
        }
        Arrays.sort(pos, (a1, a2) -> a1[0] - a2[0]);

        long ret = 1;
        for (int i = 0 ; i < n-1 ; i++) {
            int gap = pos[i+1][0] - pos[i][0] - 1;
            if ((gap%2 == 1)^(pos[i+1][1] == pos[i][1])) {
                ret *= (gap+1);
                ret %= MOD;
            }
        }
        return (int)ret;
    }
}
