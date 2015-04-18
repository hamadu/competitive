package codeforces.cr286;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class B {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        UnionFind uf = new UnionFind(n);
        int[][] graph = buildDirectedGraph(in, n, m);
        for (int i = 0 ; i < n ; i++) {
            for (int j : graph[i]) {
                uf.unite(i, j);
            }
        }

        int ans = n;
        boolean[] isok = new boolean[n];
        Arrays.fill(isok, true);

        SCC scc = new SCC(graph);
        scc.scc();
        for (int[] g : scc.groups()) {
            if (g.length >= 2) {
                int head = g[0];
                int comp = uf.find(head);
                isok[comp] = false;
            }
        }

        boolean[] done = new boolean[n];
        for (int i = 0 ; i < n ; i++) {
            int g = uf.find(i);
            if (!done[g]) {
                done[g] = true;
                if (isok[g]) {
                    ans--;
                }
            }
        }

        out.println(ans);
        out.flush();
    }

    static int[][] buildDirectedGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            deg[a]++;
            edges[i] = new int[]{a, b};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
        }
        return graph;
    }

    static class SCC {
        boolean[] visited;
        int[] node_id;
        List<Integer> rev;

        int n;
        int[][] graph;
        int[][] r_graph;

        SCC(int[][] g) {
            n = g.length;
            graph = g;
            r_graph = new int[n][];
            int[] deg = new int[n];
            for (int i = 0 ; i < n ; i++) {
                for (int j : graph[i]) {
                    deg[j]++;
                }
            }
            for (int i = 0 ; i < n ; i++) {
                r_graph[i] = new int[deg[i]];
            }
            for (int i = 0 ; i < n ; i++) {
                for (int j : graph[i]) {
                    r_graph[j][--deg[j]] = i;
                }
            }
        }

        int[] scc() {
            visited = new boolean[n];
            rev = new ArrayList<Integer>();
            for (int i = 0; i<n; i++) {
                if (!visited[i]) {
                    dfs(i);
                }
            }
            int id = 0;
            node_id = new int[n];
            visited = new boolean[n];
            for (int i = rev.size()-1; i>=0; i--) {
                if (!visited[rev.get(i)]) {
                    rdfs(rev.get(i), id);
                    id++;
                }
            }
            return node_id;
        }

        int[][] groups() {
            int max = 0;
            for (int nid : node_id) {
                max = Math.max(max, nid+1);
            }
            int[] gnum = new int[max];
            for (int nid : node_id) {
                gnum[nid]++;
            }
            int[][] groups = new int[max][];
            for (int i = 0 ; i < max ; i++) {
                groups[i] = new int[gnum[i]];
            }
            for (int i = 0 ; i < n ; i++) {
                int nid = node_id[i];
                groups[nid][--gnum[nid]] = i;
            }
            return groups;
        }

        public void dfs(int i) {
            visited[i] = true;
            for (int next : graph[i]) {
                if (!visited[next]) {
                    dfs(next);
                }
            }
            rev.add(i);
        }

        public void rdfs(int i, int id) {
            visited[i] = true;
            node_id[i] = id;
            for (int next : r_graph[i]) {
                if (!visited[next]) {
                    rdfs(next, id);
                }
            }
        }
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



