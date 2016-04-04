package codeforces.cf3xx.cr327.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/10/25.
 */
public class C {
    private static final int INF = 10000000;

    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        char[][] bd = new char[n][];
        for (int i = 0; i < n ; i++) {
            bd[i] = in.nextToken().toCharArray();
        }

        int[][][] dist = new int[3][][];
        for (int i = 0; i < 3 ; i++) {
            dist[i] = doit(bd, (char) ('1'+i));
        }

        int best = solve(bd, dist);

        out.println(best >= INF/2 ? -1 : best);
        out.flush();
    }

    private static int solve(char[][] bd, int[][][] dist) {
        UnionFind uf = new UnionFind(3);
        int n = bd.length;
        int m = bd[0].length;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                for (int d = 0; d < 4 ; d++) {
                    int ti = i+dy[d];
                    int tj = j+dx[d];
                    if (ti < 0 || ti >= n || tj < 0 || tj >= m) {
                        continue;
                    }

                    if ('1' <= bd[i][j] && bd[i][j] <= '3') {
                        if ('1' <= bd[ti][tj] && bd[ti][tj] <= '3') {
                            int t1 = bd[i][j] - '1';
                            int t2 = bd[ti][tj] - '1';
                            uf.unite(t1, t2);
                        }
                    }
                }
            }
        }
        if (uf.issame(0, 1) && uf.issame(1, 2)) {
            return 0;
        }
        if (uf.issame(0, 1)) {
            return solve2(bd, dist, 0, 1, 2);
        }
        if (uf.issame(1, 2)) {
            return solve2(bd, dist, 1, 2, 0);
        }
        if (uf.issame(2, 0)) {
            return solve2(bd, dist, 2, 0, 1);
        }

        int best = INF;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                int D = dist[0][i][j] + dist[1][i][j] + dist[2][i][j] - 2;
                best = Math.min(best, D);
            }
        }

        for (int z1 = 0; z1 <= 2 ; z1++) {
            for (int z2 = z1+1 ; z2 <= 2  ; z2++) {
                int pairCost = solve2(bd, dist, z1, z2, 3 - z1 - z2);
                int connectCost = INF;
                for (int i = 0; i < n ; i++) {
                    for (int j = 0; j < m ; j++) {
                        int D = dist[z1][i][j] + dist[z2][i][j] - 1;
                        connectCost = Math.min(connectCost, D);
                    }
                }
                best = Math.min(best, pairCost+connectCost);
            }
        }
        return best;
    }


    // connect A0-A1 to B
    private static int solve2(char[][] bd, int[][][] dist, int A0, int A1, int B) {
        int n = bd.length;
        int m = bd[0].length;

        int best = INF;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                best = Math.min(best, dist[B][i][j] + Math.min(dist[A0][i][j], dist[A1][i][j]) - 1);
            }
        }
        return best;
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


    private static int[][] doit(char[][] bd, char c) {
        int n = bd.length;
        int m = bd[0].length;
        int[][] dp = new int[n][m];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(dp[i], INF);
        }
        int[] que = new int[4*n*m];
        int qh = 0;
        int qt = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (bd[i][j] == c) {
                    dp[i][j] = 0;
                    que[qh++] = i;
                    que[qh++] = j;
                    que[qh++] = 0;
                }
            }
        }
        while (qt < qh) {
            int ny = que[qt++];
            int nx = que[qt++];
            int nt = que[qt++];
            for (int d = 0 ; d < 4 ; d++) {
                int ty = ny+dy[d];
                int tx = nx+dx[d];
                if (ty < 0 || tx < 0 || ty >= n || tx >= m || bd[ty][tx] == '#') {
                    continue;
                }
                int tt = nt+1;
                if (dp[ty][tx] > tt) {
                    dp[ty][tx] = tt;
                    que[qh++] = ty;
                    que[qh++] = tx;
                    que[qh++] = tt;
                }
            }
        }
        return dp;
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
