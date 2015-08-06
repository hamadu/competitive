package topcoder.srm5xx.srm506;

/**
 * Created by hama_du on 15/08/05.
 */
public class SlimeXSlimesCity {
    public int merge(int[] population) {
        int n = population.length;
        int cnt = 0;
        for (int i = 0; i < n ; i++) {
            boolean[] done = new boolean[n];
            done[i] = true;
            long pop = population[i];
            int ct = 1;
            boolean upd = true;
            while (upd) {
                upd = false;
                for (int j = 0; j < n ; j++) {
                    if (done[j]) {
                        continue;
                    } else if (pop >= population[j]) {
                        upd = true;
                        pop += population[j];
                        done[j] = true;
                        ct++;
                    }
                }
            }
            cnt += (ct == n) ? 1 : 0;
        }
        return cnt;
    }
}
