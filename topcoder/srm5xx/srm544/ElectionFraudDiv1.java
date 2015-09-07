package topcoder.srm5xx.srm544;

/**
 * Created by hama_du on 15/08/27.
 */
public class ElectionFraudDiv1 {
    public int MinimumVoters(int[] percentages) {
        int n = percentages.length;
        for (int vote = 1; vote <= 2000; vote++) {
            int[] done = new int[n];
            int total = 0;
            for (int i = 0; i < n ; i++) {
                while (total < vote && p(done[i], vote) < percentages[i]) {
                    total++;
                    done[i]++;
                }
            }
            for (int i = 0; i < n ; i++) {
                while (total < vote && p(done[i]+1, vote) == percentages[i]) {
                    total++;
                    done[i]++;
                }
            }
            boolean isOK = true;
            for (int i = 0; i < n; i++) {
                if (p(done[i], vote) != percentages[i]) {
                    isOK = false;
                }
            }
            if (isOK && total == vote) {
                return vote;
            }
        }
        return -1;
    }

    private int p(int x, int all) {
        int vec = x * 1000 / all;
        return vec / 10 + ((vec % 10 >= 5) ? 1 : 0);
    }
}
