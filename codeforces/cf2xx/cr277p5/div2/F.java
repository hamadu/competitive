package codeforces.cf2xx.cr277p5.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by dhamada on 15/05/24.
 */
public class F {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int m = in.nextInt();
        int MOD = in.nextInt();

        long[][] ncr = new long[601][601];
        for (int i = 0; i < ncr.length ; i++) {
            ncr[i][0] = ncr[i][i] = 1 % MOD;
            for (int j = 1 ; j < i ; j++) {
                ncr[i][j] = (ncr[i-1][j-1] + ncr[i-1][j]) % MOD;
            }
        }

        char[][] map = new char[m][];
        for (int i = 0; i < m ; i++) {
            map[i] = in.nextToken().toCharArray();
        }

        int iz = 0;
        int io = 0;
        for (int i = 0 ; i < n ; i++) {
            int ct = 0;
            for (int j = 0; j < m; j++) {
                if (map[j][i] == '1') {
                    ct++;
                }
            }
            if (ct >= 3) {
                // not happen
            } else if (ct == 1) {
                io++;
            } else if (ct == 0) {
                iz++;
            }
        }

        long[][][] dp = new long[2][n+1][n+1];
        dp[m&1][iz][io] = 1 % MOD;
        for (int i = m ; i < n ; i++) {
            int fr = i & 1;
            int to = 1 - fr;
            for (int zero = 0 ; zero <= n ; zero++) {
                for (int one = 0 ; one <= n ; one++) {
                    if (dp[fr][zero][one] == 0) {
                        continue;
                    }
                    long base = dp[fr][zero][one] % MOD;

                    // [0,0] -> [1,1]
                    if (zero >= 2) {
                        dp[to][zero-2][one+2] += (base * ncr[zero][2]) % MOD;
                        if (dp[to][zero-2][one+2] >= MOD) {
                            dp[to][zero-2][one+2] -= MOD;
                        }
                    }

                    // [1,1] -> [2,2]
                    if (one >= 2) {
                        dp[to][zero][one-2] += (base * ncr[one][2]) % MOD;
                        if (dp[to][zero][one-2] >= MOD) {
                            dp[to][zero][one-2] -= MOD;
                        }
                    }

                    // [0,1] -> [1,2]
                    if (one >= 1 && zero >= 1) {
                        dp[to][zero-1][one] += (((base * zero) % MOD) * one) % MOD;
                        if (dp[to][zero-1][one] >= MOD) {
                            dp[to][zero-1][one] -= MOD;
                        }
                    }
                }
            }
            for (int k = 0; k <= n; k++) {
                Arrays.fill(dp[fr][k], 0);
            }
        }

        long sum = dp[n&1][0][0] % MOD;
        out.println(sum);
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
                return (char)c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char)c;
            }
            throw new InputMismatchException();
        }

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char)c);
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
