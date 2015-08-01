package atcoder.kupc2012;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/30.
 */
public class J {
    private static final long INF = 1145141919810364364L;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long[] eel = new long[n];
        for (int i = 0; i < n ; i++) {
            eel[i] = in.nextLong();
        }
        imos = new long[n+1];
        for (int i = 0; i < n; i++) {
            imos[i+1] = imos[i] + eel[i];
        }

        memo = new long[n][];
        argmin = new int[n][];
        for (int i = 0; i < n ; i++) {
            memo[i] = new long[n-i];
            argmin[i] = new int[n-i];
            Arrays.fill(memo[i], INF);
        }
        for (int i = 0; i < n ; i++) {
            memo[i][0] = 0;
            argmin[i][0] = i;
        }

        out.println(dfs(0, n - 1));
        out.flush();
    }

    static long[] imos;
    static long[][] memo;
    static int[][] argmin;

    static long dfs(int i, int j) {
        if (i >= j) {
            return 0L;
        }
        int d = j-i;
        if (memo[i][d] != INF) {
            return memo[i][d];
        }
        dfs(i, j-1);
        dfs(i+1, j);
        int fk = Math.max(i, argmin[i][d-1]);
        int tk = Math.min(j-1, argmin[i+1][d-1]);
        long best = INF;
        int arg = -1;
        for (int k = fk ; k <= tk ; k++) {
            long cost = dfs(i, k) + dfs(k+1, j);
            if (best > cost) {
                best = cost;
                arg = k;
            }
        }
        best += imos[j+1] - imos[i];
        memo[i][d] = best;
        argmin[i][d] = arg;
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
