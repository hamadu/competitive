package atcoder.other2017.njpc2017;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class H {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        n = in.nextInt();
        parent = new int[n];
        parent[0] = -1;
        for (int i = 1 ; i < n ; i++) {
            parent[i] = in.nextInt()-1;
        }
        int[] initialColor = in.nextInts(n);

        int Q = in.nextInt();
        queries = new int[Q][];
        for (int i = 0; i < Q ; i++) {
            int t = in.nextInt();
            queries[i] = new int[t];
            for (int j = 0; j < t; j++) {
                queries[i][j] = in.nextInt()-1;
            }
        }
        answers = new int[Q];
        Arrays.fill(answers, -1);

        int[][] edges = new int[n-1][2];
        for (int i = 0; i < n-1 ; i++) {
            edges[i][0] = parent[i+1];
            edges[i][1] = i+1;
        }

        BatchedDyanmicConnectivity bdc = new BatchedDyanmicConnectivity(n, edges, Q+n);

        boolean[] pushed = new boolean[n];
        for (int i = 0 ; i < n-1 ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            if (initialColor[a] != initialColor[b]) {
                pushed[i] = true;
                bdc.addEdge(i, i);
            }
        }


        for (int i = 0; i < Q ; i++) {
            if (queries[i].length == 2) {
                // It's a query
            } else {
                int toggleEdgeID = queries[i][0]-1;
                if (toggleEdgeID >= 0) {
                    if (pushed[toggleEdgeID]) {
                        bdc.removeEdge(n+i, toggleEdgeID);
                    } else {
                        bdc.addEdge(n+i, toggleEdgeID);
                    }
                    pushed[toggleEdgeID] = !pushed[toggleEdgeID];
                }
            }
        }
        bdc.doit();

        for (int i = 0; i < Q ; i++) {
            if (answers[i] >= 0) {
                out.println(answers[i] == 0 ? "NO" : "YES");
            }
        }
        out.flush();
    }


    static int n;
    static int[] parent;

    static int[][] queries;
    static int[] answers;

    static class UndoableUnionFind {
        int[] parent;
        int[] size;
        int[][] stk;
        int sp;

        UndoableUnionFind(int n, int maxDepth) {
            parent = new int[n];
            size = new int[n];
            Arrays.fill(parent, -1);
            for (int i = 0; i < n ; i++) {
                parent[i] = i;
            }
            Arrays.fill(size, 1);
            stk = new int[maxDepth][3];
        }

        int find(int x) {
            while (x != parent[x]) {
                x = parent[x];
            }
            return x;
        }

        void merge(int u, int v) {
            u = find(u);
            v = find(v);
            if (size[u] < size[v]) {
                int tmp = u;
                u = v;
                v = tmp;
            }

            // memo order: parent, size
            stk[sp][0] = v;
            stk[sp++][1] = parent[v];
            stk[sp][0] = u;
            stk[sp++][1] = size[u];

            if (u == v) {
                return;
            }

            parent[v] = u;
            size[u] += size[v];
        }

        void undo() {
            // remember order: size, parent
            int[] prevSize = stk[--sp];
            int[] prevParent = stk[--sp];

            size[prevSize[0]] = prevSize[1];
            parent[prevParent[0]] = prevParent[1];
        }

        boolean isSame(int u, int v) {
            return find(u) == find(v);
        }
    }

    static class BatchedDyanmicConnectivity {
        UndoableUnionFind uf;
        int[][] edges;

        int Q;
        int en;
        int N;
        int M;
        int[] cnt;
        int[] first;

        int eidx;
        int[][] edgeTimes;
        List<Integer>[] edgeSegments;

        public BatchedDyanmicConnectivity(int n, int[][] ed, int numberOfQueries) {
            Q = numberOfQueries+1;
            N = Integer.highestOneBit(Q-1)<<2;
            M = N / 2 - 1;
            uf = new UndoableUnionFind(n, Q*2);
            edges = ed;
            en = edges.length;
            cnt = new int[en];
            first = new int[en];

            edgeSegments = new List[N];
            for (int i = 0; i < N ; i++) {
                edgeSegments[i] = new ArrayList<>();
            }

            edgeTimes = new int[M][3];
        }

        void addEdge(int queryTime, int edgeId) {
            if (cnt[edgeId] == 0) {
                first[edgeId] = queryTime;
            }
            cnt[edgeId]++;
        }

        void removeEdge(int queryTime, int edgeId) {
            cnt[edgeId]--;
            if (cnt[edgeId] == 0) {
                edgeTimes[eidx][0] = first[edgeId];
                edgeTimes[eidx][1] = queryTime;
                edgeTimes[eidx++][2] = edgeId;
            }
        }

        void pushDown(int a, int b, int edgeId) {
            pushDown(a, b, edgeId, 0, 0, M+1);
        }

        void pushDown(int from, int to, int edgeId, int id, int l, int r) {
            if (r <= from || to <= l) {
                return;
            }
            if (from <= l && r <= to) {
                edgeSegments[id].add(edgeId);
                return;
            }

            int med = (l+r)/2;
            pushDown(from, to, edgeId, id*2+1, l, med);
            pushDown(from, to, edgeId, id*2+2, med, r);
        }

        void dfs(int k) {
            int added = 0;
            for (int eidx : edgeSegments[k]) {
                added++;
                uf.merge(edges[eidx][0], edges[eidx][1]);
            }

            if (k < M) {
                dfs(k*2+1);
                dfs(k*2+2);
            } else {
                int time = k-M-n;
                if (time >= 0 && time < queries.length) {
                    if (queries[time].length == 2) {
                        answers[time] = uf.isSame(queries[time][0], queries[time][1]) ? 1 : 0;
                    }
                }
            }

            while (--added >= 0) {
                uf.undo();
            }
        }

        void doit() {
            for (int i = 0; i < en ; i++) {
                if (cnt[i] >= 1) {
                    edgeTimes[eidx][0] = first[i];
                    edgeTimes[eidx][1] = Q-1;
                    edgeTimes[eidx++][2] = i;
                }
            }

            for (int i = 0 ; i < eidx ; i++) {
                pushDown(edgeTimes[i][0], edgeTimes[i][1], edgeTimes[i][2]);
            }

            dfs(0);
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
