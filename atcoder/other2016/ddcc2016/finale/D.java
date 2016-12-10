package atcoder.other2016.ddcc2016.finale;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class D {
    private static final long INF = (long) 4e18;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long m = in.nextInt();
        long C = in.nextLong();
        int[][] pair = in.nextIntTable(n, 2);

        long[] cost = new long[501];
        Arrays.fill(cost, INF);
        for (int i = 0; i < n ; i++) {
            cost[pair[i][0]] = Math.min(cost[pair[i][0]], pair[i][1]);
        }
        for (int x = cost.length - 2 ; x >= 1 ; x--) {
            cost[x] = Math.min(cost[x], cost[x+1]);
        }

        long ans = INF;

        // [day] := minCost
        long[] dp = new long[250001];
        Arrays.fill(dp, INF);
        dp[0] = 0;

        int maxDuration = 0;
        for (int x = 500 ; x >= 1 ; x--) {
            if (cost[x] < INF) {
                maxDuration = x;
                break;
            }
        }
        for (int x = 1 ; x <= maxDuration ; x++) {
            long sentaku = C*(x-1);
            int best = 1;
            for (int i = 2 ; i <= x ; i++) {
                if (cost[best] * i > cost[i] * best) {
                    best = i;
                }
            }
            for (int i = 0 ; i < dp.length ; i++) {
                if (dp[i] == INF) {
                    continue;
                }
                int ti = i + x;
                if (ti < dp.length) {
                    dp[ti] = Math.min(dp[ti], dp[i]+cost[x]);
                }
            }


            long fog = INF;
            for (int other = 0 ; other <= Math.min(m, 250000) ; other++) {
                if (dp[other] < INF) {
                    long need = cost[best]*((m-other+best-1)/best);
                    fog = Math.min(fog, dp[other]+need);
                }
            }
            ans = Math.min(ans, fog+sentaku);
        }
        out.println(ans);
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
