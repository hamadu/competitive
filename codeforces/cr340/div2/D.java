package codeforces.cr340.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/02/16.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int[][] xy = new int[3][2];
        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 2 ; j++) {
                xy[i][j] = in.nextInt();
            }
        }
        Arrays.sort(xy, (p1, p2) -> p1[0] - p2[0]);
        int x = solve(xy);

        for (int i = 0 ; i < 3 ; i++) {
            int tmp = xy[i][0];
            xy[i][0] = xy[i][1];
            xy[i][1] = tmp;
        }
        Arrays.sort(xy, (p1, p2) -> p1[0] - p2[0]);
        int y = solve(xy);

        out.println(Math.min(x, y));
        out.flush();
    }

    private static int solve(int[][] xy) {
        if (xy[0][0] == xy[1][0] && xy[1][0] == xy[2][0]) {
            return 1;
        }
        for (int f = 0 ; f <= 1 ; f++) {
            if (xy[f][0] == xy[f+1][0]) {
                int min = Math.min(xy[f][1], xy[f+1][1]);
                int max = Math.max(xy[f][1], xy[f+1][1]);
                int med = 3-f-(f+1);
                if (min < xy[med][1] && xy[med][1] < max) {
                    return 3;
                }
                return 2;
            }
        }
        return 3;
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
