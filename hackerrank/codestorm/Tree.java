package hackerrank.codestorm;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/10/30.
 */
public class Tree {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][][] tree = buildWeightedGraph(in, n, n-1);
        LCA lca = new LCA(tree);

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < n ; i++) {
            String max = "";
            int best = 0;

            int atLeast = 0;
            for (int[] e : tree[i]) {
                if (atLeast < e[1]) {
                    atLeast = e[1];
                }
            }

            for (int j = n-1 ; j >= 0 ; j--) {
                if (i == j) {
                    continue;
                }
                char one = lca.lcaOne(i, j);
                if (one != atLeast) {
                    continue;
                }
                String lr = lca.lca(max, i, j);
                if (max.compareTo(lr) < 0) {
                    best = j;
                    max = lr;
                }
            }
            builder.append(' ').append(best+1);
        }

        out.println(builder.substring(1));
        out.flush();
    }

    static class LCA {
        int[][][] graph;
        int[][] parent;
        int[] parentChar;
        String[][] parentString;
        int[] depth;

        public LCA(int[][][] graph) {
            int n = graph.length;
            this.graph = graph;
            init(n);
        }

        void dfs(int now, int from, int dep, int c) {
            parentChar[now] = c;
            parent[0][now] = from;
            depth[now] = dep;
            for (int[] e : graph[now]) {
                int to = e[0];
                if (to != from) {
                    dfs(to, now, dep+1, e[1]);
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
            parentString = new String[log+1][n];
            parentChar = new int[n];
            depth = new int[n];

            dfs(0, -1, 0, -1);
            for (int i = 0; i < n ; i++) {
                if (parentChar[i] == -1) {
                    parentString[0][i] = "";
                } else {
                    parentString[0][i] = "" + (char)parentChar[i];
                }
            }

            for (int k = 0 ; k < log ; k++) {
                for (int v = 0 ; v < n ; v++) {
                    if (parent[k][v] < 0) {
                        parent[k+1][v] = -1;
                        parentString[k+1][v] = parentString[k][v];
                    } else {
                        int oya = parent[k][v];
                        parent[k+1][v] = parent[k][oya];
                        parentString[k+1][v] = parentString[k][v] + parentString[k][oya];
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



        char lcaOne(int u, int v) {
            int loglen = parent.length;
            int flg = 0;
            if (depth[u] > depth[v]) {
                int tmp = u;
                u = v;
                v = tmp;
                flg = 1;
            }

            char firstLeft = ' ';
            char lastRight = ' ';
            for (int k = 0 ; k < loglen ; k++) {
                if (((depth[v] - depth[u]) >> k) % 2 == 1) {
                    if (flg == 0) {
                        lastRight = parentString[k][v].charAt(parentString[k][v].length()-1);
                    } else {
                        if (firstLeft == ' ') {
                            firstLeft = parentString[k][v].charAt(0);
                        }
                    }
                    v = parent[k][v];
                }
            }

            if (u == v) {
                if (firstLeft == ' ') {
                    return lastRight;
                }
                return firstLeft;
            }

            for (int k = loglen-1 ; k >= 0 ; k--) {
                if (parent[k][u] != parent[k][v]) {
                    if (flg == 0) {
                        if (firstLeft == ' ') {
                            firstLeft = parentString[k][u].charAt(0);
                        }
                    } else {
                        if (firstLeft == ' ') {
                            firstLeft = parentString[k][v].charAt(0);
                        }
                    }
                    u = parent[k][u];
                    v = parent[k][v];
                }
            }
            if (u != v) {
                if (flg == 0) {
                    if (firstLeft == ' ') {
                        firstLeft = parentString[0][u].charAt(0);
                    }
                } else {
                    if (firstLeft == ' ') {
                        firstLeft = parentString[0][v].charAt(0);
                    }
                }
            }
            return firstLeft;
        }

        String lca(String now, int u, int v) {
            int nl = now.length();
            int loglen = parent.length;
            int flg = 0;
            if (depth[u] > depth[v]) {
                int tmp = u;
                u = v;
                v = tmp;
                flg = 1;
            }
            StringBuilder left = new StringBuilder();
            StringBuilder right = new StringBuilder();

            for (int k = 0 ; k < loglen ; k++) {
                if (((depth[v] - depth[u]) >> k) % 2 == 1) {
                    if (flg == 0) {
                        right.append(parentString[k][v]);
                    } else {
                        left.append(parentString[k][v]);
                    }
                    v = parent[k][v];
                }
            }

            if (u == v) {
                return left.append(right.reverse()).toString();
            }

            for (int k = loglen-1 ; k >= 0 ; k--) {
                if (parent[k][u] != parent[k][v]) {
                    if (flg == 0) {
                        left.append(parentString[k][u]);
                        right.append(parentString[k][v]);
                    } else {
                        left.append(parentString[k][v]);
                        right.append(parentString[k][u]);
                    }
                    u = parent[k][u];
                    v = parent[k][v];
                }
            }
            if (u != v) {
                if (flg == 0) {
                    left.append(parentString[0][u]);
                    right.append(parentString[0][v]);
                } else {
                    left.append(parentString[0][v]);
                    right.append(parentString[0][u]);
                }
            }
            return left.append(right.reverse()).toString();
        }
    }


    static int[][][] buildWeightedGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][][] graph = new int[n][][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            char w = in.nextChar();
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
