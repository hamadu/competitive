package topcoder.srm5xx.srm580;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hama_du on 15/09/13.
 */
public class EelAndRabbit {
    public int getmax(int[] l, int[] t) {
        int n = l.length;
        List<Long> candidateTime = new ArrayList<>();
        for (int i = 0; i < n ; i++) {
            candidateTime.add(1L * t[i]);
            candidateTime.add(1L * t[i] + l[i]);
        }
        int cn = candidateTime.size();
        long[] cat = new long[cn];
        for (int i = 0; i < cn ; i++) {
            long time = candidateTime.get(i);
            for (int j = 0; j < n ; j++) {
                if (t[j] <= time && time <= t[j]+l[j]) {
                    cat[i] |= 1L<<j;
                }
            }
        }
        int max = 0;
        for (int i = 0; i < cn ; i++) {
            for (int j = 0; j < cn ; j++) {
                long ptn = cat[i] | cat[j];
                max = Math.max(max, Long.bitCount(ptn));
            }
        }
        return max;
    }
}
