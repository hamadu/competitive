package codeforces.cf3xx.cf352.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/07/26.
 */
public class A {
    private static final double INF = 1e30;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        long ay = in.nextInt();
        long ax = in.nextInt();
        long by = in.nextInt();
        long bx = in.nextInt();
        long ty = in.nextInt();
        long tx = in.nextInt();
        long[][] pos = new long[][]{ {ay, ax}, {by, bx}, {ty, tx} };

        int n = in.nextInt();
        long[][] bottles = in.nextLongTable(n, 2);

        double[][] dists = new double[n][3];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 3 ; j++) {
                dists[i][j] = dist2(pos[j][0]-bottles[i][0], pos[j][1]-bottles[i][1]);
            }
        }

        double best = INF;

        for (int k = 0 ; k <= 1 ; k++) {
            // Adil or Bera take all
            double sum = 0;
            double cheapest = -INF;
            for (int i = 0; i < n ; i++) {
                sum += dists[i][2] * 2;
                cheapest = Math.max(cheapest, dists[i][2] - dists[i][k]);
            }
            sum -= cheapest;
            best = Math.min(best, sum);
        }

        if (n >= 2) {
            // each take at least one bottle
            for (int[] ord : new int[][]{{0,1},{1,0}}) {
                double sum = 0;
                for (int i = 0; i < n ; i++) {
                    sum += dists[i][2] * 2;
                }

                int takenID = -1;
                for (int oi = 0 ; oi <= 1; oi++) {
                    double cheapest = -INF;
                    for (int i = 0; i < n ; i++) {
                        if (oi == 1 && i == takenID) {
                            continue;
                        }
                        double co =  dists[i][2] - dists[i][ord[oi]];
                        if (cheapest < co) {
                            cheapest = co;
                            if (oi == 0) {
                                takenID = i;
                            }
                        }
                    }
                    sum -= cheapest;
                }
                best = Math.min(best, sum);
            }
        }
        out.println(String.format("%.9f", best));
        out.flush();
    }

    static double dist2(long dx, long dy) {
        return Math.sqrt(dx*dx+dy*dy);
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
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < m ; j++) {
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
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < m ; j++) {
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
