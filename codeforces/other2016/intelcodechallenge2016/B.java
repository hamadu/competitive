package codeforces.other2016.intelcodechallenge2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[][] tbl = in.nextIntTable(n, m);
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                tbl[i][j]--;
            }
        }

        out.println(isOK(tbl) ? "YES" : "NO");
        out.flush();
    }

    private static boolean isOK(int[][] tbl) {
        int n = tbl.length;
        int m = tbl[0].length;

        if (canMakeTable(tbl)) {
            return true;
        }

        for (int i = 0 ; i < m ; i++) {
            for (int j = i+1 ; j < m ; j++) {
                int[][] swapedTable = new int[n][m];
                for (int k = 0; k < n ; k++) {
                    swapedTable[k] = tbl[k].clone();
                }
                for (int k = 0; k < n ; k++) {
                    int tmp = swapedTable[k][i];
                    swapedTable[k][i] = swapedTable[k][j];
                    swapedTable[k][j] = tmp;
                }
                if (canMakeTable(swapedTable)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean canMakeTable(int[][] table) {
        boolean can = true;
        int n = table.length;
        for (int i = 0; i < n ; i++) {
            can &= canMakeRow(table[i]);
        }
        return can;
    }

    private static boolean canMakeRow(int[] row) {
        int n = row.length;
        int I = -1;
        int J = -1;
        for (int i = 0; i < n ; i++) {
            if (row[i] != i) {
                if (I == -1) {
                    I = i;
                } else if (J == -1) {
                    J = i;
                } else {
                    return false;
                }
            }
        }
        if (I == -1) {
            return true;
        }
        if (J == -1) {
            return false;
        }
        return row[row[I]] == I && row[row[J]] == J;
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