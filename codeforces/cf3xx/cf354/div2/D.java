package codeforces.cf3xx.cf354.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/07/17.
 */
public class D {
    private static final int INF = (int)1e8;

    private static final int[] DY = {0, 1, 0, -1};
    private static final int[] DX = {1, 0, -1, 0};


    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        char[][] rot = new char[255][];

        rot['+'] = "++++".toCharArray();
        rot['*'] = "****".toCharArray();

        rot['-'] = "-|-|".toCharArray();
        rot['|'] = "|-|-".toCharArray();

        rot['^'] = "^>v<".toCharArray();
        rot['>'] = ">v<^".toCharArray();
        rot['v'] = "v<^>".toCharArray();
        rot['<'] = "<^>v".toCharArray();

        rot['L'] = "LURD".toCharArray();
        rot['U'] = "URDL".toCharArray();
        rot['R'] = "RDLU".toCharArray();
        rot['D'] = "DLUR".toCharArray();

        int[] pass = new int[255];
        pass['+'] = 15;
        pass['-'] = 5;
        pass['|'] = 10;
        pass['^'] = 8;
        pass['>'] = 1;
        pass['v'] = 2;
        pass['<'] = 4;
        pass['L'] = 15^pass['<'];
        pass['U'] = 15^pass['^'];
        pass['R'] = 15^pass['>'];
        pass['D'] = 15^pass['v'];

        int n = in.nextInt();
        int m = in.nextInt();
        char[][] map = new char[n][];
        for (int i = 0; i < n ; i++) {
            map[i] = in.nextToken().toCharArray();
        }
        int[][][] dp = new int[4][n][m];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < n ; j++) {
                Arrays.fill(dp[i][j], INF);
            }
        }
        int sy = in.nextInt()-1;
        int sx = in.nextInt()-1;
        int gy = in.nextInt()-1;
        int gx = in.nextInt()-1;


        int[] que = new int[16000000];
        int qh = 0;
        int qt = 0;
        que[qh++] = sy;
        que[qh++] = sx;
        que[qh++] = 0;
        dp[0][sy][sx] = 0;
        while (qt < qh) {
            int ny = que[qt++];
            int nx = que[qt++];
            int nr = que[qt++];
            int time = dp[nr][ny][nx];

            // rotate
            int tr = (nr+1)%4;
            if (dp[tr][ny][nx] > time+1) {
                dp[tr][ny][nx] = time+1;
                que[qh++] = ny;
                que[qh++] = nx;
                que[qh++] = tr;
            }

            // move
            for (int d = 0 ; d < 4 ; d++) {
                int ty = ny+DY[d];
                int tx = nx+DX[d];
                if (ty < 0 || tx < 0 || ty >= n || tx >= m) {
                    continue;
                }
                char fromChar = rot[map[ny][nx]][nr];
                char toChar = rot[map[ty][tx]][nr];

                int backD = (d+2)%4;
                if ((pass[fromChar] & (1<<d)) >= 1 && (pass[toChar] & (1<<backD)) >= 1) {
                    if (dp[nr][ty][tx] > time+1) {
                        dp[nr][ty][tx] = time+1;
                        que[qh++] = ty;
                        que[qh++] = tx;
                        que[qh++] = nr;
                    }
                }
            }
        }

        int min = INF;
        for (int i = 0; i < 4 ; i++) {
            min = Math.min(min, dp[i][gy][gx]);
        }
        out.println(min >= INF ? -1 : min);
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
