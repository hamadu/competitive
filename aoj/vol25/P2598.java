package aoj.vol25;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/15.
 */
public class P2598 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            int m = in.nextInt();
            int t = in.nextInt();
            if (n == 0 && m == 0 && t == 0) {
                break;
            }
            int[][] ad = new int[n][3];
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < 3 ; j++) {
                    ad[i][j] = in.nextInt();
                }
            }
            int[][] graph = new int[n][];
            int[] deg = new int[n];
            int[][] edge = new int[m][2];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < 2 ; j++) {
                    edge[i][j] = in.nextInt()-1;
                }
                deg[edge[i][0]]++;
            }
            for (int i = 0; i < n ; i++) {
                graph[i] = new int[deg[i]];
            }
            for (int i = 0; i < m ; i++) {
                int a = edge[i][0];
                int b = edge[i][1];
                graph[a][--deg[a]] = b;
            }
            out.println(solve(ad, graph, t));
        }

        out.flush();
    }

    private static int solve(int[][] ad, int[][] graph, int T) {
        SCC scc = new SCC(graph);
        int[] gids = scc.scc();
        int n = ad.length;
        int gn = 0;
        for (int i = 0; i < n ; i++) {
            gn = Math.max(gn, gids[i]+1);
        }
        boolean[] done = new boolean[n];

        int[][] dp = new int[gn][T+1];
        int[][] dag = grouping(graph, gids);
        int[] topo = toposort(dag);

        for (int gid : topo) {
            int[] gf = new int[n];
            int gi = 0;
            for (int j = 0; j < n ; j++) {
                if (gids[j] == gid) {
                    gf[gi++] = j;
                }
            }
            if (gi == 1) {
                int myID = gf[0];
                boolean hasSelf = false;
                for (int to : graph[myID]) {
                    if (to == myID) {
                        hasSelf = true;
                    }
                }
                if (!hasSelf) {
                    int[] next = new int[T+1];
                    for (int i = 0; i <= T; i++) {
                        next[i] = Math.max(next[i], dp[gid][i]);
                        int bi = i - ad[myID][1];
                        if (0 <= bi) {
                            next[i] = Math.max(next[i], dp[gid][bi] + ad[myID][0]);
                        }
                    }

                    for(int to : dag[gid]){
                        for (int i = 0; i <= T; i++) {
                            dp[to][i] = Math.max(dp[to][i], next[i]);
                        }
                    }
                    for(int i = 0 ; i <= T ; i++){
                        dp[gid][i] = next[i];
                    }
                    continue;
                }
            }

            int[] deqIdx = new int[T+1];
            int[] deqVal = new int[T+1];
            for (int i = 0; i < gi ; i++) {
                int vi = gf[i];
                int v = ad[vi][0];
                int w = ad[vi][1];
                int m = ad[vi][2];
                for (int a = 0 ; a < w ; a++) {
                    int s = 0;
                    int t = 0;
                    for (int j = 0; j * w + a <= T ; j++) {
                        int val = dp[gid][j * w + a] - j * v;
                        while (s < t && deqVal[t-1] <= val) {
                            t--;
                        }
                        deqIdx[t] = j;
                        deqVal[t] = val;
                        t++;
                        dp[gid][j * w + a] = Math.max(dp[gid][j * w + a], deqVal[s] + j * v);
                        if (deqIdx[s] == j - m) {
                            s++;
                        }
                    }
                }
            }

            for(int to : dag[gid]){
                for (int i = 0; i <= T; i++) {
                    dp[to][i] = Math.max(dp[to][i], dp[gid][i]);
                }
            }
        }

        int maxScore = 0;
        for (int i = 0; i < gn; i++) {
            for (int j = 0; j <= T; j++) {
                maxScore = Math.max(maxScore, dp[i][j]);
            }
        }
        return maxScore;
    }

    static int[] toposort(int[][] graph) {
        int n = graph.length;
        int[] in = new int[n];
        for (int i = 0 ; i < n ; i++) {
            for (int t : graph[i]) {
                in[t]++;
            }
        }

        int[] res = new int[n];
        int idx = 0;
        for (int i = 0 ; i < n ; i++) {
            if (in[i] == 0) {
                res[idx++] = i;
            }
        }
        for (int i = 0 ; i < idx ; i++) {
            for (int t : graph[res[i]]) {
                in[t]--;
                if (in[t] == 0) {
                    res[idx++] = t;
                }
            }
        }
        for (int i = 0 ; i < n ; i++) {
            if (in[i] >= 1) {
                return null;
            }
        }
        return res;
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
                if (groups[i] != groups[j]) {
                    int gfr = groups[i];
                    int gto = groups[j];
                    long eid = ((1L*gfr)<<30)+gto;
                    if (done.contains(eid)) {
                        continue;
                    }
                    done.add(eid);

                    edges.add(gfr);
                    edges.add(gto);
                    deg[gfr]++;
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
        }
        return groupedGraph;
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
