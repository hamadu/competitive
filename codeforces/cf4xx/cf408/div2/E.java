package codeforces.cf4xx.cf408.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int p = in.nextInt();
        int k = in.nextInt();
        int[] a = new int[n+100];
        int[] b = new int[n+100];
        int an = in.nextInt();
        for (int i = 0; i < an ; i++) {
            a[in.nextInt()-1] = 1;
        }
        int bn = in.nextInt();
        for (int i = 0; i < bn ; i++) {
            b[in.nextInt()-1] = 1;
        }

        out.println(solve(n, p, k, b, a));
        out.flush();
    }

    private static int solve(int n, int p, int k, int[] a, int[] b) {
        int[] absum = new int[n+100];
        for (int i = 0; i < n ; i++) {
            absum[i+1] = absum[i] + Math.max(a[i], b[i]);
        }

        int[][][] dp = new int[2][n+2][k+1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[0][i], -1);
            Arrays.fill(dp[1][i], -1);
        }

        int best = 0;
        dp[0][0][0] = 0;
        for (int peek = 0; peek <= p ; peek++) {
            int fr = peek % 2;
            int to = 1 - fr;
            for (int idx = 0; idx <= n ; idx++) {
                Arrays.fill(dp[to][idx], -1);
            }
            for (int idx = 0; idx <= n ; idx++) {
                int addOne = peek % 2 == 1 ? a[idx] : b[idx];
                for (int l = 0 ; l <= k ; l++) {
                    int base = dp[fr][idx][l];
                    if (base == -1) {
                        continue;
                    }

                    if (l == 0) {
                        dp[fr][idx+1][0] = Math.max(dp[fr][idx+1][0], base);
                        dp[to][idx][k] = Math.max(dp[to][idx][k], base);
                    } else {
                        dp[fr][idx+1][l-1] = Math.max(dp[fr][idx+1][l-1], base+addOne);
                        if (peek < p) {
                            int rl = k-l;
                            int tidx = Math.min(n, idx+l);
                            int addTwo = absum[idx+l]-absum[idx];
                            dp[to][tidx][rl] = Math.max(dp[to][tidx][rl], base+addTwo);
                        }
                    }
                }
            }

            for (int idx = 0; idx <= n ; idx++) {
                for (int l = 0; l <= k; l++) {
                    best = Math.max(dp[to][idx][l], best);
                }
            }
        }
        return best;
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
