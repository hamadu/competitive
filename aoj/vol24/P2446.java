package aoj.vol24;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/25.
 */
public class P2446 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        n = in.nextInt();
        M = in.nextLong();
        a = new long[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextLong();
        }
        rate = new double[n];
        for (int i = 0; i < n ; i++) {
            rate[i] = in.nextInt() / 100.0d;
        }

        lcm = new long[1<<n];
        dfsL(0, 0, 0);
        lcm[0] = 1;

        table = new long[1<<n];
        for (int j = 1; j < (1<<n) ; j++) {
            table[j] = M / lcm[j];
        }
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 1<<n ; j++) {
                if ((j & (1<<i)) >= 1) {
                    table[j] -= table[j ^ (1 << i)];
                }
            }
        }

        for (int i = 0; i < 1<<n ; i++) {
            table[i] = Math.abs(table[i]);
        }

        out.println(String.format("%.9f", ret(0, 0, 1.0d)));
        out.flush();
    }

    static int n;
    static long[] a;
    static double[] rate;
    static long M;
    static long[] lcm;

    static double ret(int idx, int ptn, double r) {
        if (idx == n) {
            if (ptn == 0) {
                return 0.0d;
            }
            return r * table[ptn];
        }
        return ret(idx+1, ptn, r * (1.0d - rate[idx])) + ret(idx+1, ptn|(1<<idx), r * rate[idx]);
    }

    static void dfsL(int idx, int ptn, long l) {
        if (idx == n) {
            lcm[ptn] = l;
            return;
        }
        dfsL(idx + 1, ptn, l);
        long to = INF;
        if (l == 0) {
            to = a[idx];
        } else if (l < INF) {
            long gcd = gcd(l, a[idx]);
            to = l / gcd;
            if (to >= INF / a[idx]) {
                to = INF;
            } else {
                to *= a[idx];
            }
        }
        dfsL(idx + 1, ptn | (1 << idx), to);
    }

    static long[] table;

    private static long gcd(long a, long b) {
        return (b == 0) ? a : gcd(b, a%b);
    }

    static final long INF = 2000000000000000000L;

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
