package codeforces.cr308.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;

/**
 * Created by hama_du on 15/06/20.
 */
public class D {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] pos = new int[210][210];
        int[][] idx = new int[210][210];
        for (int i = 0; i < 210 ; i++) {
            Arrays.fill(idx[i], -1);
        }


        Set<Integer> pt = new HashSet<>();
        for (int i = 0; i < n ; i++) {
            int x = in.nextInt() + 100;
            int y = in.nextInt() + 100;
            pos[y][x]++;
            pt.add((y<<8)+x);
        }

        int pn = pt.size();
        int[][] points = new int[pn][2];
        {
            int pi = 0;
            for (int p : pt) {
                int ti = (p >> 8);
                int tj = (p & 255);
                points[pi][0] = ti;
                points[pi][1] = tj;
                idx[ti][tj] = pi;
                pi++;
            }
        }


        UnionFind uf = new UnionFind(n+1);

        long ans = 0;
        for (int i = 0 ; i < pos.length ; i++) {
            for (int j = 0; j < pos[0].length; j++) {
                if (pos[i][j] == 0) {
                    continue;
                }
                uf.clear();
                for (int pi = 0; pi < pn; pi++) {
                    int ti = points[pi][0];
                    int tj = points[pi][1];
                    int dy = (ti - i) + 200;
                    int dx = (tj - j) + 200;
                    int did = dy * 410 + dx;
                    uf.cnt[did] = pos[ti][tj];
                }

                for (int pi = 0; pi < pn; pi++) {
                    int ti = points[pi][0];
                    int tj = points[pi][1];
                    int bdy = (ti - i);
                    int bdx = (tj - j);
                    int dy = bdy;
                    int dx = bdx;
                    if (dy == 0 && dx == 0) {
                        continue;
                    }
                    while (Math.abs(dx) <= 210 && Math.abs(dy) <= 210) {
                        int tdx = dx + bdx;
                        int tdy = dy + bdy;
                        int id1 = (dy + 250) * 500 + (dx + 250);
                        int id2 = (tdy + 250) * 410 + (tdx + 250);
                        uf.unite(id1, id2);
                        dy = tdy;
                        dx = tdx;
                    }
                }

                for (int pi = 0; pi < pn; pi++) {
                    int ti = points[pi][0];
                    int tj = points[pi][1];
                    int dy = (ti - i);
                    int dx = (tj - j);
                    int id1 = (dy + 200) * 410 + (dx + 200);
                    long pair = 1L * pos[i][j] * pos[ti][tj];
                    long left = n - pos[i][j] - uf.cnt[uf.find(id1)];
                    ans += pair * left;
                }
            }
        }
        out.println(ans);
        out.flush();
    }

    static class UnionFind {
        int N;
        int[] parent, rank, cnt;
        UnionFind(int n) {
            N = n;
            parent = new int[n];
            rank = new int[n];
            cnt = new int[n];
            clear();
        }

        void clear() {
            for (int i = 0 ; i < N ; i++) {
                parent[i] = i;
                rank[i] = 0;
                cnt[i] = 0;
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
            int tct = cnt[x] + cnt[y];
            cnt[x] = cnt[y] = tct;
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
                return (char)c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char)c;
            }
            throw new InputMismatchException();
        }

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char)c);
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
