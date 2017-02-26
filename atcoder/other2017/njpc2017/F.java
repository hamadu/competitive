package atcoder.other2017.njpc2017;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class F {
    private static final double INF = 1e18;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long[][] balls = in.nextLongTable(n, 2);

        dp = new double[n+1][2];
        double max = 1e9;
        double min = 0;
        for (int cur = 0 ; cur < 60 ; cur++) {
            double med = (max+min)/2;
            if (isOK(med, balls)) {
                max = med;
            } else {
                min = med;
            }
        }

        out.println(String.format("%.9f", max));
        out.flush();
    }

    static double[][] dp;

    private static boolean isOK(double v, long[][] balls) {
        int n = balls.length;

        double prevT = 0;
        double prevX = 0;
        for (int i = 0; i <= n ; i++) {
            dp[i][0] = 1;
            dp[i][1] = -1;
        }
        dp[0][0] = dp[0][1] = 0;

        for (int i = 0; i < n ; i++) {
            if (dp[i][1] - dp[i][0] < 0) {
                continue;
            }

            double D = v*(balls[i][0]-prevT);
            double fr = dp[i][0]-D;
            double to = dp[i][1]+D;
            double needV = Math.abs(balls[i][1] - prevX) / (balls[i][0] - prevT);

            double ff = INF;
            double tt = -INF;

            if (needV <= v) {
                ff = Math.min(ff, fr);
                tt = Math.max(tt, to);
            }
            if (fr <= balls[i][1] && balls[i][1] <= to) {
                ff = Math.min(ff, prevX-D);
                tt = Math.max(tt, prevX+D);
            }
            dp[i+1][0] = ff;
            dp[i+1][1] = tt;
            prevT = balls[i][0];
            prevX = balls[i][1];
        }
        return (dp[n][1] - dp[n][0] >= 0);
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
