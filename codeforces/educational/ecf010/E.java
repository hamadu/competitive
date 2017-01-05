package codeforces.educational.ecf010;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[][] flg = in.nextIntTable(m, 3);
        int[][] edges = new int[m][2];
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < 2 ; j++) {
                flg[i][j]--;
                edges[i][j] = flg[i][j];
            }
        }
        int[][] graph = buildGraph(n, edges);
        int[] gids = SCC2.decompose(graph);
        int[][] tree = SCC2.grouping(graph, gids);

        int start = gids[in.nextInt()-1];
        int goal = gids[in.nextInt()-1];

        int[] par = new int[n];
        int[] que = new int[n];
        par[start] = -1;
        int qh = 0;
        int qt = 0;
        que[qh++] = start;
        while (qt < qh) {
            int now = que[qt++];
            for (int to : tree[now]) {
                if (to != par[now]) {
                    par[to] = now;
                    que[qh++] = to;
                }
            }
        }

        boolean[] passes = new boolean[n];
        passes[start] = true;
        while (goal != start) {
            passes[goal] = true;
            int from = par[goal];
            goal = from;
        }

        boolean isOK = false;
        for (int i = 0 ; i < m ; i++) {
            if (flg[i][2] == 1) {
                int a = gids[flg[i][0]];
                int b = gids[flg[i][1]];
                if (passes[a] && passes[b]) {
                    isOK = true;
                }
            }
        }

        out.println(isOK ? "YES" : "NO");
        out.flush();
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


    /**
     * Decompose graphs into component-bridge based tree.
     */
    public static class SCC2 {
        public static int[] decompose(int[][] graph) {
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

        public static int[][] grouping(int[][] graph, int[] groups) {
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
                        long eid = (((long) gfr)<<30)+gto;
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
    }


    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int[] nextInts(int n) {
            int[] ret = new int[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextInt();
            }
            return ret;
        }

        private int[][] nextIntTable(int n, int m) {
            int[][] ret = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextInt();
                }
            }
            return ret;
        }

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
            }
            return ret;
        }

        private long[][] nextLongTable(int n, int m) {
            long[][] ret = new long[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextLong();
                }
            }
            return ret;
        }

        private double[] nextDoubles(int n) {
            double[] ret = new double[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextDouble();
            }
            return ret;
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
            return res*sgn;
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
            return res*sgn;
        }

        public double nextDouble() {
            return Double.valueOf(nextToken());
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
