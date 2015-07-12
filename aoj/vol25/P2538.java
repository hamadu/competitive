package aoj.vol25;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by hama_du on 15/07/12.
 */
public class P2538 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        memo = new int[55][55][55][55];
        canMove = new boolean[55][55][55][55];

        while (true) {
            int h = in.nextInt();
            int w = in.nextInt();
            if (h + w == 0) {
                break;
            }
            char[][] c = new char[h+2][];
            for (int i = 1; i <= h ; i++) {
                c[i] = in.nextToken().toCharArray();
            }
            c[0] = new char[w];
            c[h+1] = new char[w];
            for (int i = 0 ; i < w ; i++) {
                c[0][i] = (i == 0) ? '.' : '#';
                c[h+1][i] = (i == w-1) ? '.' : '#';
            }
            out.println(solve(c));
        }
        out.flush();
    }

    private static long solve(char[][] board) {
        for (int i = 0; i < 55 ; i++) {
            for (int j = 0; j < 55 ; j++) {
                for (int k = 0; k < 55 ; k++) {
                    Arrays.fill(memo[i][j][k], -1);
                    Arrays.fill(canMove[i][j][k], false);
                }
            }
        }

        int h = board.length;
        int w = board[0].length;
        map = board;

        for (int i = 0 ; i < h ; i++) {
            for (int j = 0 ; j < w; j++) {
                if (board[i][j] != '#') {
                    bfs(i, j);
                    canMove[i][j][i][j] = false;
                }
            }
        }
        if (!canMove[0][0][h-1][w-1]) {
            return -1;
        }

        int[][][] jewels = new int[26][10][2];
        int[] jn = new int[26];

        int[][][] holes = new int[26][10][2];
        int[] hn = new int[26];

        for (int i = 0 ; i < h ; i++) {
            for (int j = 0; j < w ; j++) {
                if ('a' <= board[i][j] && board[i][j] <= 'z') {
                    int type = board[i][j] - 'a';
                    jewels[type][jn[type]++] = new int[]{i, j};
                } else if ('A' <= board[i][j] && board[i][j] <= 'Z') {
                    int type = board[i][j] - 'A';
                    holes[type][hn[type]++] = new int[]{i, j};
                }
            }
        }

        int J = 0;
        int H = 0;
        for (int i = 0; i < 26 ; i++) {
            J += jn[i];
            H += hn[i];
        }
        jinfo = new int[J][];
        {
            int jdx = 0;
            for (int i = 0; i < 26; i++) {
                for (int j = 0 ; j < jn[i] ; j++) {
                    jinfo[jdx++] = new int[]{i, j, jewels[i][j][0], jewels[i][j][1]};
                }
            }
        }
        hinfo = new int[H][];
        {
            int hdx = 0;
            for (int i = 0; i < 26; i++) {
                for (int j = 0 ; j < hn[i] ; j++) {
                    hinfo[hdx++] = new int[]{i, j, holes[i][j][0], holes[i][j][1]};
                }
            }
        }
        pairs = new int[J*H][2];
        P = 0;
        for (int i = 0 ; i < J ; i++) {
            for (int j = 0 ; j < H ; j++) {
                if (jinfo[i][0] == hinfo[j][0]) {
                    int tsy = jinfo[i][2];
                    int tsx = jinfo[i][3];
                    int tgy = hinfo[j][2];
                    int tgx = hinfo[j][3];
                    if (canMove[tsy][tsx][tgy][tgx]) {
                        pairs[P++] = new int[]{i, j};
                    }
                }
            }
        }
        return dfs(0, 0, h-1, w-1);
    }

    private static void bfs(int y, int x) {
        int qh = 0;
        int qt = 0;
        queue[qh++] = y;
        queue[qh++] = x;
        while (qt < qh) {
            int ny = queue[qt++];
            int nx = queue[qt++];
            for (int d = 0 ; d < 2 ; d++) {
                int ty = ny + dy[d];
                int tx = nx + dx[d];
                if (ty >= map.length || tx >= map[0].length || map[ty][tx] == '#') {
                    continue;
                }
                if (!canMove[y][x][ty][tx]) {
                    canMove[y][x][ty][tx] = true;
                    queue[qh++] = ty;
                    queue[qh++] = tx;
                }
            }
        }
    }

    static int[] dx = {1, 0};
    static int[] dy = {0, 1};


    static int[] queue = new int[5010];

    static int P;
    static int[][] pairs;
    static int[][] jinfo;
    static int[][] hinfo;

    static char[][] map;

    static int[][][][] memo;

    static boolean[][][][] canMove;

    static int dfs(int sy, int sx, int gy, int gx) {
        if (memo[sy][sx][gy][gx] != -1) {
            return memo[sy][sx][gy][gx];
        }
        int max = 0;
        for (int i = 0 ; i < P ; i++) {
            int pj = pairs[i][0];
            int ph = pairs[i][1];
            int tsy = jinfo[pj][2];
            int tsx = jinfo[pj][3];
            int tgy = hinfo[ph][2];
            int tgx = hinfo[ph][3];
            if (canMove[sy][sx][tsy][tsx] && canMove[tgy][tgx][gy][gx]) {
                max = Math.max(max, dfs(sy, sx, tsy, tsx) + dfs(tsy, tsx, tgy, tgx) + dfs(tgy, tgx, gy, gx));
            }
        }
        if (map[sy][sx] != '.' && map[gy][gx] != '.') {
            if (map[sy][sx] - 'a' == map[gy][gx] - 'A') {
                max++;
            }
        }
        memo[sy][sx][gy][gx] = max;
        return max;
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
