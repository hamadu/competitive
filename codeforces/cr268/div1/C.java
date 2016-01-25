package codeforces.cr268.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by hama_du on 15/09/10.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        long a = in.nextLong();

        char[] c = find(a);
        long L = Long.valueOf(String.valueOf(c));
        long diff = compute(L).longValue() - a;
        long[] p = findPair(L, diff);

        out.println(p[0] + " " + p[1]);
        out.flush();
    }

    static long[] findPair(long L, long diff) {
        long from = 1;
        long to = L;
        while (diff != 0) {
            long v = doit(from);
            if (from == to || v > diff) {
                to++;
                diff += doit(to);
            } else {
                from++;
                diff -= v;
            }
        }
        return new long[]{from, to};
    }

    static char[] find(long L) {
        long[] x = new long[32];
        for (int i = 0; i < 32 ; i++) {
            for (int d = 0; d <= 9; d++) {
                x[i] = d;

                long[] y = x.clone();
                for (int j = i+1 ; j < x.length ; j++) {
                    y[j] = 9;
                }
                if (compute(y).compareTo(BigInteger.valueOf(L)) >= 0) {
                    break;
                }
            }

        }
        char[] ret = new char[32];
        for (int i = 0; i < 32 ; i++) {
            ret[i] = (char)('0' + x[i]);
        }
        return ret;
    }

    private static BigInteger compute(long x) {
        char[] c = String.valueOf(x).toCharArray();
        long[] y = new long[c.length];
        for (int i = 0; i < c.length ; i++) {
            y[i] = c[i]-'0';
        }
        return compute(y);
    }

    private static BigInteger compute(long[] x) {
        BigInteger total = BigInteger.ZERO;
        BigInteger head = BigInteger.ZERO;
        boolean first = false;
        for (int i = 0; i < x.length ; i++) {
            BigInteger fullTail = BigInteger.ONE;
            BigInteger subTail = BigInteger.ZERO;
            for (int j = i+1 ; j < x.length; j++) {
                subTail = subTail.multiply(BigInteger.TEN);
                subTail = subTail.add(BigInteger.valueOf(x[j]));
                fullTail = fullTail.multiply(BigInteger.TEN);
            }
            if (first || x[i] >= 1) {
                for (int d = 1; d <= 9; d++) {
                    BigInteger fullNum = (d >= x[i]) ? head.add(BigInteger.ZERO) : head.add(BigInteger.ONE);
                    total = total.add(fullNum.multiply(fullTail).multiply(BigInteger.valueOf(d)));
                    if (d == x[i]) {
                        total = total.add(subTail.add(BigInteger.ONE).multiply(BigInteger.valueOf(d)));
                    }
                }
                first = true;
            }
            head = head.multiply(BigInteger.TEN);
            head = head.add(BigInteger.valueOf(x[i]));
        }
        return total;
    }

    static long doit(long a) {
        char[] c = String.valueOf(a).toCharArray();
        long sum = 0;
        for (int i = 0; i < c.length ; i++) {
            sum += c[i]-'0';
        }
        return sum;
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
