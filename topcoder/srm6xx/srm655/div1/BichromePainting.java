package topcoder.srm6xx.srm655.div1;

/**
 * Created by hama_du on 2016/06/17.
 */
public class BichromePainting {
    public String isThatPossible(String[] board, int k) {
        int n = board.length;
        int[][] bd = new int[n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                bd[i][j] = board[i].charAt(j) == 'W' ? 1 : 2;
            }
        }

        boolean upd = true;
        while (upd) {
            upd = false;
            for (int i = 0; i <= n-k; i++) {
                for (int j = 0; j <= n-k; j++) {
                    int un = 0;
                    for (int a = i; a < i+k; a++) {
                        for (int b = j; b < j+k; b++) {
                            un |= bd[a][b];
                        }
                    }
                    if (Integer.bitCount(un) == 1) {
                        for (int a = i; a < i+k; a++) {
                            for (int b = j; b < j+k; b++) {
                                bd[a][b] = 0;
                            }
                        }
                        upd = true;
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (bd[i][j] == 2) {
                    return "Impossible";
                }
            }
        }
        return "Possible";
    }
}
