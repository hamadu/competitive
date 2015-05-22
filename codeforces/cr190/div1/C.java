package codeforces.cr190.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/05/21.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] graph = buildGraph(in, n, n-1);

        DecomposeToCenteroid ct = new DecomposeToCenteroid(graph);
        ct.doit(0, 0);
        ct.buildCenteroidTree();

        cgraph = ct.centeroidTree;
        ans = new char[n];
        dfs(ct.centeroidRoot, -1, 0);

        StringBuilder st = new StringBuilder();
        for (char c : ans) {
            st.append(' ').append(c);
        }
        out.println(st.substring(1));
        out.flush();
    }

    static char[] ans;
    static int[][] cgraph;

    static void dfs(int now, int parent, int depth) {
        ans[now] = (char)('A' + depth);
        for (int to : cgraph[now]) {
            if (to != parent) {
                dfs(to, now, depth+1);
            }
        }
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
