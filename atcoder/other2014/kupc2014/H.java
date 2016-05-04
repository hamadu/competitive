package atcoder.other2014.kupc2014;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 5/2/16.
 */
public class H {
    private static final int INF = 114514;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int L = in.nextInt();
        int W = in.nextInt();
        int[][] ab = new int[n][2];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 2 ; j++) {
                ab[i][j] = in.nextInt();
            }
        }
        int[] dp = new int[n];
        Arrays.fill(dp, INF);
        dp[0] = 0;

        int[] first = new int[n];
        for (int i = 1 ; i < n ; i++) {
            first[i] = ab[i][1] + 1;
        }
        for (int i = 0; i < n ; i++) {
            int base = dp[i];
            if (base == INF) {
                continue;
            }
            if (first[i] > ab[i][1]) {
                continue;
            }
            int[] posL = find(first[i] + W, ab);
            int[] posR = find(ab[i][1] + W, ab);
            if (posL[1] < 0) {
                posL[1] = 0;
            }
            if (posR[1] < 0) {
                posR[0]--;
                posR[1] = ab[posR[0]][1] - ab[posR[0]][0];
            }
            for (int p = posL[0] ; p <= posR[0] ; p++) {
                dp[p] = Math.min(dp[p], base+1);
                if (p == posL[0]) {
                    first[p] = Math.min(first[p], ab[p][0] + posL[1]);
                } else {
                    first[p] = Math.min(first[p], ab[p][0]);
                }
            }
        }
        out.println(dp[n-1] >= INF || first[n-1] > L ? -1 : dp[n-1]);
        out.flush();
    }

    private static int[] find(int pos, int[][] ab) {
        int n = ab.length;
        int left = 0;
        int right = n;
        while (right - left > 1) {
            int med = (right + left) / 2;

            int fr = (med == 0) ? 0 : ab[med-1][1];
            if (pos > fr) {
                left = med;
            } else {
                right = med;
            }
        }
        return new int[]{left, pos - ab[left][0]};
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
