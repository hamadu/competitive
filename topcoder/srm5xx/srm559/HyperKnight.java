package topcoder.srm5xx.srm559;

/**
 * Created by hama_du on 15/09/02.
 */
public class HyperKnight {
    public long countCells(int a, int b, int numRows, int numColumns, int k) {
        return count(a, b, numRows, numColumns, k);
    }
    public long count(long a, long b, long n, long m, int k) {
        if (a > b) {
            return count(b, a, n, m, k);
        }

        // b-a-*-a-b
        long[] maskX = new long[16];
        maskX[15] = m - 2 * b;
        maskX[14] = b - a;
        maskX[7]  = b - a;
        maskX[12] = a;
        maskX[3]  = a;

        long[] maskY = new long[16];
        maskY[15] = n - 2 * b;
        maskY[14] = b - a;
        maskY[7]  = b - a;
        maskY[12] = a;
        maskY[3]  = a;

        int[] kx = {1, 2, 4, 8, 8, 4, 2, 1};
        int[] ky = {2, 1, 1, 2, 4, 8, 8, 4};

        long total = 0;
        for (int mx = 0; mx < 16; mx++) {
            for (int my = 0; my < 16 ; my++) {
                int validMoves = 0;
                for (int c = 0; c < 8 ; c++) {
                    if ((mx & kx[c]) >= 1 && (my & ky[c]) >= 1) {
                        validMoves++;
                    }
                }
                if (validMoves == k) {
                    total += maskX[mx] * maskY[my];
                }
            }
        }
        return total;
    }
}
