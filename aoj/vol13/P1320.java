package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/21.
 */
public class P1320 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            if (n == 0) {
                break;
            }
            String[] cities = new String[n];
            for (int i = 0; i < n ; i++) {
                cities[i] = in.nextToken();
            }
            out.println(solve(cities));
        }
        out.flush();
    }

    private static int solve(String[] cities) {
        int n = cities.length;
        int first = 0;
        for (int i = 0; i < n ; i++) {
            String c = cities[i];
            for (int j = 0; j < n ; j++) {
                if (i != j && cities[j].contains(c)) {
                    first |= 1<<i;
                }
            }
        }

        int[][][] cost = new int[n][n][2];
        for (int i = 0; i < n ; i++) {
            String placed = cities[i];
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    continue;
                }
                String con = cities[j];

                int ml = Math.min(con.length(), placed.length());
                int left = 0;
                for (int k = 1; k <= ml ; k++) {
                    if (placed.substring(0, k).equals(con.substring(con.length()-k))) {
                        left = Math.max(left, k);
                    }
                }
                cost[i][j][0] = left;

                // right
                int right = 0;
                for (int k = 1; k <= ml ; k++) {
                    if (placed.substring(placed.length()-k).equals(con.substring(0, k))) {
                        right = Math.max(right, k);
                    }
                }
                cost[i][j][1] = right;
            }
        }

        int[][][] dp = new int[n][n][1<<n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                Arrays.fill(dp[i][j], Integer.MAX_VALUE);
            }
        }

        for (int i = 0; i < n ; i++) {
            if ((first & (1<<i)) == 0) {
                dp[i][i][first|(1<<i)] = cities[i].length();
            }
        }
        for (int p = first ; p < (1<<n) ; p++) {
            for (int a = 0; a < n ; a++) {
                for (int b = 0; b < n ; b++) {
                    if (dp[a][b][p] == Integer.MAX_VALUE) {
                        continue;
                    }
                    int base = dp[a][b][p];
                    for (int u = 0; u < n ; u++) {
                        if ((p & (1<<u)) >= 1) {
                            continue;
                        }
                        int tp = p | (1<<u);

                        // left
                        {
                            int tc = base + cities[u].length() - cost[a][u][0];
                            dp[u][b][tp] = Math.min(dp[u][b][tp], tc);
                        }

                        // right
                        {
                            int tc = base + cities[u].length() - cost[b][u][1];
                            dp[a][u][tp] = Math.min(dp[a][u][tp], tc);
                        }
                    }
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                min = Math.min(min, dp[i][j][(1<<n)-1]);
            }
        }
        return min == Integer.MAX_VALUE ? -1 : min;
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
