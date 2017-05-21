package codeforces.cf4xx.cf405.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class C {
    private static final int INF = 10000000;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        in.nextInt();
        out.println(solve(in.nextToken().toCharArray()));
        out.flush();
    }

    private static int solve(char[] c) {
        int n = c.length;
        int vn = 0;
        int kn = 0;
        int an = 0;

        int[][] vkapos = new int[3][n];
        int[][] vkasum = new int[3][n+1];

        for (int i = 0; i < c.length ; i++) {
            if (c[i] == 'V') {
                vkapos[0][vn++] = i;
                vkasum[0][i]++;
            } else if (c[i] == 'K') {
                vkapos[1][kn++] = i;
                vkasum[1][i]++;
            } else {
                vkapos[2][an++] = i;
                vkasum[2][i]++;
            }
        }

        for (int k = 0 ; k <= 2; k++) {
            for (int i = 0; i < n; i++) {
                vkasum[k][i+1] += vkasum[k][i];
            }
        }


        int[][][][] dp = new int[vn+1][kn+1][an+1][3];
        for (int vi = 0 ; vi <= vn ; vi++) {
            for (int ki = 0; ki <= kn; ki++) {
                for (int ai = 0; ai <= an; ai++) {
                    Arrays.fill(dp[vi][ki][ai], INF);
                }
            }
        }

        dp[0][0][0][2] = 0;
        for (int vi = 0 ; vi <= vn ; vi++) {
            for (int ki = 0 ; ki <= kn ; ki++) {
                for (int ai = 0 ; ai <= an ; ai++) {
                    for (int last = 0 ; last <= 2 ; last++) {
                        int base = dp[vi][ki][ai][last];
                        if (base == INF) {
                            continue;
                        }

                        int[] vka = new int[]{vi, ki, ai};
                        for (int next = 0 ; next <= 2 ; next++) {
                            if (last == 0 && next == 1) {
                                // VK
                                continue;
                            }
                            if (vi == vn && next == 0) {
                                continue;
                            }
                            if (ki == kn && next == 1) {
                                continue;
                            }
                            if (ai == an && next == 2) {
                                continue;
                            }

                            int tvi = vi + ((next == 0) ? 1 : 0);
                            int tki = ki + ((next == 1) ? 1 : 0);
                            int tai = ai + ((next == 2) ? 1 : 0);


                            int pos = vkapos[next][vka[next]];
                            int used = 0;
                            for (int vkai = 0; vkai <= 2; vkai++) {
                                used += Math.min(vkasum[vkai][pos], vka[vkai]);
                            }
                            int cost = pos - used;

                            dp[tvi][tki][tai][next] = Math.min(dp[tvi][tki][tai][next], base+cost);
                        }
                    }
                }
            }
        }


        return Math.min(dp[vn][kn][an][0], Math.min(dp[vn][kn][an][1], dp[vn][kn][an][2]));
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
