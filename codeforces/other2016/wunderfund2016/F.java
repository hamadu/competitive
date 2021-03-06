package codeforces.other2016.wunderfund2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/01/30.
 */
public class F {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long[] a = new long[n];
        long[] b = new long[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt();
        }
        for (int i = 0; i < n ; i++) {
            b[i] = in.nextInt();
        }
        long sumA = 0;
        long sumB = 0;
        for (int i = 0; i < n ; i++) {
            sumA += a[i];
            sumB += b[i];
        }
        int[] segments = sumA <= sumB ? solve(a, b) : solve(b, a);
        int fromA = segments[0];
        int toA = segments[1];
        int fromB = segments[2];
        int toB = segments[3];
        if (sumA > sumB) {
            fromA = segments[2];
            toA = segments[3];
            fromB = segments[0];
            toB = segments[1];
        }

        String lineA = buildIntegers(fromA, toA);
        String lineB = buildIntegers(fromB, toB);

        out.println(toA - fromA + 1);
        out.println(lineA);
        out.println(toB - fromB + 1);
        out.println(lineB);
        out.flush();
    }

    private static int[] solve(long[] a, long[] b) {
        int[] diffA = new int[1000010];
        int[] diffB = new int[1000010];
        Arrays.fill(diffA, -2);
        Arrays.fill(diffB, -2);
        diffA[0] = -1;
        diffB[0] = -1;

        int n = a.length;
        int fromA = -1;
        int toA = -1;
        int fromB = -1;
        int toB = -1;
        int bi = 0;
        long asum = 0, bsum = 0;
        for (int i = 0 ; i < n ; i++) {
            asum += a[i];
            while (bi < n && bsum + b[bi] <= asum) {
                bsum += b[bi];
                bi++;
            }
            int d = (int)(asum - bsum);
            if (diffA[d] != -2) {
                fromA = diffA[d]+1;
                toA = i;
                fromB = diffB[d]+1;
                toB = bi-1;
                break;
            }
            diffA[d] = i;
            diffB[d] = bi-1;
        }
        return new int[]{fromA, toA, fromB, toB};

    }

    private static String buildIntegers(int fromA, int toA) {
        StringBuilder line = new StringBuilder();
        for (int i = fromA ; i <= toA ; i++) {
            line.append(' ').append(i+1);
        }
        return line.substring(1);
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
