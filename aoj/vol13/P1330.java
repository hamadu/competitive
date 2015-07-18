package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by hama_du on 15/07/17.
 */
public class P1330 {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        UnionFind uf = new UnionFind(100010);

        while (true) {
            int n = in.nextInt();
            int m = in.nextInt();
            if (n + m == 0) {
                break;
            }
            uf.claer(n);
            while (--m >= 0) {
                char c = in.nextToken().toCharArray()[0];
                int a = in.nextInt() - 1;
                int b = in.nextInt() - 1;
                if (c == '?') {
                    if (uf.issame(a, b)) {
                        out.println(uf.diffToParent[a] - uf.diffToParent[b]);
                    } else {
                        out.println("UNKNOWN");
                    }
                } else {
                    int w = in.nextInt();
                    uf.unite(a, b, w);
                }
            }
        }
        out.flush();
    }

    static class UnionFind {
        int[] parent, rank;
        long[] diffToParent;
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            diffToParent = new long[n];
            for (int i = 0 ; i < n ; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        void claer(int n) {
            for (int i = 0 ; i < n ; i++) {
                parent[i] = i;
                rank[i] = 0;
                diffToParent[i] = 0;
            }
        }

        int find(int x) {
            if (parent[x] == x) {
                return x;
            }
            int px = find(parent[x]);
            diffToParent[x] += diffToParent[parent[x]];
            parent[x] = px;
            return parent[x];
        }

        // x + w = y
        void unite(int x, int y, int w) {
            int xid = find(x);
            int yid = find(y);
            if (xid == yid) {
                return;
            }

            if (rank[xid] < rank[yid]) {
                parent[xid] = yid;
                diffToParent[xid] = w - diffToParent[x] + diffToParent[y];
                find(x);
            } else {
                parent[yid] = xid;
                diffToParent[yid] = - (w - diffToParent[x] + diffToParent[y]);
                find(y);
                if (rank[xid] == rank[yid]) {
                    rank[xid]++;
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
