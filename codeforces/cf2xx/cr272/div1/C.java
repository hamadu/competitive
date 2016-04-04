package codeforces.cf2xx.cr272.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by dhamada on 15/06/07.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        char[] s = in.nextToken().toCharArray();
        char[] p = in.nextToken().toCharArray();

        int n = s.length;
        int m = p.length;
        int[] next = new int[n];
        Arrays.fill(next, -1);
        for (int i = 0 ; i < n ; i++) {
            int nu = 0;
            for (int j = i ; j < n ; j++) {
                if (s[j] == p[nu]) {
                    nu++;
                    if (nu == m) {
                        next[i] = j+1;
                        break;
                    }
                }
            }
        }



        int[][] dp = new int[n+1][n+10];
        for (int i = 0 ; i <= n ; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[0][0] = 0;

        for (int i = 0 ; i < n ; i++) {
            for (int j = 0; j <= n ; j++) {
                if (dp[i][j] == Integer.MAX_VALUE) {
                    continue;
                }
                int base = dp[i][j];
                if (next[i] != -1) {
                    int to = next[i];
                    int cost = next[i] - i - m;
                    dp[to][j+1] = Math.min(dp[to][j+1], base + cost);
                }

                // ignore
                dp[i+1][j] = Math.min(dp[i + 1][j], base + 1);
            }
        }

        int[] best = new int[n+1];
        for (int cost = 0 ; cost <= n ; cost++) {
            for (int score = 0 ; score <= n ; score++) {
                if (dp[n][score] == cost) {
                    best[cost] = score;
                }
            }
        }

        StringBuilder line = new StringBuilder();
        for (int i = 0 ; i <= n ; i++) {
            line.append(' ').append(best[i]);
        }
        out.println(line.substring(1));
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
