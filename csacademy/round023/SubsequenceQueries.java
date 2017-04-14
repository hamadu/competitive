package csacademy.round023;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class SubsequenceQueries {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        char[] ch = in.nextToken().toCharArray();

        int[][][] baseMats = new int[9][10][10];
        for (int i = 0; i < 9 ; i++) {
            for (int j = 0; j < 10 ; j++) {
                baseMats[i][j][j] = 1;
            }
            for (int j = 0 ; j < 10 ; j++) {
                baseMats[i][j][i] = 1;
            }
        }

        int q = in.nextInt();
        int[][][] left = new int[ch.length+1][][];
        left[0] = Matrix.e(10);
        for (int i = 0 ; i < ch.length ; i++) {
            left[i+1] = Matrix.mul(baseMats[ch[i]-'a'], left[i]);
        }

        int[][][] invLeft = new int[ch.length+1][][];
        invLeft[ch.length] = Matrix.inv(left[ch.length]);
        for (int i = ch.length-1 ; i >= 0; i--) {
            invLeft[i] = Matrix.mul(invLeft[i+1], baseMats[ch[i]-'a']);
        }

        while (--q >= 0) {
            int l = in.nextInt()-1;
            int r = in.nextInt();
            int[][] leftPart = left[r];
            int[][] invPart = invLeft[l];

            int[][] ans = Matrix.mul(leftPart, invPart);

            long sum = 0;
            for (int i = 0; i < 10 ; i++) {
                sum += ans[9][i];
            }
            sum += Matrix.MOD-1;
            sum %= Matrix.MOD;
            out.println(sum);
        }

        out.flush();
    }

    public static class Matrix {
        static final int MOD = 1000000007;
        static final long MOD2 = (long)MOD * MOD * 8;

        static long pow(long a, long x) {
            long res = 1;
            while (x > 0) {
                if (x % 2 != 0) {
                    res = (res * a) % MOD;
                }
                a = (a * a) % MOD;
                x /= 2;
            }
            return res;
        }

        static long inv(long a) {
            return pow(a, MOD - 2) % MOD;
        }


        static void swapRow(int[][] x, int p, int q) {
            int n = x[0].length;
            for (int i = 0; i < n ; i++) {
                int tmp = x[p][i];
                x[p][i] = x[q][i];
                x[q][i] = tmp;
            }
        }

        static void addRow(int[][] x, int p, int q, int mul) {
            int n = x[0].length;
            mul = mul < 0 ? MOD+mul : mul;
            for (int i = 0; i < n ; i++) {
                int add = (int)(((long) x[q][i]) * mul % MOD);
                x[p][i] += add;
                if (x[p][i] >= MOD) {
                    x[p][i] -= MOD;
                }
            }
        }

        static void mulRow(int[][] x, int p, long mul) {
            int n = x[0].length;
            for (int i = 0; i < n ; i++) {
                long to = (((long) x[p][i]) * mul % MOD);
                x[p][i] = (int)to;
            }
        }

        static int[][] inv(int[][] x) {
            int n = x.length;

            int[][] fr = new int[n][n];
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < n ; j++) {
                    fr[i][j] = x[i][j];
                }
            }
            int[][] to = new int[n][n];
            for (int i = 0; i < n ; i++) {
                to[i][i] = 1;
            }
            for (int i = 0; i < n ; i++) {
                int pos = i;
                while (pos < n && fr[pos][i] == 0) {
                    pos++;
                }
                if (pos != i) {
                    swapRow(fr, pos, i);
                    swapRow(to, pos, i);
                }

                long kake = inv(fr[i][i]);
                mulRow(fr, i, kake);
                mulRow(to, i, kake);

                for (int j = 0; j < n ; j++) {
                    if (i != j) {
                        int bai = -fr[j][i];
                        addRow(to, j, i, bai);
                        addRow(fr, j, i, bai);
                    }
                }
            }
            return to;
        }

        static int[][] e(int n) {
            int[][] mat = new int[n][n];
            for (int i = 0; i < n ; i++) {
                mat[i][i] = 1;
            }
            return mat;
        }


        static int[][] mul(int[][] a, int[][] b) {
            int n = a.length;
            int k = a[0].length;
            int m = b[0].length;

            int[][] ret = new int[n][m];
            long[] row = new long[m];
            for (int i = 0; i < n ; i++) {
                Arrays.fill(row, 0);
                for (int l = 0; l < k ; l++) {
                    for (int j = 0; j < m ; j++) {
                        row[j] += (long)a[i][l] * b[l][j];
                        if (row[j] >= MOD2) {
                            row[j] -= MOD2;
                        }
                    }
                }
                for (int j = 0; j < m ; j++) {
                    ret[i][j] = (int)(row[j] % MOD);
                }
            }
            return ret;
        }
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
