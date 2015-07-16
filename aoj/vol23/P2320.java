package aoj.vol23;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/15.
 */
public class P2320 {

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static int[] dir = new int[256];

    public static void main(String[] args) {
        dir['N'] = 0;
        dir['E'] = 1;
        dir['S'] = 2;
        dir['W'] = 3;

        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        while (true) {
            int h = in.nextInt();
            int w = in.nextInt();
            long L = in.nextLong();
            if (h + w + L == 0) {
                break;
            }

            char[][] map = new char[h][];
            for (int i = 0; i < h ; i++) {
                map[i] = in.nextToken().toCharArray();
            }

            int rx = -1;
            int ry = -1;
            int rd = 0;
            for (int i = 0; i < h ; i++) {
                for (int j = 0; j < w ; j++) {
                    if (map[i][j] != '.' && map[i][j] != '#') {
                        ry = i;
                        rx = j;
                        rd = dir[map[i][j]];
                    }
                }
            }
            out.println(solve(map, ry, rx, rd, L));
        }

        out.flush();
    }

    private static String solve(char[][] map, int ry, int rx, int rd, long L) {
        int H = map.length;
        int W = map[0].length;
        int[][][] dp = new int[H][W][4];
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W ; j++) {
                Arrays.fill(dp[i][j], Integer.MAX_VALUE);
            }
        }
        dp[ry][rx][rd] = 0;
        int time = 0;

        long cycle = -1;
        while (time < L) {
            time++;
            int ty = ry + dy[rd];
            int tx = rx + dx[rd];
            int td = rd;
            while (ty < 0 || tx < 0 || ty >= H || tx >= W || map[ty][tx] == '#') {
                td = (td + 1) % 4;
                ty = ry + dy[td];
                tx = rx + dx[td];
            }
            if (dp[ty][tx][td] == Integer.MAX_VALUE) {
                dp[ty][tx][td] = time;
            } else {
                if (cycle == -1) {
                    cycle = time - dp[ty][tx][td];
                } else {
                    if ((L - dp[ty][tx][td]) % cycle == 0) {
                        ry = ty;
                        rx = tx;
                        rd = td;
                        break;
                    }
                }
            }
            ry = ty;
            rx = tx;
            rd = td;
        }

        char[] dirs = new char[]{'N', 'E', 'S', 'W'};

        return String.format("%d %d %c", ry+1, rx+1, dirs[rd]);
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
