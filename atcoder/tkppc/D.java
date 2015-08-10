package atcoder.tkppc;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by hama_du on 15/08/01.
 */
public class D {
    static final int INF = 1145141919;

    static int[] dx = {1, 0};
    static int[] dy = {0, 1};

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int r = in.nextInt();
        int c = in.nextInt();
        char[][][] room = new char[n][r][];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < r; j++) {
                room[i][j] = in.nextToken().toCharArray();
            }
        }

        int[][][] dp = new int[n+1][r][c];
        for (int i = 0; i <= n ; i++) {
            for (int j = 0; j < r ; j++) {
                Arrays.fill(dp[i][j], INF);
            }
        }
        dp[0][0][0] = 0;

        Queue<State> q = new PriorityQueue<>();
        q.add(new State(0, 0, 0, 0));
        while (q.size() >= 1) {
            State st = q.poll();
            for (int d = 0; d <= 1; d++) {
                int tx = st.x + dx[d];
                int ty = st.y + dy[d];
                if (tx >= c || ty >= r) {
                    continue;
                }
                int tl = st.l;
                int tc = st.t;
                if (room[st.l][ty][tx] == 'H') {
                    tl++;
                }
                if (tl >= n) {
                    tc = INF;
                } else {
                    tc += room[tl][ty][tx] - '0';
                }
                if (dp[tl][ty][tx] > tc) {
                    dp[tl][ty][tx] = tc;
                    q.add(new State(tl, ty, tx, tc));
                }
            }
        }
        out.println(dp[n-1][r-1][c-1]);
        out.flush();
    }

    static class State implements Comparable<State> {
        int l;
        int y;
        int x;
        int t;

        State(int _a, int _b, int _c, int _d) {
            l = _a;
            y = _b;
            x = _c;
            t = _d;
        }

        @Override
        public int compareTo(State o) {
            return t - o.t;
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
