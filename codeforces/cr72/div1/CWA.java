package codeforces.cr72.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by dhamada on 15/05/30.
 */
public class CWA {

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void dfs(boolean[] available, int idx, int cnt, int k) {
        if (1 <= cnt && cnt <= k) {
            solve(available);
        }
        if (idx+1 <= 'z') {
            if (cnt < k) {
                available[idx] = true;
                dfs(available, idx + 1, cnt + 1, k);
                available[idx] = false;
            }
            dfs(available, idx + 1, cnt, k);
        }
    }

    private static void solve(boolean[] available) {
        int qh = 0;
        int qt = 0;
        q[qh++] = sx;
        q[qh++] = sy;
        q[qh++] = 0;

        for (int i = 0; i < 51 ; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        for (int i = 0; i < 51 ; i++) {
            for (int j = 0; j < 51 ; j++) {
                Arrays.fill(dpFrom[i][j], -1);
            }
        }
        dp[sy][sx] = 0;

        while (qt < qh) {
            int nx = q[qt++];
            int ny = q[qt++];
            int time = q[qt++];
            for (int i = 0; i < 4; i++) {
                int tx = nx + dx[i];
                int ty = ny + dy[i];
                if (ty < 0 || tx < 0 || ty >= map.length || tx >= map[0].length) {
                    continue;
                }
                char c = map[ty][tx];
                if (!available[c]) {
                    continue;
                }
                int tt = time+1;
                boolean upd = false;
                if (dp[ty][tx] > tt) {
                    upd = true;
                } else if (dp[ty][tx] == tt && map[ny][nx] < dpFrom[ty][tx][2]) {
                    upd = true;
                }
                if (upd) {
                    dp[ty][tx] = tt;
                    dpFrom[ty][tx][0] = nx;
                    dpFrom[ty][tx][1] = ny;
                    dpFrom[ty][tx][2] = map[ny][nx];
                    q[qh++] = tx;
                    q[qh++] = ty;
                    q[qh++] = tt;
                }
            }
        }
        if (dp[gy][gx] <= best) {
            int nx = gx;
            int ny = gy;

            char[] c = new char[dp[gy][gx]-1];

            int idx = dp[gy][gx]-1;
            while (true) {
                int tx = dpFrom[ny][nx][0];
                int ty = dpFrom[ny][nx][1];
                if (tx == sx && ty == sy) {
                    break;
                }
                nx = tx;
                ny = ty;
                c[--idx] = map[ny][nx];
            }
            debug(c, dpFrom[1][2]);

            if (dp[gy][gx] < best) {
                best = dp[gy][gx];
                bestCode = c;
            } else if (dp[gy][gx] == best) {
                int len = bestCode.length;

                if (len != c.length) {
                    throw new RuntimeException("ee " + len + "/" + c.length);
                }

                int sign = 0;
                for (int i = 0 ; i < len ; i++) {
                    if (c[i] < bestCode[i]) {
                        sign = -1;
                        break;
                    } else if (c[i] > bestCode[i]) {
                        sign = 1;
                        break;
                    }
                }
                if (sign < 0) {
                    bestCode = c;
                }
            }
        }
    }

    static int best = 1000000;
    static char[] bestCode = new char[0];

    static int[] q = new int[50100];
    static int[][] dp = new int[51][51];
    static int[][][] dpFrom = new int[51][51][3];

    static char[][] map;
    static int sx, sy;
    static int gx, gy;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();

        map = new char[n][m];
        for (int i = 0; i < n; i++) {
            map[i] = in.nextToken().toCharArray();
            for (int j = 0; j < m ; j++) {
                if (map[i][j] == 'S') {
                    sx = j;
                    sy = i;
                } else if (map[i][j] == 'T') {
                    gx = j;
                    gy = i;
                }
            }
        }

        boolean[] available = new boolean[255];
        available['S'] = available['T'] = true;
        dfs(available, 'a', 0, k);

        if (best >= 1000000) {
            out.println(-1);
        } else {
            out.println(String.valueOf(bestCode));
        }
        out.flush();
    }

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
                return (char)c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char)c;
            }
            throw new InputMismatchException();
        }

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char)c);
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
