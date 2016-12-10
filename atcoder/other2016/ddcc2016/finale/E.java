package atcoder.other2016.ddcc2016.finale;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] edges = in.nextIntTable(n-1, 3);
        for (int i = 0; i < n-1 ; i++) {
            edges[i][0]--;
            edges[i][1]--;
        }

        int[][][] wtree = buildWeightedGraph(n, edges);
        tree = buildGraph(n, edges);
        lca = new LCA(tree);

        int q = in.nextInt();
        queries = new int[q][4];
        for (int i = 0; i < q ; i++) {
            int type = in.nextInt();
            queries[i][0] = type;
            int cnt = type == 1 ? 3 : 2;
            for (int j = 0; j < cnt ; j++) {
                queries[i][j+1] = in.nextInt();
            }
            if (type == 1) {
                queries[i][1]--;
            } else {
                queries[i][1]--;
                queries[i][2]--;
            }
        }

        dist = new long[n];
        tmp = new long[2*n+10];

        qnum = new List[n];
        for (int i = 0; i < n ; i++) {
            qnum[i] = new ArrayList();
        }
        int[] que = new int[n];
        int qh = 0;
        int qt = 0;
        que[qh++] = 0;
        while (qt < qh) {
            int now = que[qt++];
            for (int[] to : wtree[now]) {
                if (to[0] == lca.parent[0][now]) {
                    continue;
                }
                dist[to[0]] = dist[now] + to[1];
                que[qh++] = to[0];
            }
        }

        int[] qDeg = new int[n];
        for (int i = 0; i < q ; i++) {
            if (queries[i][0] == 1) {
                qDeg[queries[i][1]]++;
            }
        }
        queryNode = new int[n][];
        for (int i = 0; i < n ; i++) {
            queryNode[i] = new int[qDeg[i]];
        }
        Arrays.fill(qDeg, 0);
        for (int i = 0; i < q ; i++) {
            if (queries[i][0] == 1) {
                int v = queries[i][1];
                queryNode[v][qDeg[v]++] = i;
            }
        }

        cmark = new long[6][n];
        int BUCKET = 380;
        tmpFrom = new int[n];
        tmpTo = new int[n];
        for (int i = 0; i < q ; ) {
            int j = Math.min(q, i + BUCKET);

            for (int k = 0; k < cmark.length ; k++) {
                Arrays.fill(cmark[k], 0);
            }
            for (int k = i ; k < j ; k++) {
                if (queries[k][0] == 1) {
                    int bi = (k-i)/64;
                    int bj = (k-i)&63;
                    cmark[bi][queries[k][1]] |= 1L<<bj;
                }
            }
            markDfs(0, -1);

            for (int k = i ; k < j ; k++) {
                if (queries[k][0] == 2) {
                    long du = find(queries[k][1], i, k);
                    long dv = find(queries[k][2], i, k);
                    long dx = find(lca.lca(queries[k][1], queries[k][2]), i, k);
                    out.println(du+dv-dx*2);
                }
            }

            // process queries once in a row.
            for (int k = 0; k < n ; k++) {
                qnum[k].clear();
            }
            for (int k = i ; k < j ; k++) {
                if (queries[k][0] == 1) {
                    tmpTo[queries[k][1]]++;
                }
            }
            Arrays.fill(tmp, 0);
            dfs(0, -1, 0);
            i = j;
            for (int k = 0; k < n ; k++) {
                tmpFrom[k] = tmpTo[k];
            }
        }


        out.flush();
    }


    static List<Integer>[] qnum;

    static void dfs(int now, int par, long sub) {
        // enter
        for (int k = tmpFrom[now] ; k < tmpTo[now] ; k++) {
            int qi = queryNode[now][k];
            tmp[queries[qi][2]+lca.depth[now]] += queries[qi][3];
        }
        sub += tmp[lca.depth[now]];

        dist[now] += sub;
        for (int to : tree[now]) {
            if (to == par) {
                continue;
            }
            dfs(to, now, sub);
        }

        // exit
        for (int k = tmpFrom[now] ; k < tmpTo[now] ; k++) {
            int qi = queryNode[now][k];
            tmp[queries[qi][2]+lca.depth[now]] -= queries[qi][3];
        }
    }


    private static long find(int i, int from, int to) {
        long fromOne = dist[i];
        for (int u = from ; u < to ; u++) {
            if (queries[u][0] == 1) {
                int q = queries[u][1];
                int bi = (u-from)/64;
                int bj = (u-from)&63;
                if (lca.depth[i] >= lca.depth[q] + queries[u][2] && ((cmark[bi][i] >> bj) & 1) == 1) {
                    fromOne += queries[u][3];
                }
            }
        }
        return fromOne;
    }

    static LCA lca;

    static long[][] cmark;

    static int[][] queries;

    static long[] dist;

    static long[] tmp;
    static int[] tmpFrom;
    static int[] tmpTo;
    static int[][] queryNode;

    static int[][] tree;

    static void markDfs(int now, int par) {
        for (int to : tree[now]) {
            if (to == par) {
                continue;
            }
            for (int i = 0; i < cmark.length ; i++) {
                cmark[i][to] |= cmark[i][now];
            }
            markDfs(to, now);
        }
    }


    public static class LCA {
        int[][] graph;
        int[][] parent;
        int[] depth;

        public LCA(int[][] graph) {
            int n = graph.length;
            this.graph = graph;
            init(n);
        }

        void dfs(int now, int from, int dep) {
            parent[0][now] = from;
            depth[now] = dep;
            for (int to : graph[now]) {
                if (to != from) {
                    dfs(to, now, dep+1);
                }
            }
        }

        void init(int n) {
            int log = 1;
            int nn = n;
            while (nn >= 1) {
                nn /= 2;
                log++;
            }
            parent = new int[log+1][n];
            for (int i = 0 ; i <= log ; i++) {
                Arrays.fill(parent[i], -1);
            }
            depth = new int[n];

            dfs(0, -1, 0);

            for (int k = 0 ; k < log ; k++) {
                for (int v = 0 ; v < n ; v++) {
                    if (parent[k][v] < 0) {
                        parent[k+1][v] = -1;
                    } else {
                        parent[k+1][v] = parent[k][parent[k][v]];
                    }
                }
            }
        }

        int lca(int u, int v) {
            int loglen = parent.length;
            if (depth[u] > depth[v]) {
                int tmp = u;
                u = v;
                v = tmp;
            }
            for (int k = 0 ; k < loglen ; k++) {
                if (((depth[v] - depth[u]) >> k) % 2 == 1) {
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

        int dist(int x, int y) {
            int l = lca(x, y);
            return depth[x] + depth[y] - depth[l] * 2;
        }
    }


    static int[][][] buildWeightedGraph(int n, int[][] edges) {
        int m = edges.length;
        int[][][] graph = new int[n][][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            deg[a]++;
            deg[b]++;
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]][2];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            int w = edges[i][2];
            graph[a][--deg[a]][0] = b;
            graph[b][--deg[b]][0] = a;
            graph[a][deg[a]][1] = w;
            graph[b][deg[b]][1] = w;
        }
        return graph;
    }

    static int[][] buildGraph(int n, int[][] edges) {
        int m = edges.length;
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            deg[a]++;
            deg[b]++;
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
