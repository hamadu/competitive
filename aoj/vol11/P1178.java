package aoj.vol11;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/23.
 */
public class P1178 {
    static int[] dx = {1, 0, 0, -1};
    static int[] dy = {0, 1, -1, 0};

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            int m = in.nextInt();
            if (n + m == 0) {
                break;
            }
            int[][][] map = new int[n][m][4];
            for (int i = 0; i < 2*n-1 ; i++) {
                int h = i / 2;
                if (i % 2 == 0) {
                    for (int j = 0; j < m-1 ; j++) {
                        int x = in.nextInt();
                        map[h][j][0] = map[h][j+1][3] = x;
                    }
                } else {
                    for (int j = 0; j < m ; j++) {
                        int x = in.nextInt();
                        map[h][j][1] = map[h+1][j][2] = x;
                    }
                }
            }
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < m ; j++) {
                    for (int k = 0; k < 4 ; k++) {
                        int y = i + dy[k];
                        int x = j + dx[k];
                        if (x < 0 || y < 0 || x >= m || y >= n) {
                            map[i][j][k] = 1;
                        }
                    }
                }
            }
            out.println(solve(map));
        }
        out.flush();
    }

    private static int solve(int[][][] M) {
        map = M;
        int n = map.length;
        int m = map[0].length;
        int[][] best = new int[n][m];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(best[i], INF);
        }
        memo = new int[n][m][4];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                Arrays.fill(memo[i][j], INF);
            }
        }

        best[n-1][m-1] = 0;
        boolean upd = true;
        while (upd) {
            upd = false;
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < m ; j++) {
                    if (best[i][j] == INF) {
                        continue;
                    }
                    for (int d = 0; d < 4 ; d++) {
                        if (map[i][j][d] == 1) {
                            continue;
                        }
                        int ti = i + dy[d];
                        int tj = j + dx[d];
                        int worst = Math.max(bfs(ti, tj, 3-d), best[i][j]+1);
                        if (best[ti][tj] > worst) {
                            best[ti][tj] = worst;
                            upd = true;
                        }
                    }
                }
            }
            if (!upd) {
                break;
            }
        }
        return best[0][0] >= INF ? -1 : best[0][0];
    }

    static int[][][] memo;
    static final int INF = 1000000;

    static int bfs(int sy, int sx, int dir) {
        if (memo[sy][sx][dir] != INF) {
            return memo[sy][sx][dir];
        }
        map[sy][sx][dir] = 1;

        int qh = 0;
        int qt = 0;
        int h = map.length;
        int w = map[0].length;
        int[][] dp = new int[h][w];
        for (int i = 0; i < h ; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[sy][sx] = 0;
        _que[qh++] = sy;
        _que[qh++] = sx;
        _que[qh++] = 0;
        while (qt < qh) {
            int y = _que[qt++];
            int x = _que[qt++];
            int t = _que[qt++];
            int tt = t+1;
            for (int d = 0; d < 4; d++) {
                if (map[y][x][d] == 0) {
                    int ty = y+dy[d];
                    int tx = x+dx[d];
                    if (dp[ty][tx] > tt) {
                        dp[ty][tx] = tt;
                        _que[qh++] = ty;
                        _que[qh++] = tx;
                        _que[qh++] = tt;
                    }
                }
            }
        }
        map[sy][sx][dir] = 0;
        memo[sy][sx][dir] = dp[h-1][w-1];
        return dp[h-1][w-1];
    }

    static int[] _que = new int[25000];
    static int[][][] map;

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
