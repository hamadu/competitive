package codeforces.cr286;

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

        int[][] edges = new int[m][3];
        for (int i = 0 ; i < m ; i++) {
            // u,v,color
            edges[i][0] = in.nextInt()-1;
            edges[i][1] = in.nextInt()-1;
            edges[i][2] = in.nextInt()-1;
        }
        Arrays.sort(edges, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[2] - o2[2];
            }
        });

        int[] cdeg = new int[n];
        int[][] colorToV = new int[m][];
        UnionFind[] ufs = new UnionFind[m];

        int[] tempV = new int[n];
        boolean[] visitedV = new boolean[n];
        for (int fr = 0 ; fr < m ; ) {
            int to = fr;
            while (to < m && edges[to][2] == edges[fr][2]) {
                to++;
            }
            int cid = edges[fr][2];
            int vi = 0;
            for (int i = fr ; i < to ; i++) {
                int a = edges[i][0];
                int b = edges[i][1];
                if (!visitedV[a]) {
                    visitedV[a] = true;
                    tempV[vi++] = a;
                }
                if (!visitedV[b]) {
                    visitedV[b] = true;
                    tempV[vi++] = b;
                }
            }

            colorToV[cid] = Arrays.copyOf(tempV, vi);
            Arrays.sort(colorToV[cid]);
            for (int v : colorToV[cid]) {
                cdeg[v]++;
            }

            ufs[cid] = new UnionFind(vi);
            for (int i = fr ; i < to ; i++) {
                int a = edges[i][0];
                int b = edges[i][1];
                int ai = Arrays.binarySearch(colorToV[cid], a);
                int bi = Arrays.binarySearch(colorToV[cid], b);
                ufs[cid].unite(ai, bi);
            }

            for (int i = fr ; i < to ; i++) {
                int a = edges[i][0];
                int b = edges[i][1];
                visitedV[a] = false;
                visitedV[b] = false;
            }
            fr = to;
        }


        int[][] vToColor = new int[n][];
        for (int i = 0 ; i < n ; i++) {
            vToColor[i] = new int[cdeg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            if (colorToV[i] != null) {
                for (int v : colorToV[i]) {
                    vToColor[v][--cdeg[v]] = i;
                }
            }
        }

        Map<Long,Integer> memo = new HashMap<>();
        for (int q = in.nextInt() ; q >= 1 ; --q) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            if (vToColor[a].length > vToColor[b].length) {
                int tmp = a;
                a = b;
                b = tmp;
            }
            long pairID = a*1000000L+b;
            if (memo.containsKey(pairID)) {
                out.println(memo.get(pairID));
                continue;
            }

            int cnt = 0;
            for (int col : vToColor[a]) {
                int ai = Arrays.binarySearch(colorToV[col], a);
                int bi = Arrays.binarySearch(colorToV[col], b);
                if (ai < 0 || bi < 0) {
                    continue;
                }
                if (ufs[col].issame(ai, bi)) {
                    cnt++;
                }
            }
            memo.put(pairID, cnt);
            out.println(cnt);
        }

        out.flush();
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



