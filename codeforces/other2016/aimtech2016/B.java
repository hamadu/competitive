package codeforces.other2016.aimtech2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/08/17.
 */
public class B {
    private static final long INF = (long)1e18;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long removeCost = in.nextLong();
        long changeCost = in.nextLong();
        int[] a = in.nextInts(n);

        int[] p = generatePrimes(32000);
        Set<Integer> pset = new HashSet<>();
        for (int idx : new int[]{0, n-1}) {
            for (int diff = -1 ; diff <= 1 ; diff++) {
                int num = a[idx]+diff;
                for (int pi : p) {
                    if (num < pi) {
                        break;
                    }
                    if (num % pi == 0) {
                        pset.add(pi);
                        num /= pi;
                    }
                }
                if (num >= 2) {
                    pset.add(num);
                }
            }
        }

        long best = INF;
        for (int pr : pset) {
            best = Math.min(best, solve(a, pr, removeCost, changeCost));
        }
        out.println(best);
        out.flush();
    }

    private static long solve(int[] a, int pr, long removeCost, long changeCost) {
        int n = a.length;
        long[][][] dp = new long[2][3][2];
        for (int i = 0; i < 2 ; i++) {
            for (int j = 0; j < 3 ; j++) {
                Arrays.fill(dp[i][j], INF);
            }
        }
        dp[0][0][0] = 0;
        for (int i = 0 ; i <= n ; i++) {
            int fr = i % 2;
            int to = 1 - fr;
            for (int j = 0; j < 3 ; j++) {
                Arrays.fill(dp[to][j], INF);
            }
            for (int j = 0 ; j <= 2 ; j++) {
                for (int flg = 0 ; flg <= 1 ; flg++) {
                    if (dp[fr][j][flg] == INF) {
                        continue;
                    }
                    long base = dp[fr][j][flg];
                    if (j+1 <= 2) {
                        dp[fr][j+1][flg] = Math.min(dp[fr][j+1][flg], base);
                    }
                    if (i < n) {
                        if (j == 0 || j == 2) {
                            int mo = a[i]%pr;
                            if (mo == 0 || mo == 1 || mo == pr-1) {
                                long tocost = base+((mo == 0) ? 0 : changeCost);
                                dp[to][j][1] = Math.min(dp[to][j][1], tocost);
                            }
                        } else {
                            long tocost = base+removeCost;
                            dp[to][j][flg] = Math.min(dp[to][j][flg], tocost);
                        }
                    }
                }
            }
        }
        return dp[n%2][2][1];
    }

    static int[] generatePrimes(int upto) {
        boolean[] isp = new boolean[upto];
        Arrays.fill(isp, true);
        isp[0] = isp[1] = false;

        int pi = 0;
        for (int i = 2; i < upto ; i++) {
            if (isp[i]) {
                pi++;
                for (int j = i * 2; j < upto; j += i) {
                    isp[j] = false;
                }
            }
        }

        int[] ret = new int[pi];
        int ri = 0;
        for (int i = 2 ; i < upto ; i++) {
            if (isp[i]) {
                ret[ri++] = i;
            }
        }
        return ret;
    }

    public static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a%b);
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
