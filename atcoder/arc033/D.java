package atcoder.arc033;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/03/14.
 */
public class D {
    private static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long[] a = new long[n+1];
        for (int i = 0 ; i < n+1 ; i++) {
            a[i] = in.nextInt();
        }
        long T = in.nextInt();
        out.println(solve(a, T));
        out.flush();
    }

    private static long solve(long[] a, long t) {
        if (t < a.length) {
            return a[(int)t];
        }
        int n = a.length-1;
        long[] fact = new long[n+1];
        fact[0] = 1;
        for (int i = 1 ; i <= n ; i++) {
            fact[i] = (fact[i-1] * i) % MOD;
        }

        long[] qii = new long[n+1];
        for (int i = 0 ; i <= n ; i++) {
            qii[i] = fact[i] * fact[n-i] % MOD;
            if ((n - i) % 2 == 1) {
                qii[i] = (MOD - qii[i]) % MOD;
            }
        }
        long[] c = new long[n+1];
        for (int i = 0; i <= n ; i++) {
            c[i] = a[i] * inv(qii[i]) % MOD;
        }

        long[] qt = new long[n+1];
        qt[0] = 1;
        for (int i = 1 ; i <= n ; i++) {
            qt[0] *= (t - i + MOD) % MOD;
            qt[0] %= MOD;
        }
        for (int i = 1 ; i <= n ; i++) {
            qt[i] = qt[i-1] * inv(t-i) % MOD * (t-(i-1)+MOD) % MOD;
        }

        long ans = 0;
        for (int i = 0; i <= n ; i++) {
            ans += c[i] * qt[i] % MOD;
        }
        return ans % MOD;
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
