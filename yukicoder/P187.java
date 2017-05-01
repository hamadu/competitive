package yukicoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class P187 {
    private static final long MOD = (long)1e9+7;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long[] a = new long[n];
        long[] mod = new long[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt();
            mod[i] = in.nextInt();
        }

        boolean isOK = true;
        sch: for (int i = 0; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                long g = gcd(mod[i], mod[j]);
                if (Math.abs(a[i] - a[j]) % g != 0) {
                    isOK = false;
                    break sch;
                }
            }
        }

        if (!isOK) {
            out.println(-1);
            out.flush();
            return;
        }

        int[] primes = generatePrimes(32000);
        for (int p : primes) {
            int best = -1;
            int max = 0;
            long[] tomo = new long[n];
            for (int i = 0 ; i < n ; i++) {
                tomo[i] = mod[i];
                int cnt = 0;
                while (tomo[i] % p == 0) {
                    tomo[i] /= p;
                    cnt++;
                }
                if (max < cnt) {
                    best = i;
                    max = cnt;
                }
            }
            if (best != -1) {
                for (int i = 0; i < n; i++) {
                    if (i != best) {
                        mod[i] = tomo[i];
                        a[i] %= mod[i];
                    }
                }
            }
        }

        for (int i = 0 ; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                long g = gcd(mod[i], mod[j]);
                if (g >= 2) {
                    mod[j] /= g;
                    a[j] %= mod[j];
                }
            }
        }


        long gg = 0;
        for (int i = 0; i < n ; i++) {
            gg += a[i];
        }
        if (gg == 0) {
            long ans = 1;
            for (int i = 0; i < n ; i++) {
                ans *= mod[i];
                ans %= MOD;
            }
            out.println(ans);
            out.flush();
            return;
        }


        long ans = crtGarner(a, mod, MOD);

        out.println(ans);
        out.flush();
    }


    /**
     * Generates primes less than upto.
     *
     * O(nlog(logn))
     *
     * @param upto limit
     * @return array of primes
     */
    static int[] generatePrimes(int upto) {
        boolean[] isp = new boolean[upto];
        Arrays.fill(isp, true);
        isp[0] = isp[1] = false;

        int pi = 0;
        for (int i = 2; i < upto ; i++) {
            if (isp[i]) {
                pi++;
                for (int j = i * 2; j < upto; j += i) {
                    isp[j] = false;
                }
            }
        }

        int[] ret = new int[pi];
        int ri = 0;
        for (int i = 2 ; i < upto ; i++) {
            if (isp[i]) {
                ret[ri++] = i;
            }
        }
        return ret;
    }



    /**
     * Finds minimum number x s.t.
     *   x = a[0] (mod[0])
     *   x = a[1] (mod[1])
     *   ...
     *   x = a[n-1] (mod[n-1])
     *
     * @param a
     * @param mod <b>co-prime</b> numbers
     * @param M
     * @return x (mod M)
     */
    static long crtGarner(long[] a, long[] mod, long M) {
        int n = a.length;
        long[] gamma = new long[n];
        for (int k = 1 ; k < n ; k++) {
            long prod = 1;
            for (int i = 0; i < k ; i++) {
                prod = prod * mod[i] % mod[k];
            }
            gamma[k] = modInv(prod, mod[k]);
        }

        long[] v = new long[n];
        v[0] = a[0];
        for (int k = 1 ; k < n ; k++) {
            long temp = v[k-1];
            for (int j = k-2 ; j >= 0 ; j--) {
                temp = (temp * mod[j] + v[j]) % mod[k];
            }
            v[k] = (a[k] - temp) * gamma[k] % mod[k];
            if (v[k] < 0) {
                v[k] += mod[k];
            }
        }

        long value = 0;
        for (int i = n-1 ; i >= 0 ; i--) {
            value = value * mod[i] + v[i];
            value %= M;
        }

        return value;
    }

    /**
     * Finds number x s.t.
     *   x = a1 (mod1)
     *   x = a2 (mod2)
     *
     * @param a1
     * @param mod1
     * @param a2
     * @param mod2
     * @return (p, L) s.t. x = p (mod L)
     */
    static long[] crt(long a1, long mod1, long a2, long mod2) {
        long g = gcd(mod1, mod2);
        long x = a1 + (mod1 / g) * (a2 - a1) * modInv(mod1 / g, mod2 / g);
        long lcm = (mod1 / g) * mod2;
        return new long[]{ (lcm + (x % lcm)) % lcm, lcm};
    }

    public static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a%b);
    }


    public static long modInv(long x, long mod) {
        long y = mod, a = 1, b = 0, t;
        while (y != 0) {
            long d = x / y;
            t = x - d * y; x = y; y = t;
            t = a - d * b; a = b; b = t;
        }
        return a >= 0 ? a % mod : a % mod + mod;
    }

    /**
     * Finds (x, y) s.t. ax + by = gcd(a, b)
     *
     * @param a
     * @param b
     * @return (gcd(a, b), x, y)
     */
    static long[] extGCD(long a, long b) {
        if (b == 0) {
            return new long[]{a, 1, 0};
        }
        long[] gyx = extGCD(b, a%b);
        long g = gyx[0];
        long y = gyx[1];
        long x = gyx[2];
        y -= (a / b) * x;
        return  new long[]{g, x, y};
    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int[] nextInts(int n) {
            int[] ret = new int[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextInt();
            }
            return ret;
        }

        private int[][] nextIntTable(int n, int m) {
            int[][] ret = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextInt();
                }
            }
            return ret;
        }

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
            }
            return ret;
        }

        private long[][] nextLongTable(int n, int m) {
            long[][] ret = new long[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextLong();
                }
            }
            return ret;
        }

        private double[] nextDoubles(int n) {
            double[] ret = new double[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextDouble();
            }
            return ret;
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
            return res*sgn;
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
            return res*sgn;
        }

        public double nextDouble() {
            return Double.valueOf(nextToken());
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
