package atcoder.arc.arc030;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class C {
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

        public int[][] groupGraph() {
            int max = 0;
            for (int nid : node_id) {
                max = Math.max(max, nid+1);
            }
            Set<Integer>[] eset = new Set[max];
            List<Integer>[] ged = new List[max];
            for (int i = 0; i < max; i++) {
                eset[i] = new HashSet<>();
                ged[i] = new ArrayList<>();
            }
            int[][] groupGraph = new int[max][];
            for (int fr = 0 ; fr < graph.length ; fr++) {
                for (int to : graph[fr]) {
                    int g1 = node_id[fr];
                    int g2 = node_id[to];
                    if (g1 != g2 && !eset[g1].contains(g2)) {
                        eset[g1].add(g2);
                        ged[g1].add(g2);
                    }
                }
            }
            for (int i = 0 ; i < max ; i++) {
                groupGraph[i] = new int[ged[i].size()];
                int idx = 0;
                for (int to : ged[i]) {
                    groupGraph[i][idx++] = to;
                }
            }
            return groupGraph;
        }
    }

    public static class State implements Comparable<State> {
        int gi;
        String x;

        State(int gi, String x) {
            this.gi = gi;
            this.x = x;
        }

        @Override
        public int compareTo(State o) {
            return x.compareTo(o.x);
        }
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        char[] c = new char[n];
        for (int i = 0; i < n; i++) {
            c[i] = in.nextChar();
        }
        int[][] graph = buildDirectedGraph(in, n, m);

        SCC scc = new SCC(graph);
        scc.scc();
        int[][] groups = scc.groups();
        int[][] groupGraph = scc.groupGraph();
        int gn = groups.length;
        String[] gs = new String[gn];
        for (int i = 0 ; i < gn ; i++) {
            gs[i] = "";
            for (int v : groups[i]) {
                gs[i] += c[v];
            }
            char[] x = gs[i].toCharArray();
            Arrays.sort(x);
            gs[i] = String.valueOf(x);
        }

        String best = "~";
        String[][] dp = new String[gn][k+1];
        for (int i = 0 ; i < gn ; i++) {
            Arrays.fill(dp[i], "~");
        }
        Queue<State> q = new PriorityQueue<>();
        for (int i = 0 ; i < gn ; i++) {
            dp[i][0] = "";
            q.add(new State(i, ""));
        }

        // debug(groupGraph, gs);

        while (q.size() >= 1) {
            State s = q.poll();
            int now = s.gi;


            int len = gs[now].length();
            for (int take = 0 ; take <= len ; take++) {
                String tx = s.x + gs[now].substring(0, take);
                int tl = tx.length();
                if (tl > k) {
                    break;
                }
                if (tl == k && tx.compareTo(best) < 0) {
                    best = tx;
                }
                for (int to : groupGraph[now]) {
                    if (tx.compareTo(dp[to][tl]) < 0) {
                        dp[to][tl] = tx;
                        q.add(new State(to, tx));
                    }
                }
            }
        }
        out.println(best.equals("~") ? -1 : best);
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



