package topcoder.srm5xx.srm535;

/**
 * Created by hama_du on 15/08/23.
 */
public class FoxAndGCDLCM {
    public long get(long G, long L) {
        if (L % G != 0) {
            return -1;
        }
        long ab = L / G;
        long best = Long.MAX_VALUE;
        for (int k = 1 ; k <= 1000000; k++) {
            if (ab % k == 0) {
                long a = ab / k;
                long b = k;
                if (gcd(a, b) == 1) {
                    best = Math.min(best, a+b);
                }
            }
        }
        return best * G;
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a%b);
    }
}
