package codeforces.cr190.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/31.
 */
public class E {
    private static final long INF = 1000000000_000000L;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        imos = new int[n+1][n+1];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                imos[i+1][j+1] = imos[i+1][j] + imos[i][j+1] - imos[i][j] + in.nextInt();
            }
        }

        long[][] dp = new long[2][n+1];
        Arrays.fill(dp[0], INF);
        dp[0][0] = 0;
        for (int i = 0; i < k ; i++) {
            int fr = i % 2;
            int to = 1 - fr;
            Arrays.fill(dp[to], INF);
            doit(dp[fr], dp[to], 0, n+1, 0, n);
        }
        out.println(dp[k%2][n] / 2);
        out.flush();
    }

    private static void doit(long[] fr, long[] to, int l, int r, int minF, int maxF) {
        if (l >= r) {
            return;
        }
        int bestF = minF;
        int med = (l + r) / 2;
        for (int f = minF ; f <= maxF ; f++) {
            if (fr[f] == INF) {
                continue;
            }
            long cost = fr[f] + imos[med][med] - imos[f][med] - imos[med][f] + imos[f][f];
            if (to[med] > cost) {
                to[med] = cost;
                bestF = f;
            }
        }
        doit(fr, to, l, med, minF, bestF);
        doit(fr, to, med+1, r, bestF, maxF);
    }

    static int[][] imos;

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
