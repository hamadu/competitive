package atcoder.ttpc2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 15/09/20.
 */
public class J {
    private static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        K = in.nextInt();
        memo = new long[n+1][2];
        for (int i = 0; i <= n ; i++) {
            Arrays.fill(memo[i], -1);
        }

        ncr = new long[n+1][n+1];
        for (int i = 0; i <= n ; i++) {
            ncr[i][0] = ncr[i][i] = 1;
            for (int j = 1 ; j < i ; j++) {
                ncr[i][j] = (ncr[i-1][j-1] + ncr[i-1][j]) % MOD;
            }
        }
        fact = new long[n+1];
        fact[0] = 1;
        for (int i = 1 ; i < n ; i++) {
            fact[i] = (fact[i-1] * i) % MOD;
        }
        out.println(dfs(n, 0));
        out.flush();
    }

    static long[] fact;

    static long[][] ncr;
    static long[][] memo;
    static int K;

    static long dfs(int left, int flg) {
        if (left < 0) {
            return 0;
        }
        if (memo[left][flg] != -1) {
            return memo[left][flg];
        }
        long ret = 0;
        if (left == 1) {
        } else if (left == 0) {
            ret = flg == 1 ? 1 : 0;
        } else {
            for (int u = 1 ; u <= K-1 ; u++) {
                ret += (fact[u] * ncr[left-1][u]) % MOD * dfs(left-u-1, (u == K-1) ? 1 : flg) % MOD;
                ret %= MOD;
            }
        }
        memo[left][flg] = ret;
        return ret;
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
