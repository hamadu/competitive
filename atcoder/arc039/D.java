package atcoder.arc039;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/05/16.
 */
public class D {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        int[][] inputGraph = buildGraph(in, n, m);
        int[] groups = decompose(inputGraph);
        int[][] tree = grouping(inputGraph, groups);

        LCA lca = new LCA(tree);

        int q = in.nextInt();
        while (--q >= 0) {
            int a = groups[in.nextInt()-1];
            int b = groups[in.nextInt()-1];
            int c = groups[in.nextInt()-1];
            if (lca.dist(a, b) + lca.dist(b, c) == lca.dist(a, c)) {
                out.println("OK");
            } else {
                out.println("NG");
            }
        }

        out.flush();
    }

    private static int[][] grouping(int[][] graph, int[] groups) {
        int n = graph.length;
        int gn = 0;

        for (int g : groups) {
            gn = Math.max(gn, g);
        }

        Set<Long> done = new HashSet<>();
        List<Integer> edges = new ArrayList<>();
        int[] deg = new int[gn+1];
        for (int i = 0 ; i < n ; i++) {
            for (int j : graph[i]) {
                if (groups[i] != groups[j] && i < j) {
                    int gfr = Math.min(groups[i], groups[j]);
                    int gto = Math.max(groups[i], groups[j]);
                    long eid = ((1L*gfr)<<30)+gto;
                    if (done.contains(eid)) {
                        continue;
                    }
                    done.add(eid);

                    edges.add(gfr);
                    edges.add(gto);
                    deg[gfr]++;
                    deg[gto]++;
                }
            }
        }

        int[][] groupedGraph = new int[gn+1][];
        for (int i = 0 ; i <= gn ; i++) {
            groupedGraph[i] = new int[deg[i]];
        }
        int en = edges.size();
        for (int i = 0 ; i < en ; i += 2) {
            int u = edges.get(i);
            int v = edges.get(i+1);
            groupedGraph[u][--deg[u]] = v;
            groupedGraph[v][--deg[v]] = u;
        }
        return groupedGraph;
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

        void dfs(int now, int from, int dep) {
            parent[0][now] = from;
            depth[now] = dep;
            for (int to : graph[now]) {
                if (to != from) {
                    dfs(to, now, dep+1);
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

            dfs(0, -1, 0);

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

    /**
     * 二重辺連結成分分解(by uwitenpen)
     *
     * @param graph
     * @return
     */
    static int[] decompose(int[][] graph) {
        int n = graph.length;
        boolean[] visited = new boolean[n];
        int[] ord = new int[n];
        int[] low = new int[n];

        int[] ids = new int[n];
        int[] inds = new int[n];
        int[] parct = new int[n];
        int pos = 0;
        for (int i = 0 ; i < n ; i++) {
            if (visited[i]) {
                continue;
            }
            ids[i] = i;
            inds[0] = 0;
            int sp = 1;
            while (sp > 0) {
                int cur = ids[sp-1];
                if (inds[sp-1] == 0) {
                    visited[cur] = true;
                    ord[cur] = low[cur] = pos++;
                    parct[sp-1] = 0;
                }
                if (inds[sp-1] == graph[cur].length) {
                    if(sp-2 >= 0) {
                        low[ids[sp-2]] = Math.min(low[ids[sp-2]], low[cur]);
                    }
                    sp--;
                    continue;
                }
                int next = graph[cur][inds[sp-1]];
                if (!visited[next]){
                    ids[sp] = next;
                    inds[sp] = 0;
                    inds[sp-1]++;
                    sp++;
                    continue;
                } else if (sp-2 >= 0 && (next != ids[sp-2] || ++parct[sp-1] >= 2)){
                    low[cur] = Math.min(low[cur], ord[next]);
                }
                inds[sp-1]++;
            }
        }

        int[] clus = new int[n];
        Arrays.fill(clus, -1);
        int[] q = new int[n];
        int cnum = 0;
        for (int i = 0 ; i < n ; i++){
            if (clus[i] == -1){
                int p = 0;
                q[p++] = i;
                clus[i] = cnum++;
                for(int r = 0;r < p;r++){
                    int cur = q[r];
                    for(int next : graph[cur]){
                        if(clus[next] == -1){
                            clus[next] = ord[cur] < low[next] ? cnum++ : clus[cur];
                            q[p++] = next;
                        }
                    }
                }
            }
        }
        return clus;
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
