package codeforces.other2016.intelcodechallenge2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class G {
    private static final long MOD = (long) 1e9+7;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);


        int n = in.nextInt();
        int m = in.nextInt();
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < m ; i++) {
            int u = in.nextInt()-1;
            int v = in.nextInt()-1;
            edges.add(new Edge(u, v, in.nextLong()));
        }
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < m ; i++) {
            Edge e = edges.get(i);
            uf.unite(e.u, e.v);
        }

        Map<Integer,Integer>[] vmap = new Map[n];
        for (int i = 0; i < n ; i++) {
            int id = uf.find(i);
            if (vmap[id] == null) {
                vmap[id] = new HashMap<>();
            }
            vmap[id].put(i, vmap[id].size());
        }
        List<Edge>[] edgeLists = new List[n];
        for (int i = 0; i < n; i++) {
            edgeLists[i] = new ArrayList<>();
        }
        for (int i = 0; i < m ; i++) {
            Edge e = edges.get(i);
            int gid = uf.find(e.u);
            e.u = vmap[gid].get(e.u);
            e.v = vmap[gid].get(e.v);
            edgeLists[gid].add(e);
        }

        long ans = 0;
        for (int i = 0; i < n ; i++) {
            if (edgeLists[i].size() >= 1) {
                ans += solve(vmap[i].size(), edgeLists[i]);
                ans %= MOD;
            }
        }
        out.println(ans);
        out.flush();
    }

    static long solve(int n, List<Edge> edges) {
        if (n == 1) {
            return 0;
        }
        int m = edges.size();
        List<Edge> treeEdges = new ArrayList<>();
        List<Edge> otherEdges = new ArrayList<>();
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < m ; i++) {
            Edge e = edges.get(i);
            if (uf.issame(e.u, e.v)) {
                otherEdges.add(e);
            } else {
                treeEdges.add(e);
                uf.unite(e.u, e.v);
            }
        }
        Edge[][] graph = buildGraph(n, treeEdges);

        long[] vectors = new long[n];
        Arrays.fill(vectors, -1);
        vectors[0] = 0;
        int[] que = new int[n];
        int qh = 0;
        int qt = 0;
        que[qh++] = 0;
        while (qt < qh) {
            int now = que[qt++];
            for (Edge e : graph[now]) {
                if (vectors[e.v] == -1) {
                    vectors[e.v] = vectors[now] ^ e.w;
                    que[qh++] = e.v;
                }
            }
        }

        int x = otherEdges.size();
        long[] cycleBasis = new long[x];
        for (int i = 0; i < x ; i++) {
            Edge e = otherEdges.get(i);
            cycleBasis[i] = e.w ^ vectors[e.u] ^ vectors[e.v];
        }

        int rank = 0;
        for (int i = 0; i < 60 ; i++) {
            int pv = -1;
            for (int j = rank ; j < x ; j++) {
                if (((cycleBasis[j] >> i) & 1) == 1) {
                    pv = j;
                    break;
                }
            }
            if (pv == -1) {
                continue;
            }
            long tmp = cycleBasis[pv];
            cycleBasis[pv] = cycleBasis[rank];
            cycleBasis[rank] = tmp;
            for (int j = 0 ; j < x ; j++) {
                if (j == rank) {
                    continue;
                }
                if (((cycleBasis[j] >> i) & 1) == 1) {
                    cycleBasis[j] ^= cycleBasis[rank];
                }
            }
            rank++;
        }

        long[] one = new long[60];
        for (int i = 0; i < vectors.length ; i++) {
            for (int j = 0; j < 60 ; j++) {
                if (((vectors[i] >> j) & 1) == 1) {
                    one[j]++;
                }
            }
        }

        long[] p2 = new long[101];
        p2[0] = 1;
        for (int i = 1 ; i < p2.length ; i++) {
            p2[i] = (p2[i-1] * 2) % MOD;
        }

        long pair = n * (n - 1L) / 2 % MOD;
        long ans = 0;
        for (int i = 0 ; i < 60 ; i++) {
            boolean has = false;
            for (int j = 0; j < x ; j++) {
                has |= ((cycleBasis[j] >> i) & 1) >= 1;
            }
            if (has) {
                ans += p2[i] * pair % MOD * p2[rank-1] % MOD;
            } else {
                ans += p2[i] * one[i] % MOD * (n - one[i]) % MOD * p2[rank] % MOD;
            }
            ans %= MOD;
        }
        return ans;
    }

    static Edge[][] buildGraph(int n, List<Edge> edges) {
        Edge[][] graph = new Edge[n][];
        int[] deg = new int[n];
        for (Edge e : edges) {
            deg[e.u]++;
            deg[e.v]++;
        }
        for (int i = 0; i < n ; i++) {
            graph[i] = new Edge[deg[i]];
        }
        for (Edge e : edges) {
            int a = e.u;
            int b = e.v;
            graph[a][--deg[a]] = e;
            graph[b][--deg[b]] = new Edge(b, a, e.w);
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
            for (int i = 0; i < n ; i++) {
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



    static class Edge {
        int u;
        int v;
        long w;

        public Edge(int i, int j, long ww) {
            u = i;
            v = j;
            w = ww;
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