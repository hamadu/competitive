package topcoder.srm5xx.srm536;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/24.
 */
public class RollingDiceDivOne {
    public long mostLikely(int[] dice) {
        Arrays.sort(dice);

        int n = dice.length;
        long min = 1;
        long max = dice[n-1];
        for (int i = n-2; i >= 0 ; i--) {
            if (dice[i] == 1) {
                max++;
                min++;
            } else {
                long over = Math.max(0, dice[i] - (max - min + 1));
                long needExtend = (over + 1) / 2;
                long wmin = min-needExtend;
                long wmax = max+needExtend;
                long freedom = (wmax - wmin + 1) - dice[i];
                max = wmax+1;
                min = max-freedom;
            }
        }
        return min;
    }
}
