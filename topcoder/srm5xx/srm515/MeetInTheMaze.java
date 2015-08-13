package topcoder.srm5xx.srm515;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by hama_du on 15/08/13.
 */
public class MeetInTheMaze {
    private static final int INF = 114514;
    private static final int[] dx = {-1, 0, 1, 0};
    private static final int[] dy = {0, -1, 0, 1};

    public String getExpected(String[] maze) {
        int n = maze.length;
        int m = maze[0].length();
        char[][] map = new char[n][];
        for (int i = 0; i < n ; i++) {
            map[i] = maze[i].toCharArray();
        }
        int[][] F = new int[21][2];
        int[][] R = new int[21][2];
        int fn = 0;
        int rn = 0;
        int ln = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (map[i][j] == 'F') {
                    F[fn][0] = i;
                    F[fn][1] = j;
                    fn++;
                } else if (map[i][j] == 'R') {
                    R[rn][0] = i;
                    R[rn][1] = j;
                    rn++;
                } else if (map[i][j] == 'L') {
                    ln++;
                }
            }
        }

        int[][][] distF = new int[fn][][];
        int[][][] distR = new int[rn][][];
        for (int i = 0; i < fn ; i++) {
            distF[i] = bfs(F[i][0], F[i][1], map);
        }
        for (int i = 0; i < rn ; i++) {
            distR[i] = bfs(R[i][0], R[i][1], map);
        }

        long sum = 0;
        for (int fi = 0; fi < fn; fi++) {
            for (int ri = 0; ri < rn ; ri++) {
                int[][] dp = new int[n][m];
                for (int i = 0; i < n ; i++) {
                    Arrays.fill(dp[i], INF);
                    for (int j = 0; j < m ; j++) {
                        dp[i][j] = distF[fi][i][j] + distR[ri][i][j];
                    }
                }
                dp = bfs(map, dp);
                for (int i = 0; i < n ; i++) {
                    for (int j = 0; j < m; j++) {
                        if (map[i][j] == 'L') {
                            if (dp[i][j] >= INF) {
                                return "";
                            }
                            sum += dp[i][j];
                        }
                    }
                }
            }
        }

        long base = fn * rn * ln;
        long G = gcd(base, sum);
        base /= G;
        sum /= G;
        return String.format("%d/%d", sum, base);
    }

    static long gcd(long a, long b) {
        return (b == 0) ? a : gcd(b, a%b);
    }

    private int[][] bfs(int iy, int ix, char[][] map) {
        int n = map.length;
        int m = map[0].length;
        int[][] dp = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[iy][ix] = 0;
        return bfs(map, dp);
    }

    private int[][] bfs(char[][] map, int[][] dp) {
        int n = map.length;
        int m = map[0].length;
        Queue<State> q = new PriorityQueue<>();
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (dp[i][j] != INF)  {
                    q.add(new State(i, j, dp[i][j]));
                }
            }
        }
        while (q.size() >= 1) {
            State st = q.poll();
            int ny = st.ny;
            int nx = st.nx;
            int ti = st.time + 1;
            for (int d = 0; d < 4 ; d++) {
                int ty = ny + dy[d];
                int tx = nx + dx[d];
                if (ty < 0 || tx < 0 || ty >= n || tx >= m || map[ty][tx] == '#') {
                    continue;
                }
                if (dp[ty][tx] > ti) {
                    dp[ty][tx] = ti;
                    q.add(new State(ty, tx, ti));
                }
            }
        }
        return dp;
    }

    static class State implements Comparable<State> {
        int ny;
        int nx;
        int time;

        State(int a, int b, int c) {
            ny = a;
            nx = b;
            time = c;
        }

        @Override
        public int compareTo(State o) {
            return time - o.time;
        }
    }
}
