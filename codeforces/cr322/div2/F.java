package codeforces.cr322.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/09/29.
 */
public class F {
    private static final int INF = 114514;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();

        int[][] graph = buildGraph(in, n, n-1);
        int res = solve(graph);

        out.println(res);
        out.flush();
    }

    private static int solve(int[][] graph) {
        g = graph;
        int n = graph.length;
        if (n == 2) {
            return 1;
        }
        int root = 0;
        long bestCost = Long.MAX_VALUE;
        leaf = new int[5120];
        lcnt = new int[5120];
        for (int i = 0; i < n ; i++) {
            if (graph[i].length >= 2) {
                root = i;
                break;
            }
        }

        ln = 0;
        dfs(root, -1);
        ln /= 2;

        memo = new int[2][n][ln+1];
        for (int i = 0; i < 2 ; i++) {
            for (int j = 0; j < n ; j++) {
                Arrays.fill(memo[i][j], INF);
            }
        }
        visited = new boolean[2][n];

        doit(root, -1, 0);
        doit(root, -1, 1);

        return Math.min(memo[0][root][ln], memo[1][root][ln]);
    }

    static int[] leaf;
    static int ln;
    static int[] lcnt;
    static int[][] g;
    static int[][][] memo;
    static boolean[][] visited;

    static void doit(int now, int par, int col) {
        if (visited[col][now]) {
            return;
        }
        visited[col][now] = true;
        int cn = (g[now].length - (par == -1 ? 0 : 1));
        if (cn == 0) {
            memo[col][now][col] = 0;
            return;
        }
        int[][] subdp = new int[2][ln+1];
        for (int i = 0; i <= 1; i++) {
            Arrays.fill(subdp[i], INF);
        }
        subdp[0][0] = 0;

        int[][] lol = new int[cn][2];
        int ci = 0;
        for (int to : g[now]) {
            if (to == par) {
                continue;
            }
            doit(to, now, 0);
            doit(to, now, 1);
            lol[ci][0] = to;
            lol[ci][1] = lcnt[to];
            ci++;
        }
        Arrays.sort(lol, (l1, l2) -> l1[1] - l2[1]);

        int lsum = 0;
        for (ci = 0 ; ci < cn ; ci++) {
            int fi = ci % 2;
            int ti = 1 - fi;
            Arrays.fill(subdp[ti], INF);

            int to = lol[ci][0];
            for (int i = 0; i <= lsum ; i++) {
                if (subdp[fi][i] == INF) {
                    continue;
                }
                for (int j = 0; j <= lol[ci][1] && i+j <= ln; j++) {
                    for (int c = 0; c <= 1; c++) {
                        int theCost = memo[c][to][j] + ((c == col) ? 0 : 1);
                        subdp[ti][i+j] = Math.min(subdp[ti][i+j], subdp[fi][i] + theCost);
                    }
                }
            }
            lsum += lol[ci][1];
            lsum = Math.min(lsum, ln);
        }
        memo[col][now] = subdp[ci%2];
    }

    static int dfs(int now, int par) {
        int cnt = 0;
        for (int to : g[now]) {
            if (to == par) {
                continue;
            }
            cnt += dfs(to, now);
        }
        if (par != -1 && g[now].length == 1) {
            leaf[ln++] = now;
            cnt = 1;
        }
        lcnt[now] = cnt;
        return cnt;
    }

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
                res += c-'0';
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
