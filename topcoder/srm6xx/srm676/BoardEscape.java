package topcoder.srm6xx.srm676;

import java.util.*;

/**
 * Created by hama_du on 2015/12/19.
 */
public class BoardEscape {
    char[][] map;

    int[] dx = {-1, 0, 1, 0};
    int[] dy = {0, 1, 0, -1};

    int[][][] memo;

    public int game(int y, int x, int k) {
        if (map[y][x] == 'E' || k == 0) {
            return 0;
        }
        if (memo[y][x][k] != -1) {
            return memo[y][x][k];
        }

        Set<Integer> gr = new HashSet<>();
        for (int d = 0 ; d < 4 ; d++) {
            int ty = y + dy[d];
            int tx = x + dx[d];
            if (ty < 0 || tx < 0 || ty >= map.length || tx >= map[0].length) {
                continue;
            }
            if (map[ty][tx] == '#') {
                continue;
            }
            gr.add(game(ty, tx, k-1));
        }
        int num = -1;
        for (int i = 0 ; i < 114514 ; i++) {
            if (!gr.contains(i)) {
                num = i;
                break;
            }
        }
        memo[y][x][k] = num;
        return num;
    }

    public String findWinner(String[] s, int k) {
        int n = s.length;
        int m = s[0].length();
        map = new char[n][m];
        memo = new int[n][m][10100];

        if (k > 10000) {
            int mod = k % 2;
            k = 10000 - mod;
        }

        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }

        List<int[]> tokens = new ArrayList<>();
        for (int i = 0; i < n ; i++) {
            map[i] = s[i].toCharArray();
            for (int j = 0; j < m ; j++) {
                if (map[i][j] == 'T') {
                    map[i][j] = '.';
                    tokens.add(new int[]{i, j});
                }
            }
        }

        int xor = 0;
        for (int[] token : tokens) {
            xor ^= game(token[0], token[1], k);
        }

        for (int l = 0 ; l < 10000 ; l++) {
            if (game(1, 1, l) != l % 2) {
                debug(l, game(1, 1, l));
            }
        }

        if (xor != 0) {
            return "Alice";
        } else {
            return "Bob";
        }
    }


    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }


    public static void main(String[] args) {
        BoardEscape be = new BoardEscape();


        int size = 20;
        char[][] map = new char[size][size];
        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < size ; j++) {
                map[i][j] = Math.random() < 0.2 ? '#' : '.';
            }
        }
        map[size-1][size-1] = 'E';

        String[] ret = new String[size];
        for (int i = 0; i < size ; i++) {
            ret[i] = String.valueOf(map[i]);
        }


        be.findWinner(ret, 1000000);
    }
}
