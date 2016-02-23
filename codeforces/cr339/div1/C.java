package codeforces.cr339.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/01/21.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long[] a = new long[n];
        long sum = 0;
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt();
            sum += a[i];
        }

        long ret = 0;
        for (long part = 1 ; part * part <= sum ; part++) {
            if (sum % part == 0) {
                if (isOK(a, sum, part)) {
                    ret = Math.max(ret, part);
                }
                if (isOK(a, sum, sum / part)) {
                    ret = Math.max(ret, sum / part);
                }
            }
        }
        if (ret == 0) {
            out.println(0);
            for (int i = 0 ; i < n ; i++) {
                for (int j = 0; j < a[i] ; j++) {
                    out.print((char)('a' + i));
                }
            }
            out.println();
            out.flush();
            return;
        }

        long[] b = new long[n];
        for (int i = 0 ; i < n ; i++) {
            b[i] = a[i] / ret;
        }
        StringBuilder half = new StringBuilder();
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < b[i] / 2 ; j++) {
                half.append((char)('a' + i));
            }
        }
        StringBuilder rev = new StringBuilder(half).reverse();
        for (int i = 0 ; i < n ; i++) {
            if (b[i] % 2 == 1) {
                half.append((char)('a' + i));
            }
        }
        half.append(rev);


        StringBuilder halfRev = new StringBuilder(half).reverse();
        StringBuilder all = new StringBuilder();
        for (int i = 0 ; i < ret / 2 ; i++) {
            all.append(half);
            all.append(halfRev);
        }
        if (ret % 2 == 1) {
            all.append(half);
        }

        out.println(ret);
        out.println(all.toString());
        out.flush();
    }

    private static boolean isOK(long[] a, long sum, long part) {
        int n = a.length;
        int mod = 0;
        for (int i = 0 ; i < n ; i++) {
            if (a[i] % part != 0) {
                return false;
            }
            long pt = a[i] / part;
            mod += pt % 2;
        }
        if (part % 2 == 1 && mod >= 2) {
            return false;
        }
        return true;
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
