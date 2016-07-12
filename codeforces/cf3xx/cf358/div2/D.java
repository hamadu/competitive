package codeforces.cf3xx.cf358.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/07/12.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        char[] s = in.nextToken().toCharArray();
        char[] t = in.nextToken().toCharArray();

        int[][][] dp = new int[2][n+1][m+1];
        for (int i = 0; i < 2 ; i++) {
            for (int j = 0; j <= n; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        dp[0][0][0] = 0;
        for (int i = 0; i <= 2*k ; i++) {
            int fr = i % 2;
            int to = 1 - fr;
            int mode = i % 2;
            for (int j = 0; j <= n; j++) {
                Arrays.fill(dp[to][j], -1);
            }
            for (int ai = 0; ai <= n ; ai++) {
                for (int bi = 0; bi <= m ; bi++) {
                    if (dp[fr][ai][bi] == -1) {
                        continue;
                    }
                    int base = dp[fr][ai][bi];
                    if (ai < n) {
                        int next = (mode == 0) ? fr : to;
                        dp[next][ai+1][bi] = Math.max(dp[next][ai+1][bi], base);
                    }
                    if (bi < m) {
                        int next = (mode == 0) ? fr : to;
                        dp[next][ai][bi+1] = Math.max(dp[next][ai][bi+1], base);
                    }
                    if (ai < n && bi < m && s[ai] == t[bi]) {
                        int next = (mode == 0) ? to : fr;
                        dp[next][ai+1][bi+1] = Math.max(dp[next][ai+1][bi+1], base+1);
                    }
                    if (mode == 1) {
                        dp[to][ai][bi] = Math.max(dp[to][ai][bi], base);
                    }
                }
            }
        }

        out.println(dp[0][n][m]);
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
