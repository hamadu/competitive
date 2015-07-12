package aoj.vol25;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/12.
 */
public class P2587 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            if (n == 0) {
                break;
            }
            int[][] range = new int[n][2];
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < 2 ; j++) {
                    range[i][j] = in.nextInt();
                }
            }
            out.println(solve(range));
        }
        out.flush();
    }

    private static long solve(int[][] range) {
        long ans = 0;
        int n = range.length;
        for (int keta = n ; keta <= 2*n ; keta++) {
            int[] ptns = new int[20];
            int pn = 0;
            for (int p = 0 ; p < 1<<n ; p++) {
                if (Integer.bitCount(p) + n == keta) {
                    ptns[pn++] = p;
                }
            }

            long[][][] okptn = new long[keta+1][10][10];
            for (int pi = 0 ; pi < pn ; pi++) {
                int ki = 0;
                for (int i = 0 ; i < n ; i++) {
                    if ((ptns[pi] & (1<<i)) >= 1) {
                        int min = Math.max(10, range[i][0]);
                        int max = range[i][1];
                        for (int d = min ; d <= max ; d++) {
                            for (int f = 0 ; f <= 9 ; f++) {
                                okptn[ki][f][d/10] |= 1<<pi;
                            }
                            okptn[ki+1][d/10][d%10] |= 1<<pi;
                        }
                        ki += 2;
                    } else {
                        int min = range[i][0];
                        int max = Math.min(9, range[i][1]);
                        for (int d = min ; d <= max ; d++) {
                            for (int f = 0 ; f <= 9 ; f++) {
                                okptn[ki][f][d] |= 1<<pi;
                            }
                        }
                        ki += 1;
                    }
                }
            }

            long[][] dp = new long[keta+1][10];
            for (int pp = 1 ; pp < (1<<pn) ; pp++) {
                dp[0][0] = 1;
                for (int i = 0 ; i < keta ; i++) {
                    Arrays.fill(dp[i+1], 0);
                    for (int p = 0 ; p <= 9 ; p++) {
                        if (dp[i][p] == 0) {
                            continue;
                        }
                        for (int ne = 0 ; ne <= 9 ; ne++) {
                            if ((okptn[i][p][ne] & pp) == pp) {
                                dp[i+1][ne] += dp[i][p];
                            }
                        }
                    }
                }
                long sum = 0;
                for (int i = 0; i <= 9 ; i++) {
                    sum += dp[keta][i];
                }
                ans += ((Integer.bitCount(pp) % 2) * 2 - 1) * sum;
            }
        }
        return ans;
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
