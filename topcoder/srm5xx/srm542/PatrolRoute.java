package topcoder.srm5xx.srm542;

/**
 * Created by hama_du on 15/08/25.
 */
public class PatrolRoute {
    private static final long MOD = 1000000007;

    public int countRoutes(int X, int Y, int minT, int maxT) {
        long sum = 0;
        for (int dx = 2 ; dx < X ; dx++) {
            long w = (X - dx) * (dx - 1) % MOD;
            for (int dy = 2; dy < Y ; dy++) {
                long h = (Y - dy) * (dy - 1) % MOD;
                int T = (dx + dy) * 2;
                if (minT <= T && T <= maxT) {
                    sum += (w * h * 6) % MOD;
                }
            }
        }
        return (int)(sum % MOD);
    }
}
