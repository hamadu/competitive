package topcoder.srm5xx.srm544;

/**
 * Created by hama_du on 15/08/27.
 */
public class FlipGame {
    public int minOperations(String[] board) {
        int n = board.length;
        int m = board[0].length();
        int[][] map = new int[n][m];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                map[i][j] = board[i].charAt(j) - '0';
            }
        }

        int time = 0;
        while (true) {
            int[] flip = new int[n];
            int now = 0;
            for (int i = 0; i < n ; i++) {
                for (int j = now ; j < m ; j++) {
                    if (map[i][j] == 1) {
                        now = j+1;
                    }
                }
                flip[i] = now;
            }
            if (now == 0) {
                break;
            }
            time++;
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < flip[i] ; j++) {
                    map[i][j] ^= 1;
                }
            }
        }
        return time;
    }
}
