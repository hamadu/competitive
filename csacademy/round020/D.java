package csacademy.round020;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.InputMismatchException;

public class D {
    private static final long INF = (long)2e18;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        long K = in.nextLong();
        long N = in.nextLong();

        long min = N;
        long max = (Long.MAX_VALUE - N - 10);
        while (max - min > 1) {
            BigInteger bg = BigInteger.valueOf(max);
            bg = bg.add(BigInteger.valueOf(min));
            long med = bg.divide(BigInteger.valueOf(2)).longValue();
            if (countBetween(N, med) >= K) {
                max = med;
            } else {
                min = med;
            }
        }

        out.println(max);
        out.flush();
    }

    private static long countBetween(long min, long max) {
        char[] low;
        char[] high;
        {
            String lower = String.valueOf(min);
            String higher = String.valueOf(max);
            while (lower.length() < higher.length()) {
                lower = '0'+lower;
            }
            low = lower.toCharArray();
            high = higher.toCharArray();
        }

        // m <= n
        int n = high.length;
        long[][][][] dp = new long[n+1][11][2][2];
        dp[0][10][0][0] = 1;
        for (int idx = 0; idx < n ; idx++) {
            int frdigit = low[idx] - '0';
            int todigit = high[idx] - '0';

            for (int last = 0; last <= 10 ; last++) {
                for (int upflg = 0; upflg <= 1; upflg++) {
                    for (int dwflg = 0; dwflg <= 1; dwflg++) {
                        if (dp[idx][last][upflg][dwflg] == 0) {
                            continue;
                        }
                        long base = dp[idx][last][upflg][dwflg];
                        for (int next = 0; next <= 10 ; next++) {
                            if (next <= 9 && last <= 9 && Math.abs(last-next) >= 2) {
                                continue;
                            }
                            if (last == 10 && next == 0) {
                                continue;
                            }
                            if (last <= 9 && next == 10) {
                                continue;
                            }
                            int nextDigit = next % 10;
                            int tup = upflg;
                            int tdw = dwflg;
                            if (upflg == 0) {
                                if (nextDigit < frdigit) {
                                    continue;
                                } else if (frdigit < nextDigit) {
                                    tup = 1;
                                }
                            }
                            if (dwflg == 0) {
                                if (todigit < nextDigit) {
                                    continue;
                                } else if (nextDigit < todigit) {
                                    tdw = 1;
                                }
                            }
                            dp[idx+1][next][tup][tdw] += base;
                            if (dp[idx+1][next][tup][tdw] >= INF) {
                                dp[idx+1][next][tup][tdw] = INF;
                            }
                        }
                    }
                }
            }
        }
        long sum = 0;
        for (int i = 0; i < 10 ; i++) {
            sum += dp[n][i][1][1] + dp[n][i][1][0];
            if (sum >= INF) {
                sum = INF;
            }
        }
        return sum;
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
