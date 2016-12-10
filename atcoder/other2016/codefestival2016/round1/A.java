package atcoder.other2016.codefestival2016.round1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class A {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[][] edges = in.nextIntTable(m, 3);
        for (int i = 0; i < m ; i++) {
            edges[i][0]--;
            edges[i][1]--;
        }

        Arrays.sort(edges, (e0, e1) -> e0[2] - e1[2]);

        long treeCost = 0;
        UnionFind uf = new UnionFind(n);
        int[][] subEdges = new int[n-1][3];
        int si = 0;
        for (int i = 0; i < m ; i++) {
            if (!uf.issame(edges[i][0], edges[i][1])) {
                treeCost += edges[i][2];
                subEdges[si++] = edges[i];
                uf.unite(edges[i][0], edges[i][1]);
            }
        }

        tree = buildWeightedGraph(n, subEdges);
        depth = new int[n];
        parent = new int[15][n];
        maxEdge = new int[15][n];
        Arrays.fill(parent[0], -1);
        dfs(0, -1);

        for (int k = 1 ; k < 15 ; k++) {
            for (int i = 0 ; i < n ; i++) {
                int par = (parent[k-1][i] == -1) ? i : parent[k-1][i];
                parent[k][i] = parent[k-1][par];
                maxEdge[k][i] = Math.max(maxEdge[k-1][i], maxEdge[k-1][par]);
            }
        }

        int q = in.nextInt();
        while (--q >= 0) {
            int s = in.nextInt()-1;
            int t = in.nextInt()-1;
            long max = 0;

            if (depth[s] < depth[t]) {
                int tmp = t;
                t = s;
                s = tmp;
            }
            for (int k = 14 ; k >= 0 ; k--) {
                if (depth[s] - (1<<k) >= depth[t]) {
                    max = Math.max(max, maxEdge[k][s]);
                    s = parent[k][s];
                }
            }
            if (s != t) {
                for (int k = 14; k >= 0; k--) {
                    if (parent[k][s] == parent[k][t]) {
                        continue;
                    }
                    max = Math.max(max, maxEdge[k][s]);
                    max = Math.max(max, maxEdge[k][t]);
                    s = parent[k][s];
                    t = parent[k][t];
                }
                if (s != t) {
                    max = Math.max(max, maxEdge[0][s]);
                    max = Math.max(max, maxEdge[0][t]);
                }
            }
            out.println(treeCost-max);
        }

        out.flush();
    }

    static int[][][] tree;

    static int[] depth;
    static int[][] parent;
    static int[][] maxEdge;

    static void dfs(int now, int par) {
        for (int[] e : tree[now]) {
            int to = e[0];
            if (to == par) {
                continue;
            }
            depth[to] = depth[now]+1;
            parent[0][to] = now;
            maxEdge[0][to] = e[1];
            dfs(to, now);
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

    static class UnionFind {
        int[] rank;
        int[] parent;
        int[] cnt;

        public UnionFind(int n) {
            rank = new int[n];
            parent = new int[n];
            cnt = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                cnt[i] = 1;
            }
        }

        public int find(int a) {
            if (parent[a] == a) {
                return a;
            }
            parent[a] = find(parent[a]);
            return parent[a];
        }

        public void unite(int a, int b) {
            a = find(a);
            b = find(b);
            if (a == b) {
                return;
            }
            if (rank[a] < rank[b]) {
                parent[a] = b;
                cnt[b] += cnt[a];
                cnt[a] = cnt[b];
            } else {
                parent[b] = a;
                cnt[a] += cnt[b];
                cnt[b] = cnt[a];
                if (rank[a] == rank[b]) {
                    rank[a]++;
                }
            }
        }

        public int groupCount(int a) {
            return cnt[find(a)];
        }

        private boolean issame(int a, int b) {
            return find(a) == find(b);
        }
    }


    static int[][][] buildWeightedGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][][] graph = new int[n][][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            int w = in.nextInt();
            deg[a]++;
            deg[b]++;
            edges[i] = new int[]{a, b, w};
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
