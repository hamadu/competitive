package codeforces.cr153.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/08/03.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long[][] a = new long[n][2];
        long x1 = 0;
        long x2 = 0;
        for (int i = 0; i < n ; i++) {
            a[i][0] = in.nextLong();
            a[i][1] = i;
            x2 ^= a[i][0];
        }
        Arrays.sort(a, new Comparator<long[]>() {
            @Override
            public int compare(long[] o1, long[] o2) {
                return Long.signum(o2[0] - o1[0]);
            }
        });

        int[] which = new int[n];
        Arrays.fill(which, 2);
        for (int i = 0; i < n ; i++) {
            int idx = (int)a[i][1];
            long t1 = x1 ^ a[i][0];
            long t2 = x2 ^ a[i][0];
            debug(a[i][0], x1, x2, t1, t2);
            if (t1 + t2 > x1 + x2 || (t1 + t2 == x1 + x2 && t1 < x1)) {
                x1 = t1;
                x2 = t2;
                which[idx] = 1;
            }
        }

        debug(x1,x2);

        StringBuilder line = new StringBuilder();
        for (int i = 0; i < n ; i++) {
            line.append(' ').append(which[i]);
        }
        out.println(line.substring(1));
        out.flush();
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
