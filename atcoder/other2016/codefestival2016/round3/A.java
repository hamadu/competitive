package atcoder.other2016.codefestival2016.round3;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.InputMismatchException;

public class A {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        long[] a = in.nextLongs(n);

        long[][] dp = new long[2][n];
        for (int i = 0; i < n ; i++) {
            dp[1][i] = a[i];
        }

        for (int i = 1 ; i < k ; i++) {
            int fr = i % 2;
            int to = 1 - fr;
            Arrays.fill(dp[to], -1);

            Deque<Integer> deq = new ArrayDeque<>();
            for (int j = 0 ; j < n ; j++) {
                if (j >= 1) {
                    while (deq.size() >= 1 && dp[fr][deq.peekLast()] <= dp[fr][j-1]) {
                        deq.pollLast();
                    }
                    deq.add(j-1);
                }

                if (j >= i) {
                    dp[to][j] = dp[fr][deq.peekFirst()]+(a[j]*(i+1));
                }

                if (j >= m) {
                    if (deq.size() >= 1 && deq.peekFirst() == j-m) {
                        deq.pollFirst();
                    }
                }
            }
        }

        long max = 0;
        for (int i = 0; i < n ; i++) {
            max = Math.max(max, dp[k%2][i]);
        }

        out.println(max);
        out.flush();
    }

    /**
     * Computes slide-window min value.
     * Returns array of length (|a|-k+1), i-th element means min(a[i],a[i+1],...,a[i+k-1]).
     *
     * @param a original array
     * @param k window size
     * @return min values
     */
    public static int[] slideMin(int[] a, int k) {
        int n = a.length;

        Deque<Integer> deq = new ArrayDeque<>();
        int[] slideMin = new int[n-k+1];
        for (int i = 0; i < n; i++) {
            while (deq.size() >= 1 && a[deq.peekLast()] >= a[i]) {
                deq.pollLast();
            }
            deq.add(i);

            if (i-k+1 >= 0) {
                int top = deq.peekFirst();
                slideMin[i-k+1] = a[top];
                if (top == i-k+1) {
                    deq.pollFirst();
                }
            }
        }
        return slideMin;
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
