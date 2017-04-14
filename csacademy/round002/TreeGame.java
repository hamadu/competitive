package csacademy.round002;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class TreeGame {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        graph = buildGraph(in, n, n-1);
        dp = new int[n][3];

        dfs(0, -1);

        out.println(Math.max(dp[0][0], dp[0][1]));
        out.flush();
    }


    static int[][] dp;

    static void dfs(int now, int par) {
        int cn = 0;
        for (int to : graph[now]) {
            if (par == to) {
                continue;
            }
            cn++;
            dfs(to, now);
        }
        if (cn == 0) {
            dp[now][0] = 0;
            dp[now][1] = 0;
            dp[now][2] = -114514;
            return;
        }

        int ci = 0;
        int[][][] subdp = new int[2][3][2];
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 2; j++) {
                Arrays.fill(subdp[i][j], -1);
            }
        }
        subdp[0][0][0] = 0;
        for (int to : graph[now]) {
            if (par == to) {
                continue;
            }
            int f = ci % 2;
            int t = 1 - f;
            for (int j = 0; j <= 2; j++) {
                Arrays.fill(subdp[t][j], -1);
            }
            for (int taken = 0 ; taken <= 2; taken++) {
                for (int flg = 0 ; flg <= 1 ; flg++) {
                    if (subdp[f][taken][flg] == -1) {
                        continue;
                    }
                    int base = subdp[f][taken][flg];
                    subdp[t][taken][flg] = Math.max(subdp[t][taken][flg], base+dp[to][0]);
                    subdp[t][taken][1] = Math.max(subdp[t][taken][1], base+dp[to][2]);

                    int tt = Math.min(2, taken+1);
                    subdp[t][tt][flg] = Math.max(subdp[t][tt][flg], base+dp[to][1]);
                }
            }
            ci++;
        }

        int[][] tbl = subdp[cn%2];

        // Pattern A: place A on here
        dp[now][1] = Math.max(dp[now][1], tbl[0][0]);
        dp[now][1] = Math.max(dp[now][1], tbl[0][1]);

        // Pattern B: place B on here
        if (tbl[2][0] >= 0) {
            dp[now][0] = Math.max(dp[now][0], tbl[2][0]+1);
        }
        if (tbl[1][0] >= 0) {
            dp[now][2] = Math.max(dp[now][2], tbl[1][0]+1);
        }

        // Pattern C: place nothing on here
        // C-0: nothing on children
        dp[now][0] = Math.max(dp[now][0], tbl[0][0]);

        // C-1: take one from children
        if (tbl[1][0] >= 0) {
            dp[now][1] = Math.max(dp[now][1], tbl[1][0]);
        }
    }

    static int[][] graph;

    static int[][] buildGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            deg[a]++;
            deg[b]++;
            edges[i] = new int[]{a, b};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
            graph[b][--deg[b]] = a;
        }
        return graph;
    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int[] nextInts(int n) {
            int[] ret = new int[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextInt();
            }
            return ret;
        }

        private int[][] nextIntTable(int n, int m) {
            int[][] ret = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextInt();
                }
            }
            return ret;
        }

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
            }
            return ret;
        }

        private long[][] nextLongTable(int n, int m) {
            long[][] ret = new long[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextLong();
                }
            }
            return ret;
        }

        private double[] nextDoubles(int n) {
            double[] ret = new double[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextDouble();
            }
            return ret;
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

        public double nextDouble() {
            return Double.valueOf(nextToken());
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
