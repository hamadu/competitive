package aoj.vol26;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/30.
 */
public class P2603 {
    private static final int INF = 1145141919;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int s = in.nextInt();
        n = in.nextInt();
        int m = in.nextInt();
        int[] x = new int[s];
        for (int i = 0; i < s ; i++) {
            x[i] = in.nextInt();
        }
        int[] pos = new int[n];
        for (int i = 0; i < n ; i++) {
            int t = in.nextInt();
            int si = in.nextInt()-1;
            pos[i] = t - x[si];
        }
        Arrays.sort(pos);
        for (int i = n-1; i >= 0 ; i--) {
            pos[i] -= pos[0];
        }

        cost = new int[n+1][n+1];
        for (int i = 0; i <= n ; i++) {
            for (int j = i+2; j <= n ; j++) {
                cost[i][j] = cost[i][j-1] + (j - i - 1) * (pos[j-1] - pos[j-2]);
            }
        }


        dp = new int[m+1][n+1];
        Arrays.fill(dp[0], INF);
        dp[0][0] = 0;
        for (int i = 1; i <= m ; i++) {
            doit(i, 0, n+1, 0, n);
        }
        out.println(dp[m][n]);
        out.flush();
    }

    static int n;
    static int[][] cost;
    static int[][] dp;

    // [f,t), [minK,maxK]
    static void doit(int idx, int f, int t, int minK, int maxK) {
        if (f >= t) {
            return;
        }
        int to = (f+t)/2;
        int best = INF;
        int bestK = -1;
        for (int k = minK ; k <= maxK ; k++) {
            int c = dp[idx-1][k] + cost[k][to];
            if (best > c) {
                best = c;
                bestK = k;
            }
        }
        dp[idx][to] = best;
        doit(idx, f, to, minK, bestK);
        doit(idx, to+1, t, bestK, maxK);
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
