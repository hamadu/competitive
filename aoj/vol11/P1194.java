package aoj.vol11;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/13.
 */
public class P1194 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int r = in.nextInt();
            int n = in.nextInt();
            if (r + n == 0) {
                break;
            }
            int[][] building = new int[n][3];
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < 3 ; j++) {
                    building[i][j] = in.nextInt();
                }
            }
            out.println(solve(r, building));
        }

        out.flush();
    }

    private static double solve(int r, int[][] building) {
        double ans = Double.MAX_VALUE;

        int[][] field = new int[22][50];
        int GETA = 25;
        int n = building.length;
        for (int i = 0 ; i < n ; i++) {
            int fr = building[i][0] + GETA;
            int to = building[i][1] + GETA;
            for (int j = fr ; j < to ; j++) {
                for (int hi = 0 ; hi < building[i][2] ; hi++) {
                    field[hi][j] = 1;
                }
            }
        }
        for (int j = 0 ; j < 50 ; j++) {
            int x = j - GETA;
            for (int i = 0 ; i < field.length ; i++) {
                if (field[i][j] == 0) {
                    int y = i;
                    ans = Math.min(ans, check(x, y, r));
                    ans = Math.min(ans, check(x+1, y, r));
                    break;
                }
            }
        }
        return ans;
    }

    private static double check(int x, int y, int r) {
        if (Math.abs(x) >= r) {
            return Double.MAX_VALUE;
        }
        double y2 = r * r - Math.abs(x) * Math.abs(x);
        double dy = Math.sqrt(y2);
        double fy = y - dy;
        return fy + r;
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
