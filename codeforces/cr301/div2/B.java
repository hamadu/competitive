package codeforces.cr301.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        int p = in.nextInt();
        int x = in.nextInt();
        int y = in.nextInt();

        int[] a = new int[n];
        for (int i = 0 ; i < k ; i++) {
            a[i] = in.nextInt();
        }

        // sum(a) <= x
        // med(a) >= y
        int[] answer = null;
        for (int med = y ; med <= p ; med++) {
            int[] res = computePossibleMark(a, k, x, med);
            if (res != null) {
                answer = res;
                break;
            }
        }

        if (answer == null) {
            out.println(-1);
        } else {
            StringBuilder s = new StringBuilder();
            for (int i = k ; i < n ; i++) {
                s.append(' ').append(answer[i]);
            }
            out.println(s.substring(1));
        }
        out.flush();
    }

    private static int[] computePossibleMark(int[] a, int k, int x, int y) {
        int n = a.length;
        int half = (n - 1) / 2;
        int lower = 0;
        for (int i = 0 ; i < k ; i++) {
            if (a[i] < y) {
                lower++;
            }
        }


        for (int i = k ; i < n ; i++) {
            if (lower < half) {
                lower++;
                a[i] = 1;
            } else {
                a[i] = y;
            }
        }

        int sum = 0;
        for (int i = 0 ; i < n ; i++) {
            sum += a[i];
        }
        int[] med = a.clone();
        Arrays.sort(med);
        if (sum <= x && med[n/2] >= y) {
            return a;
        }
        return null;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
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
}



