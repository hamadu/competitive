package codeforces.cf3xx.cf391;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class C {
    private static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        long[] fact = new long[m+1];
        fact[0] = 1;
        for (int i = 1 ; i <= m; i++) {
            fact[i] = (fact[i-1] * i) % MOD;
        }

        int[][] gym = new int[n][];
        int[] deg = new int[m];
        for (int i = 0; i < n ; i++) {
            int k = in.nextInt();
            gym[i] = new int[k];
            for (int j = 0; j < k ; j++) {
                gym[i][j] = in.nextInt()-1;
                deg[gym[i][j]]++;
            }
        }
        int[][] gset = new int[m][];
        for (int i = 0; i < m ; i++) {
            gset[i] = new int[deg[i]];
        }
        for (int i = 0; i < n ; i++) {
            for (int a : gym[i]) {
                gset[a][--deg[a]] = i;
            }
        }
        for (int i = 0; i < m ; i++) {
            Arrays.sort(gset[i]);
        }
        Arrays.sort(gset, (a, b) -> {
            int ml = Math.min(a.length, b.length);
            for (int f = 0 ; f < ml ; f++) {
                if (a[f] < b[f]) {
                    return -1;
                } else if (a[f] > b[f]) {
                    return 1;
                }
            }
            if (a.length == b.length) {
                return 0;
            }
            return a.length < b.length ? -1 : 1;
        });

        long ans = 1;
        for (int i = 0 ; i < m ; ) {
            int l1 = gset[i].length;
            int j = i;
            while (j < m) {
                int l2 = gset[j].length;
                if (l1 != l2) {
                    break;
                }
                boolean different = false;
                for (int k = 0 ; k < l1 ; k++) {
                    if (gset[i][k] != gset[j][k]) {
                        different = true;
                        break;
                    }
                }
                if (different) {
                    break;
                }
                j++;
            }
            ans *= fact[j-i];
            ans %= MOD;
            i = j;
        }

        out.println(ans);
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
