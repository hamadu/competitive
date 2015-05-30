package codeforces.cr72.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by dhamada on 15/05/30.
 */
public class D {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        long a = in.nextInt();
        long b = in.nextInt();
        long k = in.nextInt();

        boolean isPrime = true;
        for (long f = 2 ; f * f <= k ; f++) {
            if (k % f == 0) {
                isPrime = false;
                break;
            }
        }


        long ans = 0;
        if (isPrime) {
            if (k <= 50000) {
                ans = solveSmall(b, k) - solveSmall(a - 1, k);
            } else {
                ans = solveLarge(b, k) - solveLarge(a - 1, k);
            }
        }
        out.println(ans);
        out.flush();
    }

    private static long solveLarge(long upto, long basePrime) {
        long ret = 0;
        long p = basePrime;
        long p2 = p * p;
        if (p <= upto) {
            ret++;
        }
        if (p2 <= upto) {
            ret++;
        }
        return ret;
    }

    static int[] primes = generatePrimes(1000000);

    private static long solveSmall(long upto, long basePrime) {
        int idx = -1;
        for (int pi = 0 ; pi < primes.length ; pi++) {
            if (primes[pi] == basePrime) {
                idx = pi;
                break;
            }
        }

        long sum = 0;
        int pt = 1<<idx;
        for (int i = 0 ; i < pt ; i++) {
            long mul = basePrime;
            for (int k = 0 ; k < pt ; k++) {
                if ((i & (1<<k)) >= 1) {
                    mul *= primes[k];
                    if (mul > upto) {
                        mul = -1;
                        break;
                    }
                }
            }
            if (mul >= 1) {
                int sign = (Integer.bitCount(i) % 2 == 1) ? -1 : 1;
                sum += (upto / mul) * sign;
            }
        }
        return sum;
    }

    static int[] generatePrimes(int upto) {
        boolean[] isp = new boolean[upto];
        Arrays.fill(isp, true);

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
