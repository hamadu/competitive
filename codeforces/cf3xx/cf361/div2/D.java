package codeforces.cf3xx.cf361.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.TreeSet;

/**
 * Created by hama_du on 2016/08/07.
 */
public class D {
    private static final int INF = (int)1e9+1;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = in.nextInts(n);
        int[] b = in.nextInts(n);
        sega = new int[18][n];
        segb = new int[18][n];
        sega[0] = a;
        segb[0] = b;
        for (int k = 0 ; k+1 < 18 ; k++) {
            for (int i = 0 ; i < n ; i++) {
                int ii = i+(1<<k);
                if (ii < n) {
                    sega[k+1][i] = Math.max(sega[k][i], sega[k][ii]);
                    segb[k+1][i] = Math.min(segb[k][i], segb[k][ii]);
                } else {
                    sega[k+1][i] = sega[k][i];
                    segb[k+1][i] = segb[k][i];
                }
            }
        }

        long cnt = 0;
        for (int i = 0; i < n ; i++) {
            cnt += findG(i) - findGEQ(i);
        }
        out.println(cnt);
        out.flush();
    }

    static int[][] sega;
    static int[][] segb;

    static int findG(int l) {
        int a = -INF;
        int b = INF;
        for (int k = sega.length-1 ;  k >= 0 ; k--) {
            if (l >= sega[0].length) {
                break;
            }
            if (Math.max(a, sega[k][l]) <= Math.min(b, segb[k][l])) {
                a = Math.max(a, sega[k][l]);
                b = Math.min(b, segb[k][l]);
                l += 1<<k;
            }
        }
        return Math.min(l, sega[0].length);
    }

    static int findGEQ(int l) {
        int a = -INF;
        int b = INF;
        for (int k = sega.length-1 ;  k >= 0 ; k--) {
            if (l > sega[0].length) {
                break;
            }
            if (Math.max(a, sega[k][l]) < Math.min(b, segb[k][l])) {
                a = Math.max(a, sega[k][l]);
                b = Math.min(b, segb[k][l]);
                l += 1<<k;
            }
        }
        return Math.min(l, sega[0].length);
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
