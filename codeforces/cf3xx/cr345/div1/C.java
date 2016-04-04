package codeforces.cf3xx.cr345.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/03/08.
 */
public class C {
    static final int TH = 8;
    static final long MASK = (1L<<30L)-1;
    static int n;
    static int m;
    static int nm;
    static long[] a;
    static long[] tmp;
    static int[] nu;
    static int[] id;
    static int[] deg;
    static int[] oudeg;
    static int[] edgeOne;
    static int[] edgeTwo;
    static int[][] edges;
    static UnionFind uf;
    static Map<Integer,List<Integer>> graph;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        n = in.nextInt();
        m = in.nextInt();
        nm = n*m;
        a = new long[nm];
        for (int i = 0 ; i < nm ; i++) {
            a[i] = in.nextInt();
        }
        edges = new int[TH][nm];
        oudeg = new int[nm];
        graph = new HashMap<>();
        tmp = new long[nm];
        nu = new int[nm];
        id = new int[nm];
        deg = new int[nm];
        edgeOne = new int[nm];
        edgeTwo = new int[nm];
        uf = new UnionFind(nm);

        Arrays.fill(edgeOne, -1);
        Arrays.fill(edgeTwo, -1);

        // yoko-same
        for (int i = 0 ; i < n ; i++) {
            fillYokoToTmp(i);
            uniteSameValue(m);
        }

        // tate-same
        for (int i = 0 ; i < m ; i++) {
            fillTateTmp(i);
            uniteSameValue(n);
        }

        // yoko-diff
        for (int i = 0 ; i < n ; i++) {
            fillYokoToTmp(i);
            edgeDiffValue(m);
        }

        // tate-diff
        for (int i = 0 ; i < m ; i++) {
            fillTateTmp(i);
            edgeDiffValue(n);
        }

        Arrays.fill(nu, 0);
        Arrays.fill(id, 1);
        int qh = 0;
        int qt = 0;
        for (int i = 0 ; i < nm ; i++) {
            if (deg[i] == 0) {
                nu[qh++] = i;
                id[i] = 1;
            }
        }
        while (qt < qh) {
            int now = nu[qt++];
            int weg = id[now];
            for (int t = 0 ; t < Math.min(TH, oudeg[now]) ; t++) {
                int to = edges[t][now];
                id[to] = Math.max(id[to], weg+1);
                deg[to]--;
                if (deg[to] == 0) {
                    nu[qh++] = to;
                }
            }
            if (!graph.containsKey(now)) {
                continue;
            }
            for (int to : graph.get(now)) {
                if (to == -1) {
                    continue;
                }
                id[to] = Math.max(id[to], weg+1);
                deg[to]--;
                if (deg[to] == 0) {
                    nu[qh++] = to;
                }
            }
        }

        for (int i = 0 ; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                int rep = uf.find(i*m+j);
                if (j >= 1) {
                    out.print(' ');
                }
                out.print(id[rep]);
            }
            out.println();
        }
        out.flush();
    }

    private static void edgeDiffValue(int max) {
        for (int j = 0 ; j < max ; j++) {
            nu[j] = (int)(tmp[j]>>30L);
            id[j] = (int)(tmp[j]& MASK);
        }
        for (int j = 0 ; j < max-1 ; j++) {
            if (nu[j] != nu[j+1]) {
                int idx1 = uf.find(id[j]);
                int idx2 = uf.find(id[j+1]);
                if (nu[j] < nu[j+1]) {
                    deg[idx2]++;
                    addEdge(idx1, idx2);
                } else {
                    deg[idx1]++;
                    addEdge(idx2, idx1);
                }
            }
        }
    }

    private static void addEdge(int idx1, int idx2) {
        if (oudeg[idx1] < TH) {
            edges[oudeg[idx1]][idx1] = idx2;
        } else {
            if (oudeg[idx1] == TH) {
                List<Integer> ne = new ArrayList<>();
                graph.put(idx1, ne);
            }
            graph.get(idx1).add(idx2);
        }
        oudeg[idx1]++;
    }


    private static void uniteSameValue(int max) {
        for (int j = 0 ; j < max ; j++) {
            nu[j] = (int)(tmp[j]>>30L);
            id[j] = (int)(tmp[j]& MASK);
        }
        for (int j = 0 ; j < max-1 ; j++) {
            if (nu[j] == nu[j+1]) {
                uf.unite(id[j], id[j+1]);
            }
        }
    }

    private static void fillYokoToTmp(int i) {
        int base = i*m;
        for (int j = 0 ; j < m ; j++) {
            tmp[j] = (a[base+j]<<30L)+base+j;
        }
        Arrays.sort(tmp, 0, m);
    }

    private static void fillTateTmp(int i) {
        int base = i;
        for (int j = 0 ; j < n ; j++) {
            tmp[j] = (a[base+j*m]<<30L)+base+j*m;
        }
        Arrays.sort(tmp, 0, n);
    }

    static class UnionFind {
        int[] parent, rank;
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0 ; i < n ; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        int find(int x) {
            if (parent[x] == x) {
                return x;
            }
            parent[x] = find(parent[x]);
            return parent[x];
        }

        void unite(int x, int y) {
            x = find(x);
            y = find(y);
            if (x == y) {
                return;
            }
            if (rank[x] < rank[y]) {
                parent[x] = y;
            } else {
                parent[y] = x;
                if (rank[x] == rank[y]) {
                    rank[x]++;
                }
            }
        }
        boolean issame(int x, int y) {
            return (find(x) == find(y));
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
