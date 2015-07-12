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
        for (int i = 0; i < n ; i++) {
            s[i] = in.nextToken().toCharArray();
        }
        S = s;
        memo = new long[21][51][51][26];
        for (int i = 0; i < 21 ; i++) {
            for (int j = 0; j < 51 ; j++) {
                for (int k = 0; k < 51 ; k++) {
                    Arrays.fill(memo[i][j][k], -1);
                }
            }
        }

        fillable = new boolean[21][51][51][26];
        for (int i = 0 ; i < 21 ; i++) {
            for (int l = 0 ; l < n ; l++) {
                for (int c = 0 ; c < 26 ; c++) {
                    for (int r = l ; r < n ; r++) {
                        if (i >= S[r].length || (S[r][i] - 'a' != c && S[r][i] != '?')) {
                            break;
                        }
                        fillable[i][l][r][c] = true;
                    }
                }
            }
        }

        out.println(dfs(0, 0, n, 0));
        out.flush();
    }

    static char[][] S;

    static long[][][][] memo;

    static boolean[][][][] fillable;

    private static long dfs(int idx, int l, int r, int c) {
        if (idx >= 21) {
            return 1;
        }
        if (l == r) {
            return 1;
        }
        if (l + 1 == r && idx >= S[l].length) {
            return 1;
        }
        if (memo[idx][l][r][c] != -1) {
            return memo[idx][l][r][c];
        }

        long sum = 0;
        for (int f = l ; f < r; f++) {
            for (int cc = c ; cc < 26 ; cc++) {
                if (fillable[idx][l][f][cc]) {
                    long next = dfs(idx+1, l, f+1, 0);
                    long left = dfs(idx, f+1, r, cc);
                    sum += next * left % MOD;
                }
            }
        }
        sum %= MOD;
        memo[idx][l][r][c] = sum;
        return sum;
    }

    private static boolean canFill(int idx, int l, int f, int cc) {
        return true;
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
