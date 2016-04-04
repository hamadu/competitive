package codeforces.cf0xx.cr66.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/09/07.
 */
public class C {
    private static final int INF = 1145141919;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        char[] c = in.nextToken().toCharArray();
        int k = in.nextInt();
        int[][] table = new int[26][26];
        int n = in.nextInt();
        for (int i = 0; i < n ; i++) {
            int a = in.nextToken().charAt(0)-'a';
            int b = in.nextToken().charAt(0)-'a';
            table[a][b] = in.nextInt();
        }

        int[][][] dp = new int[c.length][26][105];
        for (int i = 0; i < c.length ; i++) {
            for (int j = 0; j < 26 ; j++) {
                Arrays.fill(dp[i][j], -INF);
            }
        }
        for (int i = 0; i < 26 ; i++) {
            if (c[0]-'a' == i) {
                dp[0][i][0] = 0;
            } else {
                dp[0][i][1] = 0;
            }
        }
        for (int i = 0; i+1 < c.length ; i++) {
            for (int l = 0; l < 26 ; l++) {
                for (int j = 0; j <= k; j++) {
                    if (dp[i][l][j] == -INF) {
                        continue;
                    }
                    int base = dp[i][l][j];
                    for (int t = 0; t < 26 ; t++) {
                        int tj = j + ((c[i+1]-'a' == t) ? 0 : 1);
                        dp[i+1][t][tj] = Math.max(dp[i+1][t][tj], base+table[l][t]);

                    }
                }
            }
        }

        int max = -INF;
        for (int l = 0 ; l < 26 ; l++) {
            for (int j = 0; j <= k; j++) {
                max = Math.max(max, dp[c.length-1][l][j]);
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
