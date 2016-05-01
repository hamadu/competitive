package atcoder.other2014.codefestival2014.finale;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 5/1/16.
 */
public class I {

    public static final int SHAPE_IN = 0;
    public static final int SHAPE_OUT = 1;
    public static final int QUERY_POINT = 2;

    static class Shape implements Comparable<Shape> {
        int idx;
        int upperY;
        int lowerIdx;

        @Override
        public int compareTo(Shape o) {
            if (upperY == o.upperY) {
                return idx - o.idx;
            }
            return upperY - o.upperY;
        }

        public Shape(int id, int y, int low) {
            idx = id;
            upperY = y;
            lowerIdx = low;
        }
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] shape = new int[n][3];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 3 ; j++) {
                shape[i][j] = in.nextInt();
            }
        }
        int m = in.nextInt();
        int[][] queries = new int[m][4];
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < 4 ; j++) {
                queries[i][j] = in.nextInt();
            }
        }
        for (int i = 0; i < n ; i++) {
            int tx = shape[i][0] + shape[i][1];
            int ty = shape[i][0] - shape[i][1];
            shape[i][0] = tx;
            shape[i][1] = ty;
        }
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < 2 ; j++) {
                int tx = queries[i][j*2] + queries[i][j*2+1];
                int ty = queries[i][j*2] - queries[i][j*2+1];
                queries[i][j*2] = tx;
                queries[i][j*2+1] = ty;
            }
        }

        int[][] events = new int[2*n+2*m][3];
        for (int i = 0; i < n ; i++) {
            // shape in
            events[i*2][0] = shape[i][0] - shape[i][2];
            events[i*2][1] = i;
            events[i*2][2] = SHAPE_IN;

            // out
            events[i*2+1][0] = shape[i][0] + shape[i][2];
            events[i*2+1][1] = i;
            events[i*2+1][2] = SHAPE_OUT;
        }
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < 2 ; j++) {
                // points
                events[n*2+i*2+j][0] = queries[i][j*2];
                events[n*2+i*2+j][1] = i*2+j;
                events[n*2+i*2+j][2] = QUERY_POINT;
            }
        }

        Arrays.sort(events, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        TreeSet<Shape> outerShapes = new TreeSet<>();

        int[][] edges = new int[n][2];
        int[][] queryV = new int[m][2];
        int ei = 0;

        for (int i = 0; i < events.length ; i++) {
            int idx =  events[i][1];
            if (events[i][2] == SHAPE_IN) {
                Shape headShape = new Shape(idx, shape[idx][1] - shape[idx][2], -1);
                int parentID = n;
                if (outerShapes.size() >= 1) {
                    Shape ps = outerShapes.lower(headShape);
                    if (ps != null) {
                        parentID = ps.lowerIdx;
                    }
                }
                edges[ei][0] = idx;
                edges[ei][1] = parentID;
                ei++;
                outerShapes.add(new Shape(idx, shape[idx][1] - shape[idx][2], idx));
                outerShapes.add(new Shape(idx, shape[idx][1] + shape[idx][2], parentID));
            } else if (events[i][2] == SHAPE_OUT) {
                outerShapes.remove(new Shape(idx, shape[idx][1] - shape[idx][2], -1));
                outerShapes.remove(new Shape(idx, shape[idx][1] + shape[idx][2], -1));
            } else {
                int qid = idx / 2;
                int whc = idx % 2;
                Shape headPoint = new Shape(-1, queries[qid][whc*2+1], -1);
                int parentID = n;
                if (outerShapes.size() >= 1) {
                    Shape ps = outerShapes.lower(headPoint);
                    if (ps != null) {
                        parentID = ps.lowerIdx;
                    }
                }
                queryV[qid][whc] = parentID;
            }
        }


        int[][] graph = buildGraph(n+1, edges);
        // debug(graph);
        LCA lca = new LCA(graph);

        for (int i = 0; i < m ; i++) {
            out.println(lca.dist(queryV[i][0], queryV[i][1]));
        }
        out.flush();
    }

    static class LCA {
        int[][] graph;
        int[][] parent;
        int[] depth;

        public LCA(int[][] graph) {
            int n = graph.length;
            this.graph = graph;
            init(n, n-1);
        }

        void buildDepth(int root) {
            int[] que = new int[graph.length*3+10];
            int qh = 0;
            int qt = 0;
            que[qh++] = root;
            que[qh++] = -1;
            que[qh++] = 0;
            depth[root] = 0;
            parent[0][root] = -1;
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

        void init(int n, int root) {
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
            buildDepth(root);

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
