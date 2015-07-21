package aoj.vol21;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 15/07/20.
 */
public class P2172 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int w = in.nextInt();
            int h = in.nextInt();
            if (w + h == 0) {
                break;
            }
            char[][] map = new char[h][w];
            for (int i = 0; i < h ; i++) {
                map[i] = in.nextToken().toCharArray();
            }
            int qy = 0;
            int qx = 0;
            int ay = 0;
            int ax = 0;
            for (int i = 0; i < h ; i++) {
                for (int j = 0; j < w ; j++) {
                    if (map[i][j] == 'Q') {
                        qy = i;
                        qx = j;
                        map[i][j] = '.';
                    } else if (map[i][j] == 'A') {
                        ay = i;
                        ax = j;
                        map[i][j] = '.';
                    }
                }
            }

            int res = solve(map, qy, qx, ay, ax);
            if (res == 1) {
                out.println("Queen can escape.");
            } else if (res == 0) {
                out.println("Queen can not escape and Army can not catch Queen.");
            } else {
                out.println("Army can catch Queen.");
            }
        }
        out.flush();
    }

    private static int solve(char[][] M, int qy, int qx, int ay, int ax) {
        map = M;
        for (int i = 0; i < 2 ; i++) {
            for (int j = 0; j < 31 ; j++) {
                for (int k = 0; k < 31 ; k++) {
                    for (int l = 0; l < 31 ; l++) {
                        Arrays.fill(dp[i][j][k][l], 0);
                        Arrays.fill(sr[i][j][k][l], 0);
                    }
                }
            }
        }


        _head = _tail = 0;

        int n = M.length;
        int m = M[0].length;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                dp[0][i][j][i][j] = -1;
                addQue(0, i, j, i, j);
                dp[1][i][j][i][j] = 1;
                addQue(1, i, j, i, j);
                for (int k = 0; k < n ; k++) {
                    for (int l = 0; l < m ; l++) {
                        if (!(i == k && j == l) && map[i][j] == 'E') {
                           dp[0][i][j][k][l] = 1;
                           addQue(0, i, j, k, l);
                        }
                    }
                }
            }
        }

        int[][] empt = new int[n][m];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                for (int d = 0; d < 5; d++) {
                    int ty = i+dy[d];
                    int tx = j+dx[d];
                    if (ty < 0 || tx < 0 || ty >= n || tx >= m || map[ty][tx] == '#') {
                        continue;
                    }
                    empt[i][j]++;
                }
            }
        }

        while (_tail < _head) {
            int[] st = pullQue();
            int tu = st[0];
            int qi = st[1];
            int qj = st[2];
            int ai = st[3];
            int aj = st[4];
            int game = dp[tu][qi][qj][ai][aj];
            for (int d = 0; d < 5; d++) {
                int ty = dy[d] + ((tu == 0) ? ai : qi);
                int tx = dx[d] + ((tu == 0) ? aj : qj);
                if (ty < 0 || tx < 0 || ty >= n || tx >= m || map[ty][tx] == '#') {
                    continue;
                }

                if (tu == 0) {
                    if (dp[1][qi][qj][ty][tx] != 0) {
                        continue;
                    }
                    if (game == -1) {
                        dp[1][qi][qj][ty][tx] = 1;
                        addQue(1, qi, qj, ty, tx);
                    } else {
                        sr[1][qi][qj][ty][tx]++;
                        if (sr[1][qi][qj][ty][tx] == empt[ty][tx]) {
                            dp[1][qi][qj][ty][tx] = -1;
                            addQue(1, qi, qj, ty, tx);
                        }
                    }

                } else {
                    if (dp[0][ty][tx][ai][aj] != 0) {
                        continue;
                    }
                    if (game == -1) {
                        dp[0][ty][tx][ai][aj] = 1;
                        addQue(0, ty, tx, ai, aj);
                    } else {
                        sr[0][ty][tx][ai][aj]++;
                        if (sr[0][ty][tx][ai][aj] == empt[ty][tx]) {
                            dp[0][ty][tx][ai][aj] = -1;
                            addQue(0, ty, tx, ai, aj);
                        }
                    }
                }
            }
        }
        return dp[0][qy][qx][ay][ax];
    }

    static void addQue(int t, int qi, int qj, int ai, int aj) {
        _que[_head++] = t;
        _que[_head++] = qi;
        _que[_head++] = qj;
        _que[_head++] = ai;
        _que[_head++] = aj;
    }


    static int[] pullQue() {
        int tu = _que[_tail++];
        int qi = _que[_tail++];
        int qj = _que[_tail++];
        int ai = _que[_tail++];
        int aj = _que[_tail++];
        return new int[]{tu, qi, qj, ai, aj};
    }

    static int _head;
    static int _tail;
    static int[] _que = new int[10000000];

    static int[] dx = {-1, 0, 1, 0, 0};
    static int[] dy = {0, 1, 0, -1, 0};

    static int[][][][][] dp = new int[2][31][31][31][31];
    static int[][][][][] sr = new int[2][31][31][31][31];

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
