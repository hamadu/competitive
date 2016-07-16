package codeforces.cf3xx.cf355.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/07/16.
 */
public class D {
    private static final int INF = (int)1e8;
    private static final int[] DY = {-1, 0, 1, 0};
    private static final int[] DX = {0, -1, 0, 1};

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int p = in.nextInt();
        int[][] map = new int[n][m];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                map[i][j] = in.nextInt();
            }
        }
        int[] deg = new int[p+1];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                deg[map[i][j]]++;
            }
        }
        int[][][] chests = new int[p+1][][];
        for (int i = 1 ; i <= p ; i++) {
            chests[i] = new int[deg[i]][2];
        }
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m; j++) {
                int d = map[i][j];
                chests[d][--deg[d]] = new int[]{i, j};
            }
        }

        int[][] dp = new int[n][m];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(dp[i], INF);
        }
        for (int[] to : chests[1]) {
            int c = Math.abs(to[0])+Math.abs(to[1]);
            dp[to[0]][to[1]] = Math.min(dp[to[0]][to[1]], c);
        }
        for (int toLevel = 2 ; toLevel <= p ; toLevel++) {
            int fn = chests[toLevel-1].length;
            int tn = chests[toLevel].length;
            if (fn * tn <= 8*n*m) {
                for (int[] from : chests[toLevel-1]) {
                    int cost = dp[from[0]][from[1]];
                    for (int[] to : chests[toLevel]) {
                        int d = Math.abs(from[0]-to[0])+Math.abs(from[1]-to[1]);
                        dp[to[0]][to[1]] = Math.min(dp[to[0]][to[1]], cost+d);
                    }
                }
            } else {
                doBFS(dp, chests[toLevel-1], chests[toLevel]);
            }
        }

        int gy = chests[p][0][0];
        int gx = chests[p][0][1];

        out.println(dp[gy][gx]);
        out.flush();
    }

    static int[][] subdp = new int[310][310];

    private static void doBFS(int[][] dp, int[][] from, int[][] to) {
        int n = dp.length;
        int m = dp[0].length;
        for (int i = 0; i < n ; i++) {
            Arrays.fill(subdp[i], INF);
        }
        for (int[] f: from) {
            subdp[f[0]][f[1]] = dp[f[0]][f[1]];
        }

        // go to downright
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                for (int d = 0; d < 4 ; d++) {
                    int ti = i+DY[d];
                    int tj = j+DX[d];
                    if (ti < 0 || tj < 0 || ti >= n || tj >= m) {
                        continue;
                    }
                    subdp[ti][tj] = Math.min(subdp[ti][tj], subdp[i][j]+1);
                }
            }
        }

        // go to upleft
        for (int i = n-1 ; i >= 0 ; i--) {
            for (int j = m-1 ; j >= 0 ; j--) {
                for (int d = 0; d < 4 ; d++) {
                    int ti = i+DY[d];
                    int tj = j+DX[d];
                    if (ti < 0 || tj < 0 || ti >= n || tj >= m) {
                        continue;
                    }
                    subdp[ti][tj] = Math.min(subdp[ti][tj], subdp[i][j]+1);
                }
            }
        }

        for (int[] t : to) {
            dp[t[0]][t[1]] = subdp[t[0]][t[1]];
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
