package topcoder.srm5xx.srm550;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/30.
 */
public class RotatingBot {
    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};

    public int minArea(int[] moves) {
        int n = moves.length;

        boolean[][] visited = new boolean[1024][1024];

        {
            int nx = 512;
            int ny = 512;
            visited[ny][nx] = true;
            int dir = 0;
            for (int i = 0; i < n; i++) {
                for (int k = 0; k < moves[i]; k++) {
                    int tx = nx+dx[dir];
                    int ty = ny+dy[dir];
                    if (visited[ty][tx]) {
                        return -1;
                    }
                    visited[ty][tx] = true;
                    nx = tx;
                    ny = ty;
                }
                dir = (dir+1) % 4;
            }
        }

        int minX = 1024;
        int maxX = -1;
        int minY = 1024;
        int maxY = -1;
        for (int i = 0; i < 1024 ; i++) {
            for (int j = 0; j < 1024; j++) {
                if (visited[i][j]) {
                    minY = Math.min(minY, i);
                    maxY = Math.max(maxY, i);
                    minX = Math.min(minX, j);
                    maxX = Math.max(maxX, j);
                }
            }
        }

        {
            int ny = 512;
            int nx = 512;
            int dir = 0;
            for (int i = 0; i < visited.length ; i++) {
                Arrays.fill(visited[i], false);
            }
            visited[ny][nx] = true;
            for (int i = 0; i < n; i++) {
                for (int k = 0; k < moves[i]; k++) {
                    int tx = nx+dx[dir];
                    int ty = ny+dy[dir];
                    visited[ty][tx] = true;
                    nx = tx;
                    ny = ty;
                }
                if (i != n-1) {
                    int fx = nx + dx[dir];
                    int fy = ny + dy[dir];
                    if (visited[fy][fx] || fy > maxY || fy < minY || fx > maxX || fx < minX) {
                        // it's valid position to rotate.
                    } else {
                        return -1;
                    }
                }
                dir = (dir+1) % 4;
            }
        }


        return (maxX - minX + 1) * (maxY - minY + 1);
    }
}
