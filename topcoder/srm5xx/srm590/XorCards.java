package topcoder.srm5xx.srm590;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/30.
 */
public class XorCards {
    public long numberOfWays(long[] number, long limit) {
        int n = number.length;

        long sum = 1L<<n;
        long more = 0;

        int cnt = 0;
        int[] need = new int[61];
        for (int f = 60 ; f >= 0; f--) {
            cnt++;
            if ((limit & (1L<<f)) == 0) {
                need[f] = 1;
                int[][] table = new int[cnt][n+1];
                for (int idx = 60 ; idx >= f ; idx--) {
                    for (int i = 0; i < n ; i++) {
                        table[(60-idx)][i] = (number[i] & (1L<<idx)) >= 1 ? 1 : 0;
                    }
                    table[(60-idx)][n] = need[idx];
                }
                int freedom = hakidasiMOD2(table);
                if (freedom >= 0) {
                    more += 1L<<freedom;
                }
                need[f] = 0;
            } else {
                need[f] = 1;
            }
        }
        return sum - more;
    }

    public int hakidasiMOD2(int[][] B) {
        int row = B.length;
        int col = B[0].length-1;

        int fromCol = 0;
        for (int toRow = 0; toRow < row && fromCol <= col ; fromCol++) {
            int pv = -1;
            for (int r = toRow; r < row; r++) {
                if (B[r][fromCol] == 1) {
                    pv = r;
                    break;
                }
            }
            if (pv != -1) {
                int[] tmp = B[toRow].clone();
                B[toRow] = B[pv].clone();
                B[pv] = tmp;
                for (int r = toRow + 1; r < row; r++) {
                    if (B[r][fromCol] == 1) {
                        for (int c = fromCol; c <= col; c++) {
                            B[r][c] ^= B[toRow][c];
                        }
                    }
                }
                toRow++;
            }
        }

        int freedom = col;
        for (int i = 0 ; i < row ; i++) {
            int sum = 0;
            for (int c = 0 ; c < col ; c++) {
                sum += B[i][c];
            }
            if (sum >= 1) {
                freedom--;
            } else {
                if (B[i][col] == 1) {
                    return -1;
                }
            }
        }
        return freedom;
    }
}
