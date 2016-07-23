package codeforces.cf3xx.cf364.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/07/23.
 */
public class C {
    private static final long INF = (long)1e16;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int S = in.nextInt()-1;
        int T = in.nextInt()-1;
        Edge[] edges = new Edge[m];
        for (int i = 0; i < m ; i++) {
            edges[i] = new Edge(i, in.nextInt()-1, in.nextInt()-1, in.nextInt());
        }
        graph = new List[n];
        for (int i = 0; i < n ; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < m ; i++) {
            Edge e = edges[i];
            graph[e.a].add(e);
            graph[e.b].add(e);
        }
        ec = new int[n][n];
        for (int i = 0; i < n ; i++) {
            for (Edge e : graph[i]) {
                if (i < e.to(i)) {
                    ec[i][e.to(i)]++;
                    ec[e.to(i)][i]++;
                }
            }
        }

        List<Edge> specialEdges = gogo(S, T);
        if (specialEdges == null) {
            out.println(0);
            out.println(0);
            out.flush();
            return;
        }
        for (Edge e : specialEdges) {
            e.special = 1;
        }


        long bestCost = INF;
        List<Edge> bestEdges = new ArrayList<>();
        for (Edge e : specialEdges) {
            toggleEdge(e);

            if (!isConnected(S, T)) {
                if (bestCost > e.w) {
                    bestCost = e.w;
                    bestEdges.clear();
                    bestEdges.add(e);
                }
                toggleEdge(e);
                continue;
            }

            Edge secondE = lowlinkAndFind(S, T);
            if (secondE == null) {
                toggleEdge(e);
                continue;
            }

            long sum = e.w + secondE.w;
            if (bestCost > sum) {
                bestCost = sum;
                bestEdges.clear();
                bestEdges.add(e);
                bestEdges.add(secondE);
            }
            toggleEdge(e);
        }

        if (bestCost == INF) {
            out.println(-1);
        } else {
            long sum = 0;
            StringBuilder ids = new StringBuilder();
            for (Edge e : bestEdges) {
                sum += e.w;
                ids.append(' ').append(e.id+1);
            }

            out.println(sum);
            out.println(bestEdges.size());
            out.println(ids.substring(1));
        }
        out.flush();
    }

    private static void toggleEdge(Edge e) {
        if (e.special != 2) {
            e.special = 2;
            ec[e.a][e.b]--;
            ec[e.b][e.a]--;
        } else {
            e.special = 1;
            ec[e.a][e.b]++;
            ec[e.b][e.a]++;
        }
    }

    static boolean isConnected(int S, int T) {
        int n = graph.length;

        UnionFind uf = new UnionFind(n);
        for (int i = 0 ; i < n ; i++) {
            for (Edge e : graph[i]) {
                if (e.special == 2) {
                    continue;
                }
                uf.unite(e.a, e.b);
            }
        }
        return uf.issame(S, T);
    }

    static class UnionFind {
        int[] rank;
        int[] parent;
        int[] cnt;

        public UnionFind(int n) {
            rank = new int[n];
            parent = new int[n];
            cnt = new int[n];
            for (int i = 0; i < n ; i++) {
                parent[i] = i;
                cnt[i] = 1;
            }
        }

        public int find(int a) {
            if (parent[a] == a) {
                return a;
            }
            parent[a] = find(parent[a]);
            return parent[a];
        }

        public void unite(int a, int b) {
            a = find(a);
            b = find(b);
            if (a == b) {
                return;
            }
            if (rank[a] < rank[b]) {
                parent[a] = b;
                cnt[b] += cnt[a];
                cnt[a] = cnt[b];
            } else {
                parent[b] = a;
                cnt[a] += cnt[b];
                cnt[b] = cnt[a];
                if (rank[a] == rank[b]) {
                    rank[a]++;
                }
            }
        }

        public int groupCount(int a) {
            return cnt[find(a)];
        }

        private boolean issame(int a, int b) {
            return find(a) == find(b);
        }
    }


    static int[][] ec;

    static int oi;
    static int[] ord = new int[1010];
    static int[] low = new int[1010];
    static Edge[] parent = new Edge[1010];

    private static Edge lowlinkAndFind(int start, int goal) {
        int n = graph.length;
        Arrays.fill(ord, 0, n, -1);
        Arrays.fill(low, 0, n, n);
        oi = 0;

        dfs(start, -1);

        Edge bestEdge = null;
        int head = goal;
        while (head != start) {
            int tohead = parent[head].to(head);
            if (isBridge(tohead, head)) {
                if (bestEdge == null || bestEdge.w > parent[head].w) {
                    bestEdge = parent[head];
                }
            }
            head = tohead;
        }
        return bestEdge;
    }

    private static void dfs(int now, int par) {
        if (ord[now] != -1) {
            return;
        }
        ord[now] = oi;
        low[now] = oi++;
        for (Edge e : graph[now]) {
            int to = e.to(now);
            if (to == par || e.special == 2) {
                continue;
            }
            if (ord[to] == -1) {
                parent[to] = e;
                dfs(to, now);
                low[now] = Math.min(low[now], low[to]);
            } else {
                // that's a back edge!
                low[now] = Math.min(low[now], ord[to]);
            }
        }
    }

    private static boolean isBridge(int u, int v) {
        return ord[u] < low[v] && ec[u][v] <= 1;
    }

    static List<Edge>[] graph;

    static List<Edge> gogo(int start, int goal) {
        int n = graph.length;
        long[] dp = new long[n];
        Edge[] from = new Edge[n];

        Arrays.fill(dp, INF);
        dp[start] = 0;
        Queue<State> q = new PriorityQueue<>();
        q.add(new State(start, 0));
        while (q.size() >= 1) {
            State st = q.poll();
            if (dp[st.now] < st.time) {
                continue;
            }
            for (Edge e : graph[st.now]) {
                int to = e.to(st.now);
                long time = e.w + st.time;
                if (dp[to] > time) {
                    dp[to] = time;
                    from[to] = e;
                    q.add(new State(to, time));
                }
            }
        }
        if (from[goal] == null) {
            return null;
        }

        List<Edge> list = new ArrayList<>();
        int head = goal;
        while (head != start) {
            list.add(from[head]);
            head = from[head].to(head);
        }
        return list;
    }

    static class State implements Comparable<State> {
        int now;
        long time;

        State(int a, long t) {
            now = a;
            time = t;
        }

        @Override
        public int compareTo(State o) {
            return Long.compare(time, o.time);
        }
    }

    static class Edge {
        int a;
        int b;
        int id;
        long w;
        int special;
        int available;

        Edge(int i, int u, int v, int c) {
            a = u;
            b = v;
            id = i;
            w = c;
        }

        public int to(int from) {
            return a + b - from;
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

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
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
