package hackerrank.worldcodesprint2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/01/30.
 */
public class Flowers {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        prec(300000);

        int rr = in.nextInt();
        int rb = in.nextInt();
        int bb = in.nextInt();
        int br = in.nextInt();
        if (rr + rb + bb + br <= 10) {
            out.println(solveBruteForce(rr, rb, bb, br));
        } else {
            out.println(solveFast(rr, rb, bb, br) % MOD);
        }
        out.flush();
    }

    private static long solveFast(int rr, int rb, int bb, int br) {
        long sum = 0;
        if (Math.abs(rb - br) >= 2) {
            return 0;
        }
        if (rb + br == 0) {
            if (rr >= 1 && bb >= 1) {
                return 0;
            }
            return 1;
        }
        if (rb == br) {
            // r...r
            sum += comb(rr + rb, rb) * comb(bb + br - 1, br - 1) % MOD;

            // b...b
            sum += comb(rr + rb - 1, rb - 1) * comb(bb + br, br) % MOD;
        } else if (rb == br + 1){
            // r...b
            sum += comb(rr + rb - 1, rb - 1) * comb(bb + br, br) % MOD;
        } else {
            // b...r
            sum += comb(rr + rb, rb) * comb(bb + br - 1, br - 1) % MOD;
        }
        return sum;
    }

    private static int solveBruteForce(int rr, int rb, int bb, int br) {
        int len = rr + rb + bb + br + 1;
        int cnt = 0;
        for (int p = 0 ; p < (1<<len) ; p++) {
            int RR = 0;
            int RB = 0;
            int BB = 0;
            int BR = 0;
            for (int i = 0; i < len-1 ; i++) {
                int left = (p & (1<<i)) >= 1 ? 1 : 0;
                int right = (p & (1<<(i+1))) >= 1 ? 1 : 0;
                if (left + right == 2) {
                    RR++;
                } else if (left + right == 0) {
                    BB++;
                } else if (left == 1) {
                    RB++;
                } else {
                    BR++;
                }
            }
            if (RR == rr && RB == rb && BB == bb && BR == br) {
                cnt++;
            }
        }
        return cnt;
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
