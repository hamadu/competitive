package atcoder.arc043;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/09/19.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[] p = new int[m];
        for (int i = 0; i < m ; i++) {
            p[i] = in.nextInt();
        }
        Arrays.sort(p);

        int[] sub = new int[m+1];
        for (int i = 0 ; i < m ; i++) {
            sub[i+1] = sub[i] + p[m-i-1];
        }
        int sum = sub[m];

        long[][] dp = new long[2][sum+1];
        Arrays.fill(dp[0], -1);
        dp[0][0] = 0;
        for (int i = 0; i < m ; i++) {
            int fi = i & 1;
            int ti = 1 - fi;
            Arrays.fill(dp[ti], -1);
            for (int j = 0; j <= sum ; j++) {
                if (dp[fi][j] == -1) {
                    continue;
                }
                long base = dp[fi][j];
                int add = p[m-1-i];
                long leftPeople = j;
                long rightPeople = sub[i] - j;

                if (i == m-1 && n == m) {
                    // left and right
                    int tj = j+add;
                    dp[ti][tj] = Math.max(dp[ti][tj], base + leftPeople * (rightPeople+add) + (leftPeople+add) * rightPeople);
                } else {
                    // add left
                    {
                        int tj = j+add;
                        dp[ti][tj] = Math.max(dp[ti][tj], base+leftPeople * (sum - leftPeople));
                    }

                    // add right
                    {
                        int tj = j;
                        dp[ti][tj] = Math.max(dp[ti][tj], base + (sum - rightPeople) * rightPeople);
                    }
                }
            }
        }

        long max = 0;
        long leftEdge = Math.max(0, n - m + 1);
        if (n == m) {
            leftEdge = 0;
        }
        for (long left = 0 ; left <= sum ; left++) {
            long right = sum - left;
            if (dp[m&1][(int)left] >= 0) {
                max = Math.max(max, dp[m&1][(int)left] + leftEdge * left * right);
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
