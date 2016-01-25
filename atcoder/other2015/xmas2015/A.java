package atcoder.other2015.xmas2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2015/12/24.
 */
public class A {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);


        sch: for (int f = 1 ; f <= 2015 ; f++) {
            for (int g = f ; g <= 2015 ; g++) {
                if (solve(5, 1, 1, f, g, 2016) == 2015) {
                    debug(f, g);
                    break sch;
                }
            }
        }
        debug("done");

        int n = in.nextInt();
        long x = in.nextInt();
        long t = in.nextInt();
        long a = in.nextInt();
        long b = in.nextInt();
        long c = in.nextInt();

        long ret = solve(n, x, t, a, b, c);

        out.println(ret);
        out.flush();
    }




    private static long solve(int n, long x, long t, long a, long b, long c) {
        long[][] mat = new long[][]{ {a % c, b % c}, {0, 1} };
        long[][] cvt = pow(mat, t, c);

        long sum = 0;
        long s = x;
        for (int i = 0; i < n ; i++) {
            sum += s;
            s = (cvt[0][0] * s + cvt[0][1]) % c;
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
