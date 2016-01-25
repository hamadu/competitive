package atcoder.dwango2016.qual;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/01/23.
 */
public class D {
    static final long INF = (long)1e18;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        long[][] field = new long[n][m];
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                field[i][j] = in.nextLong();
            }
        }
        long max = -INF;
        for (int r = 0 ; r <= 1 ; r++) {
            max = Math.max(max, solve(field));
            field = rotate(field);
        }
        out.println(max);
        out.flush();
    }

    private static long[][] rotate(long[][] field) {
        int n = field.length;
        int m = field[0].length;

        long[][] res = new long[m][n];
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                res[j][n-i-1] = field[i][j];
            }
        }
        return res;
    }

    private static long solve(long[][] field) {
        int n = field.length;
        int m = field[0].length;
        long[][] field2 = flipV(field);

        long[] a = solveSub(field);
        long[] b = solveSub(field2);

        long max = -INF;
        for (int i = 0 ; i < n-1 ; i++) {
            max = Math.max(max, a[i] + b[n-i-2]);
        }
        return max;
    }

    private static long[][] flipV(long[][] field) {
        int n = field.length;
        int m = field[0].length;
        long[][] res = new long[n][m];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                res[i][j] = field[n-i-1][j];
            }
        }
        return res;
    }

    private static long[] solveSub(long[][] field) {
        int n = field.length;
        int m = field[0].length;

        long[][] imos = new long[n][m+1];
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                imos[i][j+1] = imos[i][j] + field[i][j];
            }
        }


        long[] result = new long[n];
        Arrays.fill(result, -INF);
        long[][][][] dp = new long[2][3][m][m];
        for (int j = 0; j <= 2; j++) {
            for (int i = 0 ; i < m ; i++) {
                Arrays.fill(dp[0][j][i], -INF);
            }
        }

        dp[0][0][0][0] = 0;
        for (int l = 0 ; l <= n ; l++) {
            int fr = l % 2;
            int to = 1 - fr;
            for (int f = 0; f <= 2 ; f++) {
                for (int j = 0; j < m ; j++) {
                    Arrays.fill(dp[to][f][j], -INF);
                }
            }

            if (l >= 1) {
                for (int f = 1 ; f <= 2 ; f++) {
                    for (int i = 0; i < m ; i++) {
                        for (int j = i; j < m ; j++) {
                            result[l-1] = Math.max(result[l-1], dp[fr][f][i][j]);
                        }
                    }
                }
            }

            if (l == n) {
                break;
            }
            for (int f = 0; f <= 2 ; f++) {
                if (f % 2 == 0) {
                    long base = dp[fr][f][0][0];

                    // just go
                    dp[to][f][0][0] = Math.max(dp[to][f][0][0], base);

                    // paint
                    if (f == 0) {
                        for (int i = 0; i < m; i++) {
                            for (int j = i; j < m; j++) {
                                dp[to][1][i][j] = Math.max(dp[to][1][i][j], base+imos[l][j+1]-imos[l][i]);
                            }
                        }
                    }
                } else {
                    for (int i = 0 ; i < m ; i++) {
                        for (int j = i ; j < m; j++) {
                            long base = dp[fr][f][i][j];

                            // just go
                            dp[to][f][i][j] = Math.max(dp[to][f][i][j], base + imos[l][j+1] - imos[l][i]);

                            // quit
                            dp[to][2][0][0] = Math.max(dp[to][2][0][0], base);
                        }
                    }
                }
            }
        }
        return result;
    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
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
            return res * sgn;
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
            return res * sgn;
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
