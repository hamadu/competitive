package codechef.long201505;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/05/09.
 */
public class SEZ {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[][][] graph = splitIntoGroup(buildGraph(in, n, m));

        int sum = 0;
        for (int[][] g : graph) {
            sum += solve(g);
        }
        out.println(sum);
        out.flush();
    }

    static int solve(int[][] graph) {
        int n = graph.length;
        int[][] degv = new int[n][2];
        for (int i = 0 ; i < n ; i++) {
            degv[i][0] = i;
            for (int j : graph[i]) {
                degv[i][1]++;
            }
        }
        Arrays.sort(degv, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[1] - o1[1];
            }
        });

        int max = 0;
        int tr = Math.min(16, n);
        for (int ptn = 0 ; ptn < (1<<tr) ; ptn++) {
            boolean[] removed = new boolean[n];
            for (int i = 0 ; i < tr ; i++) {
                if ((ptn & (1<<i)) >= 1) {
                    removed[degv[i][0]] = true;
                }
            }
            max = Math.max(max, solve(removed, graph));
        }
        return max;
    }

    public static int solve(boolean[] removed, int[][] graph) {
        int sum = 0;
        for (boolean b : removed) {
            if (b) {
                sum--;
            }
        }

        int n = graph.length;
        while (true) {
            boolean updated = false;
            for (int u = 0 ; u < n ; u++) {
                if (removed[u]) {
                    continue;
                }
                int deg = 0;
                int uv = -1;
                for (int v : graph[u]) {
                    if (!removed[v]) {
                        deg++;
                        uv = v;
                    }
                }
                if (deg <= 1) {
                    updated = true;
                    sum += 1 - deg;
                    removed[u] = true;
                    if (uv != -1) {
                        removed[uv] = true;
                    }
                }
            }
            if (!updated) {
                break;
            }
        }
        return sum;
    }

    static int[][][] splitIntoGroup(int[][] graph) {
        int n = graph.length;
        UnionFind uf = new UnionFind(n);
        for (int i = 0 ; i < n ; i++) {
            for (int j : graph[i]) {
                uf.unite(i, j);
            }
        }

        Set<Integer> gset = new HashSet<>();
        for (int i = 0 ; i < n ; i++) {
            gset.add(uf.find(i));
        }
        int gn = gset.size();
        int[][][] groupedGraph = new int[gn][][];
        int[] map = new int[n];
        int[] rmap = new int[n];
        int gid = 0;
        Arrays.fill(map, -1);
        for (int i = 0 ; i < n ; i++) {
            if (map[i] != -1) {
                continue;
            }
            int group = uf.find(i);
            int vid = 0;

            for (int j = 0 ; j < n ; j++) {
                if (uf.find(j) == group) {
                    map[j] = vid;
                    rmap[vid] = j;
                    vid++;
                }
            }

            int[][] subgraph = new int[vid][];
            for (int j = 0 ; j < vid ; j++) {
                int baseId = rmap[j];
                subgraph[j] = graph[baseId].clone();
                for (int k = 0 ; k < graph[baseId].length ; k++) {
                    subgraph[j][k] = map[graph[baseId][k]];
                }
            }
            groupedGraph[gid] = subgraph;
            gid++;
        }

        return groupedGraph;


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

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
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
}
