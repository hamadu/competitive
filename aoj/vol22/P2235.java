package aoj.vol22;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/23.
 */
public class P2235 {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[][] q = new int[m][3];
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < 3; j++) {
                q[i][j] = in.nextInt();
            }
        }

        final int D = 200;
        Map<Integer,Integer>[] graph = new Map[n];
        subGraph = new Set[n];
        for (int i = 0; i < n ; i++) {
            graph[i] = new HashMap<Integer,Integer>();
            subGraph[i] = new HashSet<Integer>();
        }

        UnionFind uf = new UnionFind(n);
        for (int qi = 0; qi < m ; qi++) {
            if (qi % D == 0) {
                // build!
                int fi = qi;
                int ti = Math.min(qi + D, m);
                boolean need = false;
                int ei = 0;
                for (int t = fi ; t < ti; t++) {
                    if (q[t][0] == 3) {
                        need = true;
                    }
                }
                if (need) {
                    for (int i = 0; i < n; i++) {
                        for (int j : graph[i].keySet()) {
                            if (i < j) {
                                edges[ei][0] = i;
                                edges[ei][1] = j;
                                ei++;
                            }
                        }
                    }
                }

                for (int t = fi ; t < ti; t++) {
                    if (q[t][0] == 1) {
                        graph[q[t][1]].put(q[t][2], qi);
                        graph[q[t][2]].put(q[t][1], qi);
                    } else if (q[t][0] == 2) {
                        graph[q[t][1]].remove(q[t][2]);
                        graph[q[t][2]].remove(q[t][1]);
                    }
                }

                if (need) {
                    uf.clear();
                    for (int i = 0; i < n ; i++) {
                        for (int to : graph[i].keySet()) {
                            if (graph[i].get(to) < qi) {
                                uf.unite(i, to);
                            }
                        }
                    }
                    for (int i = 0; i < n ; i++) {
                        subGraph[i].clear();
                    }
                    for (int i = 0; i < ei ; i++) {
                        int a = uf.find(edges[i][0]);
                        int b = uf.find(edges[i][1]);
                        subGraph[a].add(b);
                        subGraph[b].add(a);
                    }
                }
            }

            int type = q[qi][0];
            int a = uf.find(q[qi][1]);
            int b = uf.find(q[qi][2]);
            if (type == 1) {
                subGraph[a].add(b);
                subGraph[b].add(a);
            } else if (type == 2) {
                subGraph[b].remove(a);
                subGraph[a].remove(b);
            } else {
                out.println(doit(qi+1, a, b) ? "YES" : "NO");
            }

        }



        out.flush();
    }

    static Set<Integer>[] subGraph;
    static int[] visited = new int[100000];
    static int[] _que = new int[100000];
    static int[][] edges = new int[100000][2];

    static boolean doit(int qid, int a, int b) {
        if (a == b) {
            return true;
        }
        int qh = 0;
        int qt = 0;
        _que[qh++] = a;
        visited[a] = qid;
        while (qt < qh) {
            int now = _que[qt++];
            for (int to : subGraph[now]) {
                if (to == b) {
                    return true;
                }
                if (visited[to] != qid) {
                    visited[to] = qid;
                    _que[qh++] = to;
                }
            }
        }
        return false;
    }

    static class UnionFind {
        int[] parent, rank;
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            clear();
        }

        int find(int x) {
            if (parent[x] == x) {
                return x;
            }
            parent[x] = find(parent[x]);
            return parent[x];
        }

        void clear() {
            int n = parent.length;
            for (int i = 0 ; i < n ; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
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
