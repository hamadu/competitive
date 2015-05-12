package codeforces.cr302.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        char[][] table = new char[n][];
        for (int i = 0 ; i < n ; i++) {
            table[i] = in.nextToken().toCharArray();
        }

        int[][] cost = new int[n][m];
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                cost[i][j] = in.nextInt();
            }
        }

        int[][] scost = new int[n][m];
        int[][] sptn = new int[n][m];
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                int max = 0;
                int sum = 0;
                int ptn = 0;
                for (int k = 0 ; k < n ; k++) {
                    if (table[i][j] == table[k][j]) {
                        max = Math.max(max, cost[k][j]);
                        ptn |= 1<<k;
                        sum += cost[k][j];
                    }
                }
                scost[i][j] = sum - max;
                sptn[i][j] = ptn;
            }
        }

        int[] dp = new int[1<<n];
        int INF = Integer.MAX_VALUE / 4;
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for (int p = 1 ; p < (1<<n) ; p++) {
            int low = 0;
            while (true) {
                if ((p & (1<<low)) >= 1) {
                    break;
                }
                low++;
            }

            for (int j = 0 ; j < m ; j++) {
                // single
                dp[p] = Math.min(dp[p], dp[p & (p ^ (1<<low))] + cost[low][j]);

                // multiple
                dp[p] = Math.min(dp[p], dp[p & (p ^ sptn[low][j])] + scost[low][j]);
            }
        }

        out.println(dp[(1<<n)-1]);
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



