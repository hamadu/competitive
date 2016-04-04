package codeforces.cf1xx.cr131.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by dhamada on 15/06/03.
 */
public class C {

    // 1 2 3
    // 4 5 6
    // 7 8 9


    // 1
    // 2 4
    // 3 5 7
    //   6 8
    //     9

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] a = new int[n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                a[i][j] = in.nextInt();
            }
        }

        int len = 2*n-2;
        int[][] table = new int[len+1][n];
        for (int i = 0 ; i <= len ; i++) {
            Arrays.fill(table[i], Integer.MIN_VALUE);
        }
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                table[i+j][i] = a[i][j];
            }
        }

        int[][][] dp = new int[2][n][n];
        for (int t = 0 ; t <= 1 ; t++) {
            for (int i = 0; i < n ; i++) {
                Arrays.fill(dp[t][i], Integer.MIN_VALUE);
            }
        }
        dp[0][0][0] = a[0][0];
        for (int t = 0 ; t < len ; t++) {
            int fr = t % 2;
            int to = 1 - fr;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dp[fr][i][j] == Integer.MIN_VALUE) {
                        continue;
                    }
                    for (int di = 0; di <= 1; di++) {
                        for (int dj = 0; dj <= 1; dj++) {
                            int ti = i + di;
                            int tj = j + dj;
                            if (ti >= n || tj >= n || table[t + 1][ti] == Integer.MIN_VALUE || table[t + 1][tj] == Integer.MIN_VALUE) {
                                continue;
                            }
                            int tv = dp[fr][i][j];
                            tv += table[t + 1][ti];
                            if (ti != tj) {
                                tv += table[t + 1][tj];
                            }
                            if (dp[to][ti][tj] < tv) {
                                dp[to][ti][tj] = tv;
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < n ; i++) {
                Arrays.fill(dp[fr][i], Integer.MIN_VALUE);
            }
        }
        out.println(dp[len%2][n-1][n-1]);
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
