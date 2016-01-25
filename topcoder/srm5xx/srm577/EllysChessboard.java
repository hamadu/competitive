package topcoder.srm5xx.srm577;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/13.
 */
public class EllysChessboard {
    public int minCost(String[] board) {
        int n = board.length;
        char[][] map = new char[n][];
        for (int i = 0; i < n; i++) {
            map[i] = board[i].toCharArray();
        }
        stones = new int[64][2];
        sn = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] == '#') {
                    stones[sn][0] = i+j+8;
                    stones[sn][1] = i-j+8;
                    sn++;
                }
            }
        }

        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                for (int k = 0; k < 25; k++) {
                    Arrays.fill(memo[i][j][k], -1);
                }
            }
        }
        has = new boolean[31][31];
        for (int i = 0; i < sn ; i++) {
            has[stones[i][0]][stones[i][1]] = true;
        }
        imos = new int[32][32];
        for (int i = 0; i < 31 ; i++) {
            for (int j = 0; j < 31 ; j++) {
                imos[i+1][j+1] = imos[i+1][j] + imos[i][j+1] - imos[i][j] + (has[i][j] ? 1 : 0);
            }
        }
        int ret = Integer.MAX_VALUE;
        for (int i = 0; i < sn; i++) {
            ret = Math.min(ret, dfs(stones[i][0], stones[i][0], stones[i][1], stones[i][1]));
        }
        if (sn == 0) {
            return 0;
        }
        return ret;
    }

    int dfs(int fy, int ty, int fx, int tx) {
        if (memo[fy][ty][fx][tx] != -1) {
            return memo[fy][ty][fx][tx];
        }
        int stoneCount = range(fy, ty, fx, tx);
        if (stoneCount == sn) {
            memo[fy][ty][fx][tx] = 0;
            return 0;
        }

        int ret = Integer.MAX_VALUE / 4;
        for (int ne = 0; ne < sn ; ne++) {
            int nfy = Math.min(fy, stones[ne][0]);
            int nty = Math.max(ty, stones[ne][0]);
            int nfx = Math.min(fx, stones[ne][1]);
            int ntx = Math.max(tx, stones[ne][1]);
            if (range(nfy, nty, nfx, ntx) == stoneCount) {
                continue;
            }
            int cost = 0;
            for (int i = nfy ; i <= nty ; i++) {
                for (int j = nfx ; j <= ntx; j++) {
                    if (fy <= i && i <= ty && fx <= j && j <= tx) {
                        continue;
                    }
                    if (has[i][j]) {
                        cost += Math.max(Math.max(j-nfx, ntx-j), Math.max(i-nfy, nty-i));
                    }
                }
            }
            if (cost >= 1) {
                ret = Math.min(ret, dfs(nfy, nty, nfx, ntx)+cost);
            }
        }
        memo[fy][ty][fx][tx] = ret;
        return ret;
    }

    int sn;
    boolean[][] has;
    int[][] imos;
    int[][] stones;

    int[][][][] memo = new int[25][25][25][25];

    int range(int fy, int ty, int fx, int tx) {
        return imos[ty+1][tx+1] - imos[ty+1][fx] - imos[fy][tx+1] + imos[fy][fx];
    }
}
