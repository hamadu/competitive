package codeforces.cf2xx.cr287.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by dhamada on 15/05/18.
 */
public class D {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        long MOD = in.nextLong();


        int[][] p10modK = new int[n+1][10];
        for (int i = 0 ; i <= n ; i++) {
            int di = (i == 0) ? 1 : ((p10modK[i-1][1] * 10) % k);
            for (int d = 0 ; d <= 9 ; d++) {
                p10modK[i][d] = (di * d) % k;
            }
        }

        long[][][] dp = new long[n+1][k][2];
        dp[0][0][0] = 1;
        for (int i = 0 ; i < n ; i++) {
            for (int mk = 0 ; mk < k ; mk++) {
                for (int f = 0 ; f <= 1 ; f++) {
                    if (dp[i][mk][f] == 0) {
                        continue;
                    }
                    for (int d = 0 ; d <= 9 ; d++) {
                        if (i == n-1 && d == 0) {
                            continue;
                        }
                        int tmk = (mk + p10modK[i][d]) % k;
                        int tf = ((tmk == 0 && d >= 1) || f == 1) ? 1 : 0;

                        dp[i + 1][tmk][tf] += dp[i][mk][f];
                        dp[i + 1][tmk][tf] %= MOD;
                    }
                }
            }
        }

        long sum = 0;
        for (int mk = 0 ; mk < k ; mk++) {
            sum += dp[n][mk][1];
            sum %= MOD;
        }
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
