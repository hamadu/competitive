package codeforces.cf3xx.cf385.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class B {
    private static final int INF = 1000000009;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();

        int[][] q = new int[20][n];
        for (int idx = 0 ; idx < 10 ; idx++) {
            q[idx*2]   = query(n, in, out, make(n, idx, 0));
            q[idx*2+1] = query(n, in, out, make(n, idx, 1));
        }

        int[] min = new int[n];
        Arrays.fill(min, INF);

        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 10; j++) {
                int flg = ((i >> j) & 1) ^ 1;
                min[i] = Math.min(min[i], q[j*2+flg][i]);
            }
        }

        StringBuilder line = new StringBuilder();
        for (int i = 0; i < n ; i++) {
            line.append(' ').append(min[i]);
        }

        out.println(-1);
        out.println(line.substring(1));
        out.flush();
    }

    private static int[] query(int n, InputReader in, PrintWriter out, int[] q) {
        int[] ret = new int[n];

        if (q.length == 0) {
            Arrays.fill(ret, INF);
            return ret;
        }

        StringBuilder line = new StringBuilder();
        for (int i = 0; i < q.length ; i++) {
            line.append(' ').append(q[i]);
        }
        out.println(q.length);
        out.println(line.substring(1));
        out.flush();

        for (int i = 0; i < n ; i++) {
            ret[i] = in.nextInt();
        }
        return ret;
    }

    private static int[] make(int n, int idx, int flg) {
        List<Integer> li = new ArrayList<>();
        for (int i = 0; i < n ; i++) {
            if (((i >> idx) & 1) == flg) {
                li.add(i+1);
            }
        }
        int[] ret = new int[li.size()];
        for (int i = 0; i < li.size() ; i++) {
            ret[i] = li.get(i);
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
