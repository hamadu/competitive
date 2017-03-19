package atcoder.other2017.yahoo2017.qual;

import utils.Unsolved;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

@Unsolved("TLE. 定数倍きついのでJavaだとこの方法で解くことは不可能だと思われる")
public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] l = in.nextInts(n);
        int[] r = in.nextInts(n);

//        n = 50000;
//        l = new int[n];
//        r = new int[n];
//        for (int i = 0; i < n ; i++) {
//            l[i] = 1;
//            r[i] = (int)(Math.random() * 100000);
//        }

        // l -> r
        scc = new SCC(4000000, 4000000);
        M = Integer.highestOneBit(Math.max(4, n-1))<<1;
        N = 2*M;
        actualId = new int[N];
        int cn = n; // reserve n vertices

        int[][] event = new int[2*n][2];

        // right to left
        {
            for (int i = M-1; i < M-1+M ; i++) {
                actualId[i] = cn++;
            }
            for (int i = M-2 ; i >= 0 ; i--) {
                actualId[i] = cn++;
                scc.add(actualId[i], actualId[i*2+1]);
                scc.add(actualId[i], actualId[i*2+2]);
            }
            for (int i = 0; i < n ; i++) {
                event[i*2][0] = l[i]-i;
                event[i*2][1] = i;
                event[i*2+1][0] = r[i]-i;
                event[i*2+1][1] = i+n;
            }
            Arrays.sort(event, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
            for (int ei = 0 ; ei < 2*n ; ei++) {
                int aidx = event[ei][1] < n ? event[ei][1] : event[ei][1] - n;
                if (event[ei][1] < n) {
                    // into the atraction: remake the vertices
                    int segV = aidx + M -1;
                    actualId[segV] = cn++;
                    scc.add(actualId[segV], aidx);
                    while (segV > 0) {
                        segV = (segV - 1) / 2;
                        actualId[segV] = cn++;
                        scc.add(actualId[segV], actualId[segV*2+1]);
                        scc.add(actualId[segV], actualId[segV*2+2]);
                    }
                } else {
                    // exit the atraction
                    addEdges(aidx, 0, aidx);
                }
            }
        }

        debug("==");

        // left to right
        {
            for (int i = M-1; i < M-1+M ; i++) {
                actualId[i] = cn++;
            }
            for (int i = M-2 ; i >= 0 ; i--) {
                actualId[i] = cn++;
                scc.add(actualId[i], actualId[i*2+1]);
                scc.add(actualId[i], actualId[i*2+2]);
            }
            for (int i = 0; i < n ; i++) {
                event[i*2][0] = l[i]+i;
                event[i*2][1] = i;
                event[i*2+1][0] = r[i]+i;
                event[i*2+1][1] = i+n;
            }
            Arrays.sort(event, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
            for (int ei = 0 ; ei < 2*n ; ei++) {
                int aidx = event[ei][1] < n ? event[ei][1] : event[ei][1] - n;
                if (event[ei][1] < n) {
                    // into the atraction: remake the vertices
                    int segV = aidx + M -1;
                    actualId[segV] = cn++;
                    scc.add(actualId[segV], aidx);
                    while (segV > 0) {
                        segV = (segV - 1) / 2;
                        actualId[segV] = cn++;
                        scc.add(actualId[segV], actualId[segV*2+1]);
                        scc.add(actualId[segV], actualId[segV*2+2]);
                    }
                } else {
                    // exit the atraction
                    addEdges(aidx, aidx+1, n);
                }
            }
        }

        int[] gids = scc.scc();
        int gn = 0;
        for (int i = 0; i < gids.length ; i++) {
            gn = Math.max(gn, gids[i]+1);
        }
        Set<Integer>[] graph = new Set[gn];
        int[] cnt = new int[gn];
        int[] dp = new int[gn];
        for (int i = 0; i < gn ; i++) {
            graph[i] = new HashSet<>();
        }

        for (int i = 0; i < gids.length ; i++) {
            if (i < n) {
                cnt[gids[i]]++;
            }
            for (int to : scc.graph.nexts(i)) {
                if (gids[i] != gids[to]) {
                    graph[gids[i]].add(gids[to]);
                }
            }
        }

        int[] indeg = new int[gn];
        for (int i = 0; i < gn ; i++) {
            for (int to : graph[i]) {
                indeg[to]++;
            }
        }
        int[] que = new int[gn];
        int head = 0;
        int tail = 0;
        for (int i = 0; i < gn ; i++) {
            if (indeg[i] == 0) {
                que[head++] = i;
            }
        }
        while (tail < head) {
            int v = que[tail++];
            dp[v] += cnt[v];
            for (int tv : graph[v]) {
                dp[tv] = Math.max(dp[tv], dp[v]);
                indeg[tv]--;
                if (indeg[tv] == 0) {
                    que[head++] = tv;
                }
            }
        }

        int ans = 0;
        for (int i = 0; i < gn ; i++) {
            ans = Math.max(ans, dp[i]);
        }

        out.println(ans);
        out.flush();
    }

    static int[] actualId;

    static int N;

    static int M;

    static SCC scc;

    static void addEdges(int v, int l, int r) {
        if (l < r) {
            add(v, l, r, 0, 0, M);
        }
    }

    static void add(int v, int ql, int qr, int idx, int fr, int to) {
        if (to <= ql || qr <= fr) {
            return;
        }
        if (ql <= fr && to <= qr) {
            scc.add(v, actualId[idx]);
            return;
        }
        add(v, ql, qr, idx*2+1, fr, (fr+to)/2);
        add(v, ql, qr, idx*2+2, (fr+to)/2, to);
    }

    public static class DirectedGraph {
        int[] head;
        int[] next;
        int[] to;
        int eidx;

        public DirectedGraph(int v, int e) {
            head = new int[v];
            next = new int[e];
            to = new int[e];
            clear();
        }

        public void clear() {
            Arrays.fill(head, -1);
            eidx = 0;
        }

        public void add(int a, int b) {
            next[eidx] = head[a];
            head[a] = eidx;
            to[eidx++] = b;
        }

        public Iterable<Integer> nexts(int v) {
            final int firstE = head[v];
            return () -> new Iterator<Integer>() {
                int cursor = firstE;

                @Override
                public boolean hasNext() {
                    return cursor != -1;
                }

                @Override
                public Integer next() {
                    int ret = to[cursor];
                    cursor = next[cursor];
                    return ret;
                }
            };
        }
    }

    public static class SCC {
        boolean[] visited;
        int[] node_id;
        List<Integer> rev;

        int n;
        DirectedGraph graph;
        DirectedGraph r_graph;

        public SCC(int n, int m) {
            graph = new DirectedGraph(n, m);
            r_graph = new DirectedGraph(n, m);
            this.n = n;
        }

        public void add(int u, int v) {
            graph.add(u, v);
            r_graph.add(v, u);
        }

        public int[] scc() {
            visited = new boolean[n];
            rev = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    dfs(i);
                }
            }
            int id = 0;
            node_id = new int[n];
            visited = new boolean[n];
            for (int i = rev.size()-1; i >= 0; i--) {
                if (!visited[rev.get(i)]) {
                    rdfs(rev.get(i), id);
                    id++;
                }
            }
            return node_id;
        }

        private void dfs(int i) {
            visited[i] = true;
            for (int next : graph.nexts(i)) {
                if (!visited[next]) {
                    dfs(next);
                }
            }
            rev.add(i);
        }

        private void rdfs(int i, int id) {
            visited[i] = true;
            node_id[i] = id;
            for (int next : r_graph.nexts(i)) {
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
