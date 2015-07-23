package aoj.vol25;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/22.
 */
public class P2595 {
    private static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        for (int i = 1 ; i <= 2000; i++) {
            inv[i] = inv(i);
        }

        while (true) {
            int n = in.nextInt();
            long D = in.nextLong();
            int x = in.nextInt();
            if (D + n + x == 0) {
                break;
            }
            out.println(solve(n, x-1, D));
        }

        out.flush();
    }

    private static long solve(int n, int x, long d) {
        for (int i = 0; i <= n ; i++) {
            Arrays.fill(dp[i], 0);
        }
        dp[0][0] = 1;
        for (int i = 1 ; i <= n; i++) {
            imos[0] = 0;
            for (int j = 1; j <= n ; j++) {
                imos[j] = imos[j-1] + dp[i-1][j-1];
            }
            for (int j = 0; j <= n ; j++) {
                int fr = Math.max(0, j-x);
                dp[i][j] = imos[j] - imos[fr];
                dp[i][j] %= MOD;
            }
        }

        long ptn = 0;
        long dist = 1;
        for (int i = 1 ; i <= n; i++) {
            if (i > d) {
                break;
            }
            dist *= (d-i+1) % MOD;
            dist %= MOD;
            dist *= inv[i];
            dist %= MOD;

            long way = dp[i][n];
            ptn += (way * dist) % MOD;
        }
        return ptn % MOD;
    }

    static long pow(long a, long x) {
        long res = 1;
        while (x > 0) {
            if (x % 2 != 0) {
                res = (res * a) % MOD;
            }
            a = (a * a) % MOD;
            x /= 2;
        }
        return res;
    }

    static long inv(long a) {
        return pow(a, MOD - 2) % MOD;
    }

    static long[] inv  = new long[2010];
    static long[] imos = new long[2010];
    static long[][] dp = new long[2010][2010];

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
                res += c - '0';
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
                res += c - '0';
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
