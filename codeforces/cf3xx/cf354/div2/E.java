package codeforces.cf3xx.cf354.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/07/17.
 */
public class E {
    static long limit;
    static long Q = (long)1e9;

    public static void main(String[] args) {
        limit = System.currentTimeMillis() + 900;

        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long k = in.nextLong();

        long[] co = new long[n+1];
        int turn = 1;
        int q = 0;
        for (int i = 0; i <= n; i++) {
            String l = in.nextToken();
            if (l.charAt(0) == '?') {
                co[i] = Q;
                q++;
            } else {
                co[i] = Long.valueOf(l);
                turn = 1 - turn;
            }
        }
        if (q == 0) {
            out.println(verify(co, k) ? "Yes" : "No");
        } else {
            if (k == 0) {
                if (co[0] == Q) {
                    out.println(turn == 0 ? "Yes" : "No");
                } else {
                    out.println(co[0] == 0 ? "Yes" : "No");
                }
            } else {
                int lastMove = (turn+q%2)%2;
                out.println(lastMove == 1 ? "Yes" : "No");
            }
        }
        out.flush();
    }

    private static boolean verify(long[] co, long k) {
        for (long p : new long[]{
                999999929,
                999999937,
                1000000007,
                1000000009,
                1000000021,
                1000000033
        }) {
            if (System.currentTimeMillis() >= limit) {
                break;
            }
            if (nonZero(co, k, p)) {
                return false;
            }
        }
        return true;
    }

    private static boolean nonZero(long[] co, long k, long p) {
        long sum = 0;
        int n = co.length;
        long kk = 1;
        for (int i = 0; i < n; i++) {
            sum = (sum + co[i] * kk) % p;
            kk = (kk * k) % p;
        }
        return sum != 0;
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
