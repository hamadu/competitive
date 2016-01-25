package codeforces.cr336.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/01/15.
 */
public class B {
    private static final int INF = 114514;
    static int[] a;
    static int[][][] memo;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        a = new int[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt();
        }
        memo = new int[2][n][n];
        for (int k = 0; k < 2; k++) {
            for (int i = 0; i < n ; i++) {
                Arrays.fill(memo[k][i], INF);
            }
        }

        out.println(dfs(0, n-1, 1));

        out.flush();
    }

    private static int dfs(int l, int r, int f) {
        if (l > r) {
            return 0;
        }
        if (memo[f][l][r] != INF) {
            return memo[f][l][r];
        }
        // left
        int best = INF;
        for (int k = l ; k <= r ; k++) {
            if (a[k] == a[r]) {
                best = Math.min(best, dfs(l, k-1, 1) + dfs(k+1, r-1, 0));
            }
            if (a[l] == a[k]) {
                best = Math.min(best, dfs(l+1, k-1, 0) + dfs(k+1, r, 1));
            }
        }
        if (f == 1) {
            best++;
        }
        memo[f][l][r] = best;
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
