package codeforces.cf3xx.cr315.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/08/11.
 */
public class B {
    static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        prec(20000);

        int n = in.nextInt();
        memo = new long[2][n+1];
        Arrays.fill(memo[0], -1);
        Arrays.fill(memo[1], -1);
        memo[0][0] = 1;
        memo[1][0] = 0;
        memo[0][1] = 2;
        memo[1][1] = 1;

        dfs(n, 1);

        out.println(memo[1][n]);
        out.flush();
    }

    static int dbg(int n) {
        int ct = 0;
        int ptn = n*n;
        for (int i = 0; i < (1<<ptn) ; i++) {
            int[][] flg = new int[n][n];
            for (int j = 0; j < ptn ; j++) {
                if ((i & (1<<j)) >= 1) {
                    flg[j/n][j%n] = 1;
                }
            }

            boolean whoa = true;
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n ; k++) {
                    if (flg[j][k] == 1 && flg[k][j] == 0) {
                        whoa = false;
                    }
                    for (int l = 0; l < n ; l++) {
                        if (flg[j][k] == 1 && flg[k][l] == 1 && flg[j][l] == 0) {
                            whoa = false;
                        }
                    }
                }
            }
            if (whoa) {
                boolean fnd = true;
                for (int j = 0; j < n ; j++) {
                    if (flg[j][j] == 0) {
                        fnd = false;
                        break;
                    }
                }
                if (!fnd) {
                    ct++;
                }
            }
        }
        return ct;
    }

    static long pow(long a, long x) {
        long res = 1;
        while (x > 0) {
            if (x % 2 != 0) {
                res = (res * a) % MOD;
            }
            a = (a * a) % MOD;
            x /= 2;
        }
        return res;
    }

    static long inv(long a) {
        return pow(a, MOD - 2) % MOD;
    }

    static long[] _fact;
    static long[] _invfact;
    static long comb(long ln, long lr) {
        int n = (int)ln;
        int r = (int)lr;
        if (n < 0 || r < 0 || r > n) {
            return 0;
        }
        if (r > n / 2) {
            r = n - r;
        }
        return (((_fact[n] * _invfact[n - r]) % MOD) * _invfact[r]) % MOD;
    }

    static void prec(int n) {
        _fact = new long[n + 1];
        _invfact = new long[n + 1];
        _fact[0] = 1;
        _invfact[0] = 1;
        for (int i = 1; i <= n; i++) {
            _fact[i] = _fact[i - 1] * i % MOD;
            _invfact[i] = inv(_fact[i]);
        }
    }

    static long[][] memo;

    static long dfs(int n, int f) {
        if (memo[f][n] != -1) {
            return memo[f][n];
        }
        long ret = 0;
        if (f == 1) {
            ret += dfs(n-1, 0);
            ret += dfs(n-1, 1);
        } else {
            ret += 2 * dfs(n-1, 0);
        }
        for (int i = 1 ; i <= n-1; i++) {
            long pt = comb(n-1, i);
            long add = pt * dfs(n-i-1, f) % MOD;
            ret += add;
        }
        ret %= MOD;
        memo[f][n] = ret;
        return ret;
    }

    static long[][] C;

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
