package codeforces.cf1xx.cr199.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/05/23.
 */
public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[][] graph = buildGraph(in, n, n-1);

        DecomposeToCenteroid center = new DecomposeToCenteroid(graph);
        center.doit(0, 0);
        LCA lca = new LCA(graph);

        int[] distToRed = new int[n];
        for (int i = 0 ; i < n ; i++) {
            distToRed[i] = lca.depth[i];
        }

        for (int i = 0; i < m ; i++) {
            int t = in.nextInt();
            int x = in.nextInt()-1;
            if (t == 1) {
                // paint
                distToRed[x] = 0;
                int now = x;
                while (now != -1) {
                    distToRed[now] = Math.min(distToRed[now], lca.dist(x, now));
                    now = center.centerParent[now];
                }
            } else {
                // query
                int best = distToRed[x];
                int now = x;
                while (now != -1) {
                    best = Math.min(best, distToRed[now] + lca.dist(x, now));
                    now = center.centerParent[now];
                }
                out.println(best);
            }
        }
        out.flush();
    }

    static class DecomposeToCenteroid {
        // 元のグラフ（参照のみ）
        int[][] tree;

        int n;

        // 処理に使う変数達
        int[] vertex;
        int[] parent;
        int[] children;
        boolean[] finished;

        // 分割で木を作った時の親
        int[] centerParent;

        int centeroidRoot;
        int[][] centeroidTree;

        public DecomposeToCenteroid(int[][] tree) {
            this.tree = tree;
            this.n = tree.length;

            this.vertex = new int[n];
            this.parent = new int[n];
            this.children = new int[n];
            this.finished = new boolean[n];

            this.centerParent = new int[n];
            Arrays.fill(this.centerParent, -1);
        }

        public void buildCenteroidTree() {
            int[] deg = new int[n];
            int root = 0;

            int[][] edge = new int[n-1][2];
            int ei = 0;
            for (int i = 0 ; i < n ; i++) {
                if (centerParent[i] != -1) {
                    edge[ei][0] = i;
                    edge[ei][1] = centerParent[i];
                    deg[i]++;
                    deg[centerParent[i]]++;
                    ei++;
                } else {
                    root = i;
                }
            }

            int[][] graph = new int[n][];
            for (int i = 0 ; i < n ; i++) {
                graph[i] = new int[deg[i]];
            }
            for (int i = 0 ; i < ei ; i++) {
                int a = edge[i][0];
                int b = edge[i][1];
                graph[a][--deg[a]] = b;
                graph[b][--deg[b]] = a;
            }
            this.centeroidRoot = root;
            this.centeroidTree = graph;
        }

        public int doit(int root, int d) {
            // debug(root, d);

            int qh = 0;
            int qt = 0;
            vertex[qh] = root;
            parent[qh++] = -1;
            while (qt < qh) {
                int v = vertex[qt];
                int p = parent[qt++];
                for (int to : tree[v]) {
                    if (to != p && !finished[to]) {
                        vertex[qh] = to;
                        parent[qh++] = v;
                    }
                }
            }

            int center = -1;

            sch: for (int i = qt-1 ; i >= 0 ; i--) {
                int vi = vertex[i];
                children[vi] = 1;
                for (int to : tree[vi]) {
                    if (to != parent[i] && !finished[to]) {
                        children[vi] += children[to];
                    }
                }

                if (qt - children[vi] <= qt / 2) {
                    for (int to : tree[vi]) {
                        if (to != parent[i] && !finished[to] && children[to] >= qt/2+1) {
                            continue sch;
                        }
                    }
                    center = vi;
                    break;
                }
            }

            finished[center] = true;

            for (int to : tree[center]) {
                if (!finished[to]) {
                    centerParent[doit(to, d+1)] = center;
                }
            }

            return center;
        }
    }

    static class LCA {
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
                return (char)c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char)c;
            }
            throw new InputMismatchException();
        }

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char)c);
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
