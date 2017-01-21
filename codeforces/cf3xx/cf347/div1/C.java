package codeforces.cf3xx.cf347.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class C {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        paint = new int[n];
        graph = buildWeightedGraph(in, n, m);
        int[] d1 = solve(graph);
        for (int i = 0; i < n ; i++) {
            for (int j = 0 ; j < graph[i].length ; j++) {
                graph[i][j][1] ^= 1;
            }
        }
        int[] d2 = solve(graph);
        int[] ans = selectLess(d1, d2);

        if (ans == null) {
            out.println(-1);
        } else {
            int cnt = 0;
            for (int i = 0; i < n ; i++) {
                cnt += ans[i];
            }
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < n ; i++) {
                if (ans[i] == 1) {
                    line.append(' ').append(i+1);
                }
            }
            out.println(cnt);
            if (line.length() >= 1) {
                out.println(line.substring(1));
            }
        }


        out.flush();
    }

    private static int[] selectLess(int[] d1, int[] d2) {
        if (d1 == null && d2 == null) {
            return null;
        }
        if (d1 == null) {
            return d2;
        }
        if (d2 == null) {
            return d1;
        }
        int n = d1.length;
        int sum1 = 0;
        int sum2 = 0;
        for (int i = 0; i < n ; i++) {
            sum1 += d1[i];
            sum2 += d2[i];
        }
        return sum1 < sum2 ? d1 : d2;
    }

    static int[] paint;
    static int[][][] graph;

    static boolean[] visited = new boolean[100000];
    static boolean isOK = true;

    static int[] doit(List<Integer> group, int flg) {
        isOK = true;
        for (int i : group) {
            visited[i] = false;
        }
        dfs(group.get(0), -1, flg);
        if (!isOK) {
            return null;
        }
        int x = group.size();
        int[] ret = new int[x];
        for (int i = 0; i < x ; i++) {
            ret[i] = paint[group.get(i)];
        }
        return ret;
    }

    static void dfs(int now, int par, int flg) {
        if (visited[now]) {
            return;
        }
        paint[now] = flg;
        visited[now] = true;
        for (int[] e : graph[now]) {
            if (e[0] == par) {
                continue;
            }
            if (visited[e[0]]) {
                if ((flg ^ paint[e[0]] ^ e[1]) == 1) {
                    isOK = false;
                }
            } else {
                dfs(e[0], now, e[1]^flg);
            }

        }
    }

    private static int[] solve(int[][][] graph) {
        int n = graph.length;
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n ; i++) {
            for (int[] e : graph[i]) {
                uf.unite(i, e[0]);
            }
        }

        int[] flg = new int[n];
        List<Integer>[] gs = new List[n];
        for (int i = 0; i < n ; i++) {
            gs[i] = new ArrayList<>();
        }
        for (int i = 0; i < n ; i++) {
            int id = uf.find(i);
            gs[id].add(i);
        }
        for (int i = 0; i < n ; i++) {
            if (gs[i].size() == 0) {
                continue;
            }
            int[] ct1 = doit(gs[i], 0);
            int[] ct2 = doit(gs[i], 1);
            int[] ct = selectLess(ct1, ct2);
            if (ct == null) {
                return null;
            }
            for (int j = 0; j < gs[i].size() ; j++) {
                flg[gs[i].get(j)] = ct[j];
            }
        }
        return flg;
    }

    static class UnionFind {
        int[] rank;
        int[] parent;
        int[] cnt;

        public UnionFind(int n) {
            rank = new int[n];
            parent = new int[n];
            cnt = new int[n];
            for (int i = 0; i < n; i++) {
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


    static int[][][] buildWeightedGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][][] graph = new int[n][][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            int w = in.nextToken().toCharArray()[0] == 'R' ? 0 : 1;
            deg[a]++;
            deg[b]++;
            edges[i] = new int[]{a, b, w};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]][2];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            int w = edges[i][2];
            graph[a][--deg[a]][0] = b;
            graph[b][--deg[b]][0] = a;
            graph[a][deg[a]][1] = w;
            graph[b][deg[b]][1] = w;
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
