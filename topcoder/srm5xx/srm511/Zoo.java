package topcoder.srm5xx.srm511;

/**
 * Created by hama_du on 15/08/07.
 */
public class Zoo {
    public long theCount(int[] answers) {
        long ptn = 1;
        int n = answers.length;
        int max = 2;
        int find = 0;
        int counted = 0;
        while (true) {
            int ct = 0;
            for (int i = 0; i < n; i++) {
                if (answers[i] == find) {
                    ct++;
                }
            }
            if (ct > max) {
                return 0;
            }
            if (ct == 0) {
                break;
            }
            counted += ct;
            ptn *= max;
            max = Math.min(max, ct);
            find++;
        }
        return (counted == n) ? ptn : 0;
    }
}
