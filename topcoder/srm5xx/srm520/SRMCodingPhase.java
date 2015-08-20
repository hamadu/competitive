package topcoder.srm5xx.srm520;

/**
 * Created by hama_du on 15/08/16.
 */
public class SRMCodingPhase {
    public int countScore(int[] points, int[] skills, int luck) {
        int max = 0;

        int[] tm = new int[]{2,4,8};
        for (int p = 1 ; p <= 7 ; p++) {
            for (int le = 0 ; le < skills[0] ; le++) {
                for (int lm = 0 ; lm < skills[1] ; lm++) {
                    for (int lh = 0 ; lh < skills[2] ; lh++) {
                        if (le + lm + lh > luck) {
                            break;
                        }
                        int[] lu = new int[]{le,lm,lh};

                        int score = 0;
                        int total = 0;
                        for (int i = 0; i < 3 ; i++) {
                            if ((p & (1<<i)) >= 1) {
                                score += points[i];
                                score -= (skills[i] - lu[i]) * tm[i];
                                total += skills[i] - lu[i];
                            }
                        }
                        if (total <= 75) {
                            max = Math.max(max, score);
                        }
                    }
                }
            }
        }
        return max;
    }
}
