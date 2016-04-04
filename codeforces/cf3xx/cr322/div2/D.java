package codeforces.cf3xx.cr322.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/09/29.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int[][] ref = new int[3][2];
        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 2 ; j++) {
                ref[i][j] = in.nextInt();
            }
        }

        if (!isOK(ref)) {
            out.println(-1);
        } else {
            out.println(map.length);
            for (int i = 0; i < map.length ; i++) {
                for (int j = 0; j < map.length ; j++) {
                    out.print(map[i][j]);
                }
                out.println();
            }
        }
        out.flush();
    }

    private static boolean isOK(int[][] ref) {
        int total = 0;
        for (int i = 0; i < 3 ; i++) {
            total += ref[i][0] * ref[i][1];
        }
        int size = (int)Math.sqrt(total);
        if (size * size != total) {
            return false;
        }
        map = new char[size][size];

        int[] ord = new int[]{0, 1, 2};
        do {
            for (int f = 0; f <= 7; f++) {
                int[][] query = new int[3][2];
                for (int i = 0; i < 3 ; i++) {
                    int oi = ord[i];
                    query[i][0] = ((f & (1<<i)) >= 1) ? ref[oi][1] : ref[oi][0];
                    query[i][1] = ((f & (1<<i)) >= 1) ? ref[oi][0] : ref[oi][1];
                }
                if (doit(ord, query)) {
                    return true;
                }
            }
        } while (next_permutation(ord));

        return false;
    }

    private static boolean doit(int[] ord, int[][] query) {
        int n = map.length;
        if (query[0][0] != n) {
            return false;
        }

        if (query[1][0] == n && query[2][0] == n) {
            int row = query[0][1] + query[1][1] + query[2][1];
            if (row != n) {
                return false;
            }
            // build
            fill((char)(ord[0]+'A'), 0, 0, query[0][1], n);
            fill((char)(ord[1]+'A'), query[0][1], 0, query[1][1], n);
            fill((char)(ord[2]+'A'), query[0][1] + query[1][1], 0, query[2][1], n);
            return true;
        } else if (query[1][0] + query[2][0] == n && query[1][1] == query[2][1]) {
            if (query[0][1] + query[1][1] != n) {
                return false;
            }
            // build
            fill((char)(ord[0]+'A'), 0, 0, query[0][1], n);
            fill((char)(ord[1]+'A'), query[0][1], 0, query[1][1], query[1][0]);
            fill((char)(ord[2]+'A'), query[0][1], query[1][0], query[2][1], query[2][0]);
            return true;
        }
        return false;
    }

    public static void fill(char ch, int y0, int x0, int h, int w) {
        for (int i = y0 ; i < y0+h; i++) {
            for (int j = x0; j < x0+w; j++) {
                map[i][j] = ch;
            }
        }
    }

    public static boolean next_permutation(int[] num) {
        int len = num.length;
        int x = len - 2;
        while (x >= 0 && num[x] >= num[x+1]) {
            x--;
        }
        if (x == -1) return false;

        int y = len - 1;
        while (y > x && num[y] <= num[x]) {
            y--;
        }
        int tmp = num[x];
        num[x] = num[y];
        num[y] = tmp;
        java.util.Arrays.sort(num, x+1, len);
        return true;
    }

    static char[][] map;

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
