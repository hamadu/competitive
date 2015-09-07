package topcoder.srm5xx.srm545;

/**
 * Created by hama_du on 15/08/27.
 */
public class Spacetsk {
    private static final int MOD = 1000000007;

    public int countsets(int L, int H, int K) {
        if (K == 1) {
            return (L + 1) * (H + 1);
        }

        int[][] ncr = new int[3200][3200];
        for (int i = 0; i < ncr.length; i++) {
            ncr[i][0] = ncr[i][i] = 1;
            for (int j = 1 ; j <= i; j++) {
                ncr[i][j] = (ncr[i-1][j-1] + ncr[i-1][j]);
                if (ncr[i][j] >= MOD) {
                    ncr[i][j] -= MOD;
                }
            }
        }

        long sum = 0;
        for (int dx = 0; dx <= L; dx++) {
            for (int dy = 1 ; dy <= H ; dy++) {
                int pos = (L - dx + 1) % MOD;
                int points = (dx == 0) ? dy : gcd(dx, dy);
                long flip = (dx == 0) ? 1 : 2;
                sum += flip * pos * ncr[points][K-1] % MOD;
                sum %= MOD;
            }
        }
        return (int)sum;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a%b);
    }
}
