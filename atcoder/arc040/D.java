package atcoder.arc040;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/08.
 */
public class D {

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        char[][] map = new char[n][n];

        int sx = 0;
        int sy = 0;
        for (int i = 0; i < n; i++) {
            map[i] = in.nextToken().toCharArray();
            for (int j = 0; j < n; j++) {
                if (map[i][j] == 's') {
                    sy = i;
                    sx = j;
                    map[sy][sx] = '.';
                }
            }
        }
        boolean isOK = false;
        for (int d = 0 ; d < 4 ; d++) {
            isOK |= solve(map, sy, sx, d);
            if (isOK) {
                break;
            }
        }
        out.println(isOK ? "POSSIBLE" : "IMPOSSIBLE");
        out.flush();
    }

    public static boolean solve(char[][] map, int sy, int sx, int ds) {
        int n = map.length;
        int tsy = sy + dy[ds];
        int tsx = sx + dx[ds];
        if (tsy < 0 || tsx < 0 || tsy >= n || tsx >= n || map[tsy][tsx] == '#') {
            return false;
        }

        boolean[] candidateX = new boolean[n];
        map[sy][sx] = (sx != tsx) ? '#' : '.';
        for (int j = 0 ; j < n ; j++) {
            int cnt = 0;
            for (int i = 0 ; i < n ; i++) {
                if (map[i][j] == '.') {
                    cnt++;
                }
            }
            if (cnt % 2 == 1) {
                candidateX[j] = true;
            }
        }

        boolean[] candidateY = new boolean[n];
        map[sy][sx] = (sx != tsx) ? '.' : '#';
        for (int i = 0 ; i < n ; i++) {
            int cnt = 0;
            for (int j = 0 ; j < n ; j++) {
                if (map[i][j] == '.') {
                    cnt++;
                }
            }
            if (cnt % 2 == 1) {
                candidateY[i] = true;
            }
        }
        map[sy][sx] = '.';

        int badParityCountX = 0;
        int badParityCountY = 0;
        for (int i = 0 ; i < n ; i++) {
            if (candidateX[i]) {
                badParityCountX++;
            }
            if (candidateY[i]) {
                badParityCountY++;
            }
        }
        if (badParityCountX >= 2 || badParityCountY >= 2) {
            return false;
        }

        for (int gy = 0; gy < n; gy++) {
            for (int gx = 0; gx < n; gx++) {
                if (gy == sy && gx == sx) {
                    continue;
                }
                if (map[gy][gx] == '#') {
                    continue;
                }
                if (!candidateY[gy] && !candidateX[gx]) {
                    continue;
                }
                for (int dg = 0; dg < 4; dg++) {
                    int tgy = gy + dy[dg];
                    int tgx = gx + dx[dg];
                    if (tgy < 0 || tgx < 0 || tgy >= n || tgx >= n || map[tgy][tgx] == '#') {
                        continue;
                    }
                    if (candidateY[gy]) {
                        if (dy[dg] == 0) {
                            continue;
                        }
                    }
                    if (candidateX[gx]) {
                        if (dx[dg] == 0) {
                            continue;
                        }
                    }

                    boolean isOK = solve(sy, sx, tsy, tsx, gy, gx, tgy, tgx, map);
                    if (isOK) {
                        return true;
                    }
                    map[sy][sx] = map[gy][gx] = '.';
                }
            }
        }
        return false;
    }

    private static boolean solve(int sy, int sx, int tsy, int tsx, int gy, int gx, int tgy, int tgx, char[][] map) {
        int n = map.length;
        UnionFind uf = new UnionFind(n*n);

        // vertical
        map[sy][sx] = (sx != tsx) ? '#' : '.';
        map[gy][gx] = (gx != tgx) ? '#' : '.';
        for (int c = 0 ; c < n ; c++) {
            for (int r = 0 ; r < n ; r++) {
                if (map[r][c] == '.') {
                    if (r+1 < n && map[r+1][c] == '.' && !uf.issame(r*n+c, (r+1)*n+c)) {
                        uf.unite(r*n+c, (r+1)*n+c);
                        r++;
                    } else {
                        return false;
                    }
                }
            }
        }

        // horizontal
        map[sy][sx] = (sx != tsx) ? '.' : '#';
        map[gy][gx] = (gx != tgx) ? '.' : '#';

        for (int r = 0 ; r < n ; r++) {
            for (int c = 0 ; c < n ; c++) {
                if (map[r][c] == '.') {
                    if (c+1 < n && map[r][c+1] == '.' && !uf.issame(r*n+c, r*n+c+1)) {
                        uf.unite(r*n+c, r*n+c+1);
                        c++;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
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
