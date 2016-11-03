package codeforces.cf3xx.cf378.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class F {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[] w = in.nextInts(m);
        int[] c = in.nextInts(m);

        Edge[] ed = new Edge[m];
        for (int i = 0; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            ed[i] = new Edge(i, a, b, w[i], c[i]);
        }

        int S = in.nextInt();

        Arrays.sort(ed, (a, b) -> a.w - b.w);

        List<Edge> tree = new ArrayList<>();
        List<Edge> otherEdges = new ArrayList<>();
        UnionFind uf = new UnionFind(n);

        long baseWeight = 0;
        for (int i = 0; i < m ; i++) {
            int a = ed[i].a;
            int b = ed[i].b;
            if (uf.issame(a, b)) {
                otherEdges.add(ed[i]);
            } else {
                uf.unite(a, b);
                tree.add(ed[i]);
                baseWeight += ed[i].w;
            }
        }

        Arrays.sort(ed, (a, b) -> a.id - b.id);

        long bestWeight = baseWeight;
        int doe = -1;
        int treeRemove = -1;

        for (Edge e : tree) {
            long to = baseWeight - S / e.c;
            if (bestWeight > to) {
                bestWeight = to;
                doe = e.id;
            }
        }

        graph = new List[n];
        for (int i = 0; i < n ; i++) {
            graph[i] = new ArrayList<>();
        }
        for (Edge e : tree) {
            graph[e.a].add(e);
            graph[e.b].add(e);
        }
        depth = new int[n];
        parentEdgeID = new int[n];
        parentEdgeID[0] = -1;
        parent = new int[18][n];
        findPar(0, -1);

        int[][] maxEdgeID = new int[18][n];
        for (int k = 0 ; k < 18 ; k++) {
            if (k == 0) {
                for (int i = 0; i < n; i++) {
                    maxEdgeID[k][i] = parentEdgeID[i];
                }
            } else {
                for (int i = 0; i < n ; i++) {
                    int p0 = parent[k-1][i] == -1 ? -1 : parent[k-1][parent[k-1][i]];
                    parent[k][i] = p0;
                    int e0 = maxEdgeID[k-1][i];
                    int e1 = parent[k-1][i] == -1 ? maxEdgeID[k-1][i] : maxEdgeID[k-1][parent[k-1][i]];
                    if (e0 == -1) {
                        maxEdgeID[k][i] = e1;
                    } else if (e1 == -1) {
                        maxEdgeID[k][i] = e0;
                    } else {
                        maxEdgeID[k][i] = ed[e0].w > ed[e1].w ? e0 : e1;
                    }
                }
            }
        }


        for (Edge e : otherEdges) {
            long weight = baseWeight + e.w - S / e.c;
            int a = e.a;
            int b = e.b;
            if (depth[a] < depth[b]) {
                int tmp = b;
                b = a;
                a = tmp;
            }
            if (parent[0][a] == b) {
                continue;
            }

            int deleteID = -1;
            long maxWeight = 0;
            int DA = depth[a];
            int DB = depth[b];
            for (int k = 17 ; k >= 0 ; k--) {
                if ((DA - (1<<k)) >= DB) {
                    if (maxWeight < ed[maxEdgeID[k][a]].w) {
                        maxWeight = ed[maxEdgeID[k][a]].w;
                        deleteID = maxEdgeID[k][a];
                    }
                    maxWeight = Math.max(maxWeight, ed[maxEdgeID[k][a]].w);
                    DA -= 1<<k;
                    a = parent[k][a];
                }
            }

            for (int wk = 17 ; wk >= -1 ; wk--) {
                if (a == b) {
                    break;
                }
                int k = Math.max(0, wk);
                if (wk == -1 || parent[k][a] != parent[k][b]) {
                    if (maxWeight < ed[maxEdgeID[k][a]].w) {
                        maxWeight = ed[maxEdgeID[k][a]].w;
                        deleteID = maxEdgeID[k][a];
                    }
                    if (maxWeight < ed[maxEdgeID[k][b]].w) {
                        maxWeight = ed[maxEdgeID[k][b]].w;
                        deleteID = maxEdgeID[k][b];
                    }
                    a = parent[k][a];
                    b = parent[k][b];
                }
            }

            weight -= maxWeight;
            if (bestWeight > weight) {
                bestWeight = weight;
                doe = e.id;
                treeRemove = deleteID;
            }
        }


        boolean inTree = false;

        out.println(bestWeight);
        for (Edge e : tree) {
            if (e.id == treeRemove) {
                continue;
            }
            if (doe == e.id) {
                inTree = true;
                out.println(String.format("%d %d", e.id+1, e.w - S / e.c));
            } else {
                out.println(String.format("%d %d", e.id+1, e.w));
            }
        }
        if (!inTree && doe >= 0) {
            out.println(String.format("%d %d", doe+1, ed[doe].w-S/ed[doe].c));
        }
        out.flush();
    }



    static List<Edge>[] graph;
    static int[] depth;
    static int[][] parent;
    static int[] parentEdgeID;

    static void findPar(int now, int par) {
        parent[0][now] = par;
        depth[now] = par == -1 ? 0 : depth[par]+1;
        for (Edge e : graph[now]) {
            int to = e.a + e.b - now;
            if (to == par) {
                parentEdgeID[now] = e.id;
                continue;
            }
            findPar(to, now);
        }
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
        int id;
        int a;
        int b;
        int w;
        int c;

        public Edge(int _i, int i, int j, int k, int l) {
            id = _i;
            a = i;
            b = j;
            w = k;
            c = l;
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
