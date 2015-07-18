package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by hama_du on 15/07/17.
 */
public class P1329 {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            int m = in.nextInt();
            if (n + m == 0) {
                break;
            }
            char[][] map = new char[n][];
            for (int i = 0; i < n ; i++) {
                map[i] = in.nextToken().toCharArray();
            }

            int open1 = -1;
            int open2 = -1;
            int king = -1;
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < m ; j++) {
                    if (map[i][j] == '.') {
                        if (open1 == -1) {
                            open1 = i * m + j;
                        } else {
                            open2 = i * m + j;
                        }
                    } else if (map[i][j] == 'X') {
                        map[i][j] = 'o';
                        if (king == -1) {
                            king = i * m + j;
                        }
                    } else if (map[i][j] == '*') {
                        map[i][j] = '#';
                    }
                }
            }
            out.println(solve(map, king, open1, open2));
        }
        out.flush();
    }

    static int[] kx = {0, 1, 0, 1};
    static int[] ky = {0, 0, 1, 1};

    static int[] sx = {-1, -1, 0, 1, 0, 1, 2, 2};
    static int[] sy = {0, 1, -1, -1, 2, 2, 0, 1};

    static int[] dx = {-1, 0, 0, 1};
    static int[] dy = {0, -1, 1, 0};

    static int INF = 100000000;

    private static int solve(char[][] map, int king, int open1, int open2) {
        int n = map.length;
        int m = map[0].length;
        if (king == 0) {
            return 0;
        }

        // compute move cost
        int[][][][] moveCost = new int[n-1][m-1][4][4];
        for (int i = 0; i < n-1 ; i++) {
            for (int j = 0; j < m-1 ; j++) {
                for (int k = 0; k < 4 ; k++) {
                    Arrays.fill(moveCost[i][j][k], INF);
                }

                if (map[i][j] == '#' || map[i+1][j] == '#' || map[i][j+1] == '#' || map[i+1][j+1] == '#') {
                    continue;
                }

                int[][] table = new int[8][8];
                for (int d = 0 ; d < 8 ; d++) {
                    Arrays.fill(table[d], INF);
                    int py = i + sy[d];
                    int px = j + sx[d];
                    if (py < 0 || px < 0 || py >= n || px >= m || map[py][px] == '#') {
                        continue;
                    }
                    int[][] time = bfs(py, px, map, i, j);
                    for (int f = 0; f < 8 ; f++) {
                        int qy = i + sy[f];
                        int qx = j + sx[f];
                        if (qy < 0 || qx < 0 || qy >= n || qx >= m || map[qy][qx] == '#') {
                            continue;
                        }
                        table[d][f] = time[qy][qx];
                    }
                }

                for (int d = 0 ; d < 8 ; d += 2) {
                    for (int f = d + 2 ; f < 8 ; f += 2) {
                        int ptnA = table[d][f] + table[d+1][f+1];
                        int ptnB = table[d+1][f] + table[d][f+1];
                        moveCost[i][j][d/2][f/2] = moveCost[i][j][f/2][d/2] = Math.min(ptnA, ptnB);
                    }
                }
            }
        }

        // compute initial cost
        int[][] timeA = bfs(open1 / m, open1 % m, map, king / m, king % m);
        int[][] timeB = bfs(open2 / m, open2 % m, map, king / m, king % m);

        // find the minimum cost
        Queue<State> q = new PriorityQueue<State>();
        int iky = king / m;
        int ikx = king % m;
        int[][][] dp = new int[n-1][m-1][4];
        for (int i = 0; i < n-1 ; i++) {
            for (int j = 0; j < m-1 ; j++) {
                Arrays.fill(dp[i][j], INF);
            }
        }
        for (int i = 0; i < 8 ; i += 2) {
            int oy1 = iky + sy[i];
            int ox1 = ikx + sx[i];
            int oy2 = iky + sy[i+1];
            int ox2 = ikx + sx[i+1];
            if (oy1 < 0 || oy2 < 0 || ox1 < 0 || ox2 < 0 || oy1 >= n || oy2 >= n || ox1 >= m || ox2 >= m) {
                continue;
            }
            int cost = Math.min(timeA[oy1][ox1] + timeB[oy2][ox2], timeB[oy1][ox1] + timeA[oy2][ox2]);
            if (cost < INF) {
                dp[iky][ikx][i / 2] = cost;
                q.add(new State(iky, ikx, i/2, cost));
            }
        }

        while (q.size() >= 1) {
            State st = q.poll();

            // debug(st.y,st.x,st.d,st.time);
            // move pawn to another space
            for (int td = 0; td < 4; td++) {
                int ty = st.y;
                int tx = st.x;
                int tcost = st.time + moveCost[st.y][st.x][st.d][td];
                if (tcost < INF && dp[ty][tx][td] > tcost) {
                    dp[ty][tx][td] = tcost;
                    q.add(new State(ty, tx, td, tcost));
                }
            }

            // move king to the open direction
            {
                int ty = st.y + dy[st.d];
                int tx = st.x + dx[st.d];
                int td = 3 - st.d;
                if (ty < 0 || tx < 0 || ty >= n-1 || tx >= m-1) {
                    continue;
                }
                int tcost = st.time + 1;
                // debug(ty,tx,td,tcost);
                if (tcost < INF && dp[ty][tx][td] > tcost) {
                    dp[ty][tx][td] = tcost;
                    q.add(new State(ty, tx, td, tcost));
                }
            }
        }

        int best = INF;
        for (int i = 0; i < 4; i++) {
            best = Math.min(best, dp[0][0][i]);
        }
        return best == INF ? -1 : best;
    }

    static class State implements Comparable<State> {
        int y;
        int x;
        int d;
        int time;

        State(int i, int j, int di, int t) {
            y = i;
            x = j;
            d = di;
            time = t;
        }

        @Override
        public int compareTo(State o) {
            return time - o.time;
        }
    }

    static int[][] bfs(int sy, int sx, char[][] map, int iky, int ikx) {
        for (int k = 0; k < 4 ; k++) {
            map[iky+ky[k]][ikx+kx[k]] = '#';
        }
        int h = map.length;
        int w = map[0].length;

        int[][] dp = new int[h][w];
        for (int i = 0; i < h ; i++) {
            Arrays.fill(dp[i], INF);
        }
        for (int k = 0; k < 4 ; k++) {
            map[iky][ikx] = '#';
        }
        dp[sy][sx] = 0;

        int qh = 0;
        int qt = 0;
        que[qh++] = sy;
        que[qh++] = sx;
        que[qh++] = 0;
        while (qt < qh) {
            int ny = que[qt++];
            int nx = que[qt++];
            int time = que[qt++];
            for (int i = 0 ; i < 4 ; i++) {
                int ty = ny + dy[i];
                int tx = nx + dx[i];
                if (ty < 0 || tx < 0 || ty >= h || tx >= w || map[ty][tx] == '#') {
                    continue;
                }
                if (dp[ty][tx] > time+1) {
                    dp[ty][tx] = time+1;
                    que[qh++] = ty;
                    que[qh++] = tx;
                    que[qh++] = time+1;
                }
            }
        }
        for (int k = 0; k < 4 ; k++) {
            map[iky+ky[k]][ikx+kx[k]] = 'o';
        }
        return dp;
    }

    static int[] que = new int[10000];

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int next() {
            if (numChars == -1)
                throw new InputMismatchException();
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public char nextChar() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            if ('a' <= c && c <= 'z') {
                return (char) c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char) c;
            }
            throw new InputMismatchException();
        }

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char) c);
                c = next();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public int nextInt() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = next();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = next();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public long nextLong() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            long sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = next();
            }
            long res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = next();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
        }
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
