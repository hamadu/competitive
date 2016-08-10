package codeforces.cf3xx.cf366.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/08/10.
 */
public class B {
    private static final long INF = (long)1e18;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int s = in.nextInt()-1;
        int e = in.nextInt()-1;
        long[] x = in.nextLongs(n);
        long[] a = in.nextLongs(n);
        long[] b = in.nextLongs(n);
        long[] c = in.nextLongs(n);
        long[] d = in.nextLongs(n);


        // a: lands large
        // b: lands small
        for (int i = 0; i < n ; i++) {
            a[i] += x[i];
            b[i] -= x[i];
        }

        // c: jumps left
        // d: jumps right
        for (int i = 0; i < n ; i++) {
            c[i] += x[i];
            d[i] -= x[i];
        }

        long[][][][] dp = new long[2][2][2][n+1];
        for (int i = 0; i < 2 ; i++) {
            for (int j = 0; j < 2 ; j++) {
                Arrays.fill(dp[0][i][j], INF);
            }
        }

        // [idx][give][take][both]
        dp[0][0][0][0] = 0;
        for (int idx = 0; idx < n ; idx++) {
            int fr = idx % 2;
            int to = 1 - fr;
            for (int i = 0; i < 2 ; i++) {
                for (int j = 0; j < 2 ; j++) {
                    Arrays.fill(dp[to][i][j], INF);
                }
            }
            for (int give = 0 ; give <= 1 ; give++) {
                for (int take = 0 ; take <= 1 ; take++) {
                    for (int both = 0 ; both <= n ; both++) {
                        if (dp[fr][give][take][both] == INF) {
                            continue;
                        }
                        long base = dp[fr][give][take][both];

                        if (idx == s) {
                            // don't connect any
                            dp[to][1][take][both] = Math.min(dp[to][1][take][both], base+d[idx]);

                            // connect to component
                            if (both >= 1) {
                                dp[to][1][take][both-1] = Math.min(dp[to][1][take][both-1], base+c[idx]);
                            }

                            // finish
                            if (idx == n-1 && take >= 1) {
                                dp[to][0][0][both] = Math.min(dp[to][0][0][both], base+c[idx]);
                            }
                        } else if (idx == e) {
                            // don't connect any
                            dp[to][give][1][both] = Math.min(dp[to][give][1][both], base+b[idx]);

                            // connect to component
                            if (both >= 1) {
                                dp[to][give][1][both-1] = Math.min(dp[to][give][1][both-1], base+a[idx]);
                            }

                            // finish
                            if (idx == n-1 && give >= 1) {
                                dp[to][0][0][both] = Math.min(dp[to][0][0][both], base+a[idx]);
                            }
                        } else {
                            // throw both
                            if (both+1 <= n) {
                                dp[to][give][take][both+1] = Math.min(dp[to][give][take][both+1], base+b[idx]+d[idx]);
                            }

                            // take from
                            if (both >= 1 || give == 1) {
                                dp[to][give][take][both] = Math.min(dp[to][give][take][both], base+a[idx]+d[idx]);
                            }

                            // take to
                            if (both >= 1 || take == 1) {
                                dp[to][give][take][both] = Math.min(dp[to][give][take][both], base+b[idx]+c[idx]);
                            }

                            // take both
                            if (both >= 2) {
                                dp[to][give][take][both-1] = Math.min(dp[to][give][take][both-1], base+a[idx]+c[idx]);
                            }

                            // take both
                            if (idx == n-1 && give >= 1 && take >= 1) {
                                dp[to][0][0][both] = Math.min(dp[to][0][0][both], base+a[idx]+c[idx]);
                            }

                            // take both
                            if ((give >= 1 && both >= 1) || (take >= 1 && both >= 1)) {
                                dp[to][give][take][both-1] = Math.min(dp[to][give][take][both-1], base+a[idx]+c[idx]);
                            }
                        }
                    }
                }
            }
        }

        out.println(dp[n%2][0][0][0]);
        out.flush();
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
