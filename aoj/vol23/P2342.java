package aoj.vol23;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by hama_du on 15/07/15.
 */
public class P2342 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int A = in.nextInt();
        char[][] map = new char[n][];
        for (int i = 0; i < n ; i++) {
            map[i] = in.nextToken().toCharArray();
        }

        int sx = -1;
        int sy = -1;
        int gx = -1;
        int gy = -1;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (map[i][j] == 'S') {
                    sy = i;
                    sx = j;
                    map[i][j] = '.';
                } else if (map[i][j] == 'G') {
                    gy = i;
                    gx = j;
                    map[i][j] = '.';
                }
            }
        }


        out.println(solve(map, sy, sx, gy, gx, A));
        out.flush();
    }

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    static class State implements Comparable<State> {
        int y;
        int x;
        int d;
        int usedP;
        int usedQ;

        State(int h, int w, int di, int up, int uq) {
            y = h;
            x = w;
            d = di;
            usedP = up;
            usedQ = uq;
        }

        @Override
        public int compareTo(State o) {
            return usedQ - o.usedQ;
        }
    }

    private static int solve(char[][] map, int sy, int sx, int gy, int gx, int A) {
        int ret = Integer.MAX_VALUE;


        int h = map.length;
        int w = map[0].length;

        int hw = h * w;

        boolean[][][][] marked = new boolean[4][hw][A+1][A+1];
        int[][][] dp = new int[4][hw][A+1];
        for (int i = 0; i < 4 ; i++) {
            for (int j = 0; j < hw; j++) {
                Arrays.fill(dp[i][j], Integer.MAX_VALUE);
            }
        }

        Queue<State> q = new PriorityQueue<State>();
        q.add(new State(sy, sx, 1, 0, 0));
        dp[1][sy*w+sx][0] = 0;
        for (int pi = 0 ; pi <= A ; pi++) {
            for (int qi = 0 ; qi <= A ; qi++) {
                marked[3][sy*w+sx][pi][qi] = true;
            }
        }


        while (q.size() >= 1) {
            State st = q.poll();
            for (int d = 0 ; d < 4 ; d++) {
                int tp = st.usedP;
                int tq = st.usedQ;

                boolean canGo = false;
                if (st.d == d) {
                    // forward
                    canGo = true;
                } else if ((st.y != sy || st.x != sx) && (st.y != gy || st.x != gx)) {
                    if (tp+1 <= A) {
                        // type P
                        if (st.d + (1 - (st.d % 2) * 2) == d) {
                            tp++;
                            canGo = true;
                        }
                    }
                    if (tq + 1 <= A) {
                        // type Q
                        if (3 - st.d == d) {
                            tq++;
                            canGo = true;
                        }
                    }
                }
                if (!canGo) {
                    continue;
                }

                int ty = st.y + dy[d];
                int tx = st.x + dx[d];
                if (ty < 0 || tx < 0 || ty >= h || tx >= w || map[ty][tx] == '#') {
                    continue;
                }
                int tt = ty*w+tx;
                if (dp[d][tt][tp] > tq && !marked[d][tt][tp][tq]) {
                    dp[d][tt][tp] = tq;
                    for (int pi = tp ; pi <= A ; pi++) {
                        for (int qi = tq ; qi <= A ; qi++) {
                            marked[(d+2)%4][tt][pi][qi] = true;
                        }
                    }

                    q.add(new State(ty, tx, d, tp, tq));
                }
            }
        }


        int tid = gy*w+gx;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <= A; j++) {
                if (dp[i][tid][j] != Integer.MAX_VALUE) {
                    ret = Math.min(ret, j + dp[i][tid][j]);
                }
            }
        }
        return (ret == Integer.MAX_VALUE) ? -1 : ret;
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
