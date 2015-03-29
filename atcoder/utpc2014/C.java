package atcoder.utpc2014;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class C {
    static List<Integer>[] graph;
    static int[] col;

    static void dfs(int now, int parent, int l) {
        col[now] = l;
        for (int to : graph[now]) {
            if (to != parent) {
                dfs(to, now, 1-l);
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);


        int n = in.nextInt();
        int[][] edge = new int[n][2];
        int[] deg = new int[n];
        for (int i = 0; i < n; i++) {
            edge[i][0] = in.nextInt()-1;
            edge[i][1] = in.nextInt()-1;
            deg[edge[i][0]]++;
            deg[edge[i][1]]++;
        }

        graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        col = new int[n];

        UnionFind uf = new UnionFind(n);
        int p1 = -1, p2 = -1;
        for (int i = 0; i < n; i++) {
            if (!uf.issame(edge[i][0], edge[i][1])) {
                uf.unite(edge[i][0], edge[i][1]);
                graph[edge[i][0]].add(edge[i][1]);
                graph[edge[i][1]].add(edge[i][0]);
            } else {
                p1 = edge[i][0];
                p2 = edge[i][1];
            }
        }
        dfs(0, -1, 0);

        int min = n;
        for (int i = 0; i < n; i++) {
            min = Math.min(min, deg[i]);
        }
        int max = n;
        if (col[p1] == col[p2]) {
            max--;
        }
        out.println(min + " " + max);
        out.flush();
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



