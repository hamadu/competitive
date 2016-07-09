package atcoder.arc.arc057;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/07/09.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long k = in.nextLong();
        long[] a = new long[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextLong();
        }

        long[] imos = new long[n+1];
        for (int i = 0; i < n ; i++) {
            imos[i+1] = imos[i] + a[i];
        }

        long[][] dp = new long[n+1][n+1];
        for (int i = 0; i <= n ; i++) {
            Arrays.fill(dp[i], -1);
        }

        dp[n][0] = k;
        for (int i = n ; i >= 1 ; i--) {
            for (int j = 0; j <= n ; j++) {
                long up = dp[i][j];
                if (up == -1) {
                    continue;
                }
                long dw = imos[i];
                long tdw = imos[i-1];

                if (up <= tdw) {
                    dp[i-1][j] = Math.max(dp[i-1][j], up);
                }

                long tup = (up * tdw);
                if (tup >= 1) {
                    if (tup%dw == 0) {
                        tup /= dw;
                        tup--;
                    } else {
                        tup /= dw;
                    }
                    if (tup > tdw) {
                        tup = tdw;
                    }
                    if (up-tup > a[i-1]) {
                        tup = up-a[i-1];
                    }
                }
                int tj = j;
                if (i-1 == 0) {
                    tj += up >= 1 ? 1 : 0;
                } else if (tup * dw < up * tdw) {
                    tj++;
                }
                dp[i-1][tj] = Math.max(dp[i-1][tj], tup);
            }
        }

        int max = 0;
        for (int i = 0; i <= n ; i++) {
            if (dp[0][i] >= 0) {
                max = Math.max(max, i);
            }
        }
        out.println(max);
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
