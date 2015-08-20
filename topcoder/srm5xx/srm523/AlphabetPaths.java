package topcoder.srm5xx.srm523;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/17.
 */
public class AlphabetPaths {
    String letter = "ABCDEFZHIKLMNOPQRSTVX";

    public long count(String[] letterMaze) {
        int[] lmap = new int[255];
        Arrays.fill(lmap, -1);
        for (int i = 0; i < letter.length() ; i++) {
            lmap[letter.charAt(i)] = i;
        }

        n = letterMaze.length;
        m = letterMaze[0].length();
        map = new int[n][m];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                char c = letterMaze[i].charAt(j);
                map[i][j] = lmap[c];
            }
        }

        for (int i = 0; i < (1<<21) ; i++) {
            if (Integer.bitCount(i) == 11) {
                poyo[pid++] = i;
            }
        }

        long ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m ; j++) {
                if (map[i][j] == -1) {
                    continue;
                }
                Arrays.fill(ptn, 0);
                dfs(i, j, 1 << map[i][j]);
                for (int p = 0; p < pid ; p++) {
                    int want = (((1<<21)-1) ^ poyo[p]) | (1<<map[i][j]);
                    ans += 1L * ptn[poyo[p]] * ptn[want];
                }
            }
        }
        return ans;
    }

    int n, m;
    int[][] map;
    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};
    int[] ptn = new int[1<<21];
    int[] poyo = new int[1<<21];
    int pid = 0;

    public void dfs(int ny, int nx, int flg) {
        if (Integer.bitCount(flg) == 11) {
            ptn[flg]++;
            return;
        }
        for (int d = 0; d < 4 ; d++) {
            int ty = ny + dy[d];
            int tx = nx + dx[d];
            if (ty < 0 || tx < 0 || ty >= n || tx >= m || (flg & (1<<map[ty][tx])) >= 1) {
                continue;
            }
            if (map[ty][tx] == -1) {
                continue;
            }
            dfs(ty, tx, flg | (1<<map[ty][tx]));
        }
    }
}
