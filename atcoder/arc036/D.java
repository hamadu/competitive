package atcoder.arc036;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int q = in.nextInt();

        UnionFind uf = new UnionFind(2*n);
        while (--q >= 0) {
            int w = in.nextInt();
            int x = in.nextInt()-1;
            int y = in.nextInt()-1;
            int z = in.nextInt();
            if (w == 1) {
                if (z % 2 == 1) {
                    uf.unite(x, n+y);
                    uf.unite(n+x, y);
                } else {
                    uf.unite(x, y);
                    uf.unite(n+x, n+y);
                }
            } else {
                if (uf.issame(x, y) || uf.issame(n+x, n+y)) {
                    out.println("YES");
                } else {
                    out.println("NO");
                }
            }
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



