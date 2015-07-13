package aoj.vol11;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/13.
 */
public class P1193 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int h = in.nextInt();
            if (h == 0) {
                break;
            }
            int[][] map = new int[h][5];
            for (int i = 0; i < h ; i++) {
                for (int j = 0; j < 5 ; j++) {
                    map[i][j] = in.nextInt();
                }
            }
            out.println(solve(map));
        }

        out.flush();
    }

    private static int solve(int[][] map) {
        int h = map.length;
        int w = map[0].length;



        boolean upd = true;
        int score = 0;
        while (upd) {
            upd = false;
            for (int i = 0 ; i < h ; i++) {
                int head = 0;
                while (head < w) {
                    int tail = head;
                    while (tail < w && map[i][head] == map[i][tail]) {
                        tail++;
                    }
                    if (tail - head >= 3 && map[i][head] >= 1) {
                        upd = true;
                        for (int j = head; j < tail; j++) {
                            score += map[i][j];
                            map[i][j] = 0;
                        }
                    }
                    head = tail;
                }
            }
            for (int j = 0 ; j < w ; j++) {
                int[] stk = new int[h];
                int si = 0;
                for (int i = 0 ; i < h ; i++) {
                    if (map[i][j] >= 1) {
                        stk[si++] = map[i][j];
                    }
                    map[i][j] = 0;
                }
                int hi = h-1;
                for (int i = si - 1 ; i >= 0 ; i--) {
                    map[hi][j] = stk[i];
                    hi--;
                }
            }
        }
        return score;
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
