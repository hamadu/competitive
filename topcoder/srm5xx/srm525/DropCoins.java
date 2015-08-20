package topcoder.srm5xx.srm525;

/**
 * Created by hama_du on 15/08/17.
 */
public class DropCoins {
    public int getMinimum(String[] board, int K) {
        int n = board.length;
        int m = board[0].length();

        int[][] imos = new int[n+1][m+1];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                imos[i+1][j+1] = imos[i+1][j] + imos[i][j+1] - imos[i][j] + ((board[i].charAt(j) == 'o') ? 1 : 0);
            }
        }

        int best = Integer.MAX_VALUE;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                for (int k = i; k <= n; k++) {
                    for (int l = j; l <= m; l++) {
                        int sum = imos[k][l] - imos[i][l] - imos[k][j] + imos[i][j];
                        if (sum == K) {
                            int top = i;
                            int bottom = (n - k);
                            int left = j;
                            int right = (m - l);
                            best = Math.min(best, top + bottom + left + right + Math.min(top, bottom) + Math.min(left, right));
                        }
                    }

                }
            }
        }
        return best == Integer.MAX_VALUE ? -1 : best;
    }
}
