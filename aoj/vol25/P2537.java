package aoj.vol25;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/22.
 */
public class P2537 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            if (n == 0) {
                break;
            }
            int w = in.nextInt();
            int h = in.nextInt();
            int r = in.nextInt();
            int vx = in.nextInt();
            int vy = in.nextInt();
            int[][] balls = new int[n][2];
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < 2 ; j++) {
                    balls[i][j] = in.nextInt();
                }
            }
            out.println(solve(w, h, r, vx, vy, balls));
        }

        out.flush();
    }

    private static boolean solve(int w, int h, int r, int vx, int vy, int[][] balls) {
        return false;
    }

    private static boolean lineToCircle(int x0, int y0, int x1, int y1, int sx, int sy, int r1r2) {
        long r2 = r1r2 * r1r2;
        long d0 = d2(x0, y0, sx, sy);
        long d1 = d2(x1, y1, sx, sy);
        if (d0 <= r2 || d1 <= r2) {
            return true;
        }

        long a2 = d2(x1 - x0, y1 - y0);
        long cr = cross2(x1 - x0, y1 - y0, sx - x0, sy - y0);
        long cr2 = cr * cr;
        return false;
    }

    private static long d2(long ax, long ay, long bx, long by) {
        return d2(bx - ax, by - ay);
    }

    private static long d2(long dx, long dy) {
        return dx * dx + dy * dy;
    }

    private static long cross2(long ax, long ay, long bx, long by) {
        return ax * bx + ay * by;
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
