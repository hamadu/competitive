package csacademy.round019;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class RoadTrips {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[] speed = in.nextInts(m);
        int[][] edges = new int[n-1][3];
        for (int i = 0; i < n-1 ; i++) {
            for (int j = 0; j < 2 ; j++) {
                edges[i][j] = in.nextInt()-1;
            }
            edges[i][2] = in.nextInt();
        }


        graph = new List[n];
        for (int i = 0; i < n ; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < n-1 ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a].add(edges[i]);
            graph[b].add(edges[i]);
        }
        parent = new int[18][n];
        level = new int[n];
        depth = new long[n];

        dfs(0, -1);
        parent[0][0] = 0;
        initLCA();
        int[][] leafLCA = new int[ln][ln];
        for (int i = 0 ; i < ln ; i++) {
            for (int j = i+1 ; j < ln ; j++) {
                leafLCA[i][j] = leafLCA[j][i] = lca(lv[i], lv[j]);
            }
        }

        long[][] prec = new long[ln][1<<ln];
        for (int ptn = 0 ; ptn < 1<<ln ; ptn++) {
            for (int j = 0 ; j < ln ; j++) {
                if (((ptn >> j) & 1) == 1) {
                    continue;
                }
                int v = 0;
                for (int k = 0 ; k < ln ; k++) {
                    if (((ptn >> k) & 1) == 1) {
                        int v0 = leafLCA[j][k];
                        if (level[v] < level[v0]) {
                            v = v0;
                        }
                    }
                }
                prec[j][ptn] = depth[lv[j]] - depth[v];
            }
        }

        Arrays.sort(speed);
        spd = new int[ln];
        spd[0] = speed[0];
        for (int i = 0 ; i < ln-1 ; i++) {
            spd[ln-1-i] = speed[speed.length-1-i];
        }

        long[][] dp = new long[ln+1][1<<ln];
        for (int i = 0; i <= ln; i++) {
            Arrays.fill(dp[i], -1);
        }
        dp[0][0] = 0;
        for (int i = 0 ; i < ln ; i++) {
            for (int p = 0 ; p < (1<<ln) ; p++) {
                if (dp[i][p] == -1) {
                    continue;
                }
                long base = dp[i][p];
                if (i >= 1) {
                    dp[i+1][p] = Math.max(dp[i+1][p], base);
                }
                for (int u = 0 ; u < ln ; u++) {
                    if (((p >> u) & 1) == 1) {
                        continue;
                    }
                    int tp = p | (1<<u);
                    dp[i+1][tp] = Math.max(dp[i+1][tp], base + prec[u][p] * spd[i]);
                }
            }
        }

        long max = 0;
        for (int i = 0; i < (1<<ln) ; i++) {
            max = Math.max(max, dp[ln][i]);
        }

        out.println(max);
        out.flush();
    }

    static void initLCA() {
        int n = graph.length;
        for (int k = 0 ; k < parent.length-1 ; k++) {
            for (int v = 0 ; v < n ; v++) {
                parent[k+1][v] = parent[k][parent[k][v]];
            }
        }
    }


    static int lca(int u, int v) {
        int loglen = parent.length;
        if (level[u] > level[v]) {
            int tmp = u;
            u = v;
            v = tmp;
        }
        for (int k = 0 ; k < loglen ; k++) {
            if (((level[v] - level[u]) >> k) % 2 == 1) {
                v = parent[k][v];
            }
        }
        if (u == v) {
            return u;
        }

        for (int k = loglen-1 ; k >= 0 ; k--) {
            if (parent[k][u] != parent[k][v]) {
                u = parent[k][u];
                v = parent[k][v];
            }
        }
        return parent[0][u];
    }


    static int[][] parent;
    static int[] spd;
    static int[] level;
    static long[] depth;
    static int[] lv = new int[20];
    static int ln;
    static List<int[]>[] graph;

    static void dfs(int now, int par) {
        parent[0][now] = par;
        level[now] = (par == -1) ? 0 : level[par]+1;
        boolean hasChild = false;
        for (int[] e : graph[now]) {
            int to = e[0]+e[1]-now;
            if (to == par) {
                continue;
            }
            hasChild = true;
            depth[to] = depth[now] + e[2];
            dfs(to, now);
        }
        if (!hasChild) {
            lv[ln++] = now;
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
