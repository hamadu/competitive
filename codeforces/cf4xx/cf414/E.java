package codeforces.cf4xx.cf414;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = in.nextInts(n);
        if (n == 1) {
            out.println(a[0]);
            out.flush();
            return;
        }

        int[] zero = solve(a);
        int[] left = solve(Arrays.copyOfRange(a, 0, n-1));
        int[] right = solve(Arrays.copyOfRange(a, 1, n));

        StringBuilder line = new StringBuilder();
        for (int i = 0; i <= n-1 ; i++) {
            int best = zero[i];
            if (i >= 1) {
                best = Math.max(best, left[i-1]);
                best = Math.max(best, right[i-1]);
            }
            line.append(' ').append(best);
        }
        out.println(line.substring(1));
        out.flush();
    }

    static int[] solve(int[] a) {
        int n = a.length;
        int[] max = new int[n+10];
        for (int i = 0 ; i < n ; i++) {
            if (i+1 < n) {
                int left = i;
                int right = n-i-2;
                int num = Math.max(right, left) - Math.min(right, left);
                max[num] = Math.max(max[num], Math.max(a[i], a[i+1]));
                if (num >= 1) {
                    max[num-1] = Math.max(max[num-1], Math.min(a[i], a[i+1]));
                }
            }
            if (i-1 >= 0) {
                int left = i-1;
                int right = n-(i-1)-2;
                int num = Math.max(right, left) - Math.min(right, left);
                max[num] = Math.max(max[num], Math.max(a[i], a[i-1]));
                if (num >= 1) {
                    max[num-1] = Math.max(max[num-1], Math.min(a[i], a[i-1]));
                }
            }
            {
                int L = i;
                int R = n-1-L;
                int diff = Math.max(R, L) - Math.min(R, L);
                if (diff >= 1) {
                    max[diff-1] = Math.max(max[diff-1], a[i]);
                }
            }
        }
        for (int i = 2 ; i <= n-1 ; i++) {
            max[i] = Math.max(max[i], max[i-2]);
        }
        for (int i = 0; i < n ; i++) {
            max[n-1] = Math.max(max[n-1], a[i]);
        }
        return max;
    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int[] nextInts(int n) {
            int[] ret = new int[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextInt();
            }
            return ret;
        }

        private int[][] nextIntTable(int n, int m) {
            int[][] ret = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextInt();
                }
            }
            return ret;
        }

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
            }
            return ret;
        }

        private long[][] nextLongTable(int n, int m) {
            long[][] ret = new long[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextLong();
                }
            }
            return ret;
        }

        private double[] nextDoubles(int n) {
            double[] ret = new double[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextDouble();
            }
            return ret;
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

        public double nextDouble() {
            return Double.valueOf(nextToken());
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
