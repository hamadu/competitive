package codeforces.cf3xx.cr301.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class D {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int r = in.nextInt();
        int s = in.nextInt();
        int p = in.nextInt();

        double[][][] dp = new double[r+1][s+1][p+1];
        dp[r][s][p] = 1.0d;

        for (int i = r ; i >= 0 ; i--) {
            for (int j = s ; j >= 0 ; j--) {
                for (int k = p ; k >= 0 ; k--) {
                    if (dp[i][j][k] == 0) {
                        continue;
                    }

                    int sum = i + j + k;
                    int pair = sum * (sum - 1) / 2;
                    int same = i * (i - 1) / 2 + j * (j - 1) / 2 + k * (k - 1) / 2;
                    if (pair == same) {
                        continue;
                    }

                    double rate = (pair * 1.0d) / (pair - same);

                    // r -> s
                    if (i >= 1 && j >= 1) {
                        dp[i][j - 1][k] += (rate * dp[i][j][k] * i * j) / pair;
                    }

                    // s -> p
                    if (j >= 1 && k >= 1) {
                        dp[i][j][k - 1] += (rate * dp[i][j][k] * j * k) / pair;
                    }

                    // p -> r
                    if (k >= 1 && i >= 1) {
                        dp[i - 1][j][k] += (rate * dp[i][j][k] * k * i) / pair;
                    }
                }
            }
        }

        double rp = 0.0d;
        double sp = 0.0d;
        double pp = 0.0d;
        for (int i = 0 ; i <= r ; i++) {
            rp += dp[i][0][0];
        }
        for (int i = 0 ; i <= s ; i++) {
            sp += dp[0][i][0];
        }
        for (int i = 0 ; i <= p ; i++) {
            pp += dp[0][0][i];
        }

        out.println(rp + " " + sp + " " + pp);
        out.flush();
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
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
}



