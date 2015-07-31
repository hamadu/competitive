package aoj.vol11;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/31.
 */
public class P1144 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int w = in.nextInt();
            int h = in.nextInt();
            if (w + h == 0) {
                break;
            }

            int sy = -1;
            int sx = -1;
            int gy = -1;
            int gx = -1;
            int[][] map = new int[h][w];
            for (int i = 0; i < h ; i++) {
                for (int j = 0; j < w ; j++) {
                    map[i][j] = in.nextInt();
                    if (map[i][j] == 2) {
                        sy = i;
                        sx = j;
                        map[i][j] = 0;
                    } else if (map[i][j] == 3) {
                        gy = i;
                        gx = j;
                        map[i][j] = 0;
                    }
                }
            }
            best = 11;
            solve(map, sy, sx, gy, gx, 0);
            out.println(best >= 11 ? -1 : best);
        }

        out.flush();
    }

    static int best = 11;

    private static void solve(int[][] map, int ny, int nx, int gy, int gx, int turn) {
        if (turn >= best) {
            return;
        }

        for (int d = 0; d < 4 ; d++) {
            for (int s = 1; s < 20 ; s++) {
                int ty = ny + dy[d] * s;
                int tx = nx + dx[d] * s;
                if (ty < 0 || tx < 0 || ty >= map.length || tx >= map[0].length) {
                    break;
                }
                if (s == 1 && map[ty][tx] == 1) {
                    break;
                }

                if (ty == gy && tx == gx) {
                    best = Math.min(best, turn+1);
                    break;
                }
                if (map[ty][tx] == 1) {
                    map[ty][tx] = 0;
                    solve(map, ty - dy[d], tx - dx[d], gy, gx, turn+1);
                    map[ty][tx] = 1;
                    break;
                }
            }
        }
    }

    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};

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
