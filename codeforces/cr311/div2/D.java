package codeforces.cr311.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/10.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        graph = buildGraph(in, n, m);

        long[] ret = solve(n, m);
        out.println(ret[0] + " " + ret[1]);
        out.flush();
    }

    private static long[] solve(int n, int m) {
        if (m == 0) {
            long ways = 1L * n * (n - 1) * (n - 2) / 6;
            return new long[]{3, ways};
        }

        color = new int[n];
        for (int i = 0 ; i < n ; i++) {
            if (color[i] == 0) {
                dfs(i, -1, 1);
            }
        }
        if (!isBiperate) {
            return new long[]{0, 1};
        }

        UnionFind uf = new UnionFind(n);
        for (int i = 0 ; i < n ; i++) {
            for (int j : graph[i]) {
                uf.unite(i, j);
            }
        }
        List<Integer>[] groups = new List[n];
        for (int i = 0 ; i < n ; i++) {
            groups[i] = new ArrayList<>();
        }
        for (int i = 0 ; i < n ; i++) {
            groups[uf.find(i)].add(i);
        }

        int maxG = 0;
        for (int i = 0 ; i < n ; i++) {
            maxG = Math.max(maxG, groups[i].size());
        }
        if (maxG >= 3) {
            long ptn = 0;
            for (int i = 0 ; i < n ; i++) {
                if (groups[i].size() >= 3) {
                    long w = 0;
                    long b = 0;
                    for (int k : groups[i]) {
                        if (color[k] == 1) {
                            w++;
                        } else {
                            b++;
                        }
                    }
                    ptn += w * (w - 1) / 2 + b * (b - 1) / 2;
                }
            }
            return new long[]{1, ptn};
        } else {
            long ptn = 0;
            long single = 0;
            long pair = 0;
            for (int i = 0 ; i < n ; i++) {
                int k = groups[i].size();
                if (k == 1) {
                    single++;
                } else if (k == 2) {
                    pair++;
                }
            }
            ptn += single * pair;
            ptn += pair * (pair - 1) * 2;
            return new long[]{2, ptn};
        }
    }

    private static void dfs(int now, int par, int col) {
        if (color[now] != 0) {
            isBiperate &= color[now] == col;
            return;
        }
        color[now] = col;
        for (int to : graph[now]) {
            if (to != par) {
                dfs(to, now, -col);
            }
        }
    }

    static boolean isBiperate = true;

    static int[] color;

    static int[][] graph;

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
