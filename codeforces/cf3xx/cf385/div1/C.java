package codeforces.cf3xx.cf385.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class C {
    private static final int INF = 1000000009;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] cards = new int[n][3];
        for (int i = 0; i < n ; i++) {
            char c = in.nextToken().toCharArray()[0];
            cards[i][0] = c == 'R' ? 1 : 0;
            for (int j = 0; j < 2; j++) {
                cards[i][j+1] = in.nextInt();
            }
        }

        int redOffset = 0;
        int blueOffset = 0;
        for (int i = 0; i < n ; i++) {
            redOffset += Math.max(0, cards[i][1]-n);
            blueOffset += Math.max(0, cards[i][2]-n);
            cards[i][1] = Math.min(cards[i][1], n);
            cards[i][2] = Math.min(cards[i][2], n);
        }


        int[][] dp = new int[513][1<<n];
        for (int i = 0; i < 513; i++) {
            Arrays.fill(dp[i], INF);
        }


        int GETA = 256;
        int init = GETA-(redOffset-blueOffset);
        init = Math.max(0, init);
        init = Math.min(dp.length-1, init);

        dp[init][0] = Math.max(redOffset, blueOffset);
        for (int p = 0; p < (1<<n); p++) {
            int redBonus = 0;
            int blueBonus = 0;
            for (int i = 0; i < n ; i++) {
                if (((p >> i) & 1) == 1) {
                    redBonus += cards[i][0];
                    blueBonus += 1-cards[i][0];
                }
            }

            for (int u = 0; u < dp.length ; u++) {
                if (dp[u][p] == INF) {
                    continue;
                }
                int base = dp[u][p];
                int haveRed = (u >= GETA) ? u - GETA : 0;
                int haveBlue = (u < GETA) ? GETA - u : 0;
                for (int i = 0 ; i < n ; i++) {
                    if (((p >> i) & 1) == 1) {
                        continue;
                    }
                    int tp = p | (1<<i);
                    int payRed = Math.max(0, cards[i][1]-redBonus);
                    int payBlue = Math.max(0, cards[i][2]-blueBonus);
                    int buy = Math.max(0, Math.max(payRed-haveRed, payBlue-haveBlue));
                    int toRed = haveRed + buy - payRed;
                    int toBlue = haveBlue + buy - payBlue;
                    int tu = (toRed >= 1) ? GETA+toRed : GETA-toBlue;
                    tu = Math.max(0, tu);
                    tu = Math.min(dp.length-1, tu);
                    dp[tu][tp] = Math.min(dp[tu][tp], base+buy);
                }
            }
        }

        int ans = INF;
        for (int i = 0; i < dp.length ; i++) {
            ans = Math.min(ans, dp[i][(1<<n)-1]);
        }
        out.println(ans+n);
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
