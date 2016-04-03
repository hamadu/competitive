package atcoder.arc.arc040;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/06/13.
 */
public class C {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        char[][] map = new char[n][n];
        for (int i = 0; i < n ; i++) {
            map[i] = in.nextToken().toCharArray();
        }

        // [row,k]
        int[][] dp = new int[n+1][n+1];
        for (int i = 0 ; i <= n ; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[0][n] = 0;

        for (int i = 0 ; i < n ; i++) {
            // painted [k,n]
            for (int k = 0 ; k <= n ; k++) {
                if (dp[i][k] == Integer.MAX_VALUE) {
                    continue;
                }

                int must = -1;
                for (int be = 0 ; be < k ; be++) {
                    if (map[i][be] == '.') {
                        must = be;
                    }
                }

                if (must == -1) {
                    // none
                    dp[i+1][n] = Math.min(dp[i+1][n], dp[i][k]);

                    // one
                    dp[i+1][0] = Math.min(dp[i+1][0], dp[i][k]+1);
                } else {
                    // one
                    dp[i+1][must] = Math.min(dp[i+1][must], dp[i][k]+1);

                    // two
                    dp[i+1][0] = Math.min(dp[i+1][0], dp[i][k]+2);
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for (int k = 0; k <= n; k++) {
            min = Math.min(min, dp[n][k]);
        }
        out.println(min);
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
