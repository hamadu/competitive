package aoj.vol20;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/25.
 */
public class P2017 {
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
            int sy = 0, sx = 0;
            int gy = 0, gx = 0;
            for (int i = 0; i < h ; i++) {
                for (int j = 0; j < w ; j++) {
                    if (map[i][j] == 'K') {
                        sy = i;
                        sx = j;
                        map[i][j] = '.';
                    } else if (map[i][j] == 'M') {
                        gy = i;
                        gx = j;
                        map[i][j] = '.';
                    }
                }
            }
            out.println(solve(map, sy, sx, gy, gx).msg);
        }

        out.flush();
    }

    private static int[] dx = {-1, 0, 1, 0};
    private static int[] dy = {0, -1, 0, 1};

    private static Result solve(char[][] map, int sy, int sx, int gy, int gx) {
        int n = map.length;
        int m = map[0].length;

        int[][][][] table = new int[n][m][4][2];
        for (int y = 0; y < n ; y++) {
            for (int x = 0; x < m ; x++) {
                if (map[y][x] == '#') {
                    continue;
                }
                for (int d = 0; d < 4 ; d++) {
                    int ny = y;
                    int nx = x;
                    while (map[ny][nx] != '#') {
                        ny += dy[d];
                        nx += dx[d];
                    }
                    // step back one
                    ny -= dy[d];
                    nx -= dx[d];
                    table[y][x][d][0] = ny;
                    table[y][x][d][1] = nx;
                }
            }
        }


        for (int i = 0; i < visited.length ; i++) {
            Arrays.fill(visited[i], 0);
        }

        int qh = 0;
        int qt = 0;
        int ip = encodePos(sy, sx);
        for (int d1 = 0; d1 < 4 ; d1++) {
            for (int d2 = 0; d2 < 4; d2++) {
                visited[ip][ip] |= 1<<((d1<<2)+d2);
                _que[qh++] = encode(sy, sx, d1, sy, sx, d2);
            }
        }
        while (qt < qh) {
            int[][] now = decode(_que[qt++]);
            int ny1 = now[0][0];
            int nx1 = now[0][1];
            int nd1 = now[0][2];
            int ny2 = now[1][0];
            int nx2 = now[1][1];
            int nd2 = now[1][2];

            for (int d = 1 ; d <= 3 ; d += 2) {
                int td1 = (nd1+d)%4;
                int ty1 = table[ny1][nx1][td1][0];
                int tx1 = table[ny1][nx1][td1][1];

                int td2 = (nd2+4-d)%4;
                int ty2 = table[ny2][nx2][td2][0];
                int tx2 = table[ny2][nx2][td2][1];

                int p1 = encodePos(ty1, tx1);
                int p2 = encodePos(ty2, tx2);
                int dd = (td1<<2)+td2;
                if ((visited[p1][p2] & (1<<dd)) == 0) {
                    visited[p1][p2] |= 1<<dd;
                    _que[qh++] = encode(ty1, tx1, td1, ty2, tx2, td2);
                }
            }
        }

        int goalPos = encodePos(gy, gx);
        if (visited[goalPos][goalPos] >= 1) {
            return Result.OK;
        }

        for (int i = 0; i < visited.length ; i++) {
            if (visited[goalPos][i] >= 1) {
                return Result.NORETURN;
            }
        }
        return Result.NOWAY;
    }

    static int[][] decode(int code) {
        return new int[][]{ decodePart(code>>12), decodePart(code&((1<<12)-1)) };

    }

    private static int[] decodePart(int code) {
        return new int[]{ code>>8, (code>>2)&63, code&3 } ;
    }

    static int encode(int y1, int x1, int d1, int y2, int x2, int d2) {
        return (encode(y1, x1, d1)<<12)|encode(y2, x2, d2);
    }

    static int encodePos(int y1, int x1) {
        return (y1<<6)|x1;
    }

    private static int encode(int y1, int x1, int d1) {
        return (encodePos(y1, x1)<<2)|d1;
    }

    static int[][] visited = new int[1024][1024];
    static int[] _que = new int[16000000];

    static enum Result {
        NOWAY("He cannot bring tea to his master."),
        NORETURN("He cannot return to the kitchen."),
        OK("He can accomplish his mission.");

        String msg;

        Result(String msg) {
            this.msg = msg;
        }
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
