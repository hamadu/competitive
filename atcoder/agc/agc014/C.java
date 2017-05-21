package atcoder.agc.agc014;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class C {

    private static final int INF = 10000000;
    private static int[] dx = new int[]{0, -1, 0, 1};
    private static int[] dy = new int[]{-1, 0, 1, 0};


    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int h = in.nextInt();
        int w = in.nextInt();
        int k = in.nextInt();

        char[][] maze = new char[h][];
        int sy = 0;
        int sx = 0;
        for (int i = 0; i < h ; i++) {
            maze[i] = in.nextToken().toCharArray();
            for (int j = 0; j < w ; j++) {
                if (maze[i][j] == 'S') {
                    sy = i;
                    sx = j;
                }
            }
        }

        int[] que = new int[2000000];
        int qh = 0;
        int qt = 0;
        int[][] dp = new int[h][w];
        for (int i = 0; i < h ; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[sy][sx] = 0;
        que[qh++] = sy;
        que[qh++] = sx;
        while (qt < qh) {
            int ny = que[qt++];
            int nx = que[qt++];
            int tt = dp[ny][nx]+1;
            for (int d = 0 ;  d < 4 ; d++) {
                int ty = ny+dy[d];
                int tx = nx+dx[d];
                if (ty < 0 || tx < 0 || ty >= h || tx >= w || maze[ty][tx] == '#') {
                    continue;
                }
                if (dp[ty][tx] > tt) {
                    dp[ty][tx] = tt;
                    que[qh++] = ty;
                    que[qh++] = tx;
                }
            }
        }

        int best = INF;
        for (int i = 0; i < h ; i++) {
            for (int j = 0; j < w ; j++) {
                int distToOut = Math.min(Math.min(i+1, h-i), Math.min(j+1, w-j));
                if (maze[i][j] == '#') {
                    for (int d = 0 ; d < 4 ; d++) {
                        int ty = i+dy[d];
                        int tx = j+dx[d];
                        if (ty < 0 || tx < 0 || ty >= h || tx >= w || maze[ty][tx] == '#') {
                            continue;
                        }
                        int come = dp[ty][tx];
                        if (come <= k) {
                            best = Math.min(best, 1 + (distToOut + k - 1) / k);
                        } else {
                            int totalCost = come + distToOut;
                            best = Math.min(best, (totalCost + k - 1) / k);
                        }
                    }
                } else {
                    int come = dp[i][j];
                    if (i == 0 || i == h-1 || j == 0 || j == w-1) {
                        best = Math.min(best, (come + k - 1) / k);
                    }
                }
            }
        }

        out.println(best);
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

        private int[] nextInts(int n) {
            int[] ret = new int[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextInt();
            }
            return ret;
        }

        private int[][] nextIntTable(int n, int m) {
            int[][] ret = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextInt();
                }
            }
            return ret;
        }

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
            }
            return ret;
        }

        private long[][] nextLongTable(int n, int m) {
            long[][] ret = new long[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextLong();
                }
            }
            return ret;
        }

        private double[] nextDoubles(int n) {
            double[] ret = new double[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextDouble();
            }
            return ret;
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
                res += c-'0';
                c = next();
            } while (!isSpaceChar(c));
            return res*sgn;
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
                res += c-'0';
                c = next();
            } while (!isSpaceChar(c));
            return res*sgn;
        }

        public double nextDouble() {
            return Double.valueOf(nextToken());
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
