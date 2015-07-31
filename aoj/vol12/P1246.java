package aoj.vol12;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/31.
 */
public class P1246 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            if (n == 0) {
                break;
            }
            int[][] customers = new int[n][3];
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < 3 ; j++) {
                    customers[i][j] = in.nextInt();
                }
            }
            out.println(solve(customers));
        }
        out.flush();
    }

    private static int solve(int[][] cs) {
        int n = cs.length;

        int[][][] dp = new int[2][400][400];
        for (int i = 0; i < 2 ; i++) {
            for (int j = 0; j < 400; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        Arrays.sort(cs, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        dp[0][0][0] = 0;
        for (int k = 0; k < n ; k++) {
            int fr = k % 2;
            int to = 1 - fr;
            for (int i = 0; i < 400; i++) {
                for (int j = 0; j < 400; j++) {
                    if (dp[fr][i][j] < 0) {
                        continue;
                    }
                    int base = dp[fr][i][j];
                    dp[to][i][j] = Math.max(dp[to][i][j], base);
                    if (i <= cs[k][0]) {
                        dp[to][cs[k][1] + 1][j] = Math.max(dp[to][cs[k][1] + 1][j], base + cs[k][2]);
                    }
                    if (j <= cs[k][0]) {
                        dp[to][i][cs[k][1] + 1] = Math.max(dp[to][i][cs[k][1] + 1], base + cs[k][2]);
                    }
                }
            }
            for (int i = 0; i < 400; i++) {
                Arrays.fill(dp[fr][i], -1);
            }
        }

        int best = 0;
        for (int i = 0; i < 400; i++) {
            for (int j = 0; j < 400; j++) {
                best = Math.max(best, dp[n%2][i][j]);
            }
        }
        return best;
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
