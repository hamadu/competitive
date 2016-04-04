package codeforces.cf3xx.cr343.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/02/24.
 */
public class C {
    private static final int MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        char[] med = in.nextToken().toCharArray();



        int deg = 0;
        int req = 0;
        for (int i = 0 ; i < m ; i++) {
            if (med[i] == ')') {
                deg--;
            } else {
                deg++;
            }
            req = Math.min(req, deg);
        }

        int need = n - m;
        int[][] dp = new int[need+1][2201];
        dp[0][0] = 1;
        for (int i = 0 ; i < need ; i++) {
            for (int j = 0 ; j <= 2101 ; j++) {
                int base = dp[i][j];
                dp[i+1][j+1] += base;
                dp[i+1][j+1] %= MOD;

                if (j >= 1) {
                    dp[i+1][j-1] += base;
                    dp[i+1][j-1] %= MOD;
                }
            }
        }

        long ans = 0;
        for (int l = 0 ; l <= need ; l++) {
            int r = need - l;
            for (int lf = 0 ; lf <= 2100 ; lf++) {
                if (lf + req < 0) {
                    continue;
                }
                int rf = lf + deg;
                if (rf < 0 || rf > 2100) {
                    continue;
                }
                ans += (long) dp[l][lf] * dp[r][rf];
                ans %= MOD;
            }
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
