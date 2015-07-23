package aoj.vol25;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/22.
 */
public class P2539 {
    private static final long LIMIT = 1000000000000000000L;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            if (n == 0) {
                break;
            }
            long[] k = new long[n];
            for (int i = 0; i < n ; i++) {
                k[i] = in.nextLong();
            }
            out.println(solve(k));
        }

        out.flush();
    }

    private static String solve(long[] k) {
        int n = k.length;
        boolean zero = true;
        for (int i = 0; i < n ; i++) {
            if (k[i] > LIMIT) {
                return "None";
            }
            if (k[i] >= 1) {
                zero = false;
                if ((1L<<(i+0L)) > LIMIT) {
                    return "None";
                }
            }
        }
        if (zero) {
            return "None";
        }

        int found = 0;
        long[] ab = new long[2];
        for (long d = k[0] * 2 - 1 ; d <= k[0] * 2 + 1; d++) {
            long[] ret = solveSub(d, k);
            if (ret != null) {
                if (ret[0] == -1) {
                    return "Many";
                }
                found++;
                ab = ret;
            }
        }
        if (found == 0) {
            return "None";
        }
        if (found >= 2) {
            return "Many";
        }
        return String.format("%d %d", ab[0], ab[1]);
    }

    private static long[] solveSub(long D, long[] K) {
        int n = K.length;
        long A = 0;
        long B = 0;
        for (int i = n-1; i >= 0; i--) {
            if (K[i] == D) {
                A |= 1L<<i;
                B |= 1L<<i;
            } else if (K[i] >= 1) {
                B |= 1L<<i;
                B += K[i] - 1;
                A = B - D + 1;
                break;
            }
        }
        if (B - A + 1 != D || A <= 0 || B > LIMIT) {
            return null;
        }
        for (int i = n-1 ; i >= 0 ; i--) {
            if (count(B+1, i) - count(A, i) != K[i]) {
                return null;
            }
        }
        return new long[]{A, B};
    }

    // count [0~A)
    static long count(long A, int k) {
        long cur = 1L<<(k+1);
        long per = cur / 2;
        long ptn = A / cur;
        return per * ptn + Math.max(0, (A % cur - per));
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
