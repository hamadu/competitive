package aoj.vol23;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/13.
 */
public class P2338 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        Map<String,Integer> wordMap = new HashMap<String,Integer>();
        for (int i = 0; i < n ; i++) {
            wordMap.put(in.nextToken(), wordMap.size());
        }
        int[][] edges = new int[m][2];
        for (int i = 0; i < m ; i++) {
            edges[i][0] = wordMap.get(in.nextToken());
            edges[i][1] = wordMap.get(in.nextToken());
        }
        int[][] graph = buildGraph(n, edges);
        out.println(solve(graph));
        out.flush();
    }

    private static long solve(int[][] graph) {
        int n = graph.length;
        int[] gid = decompose(graph);

        G = grouping(graph, gid);
        int gn = G.length;
        gw = new int[gn];
        for (int i = 0; i < n ; i++) {
            gw[gid[i]]++;
        }
        children = new int[gn];

        dp = new int[gn][n+1][n+1];
        for (int i = 0 ; i < gn ; i++) {
            for (int j = 0; j <= n; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        maxDP  = new int[2][gn][n+1];
        for (int i = 0; i < gn ; i++) {
            Arrays.fill(maxDP[0][i], -1);
            Arrays.fill(maxDP[1][i], -1);
        }
        N = n;

        dfs(0, -1);

        int ans = 0;
        for (int i = 0 ; i <= n ; i++) {
            for (int j = 0; j <= n; j++) {
                ans = Math.max(ans, dp[0][i][j]);
            }
        }
        return ans;
    }

    static int N;
    static int[][] G;
    static int[] gw;
    static int[] children;

    static int[][][] dp;

    static int[][][] maxDP;

    static void dfs(int now, int par) {
        dp[now][gw[now]][gw[now]] = gw[now] * gw[now];

        int sz = gw[now];
        for (int to : G[now]) {
            if (to == par) {
                continue;
            }
            dfs(to, now);
            for (int out = sz ; out >= 0 ; out--) {
                for (int in = sz ; in >= 0 ; in--) {
                    if (dp[now][out][in] == -1) {
                        continue;
                    }
                    int base = dp[now][out][in];
                    for (int k = children[to] ; k >= 0 ; k--) {
                        if (maxDP[0][to][k] >= 1 && out+k <= N) {
                            dp[now][out+k][in] = Math.max(dp[now][out+k][in], base + maxDP[0][to][k] + in * k);
                        }
                        if (maxDP[1][to][k] >= 1 && in+k <= N) {
                            dp[now][out][in+k] = Math.max(dp[now][out][in+k], base + maxDP[1][to][k] + out * k);
                        }
                    }
                }
            }
            sz += children[to];
        }
        children[now] = sz;

        for (int i = 0 ; i <= sz ; i++) {
            for (int j = 0; j <= sz; j++) {
                maxDP[0][now][i] = Math.max(maxDP[0][now][i], dp[now][i][j]);
                maxDP[1][now][j] = Math.max(maxDP[0][now][j], dp[now][i][j]);
            }
        }
    }

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

    private static int[][] grouping(int[][] graph, int[] groups) {
        int n = graph.length;
        int gn = 0;

        for (int g : groups) {
            gn = Math.max(gn, g);
        }

        Set<Long> done = new HashSet<Long>();
        List<Integer> edges = new ArrayList<Integer>();
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

    static int[][] buildGraph(int n, int[][] edges) {
        int m = edges.length;
        int[] deg = new int[n];
        int[][] graph = new int[n][];
        for (int i = 0; i < m; i++) {
            deg[edges[i][0]]++;
            deg[edges[i][1]]++;
        }
        for (int i = 0; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0; i < m ; i++) {
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
