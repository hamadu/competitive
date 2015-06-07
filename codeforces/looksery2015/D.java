package codeforces.looksery2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class D {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        int[][] board = new int[n][m];
        for (int i = 0 ; i < n ; i++) {
            char[] c = in.nextToken().toCharArray();
            for (int j = 0 ; j < m ; j++) {
                board[i][j] = (c[j] == 'B') ? 1 : -1;
            }
        }

        int[][][] df = new int[n+m][n+m][2];
        int[] idx = new int[n+m];
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0; j < m; j++) {
                int d = i+j;
                df[d][idx[d]][0] = i;
                df[d][idx[d]][1] = j;
                idx[d]++;
            }
        }

        BITRange[] diffRange = new BITRange[n];
        for (int i = 0; i < n ; i++) {
            diffRange[i] = new BITRange(m+10);
        }

        int cnt = 0;
        for (int i = n+m-1 ; i >= 0 ; i--) {
            int k = idx[i];
            for (int j = 0 ; j < k ; j++) {
                int ny = df[i][j][0];
                int nx = df[i][j][1];
                int diff = (int)diffRange[ny].get(nx+1);
                if (board[ny][nx] + diff != 0) {
                    cnt++;
                    int add = -(board[ny][nx] + diff);
                    for (int y = ny ; y >= 0 ; y--) {
                        diffRange[y].addRange(1, nx+1, add);
                    }
                }
            }
        }
        out.println(cnt);
        out.flush();
    }
    // 区間加算
    static class BITRange {
        BIT bit0;
        BIT bit1;
        BITRange(int n) {
            bit0 = new BIT(n);
            bit1 = new BIT(n);
        }

        void addRange(int l, int r, long x) {
            bit0.add(l, -x * (l-1));
            bit1.add(l, x);
            bit0.add(r+1, x * r);
            bit1.add(r+1, -x);
        }

        long range(int l, int r) {
            long right = bit0.sum(r) + bit1.sum(r) * r;
            long left = bit0.sum(l-1) + bit1.sum(l-1) * (l-1);
            return right - left;
        }

        long get(int i) {
            return range(i, i);
        }
    }

    // BIT, 1-indexed, range : [a,b]
    static class BIT {
        long N;
        long[] data;
        BIT(int n) {
            N = n;
            data = new long[n+1];
        }

        long sum(int i) {
            long s = 0;
            while (i > 0) {
                s += data[i];
                i -= i & (-i);
            }
            return s;
        }

        long range(int i, int j) {
            return sum(j) - sum(i-1);
        }

        void add(int i, long x) {
            while (i <= N) {
                data[i] += x;
                i += i & (-i);
            }
        }

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



