package atcoder.arc.arc042;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/11/02.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        long x = in.nextInt();
        long p = in.nextInt();
        long a = in.nextInt();
        long b = in.nextInt();
        if (b-a <= (1<<25)) {
            out.println(solve(x, p, a, b));
        } else {
            out.println(solve2(x, p, a, b));
        }
        out.flush();
    }

    static long solve2(long x, long p, long a, long b) {
        if (x % p == 0) {
            return 0;
        }
        Map<Long,Long> lmap = new HashMap<>();
        long M = (int)(Math.sqrt(p)+1);
        long xm = pow(x, M, p);
        for (long i = 1 ; i <= M; i++) {
            lmap.put(pow(xm, i, p), i);
        }
        long[] xf = new long[(int)M+1];
        xf[0] = 1;
        for (int i = 1; i <= M; i++) {
            xf[i] = (xf[i-1] * x) % p;
        }
        long L1 = Long.MAX_VALUE;
        for (long L = 1 ; L <= 1 ; L++) {
            for (int f = 0; f <= M; f++) {
                long lm = (xf[f] * L) % p;
                if (lmap.containsKey(lm)) {
                    long y = M * lmap.get(lm)-f;
                    if (y > 0) {
                        L1 = Math.min(L1, y);
                    }
                }
            }
        }

        for (long L = 1 ; ; L++) {
            // solve L = X^Y mod p (a <= Y <= b)
            for (int f = 0; f <= M ; f++) {
                long lm = (xf[f] * L) % p;
                if (lmap.containsKey(lm)) {
                    long y = M*lmap.get(lm)-f;
                    y = y % L1;
                    y = y + ((a - y + L1 - 1) / L1) * L1;
                    if (a <= y && y <= b) {
                        return L;
                    }
                }
            }
        }
//        throw new RuntimeException(x + " " + p + " " + a + " " + b);
    }

    static long solve(long x, long p, long a, long b) {
        long min = p-1;
        long val = 0;
        for (long c = a ; c <= b ; c++) {
            if (c == a) {
                val = pow(x, a, p);
            } else {
                val *= x;
                val %= p;
            }
            min = Math.min(min, val);
            if (min <= 1) {
                break;
            }
        }
        return min;
    }



    static long pow(long a, long x, long MOD) {
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
