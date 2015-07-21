package aoj.vol22;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/20.
 */
public class P2294 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int h = in.nextInt();
        int n = in.nextInt();
        int p = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        int[] pair = new int[h+1];
        Arrays.fill(pair, -1);
        for (int i = 0; i < m ; i++) {
            int hi = in.nextInt();
            int b = in.nextInt();
            pair[hi] = b-1;
        }

        double[][][] dp = new double[2][k+1][n];
        dp[h%2][0][p-1] = 1.0d;
        for (int hi = h; hi >= 1 ; hi--) {
            int fr = hi % 2;
            int to = 1 - fr;

            int freeCount = 0;
            for (int i = hi ; i >= 1 ; i--) {
                if (pair[i] == -1) {
                    freeCount++;
                }
            }

            for (int i = 0; i <= k; i++) {
                Arrays.fill(dp[to][i], 0);
            }
            for (int ki = 0; ki <= k; ki++) {
                double[] base = dp[fr][ki];
                if (pair[hi] == -1) {

                    double pick = 1.0d / (n - 1);
                    double rate = (k - ki) * 1.0d / freeCount;
                    double nonrate = 1.0d - rate;

                    if (ki+1 <= k) {
                        double[] tp = dp[to][ki+1];
                        for (int co = 0; co < n; co++) {
                            double same = 1.0d;
                            if (co >= 1) {
                                tp[co] += rate * base[co-1] * pick;
                                same -= pick;
                            }
                            if (co < n-1) {
                                tp[co] += rate * base[co+1] * pick;
                                same -= pick;
                            }
                            tp[co] += rate * base[co] * same;
                        }
                    }
                    double[] tp = dp[to][ki];
                    for (int co = 0; co < n; co++) {
                        tp[co] += nonrate * base[co];
                    }
                } else {
                    int a = pair[hi];
                    int b = a+1;
                    for (int i = 0; i < n ; i++) {
                        int ai = i;
                        if (i == a) {
                            ai = b;
                        } else if (i == b) {
                            ai = a;
                        }
                        dp[to][ki][i] += base[ai];
                    }
                }
            }
        }

        double max = 0;
        for (int i = 0; i < n ; i++) {
            max = Math.max(max, dp[0][k][i]);
        }
        out.println(String.format("%.9f", max));
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
