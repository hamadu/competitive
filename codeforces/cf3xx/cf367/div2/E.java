package codeforces.cf3xx.cf367.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/08/14.
 */
public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int q = in.nextInt();
        int[][] a = new int[n+1][m+1];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                a[i+1][j+1] = in.nextInt();
            }
        }
        right = new int[n+1][m+1][2];
        down = new int[n+1][m+1][2];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                right[i][j] = new int[]{i, j+1};
                down[i][j] = new int[]{i+1, j};
            }
        }

        for (int qi = 0; qi < q ; qi++) {
            int y0 = in.nextInt();
            int x0 = in.nextInt();
            int y1 = in.nextInt();
            int x1 = in.nextInt();
            int h = in.nextInt();
            int w = in.nextInt();

            int minX = Math.min(x0, x1)-1;
            int minY = Math.min(y0, y1)-1;

            int[] xymin;
            if (minX < minY) {
                xymin = right(new int[]{minY, 0}, minX);
            } else {
                xymin = down(new int[]{0, minX}, minY);
            }
            int[] a0, a1;
            if (x0 < x1) {
                if (y0 < y1) {
                    a0 = xymin;
                    a1 = down(right(a0, x1-x0),y1-y0);
                } else {
                    a0 = down(xymin, y0-y1);
                    a1 = right(xymin, x1-x0);
                }
            } else {
                if (y0 < y1) {
                    a0 = right(xymin, x0-x1);
                    a1 = down(xymin, y1-y0);
                } else {
                    a1 = xymin;
                    a0 = down(right(a1, x0-x1), y0-y1);
                }
            }
            int[] b0 = down(a0, h);
            int[] c0 = right(a0, w);
            int[] b1 = down(a1, h);
            int[] c1 = right(a1, w);
            int[] ay0 = down(a0, 1);
            int[] ax0 = right(a0, 1);
            int[] ay1 = down(a1, 1);
            int[] ax1 = right(a1, 1);

            // right
            {
                int[] aa0 = ax0;
                int[] aa1 = ax1;
                for (int i = 0; i < w; i++) {
                    if (i >= 1) {
                        aa0 = right[aa0[0]][aa0[1]];
                        aa1 = right[aa1[0]][aa1[1]];
                    }
                    b0 = right[b0[0]][b0[1]];
                    b1 = right[b1[0]][b1[1]];
                    swapDown(aa0, aa1);
                    swapDown(b0, b1);
                }
            }

            // down
            {
                int[] aa0 = ay0;
                int[] aa1 = ay1;
                for (int i = 0; i < h; i++) {
                    if (i >= 1) {
                        aa0 = down[aa0[0]][aa0[1]];
                        aa1 = down[aa1[0]][aa1[1]];
                    }
                    c0 = down[c0[0]][c0[1]];
                    c1 = down[c1[0]][c1[1]];
                    swapRight(aa0, aa1);
                    swapRight(c0, c1);
                }
            }
        }

        int[] head = new int[]{0, 0};
        StringBuilder line = new StringBuilder();
        for (int i = 0 ; i < n ; i++) {
            head = down(head, 1);
            int[] go = head;
            for (int j = 0 ; j < m ; j++) {
                go = right(go, 1);
                if (j >= 1) {
                    line.append(' ');
                }
                line.append(a[go[0]][go[1]]);
            }
            line.append("\n");
        }
        out.println(line.toString());
        out.flush();
    }

    static int[][][] right;
    static int[][][] down;

    private static final int[] right(int[] now, int cnt) {
        while (--cnt >= 0) {
            now = right[now[0]][now[1]];
        }
        return now;
    }

    private static final int[] down(int[] now, int cnt) {
        while (--cnt >= 0) {
            now = down[now[0]][now[1]];
        }
        return now;
    }

    private static final void swapRight(int[] a, int[] b) {
        int[] tmp = right[a[0]][a[1]];
        right[a[0]][a[1]] = right[b[0]][b[1]];
        right[b[0]][b[1]] = tmp;
    }

    private static final void swapDown(int[] a, int[] b) {
        int[] tmp = down[a[0]][a[1]];
        down[a[0]][a[1]] = down[b[0]][b[1]];
        down[b[0]][b[1]] = tmp;
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
