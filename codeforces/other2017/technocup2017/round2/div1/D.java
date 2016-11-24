package codeforces.other2017.technocup2017.round2.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class D {


    private static final int INF = 2000000000;

    public static int n;
    public static int[] a;
    public static int[] imosL;
    public static int[] imosR;

    private static int[][][] memo;
    public static int[][] lastMin;

    private static int foe = 0;

    public static int dfs(int l, int r, int last) {
        int idx = last-lastMin[l][r];
        if (memo[l][r][idx] != -INF) {
            return memo[l][r][idx];
        }

        int best = -INF;
        if (l + last + r > n) {
            best = 0;
        } else {
            if (l <= r) {
                best = Math.max(best, imosL[l+last] - imosL[l] - dfs(l+last, r, last));
                if (l+last+r+1 <= n) {
                    best = Math.max(best, imosL[l+last+1] - imosL[l] - dfs(l+last+1, r, last+1));
                }
            } else {
                best = Math.max(best, imosR[r+last] - imosR[r] - dfs(l, r+last, last));
                if (l+last+r+1 <= n) {
                    best = Math.max(best, imosR[r+last+1] - imosR[r] - dfs(l, r+last+1, last+1));
                }
            }
        }
        memo[l][r][idx] = best;
        return best;
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        n = in.nextInt();
        a = in.nextInts(n);

        lastMin = new int[n+1][];
        int[][] lastMax = new int[n+1][];
        for (int i = 0; i <= n ; i++) {
            lastMin[i] = new int[n+1-i];
            lastMax[i] = new int[n+1-i];
        }

        for (int i = 0; i <= n ; i++) {
            Arrays.fill(lastMin[i], INF);
            Arrays.fill(lastMax[i], -1);
        }

        lastMin[0][0] = lastMax[0][0] = 1;
        for (int i = 0; i <= n ; i++) {
            for (int j = 0; i+j <= n ; j++) {
                if (lastMin[i][j] == INF) {
                    continue;
                }
                for (int ls = lastMin[i][j] ; ls <= lastMax[i][j] ; ls++) {
                    if (i <= j) {
                        if (j+i+ls <= n) {
                            lastMax[i+ls][j] = Math.max(lastMax[i+ls][j], ls);
                            lastMin[i+ls][j] = Math.min(lastMin[i+ls][j], ls);
                        }
                        if (j+i+ls+1 <= n) {
                            lastMax[i+ls+1][j] = Math.max(lastMax[i+ls+1][j], ls+1);
                            lastMin[i+ls+1][j] = Math.min(lastMin[i+ls+1][j], ls+1);
                        }
                    } else {
                        if (i+j+ls <= n) {
                            lastMax[i][j+ls] = Math.max(lastMax[i][j+ls], ls);
                            lastMin[i][j+ls] = Math.min(lastMin[i][j+ls], ls);
                        }
                        if (i+j+ls+1 <= n) {
                            lastMax[i][j+ls+1] = Math.max(lastMax[i][j+ls+1], ls+1);
                            lastMin[i][j+ls+1] = Math.min(lastMin[i][j+ls+1], ls+1);
                        }
                    }
                }
            }
        }

        imosL = new int[n+1];
        for (int i = 0; i < n ; i++) {
            imosL[i+1] = imosL[i] + a[i];
        }
        imosR = new int[n+1];
        for (int i = 0; i < n ; i++) {
            imosR[i+1] = imosR[i] + a[n-i-1];
        }

        memo = new int[n+1][][];
        for (int i = 0; i <= n ; i++) {
            memo[i] = new int[n+1-i][];
            for (int j = 0; j < memo[i].length ; j++) {
                int d = lastMax[i][j] - lastMin[i][j] + 1;
                if (d >= 0) {
                    memo[i][j] = new int[d];
                    Arrays.fill(memo[i][j], -INF);
                }
            }
        }

        out.println(dfs(0, 0, 1));
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

        private int[] nextInts(int n) {
            int[] ret = new int[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextInt();
            }
            return ret;
        }

        private int[][] nextIntTable(int n, int m) {
            int[][] ret = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextInt();
                }
            }
            return ret;
        }

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
            }
            return ret;
        }

        private long[][] nextLongTable(int n, int m) {
            long[][] ret = new long[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextLong();
                }
            }
            return ret;
        }

        private double[] nextDoubles(int n) {
            double[] ret = new double[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextDouble();
            }
            return ret;
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

        public double nextDouble() {
            return Double.valueOf(nextToken());
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
