package atcoder.tdpc;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class T {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int k = in.nextInt();
        int n = in.nextInt();

        base = new long[k];
        Arrays.fill(base, 1);

        if (n <= k) {
            out.println(1);
        } else {
            long needToProceed = n-1;
            long[] a = new long[k];
            a[0] = 1;
            for (int l = 32 ; l >= 0 ; l--) {
                long ptn = 1L<<l;
                if ((needToProceed & ptn) >= 1) {
                    a = stepTwo(a);
                    a = stepOne(a);
                } else {
                    a = stepTwo(a);
                }
            }

            long ret = 0;
            for (int i = 0; i < k ; i++) {
                ret += a[i];
            }
            out.println(ret % MOD);
        }
        out.flush();
    }

    static long[] base;

    static long[] stepOne(long[] tbl) {
        int k = tbl.length;
        long[] newTbl = new long[k];
        newTbl[0] = tbl[k-1] * base[0] % MOD;
        for (int i = 1; i < k ; i++) {
            newTbl[i] = (tbl[i-1] + tbl[k-1] * base[i]) % MOD;
        }
        return newTbl;
    }

    static long[] stepTwo(long[] tbl) {
        int k = tbl.length;
        long[][] subK = new long[2][];
        long[] ret = new long[k];
        subK[0] = tbl.clone();
        for (int i = 1 ; i <= k ; i++) {
            int to = i%2;
            int fr = 1-to;
            for (int j = 0; j < k; j++) {
                ret[j] += subK[fr][j] * tbl[i-1] % MOD;
            }
            if (i < k) {
                subK[to] = stepOne(subK[fr]);
            }
        }
        for (int i = 0; i < k ; i++) {
            ret[i] %= MOD;
        }
        return ret;
    }

    static final long MOD = (long)1e9+7;

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
