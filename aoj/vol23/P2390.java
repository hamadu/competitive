package aoj.vol23;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/15.
 */
public class P2390 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int cn = 0;
        while (true) {
            cn++;
            int n = in.nextInt();
            int t = in.nextInt();
            int k = in.nextInt();
            if (n + t + k == 0) {
                break;
            }
            int[][] edge = new int[n-1][3];
            for (int i = 0; i < n-1; i++) {
                for (int j = 0; j < 2 ; j++) {
                    edge[i][j] = in.nextInt()-1;
                }
                edge[i][2] = in.nextInt();
            }
            int[] base = new int[t];
            for (int i = 0; i < t ; i++) {
                base[i] = in.nextInt()-1;
            }

            out.println(String.format("Case %d: %d", cn, solve(n, k, edge, base)));
            
        }

        out.flush();
    }

    private static int solve(int n, int k, int[][] edge, int[] base) {
        Arrays.sort(edge, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[2] - o1[2];
            }
        });

        UnionFind uf = new UnionFind(n);
        for (int i : base) {
            uf.weight[i]++;
        }

        int use = (n - 1) - k;
        int free = base.length - (k + 1);
        int total = 0;
        for (int[] ed : edge) {
            total += ed[2];
        }

        for (int[] ed : edge) {
            if (use == 0) {
                break;
            }
            if (uf.issame(ed[0], ed[1])) {
                continue;
            }
            int w1 = uf.weight[uf.find(ed[0])];
            int w2 = uf.weight[uf.find(ed[1])];

            boolean con = false;
            if (w1 >= 1 && w2 >= 1) {
                if (free >= 1) {
                    free--;
                    con = true;
                }
            } else {
                con = true;
            }
            if (con) {
                uf.unite(ed[0], ed[1]);
                total -= ed[2];
                use--;
            }
        }
        return total;
    }

    static class UnionFind {
        int[] parent, rank;
        int[] weight;
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            weight = new int[n];
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
            int w1 = weight[x];
            int w2 = weight[y];
            weight[x] = weight[y] = w1 + w2;
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
