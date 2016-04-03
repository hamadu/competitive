package atcoder.arc.arc048;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2015/12/13.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int q = in.nextInt();
        int[][] tree = buildGraph(in, n, n-1);
        char[] taco = in.nextToken().toCharArray();
        int[] toTaco = new int[n];
        Arrays.fill(toTaco, n+1);

        {
            int[] que = new int[1000000];
            int qh = 0;
            int qt = 0;
            for (int i = 0 ; i < n ; i++) {
                if (taco[i] == '1') {
                    que[qh++] = i;
                    que[qh++] = 0;
                    toTaco[i] = 0;
                }
            }
            while (qt < qh) {
                int now = que[qt++];
                int time = que[qt++];
                for (int to : tree[now]) {
                    if (toTaco[to] > time+1) {
                        toTaco[to] = time+1;
                        que[qh++] = to;
                        que[qh++] = time+1;
                    }
                }
            }
        }
        // debug(toTaco);

        LCA lca = new LCA(tree);
        int[] valueOne = new int[n];
        int[] valueTwo = new int[n];
        for (int i = 0 ; i < n ; i++) {
            valueOne[i] = toTaco[i] * 3 - lca.depth[i];
            valueTwo[i] = toTaco[i] * 3 + lca.depth[i];
        }
        TreeLCAQuery q1 = new TreeLCAQuery(lca, valueOne);
        TreeLCAQuery q2 = new TreeLCAQuery(lca, valueTwo);


        while (--q >= 0) {
            int u = in.nextInt()-1;
            int v = in.nextInt()-1;
            int l = lca.lca(u, v);
            int dist = lca.depth[u] + lca.depth[v] - lca.depth[l] * 2;
            int ans = dist * 2;
            ans = Math.min(ans, dist + q1.min(u, l) + lca.depth[u]);
            ans = Math.min(ans, dist + q2.min(v, l) + lca.depth[u] - lca.depth[l] * 2);
            ans = Math.max(0, ans);
            out.println(ans);
        }
        out.flush();
    }

    static class TreeLCAQuery {
        LCA lca;
        int[] val;
        int[][] dbl;

        public TreeLCAQuery(LCA lca, int[] val) {
            int n = val.length;
            this.lca = lca;
            this.val = val;
            int lg = lca.parent.length;
            dbl = new int[lg][n];
            for (int i = 0 ; i < n ; i++) {
                dbl[0][i] = val[i];
            }
            for (int l = 1 ; l < lg ; l++) {
                for (int i = 0 ; i < n ; i++) {
                    int half = lca.parent[l-1][i];
                    if (half >= 0) {
                        dbl[l][i] = Math.min(dbl[l-1][i], dbl[l-1][half]);
                    } else {
                        dbl[l][i] = dbl[l-1][i];
                    }
                }
            }
        }

        public int min(int u, int v) {
            int min = Integer.MAX_VALUE;
            while (u != v) {
                int diff = lca.depth[u] - lca.depth[v];
                int ctr = -1;
                while (diff > 0) {
                    ctr++;
                    diff /= 2;
                }
                min = Math.min(min, dbl[ctr][u]);
                u = lca.parent[ctr][u];
            }
            min = Math.min(min, dbl[0][u]);
            return min;
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

        void buildDepth() {
            int[] que = new int[graph.length*3+10];
            int qh = 0;
            int qt = 0;
            que[qh++] = 0;
            que[qh++] = -1;
            que[qh++] = 0;
            depth[0] = 0;
            parent[0][0] = -1;
            while (qt < qh) {
                int now = que[qt++];
                int frm = que[qt++];
                int dep = que[qt++];
                for (int to : graph[now]) {
                    if (to != frm) {
                        parent[0][to] = now;
                        depth[to] = dep+1;
                        que[qh++] = to;
                        que[qh++] = now;
                        que[qh++] = dep+1;
                    }
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
            buildDepth();

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
