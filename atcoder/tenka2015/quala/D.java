package atcoder.tenka2015.quala;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2015/11/22.
 */
public class D {
    private static final int INF = 114514;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int _n = in.nextInt();
        int _m = in.nextInt();
        int[][] _graph = buildGraph(in, _n, _m);
        int[] dec = decompose(_graph);
        vec = new int[_n];
        for (int i = 0 ; i < dec.length ; i++) {
            vec[dec[i]]++;
        }
        graph = grouping(_graph, decompose(_graph));
        ccnt = new int[_n];
        lcnt = new int[_n];
        dfs(0, -1);
        children(0, -1);

        solve(0, -1);

        if (ans == INF) {
            out.println("IMPOSSIBLE");
        } else {
            out.println(ans);
        }
        out.flush();
    }

    static int[][] graph;
    static int[] vec;
    static int ans = INF;

    static int[] ccnt;
    static int[] lcnt;

    static void solve(int now, int par) {
        if (par != -1) {
            int left = lcnt[now];
            int right = lcnt[0] - left;
            if (graph[0].length == 1) {
                right++;
            }
            int lc = ccnt[now];
            int rc = ccnt[0] - lc;

            if (graph[now].length == 2) {
                left++;
            } else if (graph[now].length == 1) {
                left = 0;
            }
            if (graph[par].length == 2) {
                right++;
            } else if (graph[par].length == 1) {
                right = 0;
            }
            if (lc == 2) {
                left = INF * 10;
            }
            if (rc == 2) {
                right = INF * 10;
            }
            ans = Math.min(ans, (left+1)/2+(right+1)/2);
        }
        for (int to : graph[now]) {
            if (to != par) {
                solve(to, now);
            }
        }
    }

    static int children(int now, int par) {
        int total = vec[now];
        for (int to : graph[now]) {
            if (to != par) {
                total += children(to, now);
            }
        }
        ccnt[now] = total;
        return total;
    }

    static int dfs(int now, int par) {
        int cnt = 0;
        boolean flg = false;
        for (int to : graph[now]) {
            if (to != par) {
                flg = true;
                cnt += dfs(to, now);
            }
        }
        if (!flg) {
            cnt++;
        }
        lcnt[now] = cnt;
        return cnt;
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
            int a = in.nextInt();
            int b = in.nextInt();
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
