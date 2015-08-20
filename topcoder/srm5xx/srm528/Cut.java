package topcoder.srm5xx.srm528;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/18.
 */
public class Cut {
    public int getMaximum(int[] eelLengths, int maxCuts) {
        Arrays.sort(eelLengths);
        int cnt = 0;
        for (int e : eelLengths) {
            if (e % 10 == 0) {
                if (maxCuts >=  e / 10 - 1) {
                    cnt += e / 10;
                    maxCuts -= e / 10 - 1;
                } else {
                    cnt += maxCuts;
                    maxCuts = 0;
                }
            }
        }
        for (int e : eelLengths) {
            if (e % 10 != 0) {
                int gain = e / 10;
                if (maxCuts >= gain) {
                    cnt += gain;
                    maxCuts -= gain;
                } else {
                    cnt += maxCuts;
                    maxCuts = 0;
                }
            }
        }
        return cnt;
    }
}
