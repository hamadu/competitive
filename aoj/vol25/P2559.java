package aoj.vol25;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/19.
 */
public class P2559 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        edges = new int[m][4];
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < 2 ; j++) {
                edges[i][j] = in.nextInt()-1;
            }
            edges[i][2] = in.nextInt();
            edges[i][3] = i;
        }
        graph = buildWeightedGraph(n, edges);

        Arrays.sort(edges, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[2] - o2[2];
            }
        });

        int[] tdeg = new int[n];

        ans = new long[m];
        UnionFind uf = new UnionFind(n);
        long minCost = 0;
        int ct = 0;
        for (int i = 0; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            if (uf.issame(a, b)) {
                ans[edges[i][3]] = Long.MIN_VALUE;
            } else {
                ct++;
                minCost += edges[i][2];
                uf.unite(a, b);
                tdeg[a]++;
                tdeg[b]++;
            }
        }
        Arrays.sort(edges, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[3] - o2[3];
            }
        });

        mst = new int[n][][];
        for (int i = 0; i < n ; i++) {
            mst[i] = new int[tdeg[i]][];
        }
        for (int i = 0; i < m ; i++) {
            if (ans[i] == 0) {
                int a = edges[i][0];
                int b = edges[i][1];
                mst[a][--tdeg[a]] = new int[]{b, i, edges[i][2]};
                mst[b][--tdeg[b]] = new int[]{a, i, edges[i][2]};
            }
        }

        if (ct == n-1) {
            dfs(0, minCost);
        } else {
            Arrays.fill(ans, -1);
        }

        for (int i = 0 ; i < m ; i++) {
            if (ans[i] == Long.MIN_VALUE) {
                out.println(minCost);
            } else {
                out.println(ans[i]);
            }
        }
        out.flush();
    }

    @SuppressWarnings("unchecked")
    static void dfs(int root, long mstCost) {
        LCA lca = new LCA(mst);

        int head = 0;
        _stk[head++] = root;
        _stk[head++] = -1;
        _stk[head++] = -1;

        int n = graph.length;
        int[] ccnt = new int[n];
        int qh = 0;
        int qt = 0;
        for (int i = 0 ; i < n ; i++) {
            ccnt[i] = (i == root) ? mst[i].length : mst[i].length-1;
            if (ccnt[i] == 0) {
                _que[qh++] = i;
            }
        }

        TreeSet<int[]>[] availableEdges = new TreeSet[n];
        Comparator<int[]> cmpr = new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[2] == o2[2]) {
                    return o1[3] - o2[3];
                }
                return o1[2] - o2[2];
            }
        };
        for (int i = 0; i < n ; i++) {
            availableEdges[i] = new TreeSet<int[]>(cmpr);
        }

        while (qt < qh) {
            int now = _que[qt++];
            int par = lca.parent[0][now];
            int min = Integer.MAX_VALUE;
            int eid = -1;
            for (int[] ed : graph[now]) {
                if (ed[0] == par) {
                    eid = ed[2];
                    continue;
                }
                if (lca.lca(now, ed[0]) == now) {
                    continue;
                }
                min = Math.min(min, ed[1]);
                availableEdges[now].add(edges[ed[2]]);
            }

            if (eid != -1) {
                for (int[] ed : mst[now]) {
                    if (ed[0] != par) {
                        if (availableEdges[now].size() < availableEdges[ed[0]].size()) {
                            availableEdges[ed[0]].addAll(availableEdges[now]);
                            availableEdges[now] = availableEdges[ed[0]];
                        } else {
                            availableEdges[now].addAll(availableEdges[ed[0]]);
                            availableEdges[ed[0]].clear();
                        }
                    }
                }

                int best = Integer.MAX_VALUE;
                while (availableEdges[now].size() >= 1) {
                    int[] ed = availableEdges[now].first();
                    int a = ed[0];
                    int b = ed[1];
                    if (lca.lca(now, lca.lca(a, b)) == now) {
                        availableEdges[now].remove(ed);
                        continue;
                    }
                    best = Math.min(best, ed[2]);
                    break;
                }
                if (best != Integer.MAX_VALUE) {
                    ans[eid] = mstCost - edges[eid][2] + best;
                } else {
                    ans[eid] = -1;
                }
            }
            if (par >= 0) {
                ccnt[par]--;
                if (ccnt[par] == 0) {
                    _que[qh++] = par;
                }
            }
        }
    }

    static int[] _que = new int[1000000];

    static int[] _stk = new int[1000000];

    static int[][] edges;
    static int[][][] mst;
    static int[][][] graph;
    static long[] ans;

    static class LCA {
        int[][][] graph;
        int[][] parent;
        int[] depth;

        public LCA(int[][][] graph) {
            int n = graph.length;
            this.graph = graph;
            init(n);
        }

        void dfs0(int root) {
            int head = 0;
            _stk[head++] = root;
            _stk[head++] = -1;
            _stk[head++] = 0;
            while (head > 1) {
                int dep = _stk[--head];
                int from = _stk[--head];
                int now = _stk[--head];
                parent[0][now] = from;
                depth[now] = dep;
                for (int[] to : graph[now]) {
                    if (to[0] != from) {
                        _stk[head++] = to[0];
                        _stk[head++] = now;
                        _stk[head++] = dep+1;
                    }
                }
            }
        }

        void dfs(int now, int from, int dep) {
            parent[0][now] = from;
            depth[now] = dep;
            for (int[] to : graph[now]) {
                if (to[0] != from) {
                    dfs(to[0], now, dep+1);
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

            dfs0(0);

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
            graph[i] = new int[deg[i]][3];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            int w = edges[i][2];
            graph[a][--deg[a]][0] = b;
            graph[b][--deg[b]][0] = a;
            graph[a][deg[a]][1] = w;
            graph[b][deg[b]][1] = w;
            graph[a][deg[a]][2] = i;
            graph[b][deg[b]][2] = i;
        }
        return graph;
    }




    static class UnionFind {
        int[] parent, rank;
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0 ; i < n ; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        int find(int x) {
            if (parent[x] == x) {
                return x;
            }
            parent[x] = find(parent[x]);
            return parent[x];
        }

        void unite(int x, int y) {
            x = find(x);
            y = find(y);
            if (x == y) {
                return;
            }
            if (rank[x] < rank[y]) {
                parent[x] = y;
            } else {
                parent[y] = x;
                if (rank[x] == rank[y]) {
                    rank[x]++;
                }
            }
        }
        boolean issame(int x, int y) {
            return (find(x) == find(y));
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
