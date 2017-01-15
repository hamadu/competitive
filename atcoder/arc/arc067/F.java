package atcoder.arc.arc067;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class F {
    static long best = 0;

    static long[][] imos = new long[5001][5001];

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        long[] dist = new long[n];
        for (int i = 1; i < n ; i++) {
            dist[i] = dist[i-1] + in.nextLong();
        }

        int[][] table = new int[m][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                table[j][i] = in.nextInt();
            }
        }

        int[] nextIdx = new int[n];
        int[] prevIdx = new int[n];


        int[] stk = new int[10000];
        for (int mi = 0; mi < m ; mi++) {
            int head = 0;
            for (int j = 0; j < n ; j++) {
                while (head >= 1 && table[mi][stk[head-1]] < table[mi][j]) {
                    head--;
                }
                prevIdx[j] = head >= 1 ? stk[head-1]+1 : 0;
                stk[head++] = j;
            }
            head = 0;
            for (int j = n-1 ; j >= 0 ; j--) {
                while (head >= 1 && table[mi][stk[head-1]] < table[mi][j]) {
                    head--;
                }
                nextIdx[j] = head >= 1 ? stk[head-1]-1 : n-1;
                stk[head++] = j;
            }

            List<int[]> rows = new ArrayList<>();
            for (int i = 0; i < n  ; i++) {
                rows.add(new int[]{i, table[mi][i]});
            }
            Collections.sort(rows, (a, b) -> b[1] - a[1]);
            for (int[] r : rows) {
                int here = r[0];
                int L = prevIdx[r[0]];
                int R = nextIdx[r[0]];
                imos[L][here] += r[1];
                imos[here+1][R+1] += r[1];
                imos[L][R+1] -= r[1];
                imos[here+1][here] -= r[1];
            }
        }

        int imo = imos.length-1;
        for (int i = 0; i <= imo ; i++) {
            for (int j = 1; j <= imo ; j++) {
                imos[i][j] += imos[i][j-1];
            }
        }
        for (int j = 0; j <= imo ; j++) {
            for (int i = 1 ; i <= imo ; i++) {
                imos[i][j] += imos[i-1][j];
            }
        }

        long best = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = i ; j < n ; j++) {
                long D = dist[j] - dist[i];
                best = Math.max(best, imos[i][j] - D);
            }
        }
        out.println(best);
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
