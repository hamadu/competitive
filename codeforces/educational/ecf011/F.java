package codeforces.educational.ecf011;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class F {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long[] a = in.nextLongs(n);
        long ans = 0;
        long[] sum1 = new long[n+1];
        long[] sum2 = new long[n+1];
        for (int i = 1 ; i <= n ; i++) {
            sum1[i] = sum1[i-1] + a[i-1];
            sum2[i] = sum2[i-1] + i * a[i-1];
            ans = Math.max(ans, a[i-1]);
        }

        ConvexHullTech tech = new ConvexHullTech(n+10);

        for (int r = 1 ; r <= n ; r++) {
            tech.addLine(1-r, (r-1)*sum1[r-1]-sum2[r-1]);
            ans = Math.max(ans, sum2[r]+tech.computesMax(sum1[r]));
        }

        out.println(ans);
        out.flush();
    }


    /**
     * Convex hull technique.
     * Given many lines(say y = a_{i} * x + b_{i}), computes many query:
     *   what is the maximum y of current lines with given x?
     *
     * O(n+qlogn) where q = (number of queries), n = (maximum number of lines).
     */
    public static class ConvexHullTech {
        long[][] stk;
        int tail = 0;

        public ConvexHullTech(int maxLines) {
            stk = new long[maxLines][2];
        }

        // add line : ax + b
        public void addLine(long a, long b) {
            stk[tail][0] = a;
            stk[tail][1] = b;
            tail++;
            while (tail >= 3 && compare(stk[tail-3], stk[tail-2], stk[tail-1])) {
                stk[tail-2][0] = stk[tail-1][0];
                stk[tail-2][1] = stk[tail-1][1];
                tail--;
            }
        }

        private boolean compare(long[] l1, long[] l2, long[] l3) {
            long a1 = l1[0];
            long a2 = l2[0];
            long a3 = l3[0];
            long b1 = l1[1];
            long b2 = l2[1];
            long b3 = l3[1];
            return  (a2 - a1) * (b3 - b2) <= (a3 - a2) * (b2 - b1);
        }

        long val(int lidx, long x) {
            return stk[lidx][0] * x + stk[lidx][1];
        }

        public long[] queryMax(long x) {
            int min = -1;
            int max = tail - 1;
            while (max - min > 1) {
                int med = (max + min) / 2;
                if (val(med, x) <= val(med+1, x)) {
                    min = med;
                } else {
                    max = med;
                }
            }
            return stk[max];
        }

        public long computesMax(long x) {
            long[] line = queryMax(x);
            return line[0] * x + line[1];
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
