package topcoder.srm5xx.srm588;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 15/09/26.
 */
public class GameInDarknessDiv1 {
    private static final int INF = 114514;

    private int[] dx = new int[]{0, -1, 0, 1};
    private int[] dy = new int[]{-1, 0, 1, 0};

    public String check(String[] field) {
        int n = field.length;
        int m = field[0].length();

        char[][] map = new char[n][];
        for (int i = 0; i < n ; i++) {
            map[i] = field[i].toCharArray();
        }
        int ay = -1;
        int ax = -1;
        int by = -1;
        int bx = -1;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (map[i][j] == 'A') {
                    ay = i;
                    ax = j;
                    map[i][j] = '.';
                } else if (map[i][j] == 'B') {
                    by = i;
                    bx = j;
                    map[i][j] = '.';
                }
            }
        }
        if (determine(map, ay, ax, by, bx)) {
            return "Alice wins";
        }
        return "Bob wins";
    }

    private boolean determine(char[][] map, int ay, int ax, int by, int bx) {
        int n = map.length;
        int m = map[0].length;
        int[][] distA = bfs(map, ay, ax);
        int[][] distB = bfs(map, by, bx);
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (distB[i][j] + 2 <= distA[i][j]) {
                    int moves = 0;
                    for (int d = 0; d < 4 ; d++) {
                        int ty = i + dy[d];
                        int tx = j + dx[d];
                        if (ty < 0 || tx < 0 || ty >= n || tx >= m || map[ty][tx] == '#') {
                            continue;
                        }
                        if (dfs(map, ty, tx, i, j) >= 3) {
                            moves++;
                        }
                    }
                    if (moves >= 3) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private int dfs(char[][] map, int ny, int nx, int py, int px) {
        int max = 1;
        for (int d = 0; d < 4 ; d++) {
            int ty = ny + dy[d];
            int tx = nx + dx[d];
            if (ty < 0 || tx < 0 || ty >= map.length || tx >= map[0].length || map[ty][tx] == '#') {
                continue;
            }
            if (ty == py && tx == px) {
                continue;
            }
            max = Math.max(max, dfs(map, ty, tx, ny, nx)+1);
        }
        return max;
    }

    private int[][] bfs(char[][] map, int by, int bx) {
        int n = map.length;
        int m = map[0].length;

        int[][] dist = new int[n][m];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(dist[i], INF);
        }

        Queue<Integer> q = new ArrayBlockingQueue<>(INF);
        q.add(by);
        q.add(bx);
        q.add(0);
        dist[by][bx] = 0;
        while (q.size() >= 1) {
            int ny = q.poll();
            int nx = q.poll();
            int nt = q.poll();
            for (int d = 0; d < 4 ; d++) {
                int ty = ny + dy[d];
                int tx = nx + dx[d];
                if (ty < 0 || tx < 0 || ty >= n || tx >= m || map[ty][tx] == '#') {
                    continue;
                }
                if (dist[ty][tx] > nt+1) {
                    dist[ty][tx] = nt+1;
                    q.add(ty);
                    q.add(tx);
                    q.add(nt+1);
                }
            }
        }
        return dist;
    }
}
