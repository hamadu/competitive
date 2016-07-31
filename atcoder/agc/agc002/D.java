package atcoder.agc.agc002;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 2016/07/31.
 */
public class D {
    static class Group {
        int l;
        int r;
        List<int[]> query;

        public Group(int li, int ri) {
            l = li;
            r = ri;
            query = new ArrayList<>();
        }

        public Group push(int[][] q) {
            for (int[] qi : q) {
                query.add(qi);
            }
            return this;
        }

        public Group push(int[] q) {
            query.add(q);
            return this;
        }
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[][] edge = new int[m][2];
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < 2 ; j++) {
                edge[i][j] = in.nextInt()-1;
            }
        }

        int q = in.nextInt();
        int[][] query = new int[q][4];
        for (int i = 0; i < q ; i++) {
            for (int j = 0; j < 2 ; j++) {
                query[i][j] = in.nextInt()-1;
            }
            query[i][2] = in.nextInt();
            query[i][3] = i;
        }
        Queue<Group> que = new ArrayBlockingQueue<>(300000);
        que.add(new Group(0, m+1).push(query));


        UnionFind uf = new UnionFind(n);

        int[] ans = new int[q];
        int doneunion = 0;
        while (que.size() >= 1) {
            Group g = que.poll();
            if (g.l == 0) {
                uf.init();
                doneunion = 0;
            }

            int med = (g.l+g.r)/2;
            for (int i = doneunion; i < med; i++) {
                uf.unite(edge[i][0], edge[i][1]);
            }
            doneunion = med;

            if (g.r - g.l == 1) {
                for (int[] qu : g.query) {
                    ans[qu[3]] = g.l+1;
                }
                continue;
            }

            Group gOK = new Group(g.l, med);
            Group gNG = new Group(med, g.r);
            for (int[] qu : g.query) {
                int cnt = uf.issame(qu[0], qu[1]) ? uf.groupCount(qu[0]) : (uf.groupCount(qu[0]) + uf.groupCount(qu[1]));
                if (cnt >= qu[2]) {
                    gOK.push(qu);
                } else {
                    gNG.push(qu);
                }
            }
            que.add(gOK);
            que.add(gNG);
        }
        for (int i = 0; i < q ; i++) {
            out.println(ans[i]);
        }
        out.flush();
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

        public void init() {
            Arrays.fill(rank, 0);
            for (int i = 0; i < parent.length ; i++) {
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
