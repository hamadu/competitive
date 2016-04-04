package codeforces.cf2xx.cr232.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by dhamada on 15/05/21.
 */
public class D {
    private static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        // 1 2 5 4 3
        // 1 2 3 5 4 -> 1
        // 1 2 4 3 5 -> 1
        // 1 2 4 5 3 -> 2
        // 1 2 5 3 4 -> 2
        // 1 2 5 4 3 -> 3


        int n = in.nextInt();

        long[] fact = new long[n+10];
        fact[0] = 1;
        for (int i = 1 ; i < fact.length ; i++) {
            fact[i] = (fact[i-1] * i) % MOD;
        }

        long[] perm = new long[n+10];
        for (int i = 2; i < perm.length; i++) {
            long inc = (1L * i * (i-1) / 2) % MOD;
            long f = (fact[i-1] * inc) % MOD;
            long g = (i * perm[i-1]) % MOD;
            perm[i] = (f + g) % MOD;
        }

        int[] a = new int[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt();
        }


        int[] base = new int[n];
        for (int i = 0; i < n ; i++) {
            base[i] = i+1;
        }
        BIT bit = new BIT(n+10);
        for (int i = 1; i <= n ; i++) {
            bit.add(i, 1);
        }

        long[] imos = new long[n+1];
        long sum = 0;
        for (int i = 0 ; i < n ; i++) {
            int l = n - i;
            long s = bit.range(1, a[i]-1);
            long comb = (s*(s-1)/2) % MOD;
            sum += (comb * fact[l-1]) % MOD;
            sum %= MOD;
            sum += (s * perm[l-1]) % MOD;
            sum %= MOD;

            imos[i] = (s * fact[l-1]) % MOD;
            bit.add(a[i], -1);
        }

        imos[n] = 1;
        for (int i = n-1 ; i >= 0 ; i--) {
            imos[i] += imos[i+1];
            imos[i] %= MOD;
        }

        BIT bit2 = new BIT(n+10);
        for (int i = 1 ; i <= n ; i++) {
            bit2.add(i, 1);
        }
        for (int i = 0 ; i < n ; i++) {
            long smaller = bit2.range(1, a[i] - 1);
            sum += (smaller * imos[i+1]) % MOD;
            sum %= MOD;
            bit2.add(a[i], -1);
        }
        out.println(sum);
        out.flush();
    }

    // BIT, 1-indexed, range : [a,b]
    static class BIT {
        long N;
        long[] data;
        BIT(int n) {
            N = n;
            data = new long[n+1];
        }

        long sum(int i) {
            long s = 0;
            while (i > 0) {
                s += data[i];
                i -= i & (-i);
            }
            return s;
        }

        long range(int i, int j) {
            return sum(j) - sum(i-1);
        }

        void add(int i, long x) {
            while (i <= N) {
                data[i] += x;
                i += i & (-i);
            }
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

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
