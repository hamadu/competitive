package atcoder.other2013.tenka2013.finale;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 4/23/16.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int d = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt();
        }
        out.println(solve(a, d));
        out.flush();
    }

    private static long solve(int[] a, int d) {
        int sum = 0;
        for (int i = 0; i < a.length ; i++) {
            sum += a[i];
        }
        if (d < sum) {
            return 0;
        }

        prec(1000);


        memo = new long[1000][40];
        for (int i = 0; i < 1000; i++) {
            Arrays.fill(memo[i], -1);
        }

        int n = a.length;
        D = d;
        long[][][] dp = new long[n+1][n+1][sum+1];
        dp[0][0][0] = 1;
        for (int i = 0; i < n ; i++) {
            for (int u = 0; u <= n ; u++) {
                for (int j = 0; j <= sum ; j++) {
                    long base = dp[i][u][j];
                    if (base == 0) {
                        continue;
                    }

                    // dont use
                    dp[i+1][u][j] += base;
                    dp[i+1][u][j] -= (dp[i+1][u][j] >= MOD) ? MOD : 0;

                    // use
                    for (int l = 0 ; l < a[i] ; l++) {
                        int ti = i+1;
                        int tu = u+1;
                        long add = base * comb(j, l) % MOD;
                        dp[ti][tu][j+l] += add;
                        dp[ti][tu][j+l] -= (dp[ti][tu][j+l] >= MOD) ? MOD : 0;
                    }
                }
            }
        }

        long[] parity = new long[]{0, 0};
        for (int u = 0 ; u <= n ; u++) {
            for (int f = 0 ; f <= sum ; f++) {
                if (dp[n][u][f] == 0) {
                    continue;
                }
                long fillPtn = dp[n][u][f];
                long leftPtn = pow(n - u, d - f);
                long ptn = fillPtn * leftPtn % MOD;
                ptn %= MOD;

                parity[u%2] += ptn;
                parity[u%2] -= parity[u%2] >= MOD ? MOD : 0;
            }
        }


        return (parity[0] + MOD - parity[1]) % MOD;
    }

    static final int MOD = 1000000007;

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
    static long[][] memo;
    static int D;

    // it means : comb(D - n, k). D := constant
    static long comb(int u, int k) {
        if (memo[u][k] != -1) {
            return memo[u][k];
        }
        long n = D - u;
        long upper = 1;
        for (long nl = 0; nl < k; nl++) {
            upper = (upper * ((n - nl) % MOD)) % MOD;
        }
        long res = upper * _invfact[k] % MOD;
        memo[u][k] = res;
        return res;
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
