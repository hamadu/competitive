package codeforces.cr53.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/09/07.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        char[][] map = new char[n][];
        for (int i = 0; i < n ; i++) {
            map[i] = in.nextToken().toCharArray();
        }

        int all = 0;
        int[] degX = new int[m];
        int[] degY = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m ; j++) {
                if (map[i][j] == '.') {
                    degX[j]++;
                    degY[i]++;
                    all++;
                }
            }
        }
        double base = solve(degX) + solve(degY);

        int[][] x = new int[1000][2];
        int xn = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
               if (map[i][j] == 'X') {
                   x[xn][0] = i;
                   x[xn][1] = j;
                   xn++;
               }
            }
        }
        x = Arrays.copyOf(x, xn);

        long additionalMove = 0;

        // vertical move : sort by x
        for (final int xy : new int[]{0, 1}) {
            Arrays.sort(x, (x1, x2) -> x1[1-xy]-x2[1-xy]);
            for (int i = 0; i < xn; i++) {
                // bottom to top
                {
                    int to = i;
                    for (int j = i+1; j < xn; j++) {
                        if (x[j][1-xy] != x[j-1][1-xy]+1 || x[j-1][xy] >= x[j][xy]) {
                            break;
                        }
                        to++;
                    }
                    // fr-to
                    int max = (xy == 0) ? n : m;
                    for (int j = i; j <= to; j++) {
                        additionalMove += ((j == i) ? 2 : 4) * x[i][xy] * (max-x[j][xy]-1);
                    }
                }
                // top to bottom
                {
                    int to = i;
                    for (int j = i+1; j < xn; j++) {
                        if (x[j][1-xy] != x[j-1][1-xy]+1 || x[j-1][xy] <= x[j][xy]) {
                            break;
                        }
                        to++;
                    }
                    // fr-to
                    int max = (xy == 0) ? n : m;
                    for (int j = i; j <= to; j++) {
                        additionalMove += ((j == i) ? 2 : 4) * (max-x[i][xy]-1) * x[j][xy];
                    }
                }
            }
        }

        out.println(base + additionalMove * 1.0d / all / all);
        out.flush();
    }

    private static double solve(int[] deg) {
        int n = deg.length;
        int total = 0;
        for (int i = 0; i < n ; i++) {
            total += deg[i];
        }
        double ret = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                ret += Math.abs(i-j) * deg[i] * deg[j];
            }
        }
        return ret / total / total;
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
                res += c-'0';
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
