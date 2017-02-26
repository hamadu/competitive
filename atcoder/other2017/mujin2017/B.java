package atcoder.other2017.mujin2017;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class B {
    static final int INF = 100000;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] basetbl = new int[n][n];
        for (int i = 0; i < n ; i++) {
            char[] row = in.nextToken().toCharArray();
            for (int j = 0; j < n ; j++) {
                basetbl[i][j] = row[j] == '.' ? 0 : 1;
            }
        }
        int ret = solve2(basetbl);
        out.println(ret >= INF ? -1 : ret);
        out.flush();
    }


    static int solve2(int[][] basetbl) {
        int n = basetbl.length;

        boolean hasOne = false;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                hasOne |= basetbl[i][j] == 1;
            }
        }
        if (!hasOne) {
            return INF;
        }

        int[] tr = new int[n];
        for (int i = 0; i < n ; i++) {
            int[][] tbl = clone2(basetbl);
            int time = 0;
            if (tbl[i][i] == 0) {
                boolean hasSub = false;
                for (int j = 0; j < n ; j++) {
                    if (tbl[j][i] == 1) {
                        hasSub = true;
                    }
                }
                if (!hasSub) {
                    for (int j = 0; j < n; j++) {
                        int cnt = 0;
                        for (int k = 0; k < n; k++) {
                            cnt += tbl[j][k];
                        }
                        if (cnt >= 1) {
                            int[] tmp = tbl[j].clone();
                            for (int k = 0; k < n; k++) {
                                tbl[k][i] = tmp[k];
                            }
                            time++;
                            break;
                        }
                    }
                }
                for (int j = 0; j < n ; j++) {
                    if (tbl[j][i] == 1) {
                        int[] tmp = tbl[j].clone();
                        for (int k = 0; k < n ; k++) {
                            tbl[k][i] = tmp[k];
                        }
                        time++;
                        break;
                    }
                }
            }
            int[] tmp = tbl[i].clone();
            for (int j = 0; j < n ; j++) {
                if (tbl[i][j] == 0) {
                    for (int k = 0; k < n ; k++) {
                        tbl[k][j] = tmp[k];
                    }
                    time++;
                }
            }
            for (int j = 0; j < n ; j++) {
                int cnt = 0;
                for (int k = 0; k < n ; k++) {
                    cnt += tbl[k][j];
                }
                time += cnt < n ? 1 : 0;
            }
            tr[i] = time;
        }
        int ret = INF;
        for (int i = 0; i < n ; i++) {
            ret = Math.min(ret, tr[i]);
        }
        return ret;
    }

    static int[][] clone2(int[][] a) {
        int[][] b = new int[a.length][];
        for (int i = 0; i < a.length; i++) {
            b[i] = a[i].clone();
        }
        return b;
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
