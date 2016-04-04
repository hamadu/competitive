package codeforces.cf1xx.cr131.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by dhamada on 15/06/03.
 */
public class B {
    private static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        C = new long[301][301];
        for (int i = 0; i < 301 ; i++) {
            C[i][0] = C[i][i] = 1;
            for (int j = 1 ; j < i; j++) {
                C[i][j] = (C[i-1][j-1] + C[i-1][j]) % MOD;
                
            }
        }

        int n = in.nextInt();
        int[] a = new int[10];
        for (int i = 0; i < 10 ; i++) {
            a[i] = in.nextInt();
        }

        long ptn = 0;
        for (int h = 1 ; h <= 9; h++) {
            int[] nu = a.clone();
            nu[h] = Math.max(0, nu[h] - 1);
            int len = n-1;
            ptn += solve(nu, len);
            debug(h,ptn);
        }

        out.println(ptn % MOD);
        out.flush();
    }

    private static long[][] C;

    private static long solve(int[] nu, int n) {
        long[][] dp = new long[11][n+1];
        for (int i = 0 ; i <= n ; i++) {
            dp[0][i] = 1;
        }
        for (int d = 0 ; d <= 9 ; d++) {
            for (int left = n ; left >= 0 ; left--) {
                if (dp[d][left] == 0) {
                    continue;
                }
                for (int l = nu[d]; left - l >= 0 ; l++) {
                    int ti = left-l;
                    dp[d+1][ti] += (dp[d][left] * C[left][l]) % MOD;
                    dp[d+1][ti] %= MOD;
                }
            }
        }
        return dp[10][0] % MOD;
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
