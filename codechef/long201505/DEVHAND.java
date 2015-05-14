package codechef.long201505;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/05/09.
 */
public class DEVHAND {
    private static final long MOD = 1_000_000_007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int T = in.nextInt();
        while (--T >= 0) {
            int n = in.nextInt();
            int k = in.nextInt();
            out.println(solve(n, k));
        }
        out.flush();
    }

    private static long solve(int n, int k) {
        long[][] pw = pow(new long[][]{ {k, 1}, {0, 1} }, n, MOD);
        long all = (pw[0][0] + pw[0][1] - 1 + MOD) % MOD;
        all = (((all * all) % MOD) * all) % MOD;


        long[] powK = new long[3*n+1];
        powK[0] = 1;
        for (int i = 1 ; i <= 3*n ; i++) {
            powK[i] = (powK[i-1] * k) % MOD;
        }

        long sum = 0;
        for (int a = 1 ; a <= n ; a++) {
            for (int b = a ; b <= n ; b++) {
                for (int c = b ; c <= n ; c++) {
                    long ptn = (2 * powK[b+c] + powK[a+c]) % MOD;
                    ptn += ((MOD - powK[c])) % MOD;
                    ptn += ((MOD - powK[b+c-a])) % MOD;
                    ptn %= MOD;

                    long add = (MOD + all - ptn) % MOD;

                    if (a == b && b == c) {
                    } else if (a == b || b == c) {
                        add *= 3;
                    } else {
                        add *= 6;
                    }

                    sum += add;
                    sum %= MOD;

                    // debug(a, b, c, all - ptn, all, ptn);
                }
            }
        }
        return sum;
    }

    public static long[][] pow(long[][] a, long n, long mod) {
        long i = 1;
        long[][] res = E(a.length);
        long[][] ap = mul(E(a.length), a, mod);
        while (i <= n) {
            if ((n & i) >= 1) {
                res = mul(res, ap, mod);
            }
            i *= 2;
            ap = mul(ap, ap, mod);
        }
        return res;
    }

    public static long[][] E(int n) {
        long[][] a = new long[n][n];
        for (int i = 0 ; i < n ; i++) {
            a[i][i] = 1;
        }
        return a;
    }

    public static long[][] mul(long[][] a, long[][] b, long mod) {
        long[][] c = new long[a.length][b[0].length];
        if (a[0].length != b.length) {
            System.err.print("err");
        }
        for (int i = 0 ; i < a.length ; i++) {
            for (int j = 0 ; j < b[0].length ; j++) {
                long sum = 0;
                for (int k = 0 ; k < a[0].length ; k++) {
                    sum = (sum + a[i][k] * b[k][j]) % mod;
                }
                c[i][j] = sum;
            }
        }
        return c;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
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
                return (char)c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char)c;
            }
            throw new InputMismatchException();
        }

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char)c);
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
}
