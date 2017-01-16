package codeforces.cf0xx.cf17;

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
        char[] c = in.nextToken().toCharArray();

        int[][] manachar = manachar(c);

        long[] start = new long[n+1];
        long[] end = new long[n+1];
        for (int i = 0; i < n ; i++) {
            int fr = i - manachar[0][i] + 1;
            int to = i + manachar[0][i] - 1;
            start[fr]++;
            start[i+1]--;
            end[i]++;
            end[to+1]--;
            if (i < n-1 && manachar[1][i] >= 1) {
                // abba(x=2)
                //  ^
                fr = i - manachar[1][i] + 1;
                to = i + manachar[1][i];
                start[fr]++;
                start[i+1]--;
                end[i+1]++;
                end[to+1]--;
            }
        }

        for (int i = 0; i < n ; i++) {
            start[i+1] += start[i];
            end[i+1] += end[i];
        }
        for (int i = n-1 ; i >= 1 ; i--) {
            start[i-1] += start[i];
        }

        long nonIntersects = 0;
        for (int i = 0; i+1 < n ; i++) {
            nonIntersects += end[i] % MOD * start[i+1] % MOD;
            nonIntersects %= MOD;
        }

        long total = 0;
        for (int[] ma : manachar) {
            for (int x : ma) {
                total += x;
            }
        }

        long pair = total;
        if (total % 2 == 0) {
            pair /= 2;
            pair %= MOD;
            pair *= (total-1+MOD)%MOD;
        } else {
            pair %= MOD;
            pair *= ((total-1)/2)%MOD;
            pair %= MOD;
        }
        out.println((pair-nonIntersects+MOD)%MOD);
        out.flush();
    }

    static final int MOD = 51123987;

    static long pow(long a, long x) {
        long res = 1;
        while (x > 0) {
            if (x % 2 != 0) {
            res = (res * a) % MOD;
            }
            a = (a * a) % MOD;
            x /= 2;
        }
        return res;
    }

    static long inv(long a) {
        return pow(a, MOD - 2) % MOD;
    }


    public static int[][] manachar(char[] c) {
        int[] odd = manacharSub(c);
        int n = c.length;
        char[] c2 = new char[2*n-1];
        for (int i = 0; i < n ; i++) {
            c2[2*i] = c[i];
        }
        for (int i = 0; i < n-1 ; i++) {
            c2[2*i+1] = '$';
        }
        int[] e = manacharSub(c2);
        int[] even = new int[n-1];
        for (int i = 0; i < n-1 ; i++) {
            even[i] = e[i*2+1]/2;
        }
        return new int[][]{odd, even};
    }

    public static int[] manacharSub(char[] c) {
        int n = c.length;
        int[] ret = new int[n];
        int i = 0;
        int j = 0;
        while (i < n) {
            while (i-j >= 0 && i+j < n && c[i-j] == c[i+j]) {
                j++;
            }
            ret[i] = j;
            int k = 1;
            while (i - k >= 0 && i+k < n && k+ret[i-k] < j) {
                ret[i+k] = ret[i-k];
                k++;
            }
            i += k;
            j -= k;
        }
        return ret;
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
