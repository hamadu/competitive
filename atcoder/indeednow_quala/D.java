// http://indeednow-quala.contest.atcoder.jp/submissions/358173
package atcoder.indeednow_quala;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class D {
    final static int[] dx = {1, 0, 0, -1};
    final static int[] dy = {0, 1, -1, 0};
    final static int MAX = 25;

    static int W,H;
    static int[][] complete;
    static int foundMin;

    public static void dfs(int[][] b, int px, int py, int ans, int depth, int dir) {
        if (depth + (W*H-ans) / 2 > foundMin) {
            return;
        }
        if (ans == W*H) {
            foundMin = Math.min(foundMin, depth);
            return;
        }

        for (int d = 0 ; d < 4 ; d++) {
            if (d == dir) {
                continue;
            }
            int tx = px + dx[d];
            int ty = py + dy[d];
            if (tx >= W || tx < 0 || ty >= H || ty < 0) {
                continue;
            }
            int dans = 0;
            if (b[py][px] == complete[py][px]) {
                dans -= 1;
            }
            if (b[ty][tx] == complete[ty][tx]) {
                dans -= 1;
            }
            int tmp = b[py][px];
            b[py][px] = b[ty][tx];
            b[ty][tx] = tmp;
            if (b[py][px] == complete[py][px]) {
                dans += 1;
            }
            if (b[ty][tx] == complete[ty][tx]) {
                dans += 1;
            }
            dfs(b, tx, ty, ans+dans, depth+1, 3-d);
            int tmp2 = b[py][px];
            b[py][px] = b[ty][tx];
            b[ty][tx] = tmp2;
        }

    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int h = in.nextInt();
        int w = in.nextInt();
        H = h;
        W = w;
        int px = -1;
        int py = -1;
        int[][] c = new int[h][w];
        for (int i = 0 ; i < h ; i++) {
            for (int j = 0 ; j < w ; j++) {
                c[i][j] = in.nextInt();
                if (c[i][j] == 0) {
                    px = j;
                    py = i;
                }
            }
        }

        complete = new int[h][w];
        for (int i = 0 ; i < h ; i++) {
            for (int j = 0 ; j < w ; j++) {
                complete[i][j] = i*w+j+1;
                if (complete[i][j] == h*w) {
                    complete[i][j] = 0;
                }
            }
        }

        int ans = 0;
        for (int i = 0 ; i < h ; i++) {
            for (int j = 0 ; j < w ; j++) {
                if (c[i][j] == complete[i][j]) {
                    ans++;
                }
            }
        }

        foundMin = MAX;
        dfs(c, px, py, ans, 0, -1);
        out.println(foundMin);
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
