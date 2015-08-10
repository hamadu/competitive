package topcoder.srm5xx.srm513;

/**
 * Created by hama_du on 15/08/07.
 */
public class YetAnotherIncredibleMachine {
    private static final long MOD = 1000000009;

    public int countWays(int[] platformMount, int[] platformLength, int[] balls) {
        long ptn = 1;
        int n = platformMount.length;
        for (int i = 0; i < n ; i++) {
            long p = 0;
            for (int lp = platformMount[i] - platformLength[i] ; lp <= platformMount[i]; lp++) {
                int rp = lp + platformLength[i];
                boolean isOK = true;
                for (int j = 0; j < balls.length ; j++) {
                    if (lp <= balls[j] && balls[j] <= rp) {
                        isOK = false;
                        break;
                    }
                }
                if (isOK) {
                    p++;
                }
            }
            ptn *= p;
            ptn %= MOD;
        }
        return (int)ptn;
    }
}
