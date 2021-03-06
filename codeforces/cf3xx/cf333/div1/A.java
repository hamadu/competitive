package codeforces.cf3xx.cf333.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/08/19.
 */
public class A {
    private static final int INF = 1000000;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        boolean[][] rail = new boolean[n][n];
        for (int i = 0; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            rail[a][b] = rail[b][a] = true;
        }

        int[][][] dp = new int[2][n][n];
        for (int j = 0; j < 2 ; j++) {
            for (int k = 0; k < n ; k++) {
                Arrays.fill(dp[j][k], INF);
            }
        }
        int[] que = new int[2*n*n*4];
        int qh = 0;
        int qt = 0;
        que[qh++] = 0;
        que[qh++] = 0;
        que[qh++] = 0;
        que[qh++] = 0;
        dp[0][0][0] = 0;
        while (qt < qh) {
            int train = que[qt++];
            int bus = que[qt++];
            int move = que[qt++];
            int tim = que[qt++]+1;
            if (move == 0) {
                rail[n-1][n-1] = true;
                for (int toTrain = 0; toTrain < n; toTrain++) {
                    if (rail[train][toTrain] && dp[1][toTrain][bus] > tim) {
                        dp[1][toTrain][bus] = tim;
                        que[qh++] = toTrain;
                        que[qh++] = bus;
                        que[qh++] = 1;
                        que[qh++] = tim;
                    }
                }
            } else {
                rail[n-1][n-1] = false;
                for (int toBus = 0; toBus < n; toBus++) {
                    if (!rail[bus][toBus] && dp[0][train][toBus] > tim) {
                        if (toBus != n-1 && train == toBus) {
                            continue;
                        }
                        dp[0][train][toBus] = tim;
                        que[qh++] = train;
                        que[qh++] = toBus;
                        que[qh++] = 0;
                        que[qh++] = tim;
                    }
                }
            }
        }

        out.println(dp[0][n-1][n-1] == INF ? -1 : dp[0][n-1][n-1] / 2);
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
