package topcoder.srm5xx.srm555;

/**
 * Created by hama_du on 15/08/26.
 */
public class XorBoard {
    public final int MOD = 555555555;

    public int count(int H, int W, int Rcount, int Ccount, int S) {
        int[][] ncr = new int[3200][3200];
        for (int i = 0; i < ncr.length ; i++) {
            ncr[i][0] = ncr[i][i] = 1;
            for (int j = 1 ; j <= i-1; j++) {
                ncr[i][j] = (ncr[i-1][j-1] + ncr[i-1][j]) % MOD;
            }
        }

        long ans = 0;
        for (int r = Rcount % 2 ; r <= Math.min(H, Rcount) ; r += 2) {
            for (int c = Ccount % 2 ; c <= Math.min(W, Ccount) ; c += 2) {
                if (r * W + c * H - r * c * 2 != S) {
                    continue;
                }
                int twiceR = (Rcount - r) / 2;
                int twiceC = (Ccount - c) / 2;
                long choiceRC = 1L * ncr[H][r] * ncr[W][c] % MOD;
                long flipWay = 1L * ncr[twiceR+H-1][H-1] * ncr[twiceC+W-1][W-1] % MOD;
                ans += choiceRC * flipWay % MOD;
            }
        }
        ans %= MOD;
        return (int)ans;
    }
}
