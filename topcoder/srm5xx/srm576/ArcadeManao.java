package topcoder.srm5xx.srm576;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 15/09/13.
 */
public class ArcadeManao {
    public int shortestLadder(String[] level, int coinRow, int coinColumn) {
        int n = level.length;
        int m = level[0].length();
        char[][] map = new char[n][m];
        for (int i = 0; i < n ; i++) {
            map[i] = level[i].toCharArray();
        }
        int ok = n+1;
        int ng = -1;
        while (ok - ng > 1) {
            int med = (ok + ng) / 2;
            if (canReach(map, coinRow-1, coinColumn-1, med)) {
                ok = med;
            } else {
                ng = med;
            }
        }
        return ok;
    }

    private boolean canReach(char[][] map, int cy, int cx, int ladder) {
        int n = map.length;
        int m = map[0].length;

        boolean[][] visited = new boolean[n][m];
        Queue<Integer> q = new ArrayBlockingQueue<>(20000);
        for (int i = 0; i < m ; i++) {
            q.add(n-1);
            q.add(i);
            visited[n-1][i] = true;
        }

        int[] dx = new int[2+ladder*2];
        int[] dy = new int[2+ladder*2];
        dx[0] = -1;
        dx[1] = 1;
        for (int i = 1 ; i <= ladder ; i++) {
            dy[i*2] = i;
            dy[i*2+1] = -i;
        }
        while (q.size() >= 1) {
            int y = q.poll();
            int x = q.poll();
            for (int d = 0 ; d < dx.length ; d++) {
                int ty = y+dy[d];
                int tx = x+dx[d];
                if (ty < 0 || tx < 0 || ty >= n || tx >= m) {
                    continue;
                }
                if (map[ty][tx] != 'X' || visited[ty][tx]) {
                    continue;
                }
                visited[ty][tx] = true;
                q.add(ty);
                q.add(tx);
            }
        }
        return visited[cy][cx];
    }
}
