package atcoder.other2016.codefestival2016.quala;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int q = in.nextInt();
        int[] a = in.nextInts(q);
        for (int i = 0; i < q ; i++) {
            a[i]--;
        }
        int[] ord = makeord(m, a);
        out.println(isOK(n, m, a, ord) ? "Yes" : "No");
        out.flush();
    }

    private static int[] makeord(int m, int[] a) {
        boolean[] seen = new boolean[m];
        int[] ord = new int[m];
        int oi = 0;
        for (int i = a.length-1 ; i >= 0 ; i--) {
            if (!seen[a[i]]) {
                seen[a[i]] = true;
                ord[oi++] = a[i];
            }
        }
        for (int i = 0; i < m ; i++) {
            if (!seen[i]) {
                ord[oi++] = i;
            }
        }
        return ord;
    }

    private static boolean isOK(int n, int m, int[] a, int[] ord) {
        int[] iord = new int[m];
        Arrays.fill(iord, -1);
        for (int i = 0; i < ord.length; i++) {
            iord[ord[i]] = i;
        }
        int on = ord.length;
        int[] cnt = new int[on+1];
        cnt[0] = n;
        for (int i = a.length-1; i >= 0; i--) {
            int th = iord[a[i]];
            if (cnt[th] >= 1) {
                cnt[th]--;
                cnt[th+1]++;
            }
        }

        for (int i = 0; i <= on ; i++) {
            if (cnt[i] >= 1) {
                int[] ord2 = new int[m];
                int oi = 0;
                boolean[] seen = new boolean[m];
                for (int j = 0; j < i ; j++) {
                    ord2[oi++] = ord[j];
                    seen[ord[j]] = true;
                }
                for (int j = 0; j < m ; j++) {
                    if (!seen[j]) {
                        ord2[oi++] = j;
                    }
                }
                return Arrays.equals(ord, ord2);
            }
        }
        throw new RuntimeException("nyan");
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
