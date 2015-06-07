package codeforces.looksery2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class H {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        long a = in.nextLong();
        long b = in.nextLong();
        long c = in.nextLong();
        long d = in.nextLong();

        double[] base = {a, b, c, d};
        double max = Math.max(Math.abs(a - b), Math.max(Math.abs(a - c), Math.abs(a - d)));
        double min = 0.0d;
        for (int cur = 0 ; cur < 500 ; cur++) {
            double med = (max + min) / 2;
            double[][] minmax = new double[4][2];
            for (int i = 0; i < 4 ; i++) {
                minmax[i][0] = base[i] - med;
                minmax[i][1] = base[i] + med;
            }
            double[] ad = compute(minmax[0], minmax[3]);
            double[] bc = compute(minmax[1], minmax[2]);
            if (ad[1] < bc[0] || bc[1] < ad[0]) {
                min = med;
            } else {
                max = med;
            }
        }
        out.println(max);
        out.flush();
    }

    private static double[] compute(double[] d1, double[] d2) {
        double max = Math.max(d1[0] * d2[0], d1[1] * d2[1]);
        max = Math.max(max, d1[0] * d2[1]);
        max = Math.max(max, d1[1] * d2[0]);

        double min = Long.MAX_VALUE;
        if (d1[0] <= 0 && 0 <= d1[1]) {
            min = 0;
        }
        if (d2[0] <= 0 && 0 <= d2[1]) {
            min = 0;
        }
        min = Math.min(min, d1[0] * d2[1]);
        min = Math.min(min, d1[1] * d2[0]);
        min = Math.min(min, d1[0] * d2[0]);
        min = Math.min(min, d1[1] * d2[1]);
        return new double[]{min, max};
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



