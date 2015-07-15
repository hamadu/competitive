package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 15/07/14.
 */
public class P1349 {
    static final int[] dx = {1, 0, 0, -1};
    static final int[] dy = {0, 1, -1, 0};

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int[] charToDir = new int[256];
        charToDir['E'] = 0;
        charToDir['W'] = 3;
        charToDir['N'] = 1;
        charToDir['S'] = 2;

        int n = in.nextInt();
        int x0 = in.nextInt();
        int y0 = in.nextInt();
        int T = in.nextInt();

        int[][] roads = new int[n][4];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 4; j++) {
                roads[i][j] = in.nextInt();
            }
        }

        int[][][] graph = new int[51][51][4];
        for (int i = 0; i < n ; i++) {
            int xs = roads[i][0];
            int ys = roads[i][1];
            int xe = roads[i][2];
            int ye = roads[i][3];
            if (xs == xe) {
                if (ye < ys) {
                    int tmp = ys;
                    ys = ye;
                    ye = tmp;
                }
                for (int y = ys ; y < ye ; y++) {
                    graph[y][xs][1] = 1;
                    graph[y+1][xs][2] = 1;
                }
            } else {
                if (xe < xs) {
                    int tmp = xs;
                    xs = xe;
                    xe = tmp;
                }
                for (int x = xs ; x < xe ; x++) {
                    graph[ys][x][0] = 1;
                    graph[ys][x+1][3] = 1;
                }
            }
        }

        int[][][] upd = new int[51][51][4];
        int total = 0;
        List<int[]> states = new ArrayList<int[]>();
        for (int i = 0 ; i < 4 ; i++) {
            if (graph[y0][x0][i] == 1) {
                states.add(new int[]{x0, y0, i+4});
            }
        }

        while (--T >= 0) {
            int dist = in.nextInt();
            int finalDir = charToDir[in.nextToken().charAt(0)];
            for (int subtime = 0 ; subtime < dist; subtime++) {
                List<int[]> nextStates = new ArrayList<int[]>();

                total++;
                for (int[] que : states) {
                    int nx = que[0];
                    int ny = que[1];
                    int dir = que[2];
                    boolean only = false;
                    if (dir >= 4) {
                        dir -= 4;
                        only = true;
                    }
                    for (int d = 0; d < 4; d++) {
                        if (only && d != dir) {
                            continue;
                        }
                        if (graph[ny][nx][d] == 1 && d != 3-dir) {
                            int ty = ny + dy[d];
                            int tx = nx + dx[d];
                            if (upd[ty][tx][d] != total) {
                                upd[ty][tx][d] = total;
                                nextStates.add(new int[]{tx, ty, d});
                            }
                        }
                    }
                }
                states = nextStates;
            }

            List<int[]> nextStates = new ArrayList<int[]>();
            for (int[] que : states) {
                int nx = que[0];
                int ny = que[1];
                int dir = que[2];
                boolean isOK = finalDir == dir;
                if (graph[ny][nx][finalDir] == 1 && finalDir != 3-dir) {
                    isOK = true;
                }
                if (isOK) {
                    if (dir != finalDir) {
                        que[2] = finalDir + 4;
                    }
                    nextStates.add(que);
                }
            }
            states = nextStates;
        }

        int[][] have = new int[51][51];
        for (int[] state : states) {
            have[state[0]][state[1]] = 1;
        }
        for (int i = 0; i < 51 ; i++) {
            for (int j = 0; j < 51 ; j++) {
                if (have[i][j] == 1) {
                    out.println(i + " " + j);
                }
            }
        }
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
