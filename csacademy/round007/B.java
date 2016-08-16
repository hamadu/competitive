package csacademy.round007;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/08/14.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long[] a = in.nextLongs(n);


        long max = -(long)1e18;
        for (int i = 0; i < n ; i++) {
            max = Math.max(max, a[i]);
        }
        if (max < 0) {
            shuffleAndSort(a);
            out.println(a[n-1]);
            out.flush();
            return;
        }

        long[][][] dp = new long[n+1][3][4];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < 3; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        dp[0][0][0] = 0;

        long best = 0;
        for (int i = 0 ; i <= n ; i++) {
            for (int j = 0; j < 3 ; j++) {
                for (int flg = 0 ; flg < 4 ; flg++) {
                    if (dp[i][j][flg] == -1) {
                        continue;
                    }
                    long base = dp[i][j][flg];
                    if (flg == 3) {
                        best = Math.max(best, base);
                    }
                    if (i == n) {
                        continue;
                    }

                    if (j == 0) {
                        long to = base+a[i];
                        dp[i+1][0][flg|1] = Math.max(dp[i+1][0][flg|1], Math.max(to, 0));

                        dp[i][1][flg] = Math.max(dp[i][1][flg], base);
                    } else if (j == 1) {
                        dp[i+1][1][flg|2] = Math.max(dp[i+1][1][flg|2], base);

                        dp[i][2][flg] = Math.max(dp[i][2][flg], base);
                    } else {
                        long to = base+a[i];
                        dp[i+1][2][flg|1] = Math.max(dp[i+1][2][flg|1], Math.max(to, 0));
                    }
                }
            }
        }

        out.println(best);
        out.flush();
    }


    // against for quick-sort killer
    static void shuffleAndSort(long[] a) {
        int n = a.length;
        for (int i = 0; i < n ; i++) {
            int idx = (int)(Math.random() * i);
            long tmp = a[idx];
            a[idx] = a[i];
            a[i] = tmp;
        }
        Arrays.sort(a);
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
