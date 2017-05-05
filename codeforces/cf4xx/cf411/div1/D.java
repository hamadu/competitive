package codeforces.cf4xx.cf411.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int q = in.nextInt();

        forest = buildGraph(in, n, m);
        farChild = new int[n];
        far = new int[n];
        Arrays.fill(farChild, -1);
        Arrays.fill(far, -1);
        ids = new int[n];
        int[] gsize = new int[n];

        int[] gid = new int[n];
        int gn = 0;
        List<int[]> groupDegs = new ArrayList<>();
        List<int[]> groupDegDegs = new ArrayList<>();
        List<long[]> groupDegSums = new ArrayList<>();
        for (int i = 0; i < n ; i++) {

            if (farChild[i] == -1) {
                ididx = 0;
                dfs(i, -1);
                dfs2(i, -1, 0);
                gsize[gn] = ididx;

                int maxDeg = 0;
                for (int ci = 0 ; ci < ididx ; ci++) {
                    gid[ids[ci]] = gn;
                    maxDeg = Math.max(maxDeg, far[ids[ci]]);
                }
                int[] degs = new int[maxDeg+1];
                for (int ci = 0 ; ci < ididx ; ci++) {
                    degs[far[ids[ci]]]++;
                }
                int[] degDegs = new int[maxDeg+1];
                long[] degSums = new long[maxDeg+1];
                for (int d = 0 ; d <= maxDeg ; d++) {
                    degDegs[d] = ((d >= 1) ? degDegs[d-1] : 0) + degs[d];
                    degSums[d] = ((d >= 1) ? degSums[d-1] : 0) + d * degs[d];
                }
                groupDegs.add(degs);
                groupDegDegs.add(degDegs);
                groupDegSums.add(degSums);

                gn++;
            }
        }


        Map<Long,Double> ansMap = new TreeMap<>();
        while (--q >= 0) {
            int ql = in.nextInt()-1;
            int qr = in.nextInt()-1;
            int id0 = gid[ql];
            int id1 = gid[qr];
            if (id0 == id1) {
                out.println(-1);
                continue;
            }

            if (groupDegs.get(id1).length < groupDegs.get(id0).length) {
                int tmp = id0;
                id0 = id1;
                id1 = tmp;
            }

            long pairID = id0*(1L<<20)+id1;
            if (ansMap.containsKey(pairID)) {
                out.println(String.format("%.9f", ansMap.get(pairID)));
                continue;
            }

            int[] deg0 = groupDegs.get(id0);
            int[] deg1 = groupDegs.get(id1);
            int[] degDeg1 = groupDegDegs.get(id1);
            long[] deg1Sum = groupDegSums.get(id1);

            long totalWay = (long)gsize[id0] * gsize[id1];
            long totalDegree = 0;

            int minDeg = deg1.length-2;
            long totalLessThanMinDeg = 0;
            long countLessThanMinDeg = 0;
            for (int x = 0 ; x < deg0.length ; x++) {
                int y = minDeg-x;
                if (y >= 0) {
                    totalLessThanMinDeg += (long) deg0[x]*((long) x*degDeg1[y]+deg1Sum[y]);
                    countLessThanMinDeg += (long) deg0[x]*degDeg1[y];
                }
                totalDegree += (long)deg0[x] * ((long)x * degDeg1[minDeg+1] + deg1Sum[minDeg+1]);
            }

            totalDegree -= totalLessThanMinDeg;
            totalDegree += countLessThanMinDeg * minDeg;
            totalDegree += totalWay;

            double ans = 1.0d * totalDegree / totalWay;
            ansMap.put(pairID, ans);
            out.println(String.format("%.9f", ans));
        }
        out.flush();
    }


    static int ididx;
    static int[] ids;
    static int[][] forest;
    static int[] farChild;
    static int[] far;

    static int dfs(int now, int par) {
        ids[ididx++] = now;
        farChild[now] = 0;
        for (int to : forest[now]) {
            if (to == par) {
                continue;
            }
            int r = dfs(to, now) + 1;
            if (farChild[now] <= r) {
                farChild[now] = r;
            }
        }
        return farChild[now];
    }

    static void dfs2(int now, int par, int parD) {
        far[now] = Math.max(farChild[now], parD);
        int cn = par == -1 ? forest[now].length : forest[now].length-1;
        if (cn == 0) {
            return;
        }

        int ci = 0;
        int[] cc = new int[cn+1];
        for (int to : forest[now]) {
            if (to == par) {
                continue;
            }
            cc[ci++] = farChild[to];
        }
        cc[cn] = -1;
        Arrays.sort(cc);

        int best = cc[cn];
        int semiBest = cc[cn-1];
        for (int to : forest[now]) {
            if (to == par) {
                continue;
            }
            int toParD = Math.max(parD, (best == farChild[to] ? semiBest : best) + 1) + 1;
            dfs2(to, now, toParD);
        }
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
