package aoj.vol26;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/11.
 */
public class P2630 {
    private static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        char[][] s = new char[n][];
        C = 0;
        for (int i = 0; i < n ; i++) {
            s[i] = in.nextToken().toCharArray();
            C = Math.max(C, s[i].length);
        }
        for (int i = 0; i < n ; i++) {
            s[i] = Arrays.copyOf(s[i], C);
            for (int j = 0; j < C ; j++) {
                if (s[i][j] == 0) {
                    s[i][j] = '`';
                }
            }
        }

        S = s;
        memo = new long[21][51][51][30];
        for (int i = 0; i < 21 ; i++) {
            for (int j = 0; j < 51 ; j++) {
                for (int k = 0; k < 51 ; k++) {
                    Arrays.fill(memo[i][j][k], -1);
                }
            }
        }
        N = s.length;

        out.println(dfs(0, 0, n, 0));
        out.flush();
    }

    static long dfs(int c, int fr, int to, int last) {
        if (c == C) {
            return (to - fr >= 2) ? 0 : 1;
        }
        if (fr == to) {
            return 1;
        }
        if (memo[c][fr][to][last] != -1) {
            return memo[c][fr][to][last];
        }
        char min = (char)('`' + last);
        for (int i = fr; i < to; i++) {
            if (S[i][c] != '?' && S[i][c] < min) {
                memo[c][fr][to][last] = 0;
                return 0;
            }
        }
        long ret = 0;

        int[] kind = new int[255];
        int fu = 0;
        int only = -1;
        for (int i = fr ; i < to ; i++) {
            if ('`' <= S[i][c] && S[i][c] <= 'z') {
                if (kind[S[i][c]] == 0) {
                    kind[S[i][c]]++;
                    fu++;
                    only = S[i][c] - '`';
                }
            }
            if (fu <= 1) {
                if (only == 0 && i - fr + 1 >= 2) {
                    continue;
                }
                for (int u = last; u <= 26; u++) {
                    if ((only == -1 && u != 0) || only == u) {
                        ret += (dfs(c + 1, fr, i + 1, 0) * dfs(c, i + 1, to, u + 1)) % MOD;
                    }
                }
            }
        }
        ret %= MOD;
        memo[c][fr][to][last] = ret;
        return ret;
    }

    static int C;
    static int N;
    static char[][] S;

    static long[][][][] memo;

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
