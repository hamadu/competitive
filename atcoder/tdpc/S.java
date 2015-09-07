package atcoder.tdpc;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 15/09/01.
 */
public class S {
    private static final long MOD = 1000000007;

    static final int[] dx = {1, 0, -1, 0};
    static final int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        h = in.nextInt();
        w = in.nextInt();

        idmap = new int[1<<20];
        codes = new int[4000][h];
        Arrays.fill(idmap, -1);

        dfs(0, new int[h]);

        int[][] trans = new int[cn][1<<h];
        for (int ci = 0; ci < cn ; ci++) {
            Arrays.fill(trans[ci], -1);
            for (int p = 0; p < 1<<h ; p++) {
                int[][] map = new int[2][h];
                map[0] = codes[ci].clone();
                for (int i = 0; i < h ; i++) {
                    if ((p & (1<<i)) >= 1) {
                        map[1][i] = h+i;
                    }
                }
                UnionFind uf = new UnionFind(2*h+1);
                for (int i = 0; i < 2 ; i++) {
                    for (int j = 0; j < h; j++) {
                        for (int d = 0; d < 4; d++) {
                            int ti = i+dy[d];
                            int tj = j+dx[d];
                            if (ti < 0 || tj < 0 || ti >= 2 || tj >= h) {
                                continue;
                            }
                            if (map[i][j] >= 1 && map[ti][tj] >= 1) {
                                uf.unite(map[i][j], map[ti][tj]);
                            }
                        }
                    }
                }

                for (int k = 1 ; k <= 2*h ; k++) {
                    for (int i = 0; i < h ; i++) {
                        if (uf.issame(k, map[1][i])) {
                            map[1][i] = Math.min(map[1][i], k);
                        }
                    }
                }
                boolean isOK = false;
                int max = 0;
                for (int i = 0; i < h ; i++) {
                    if (map[1][i] == 1) {
                        isOK = true;
                    }
                    max = Math.max(max, map[1][i]);
                }
                if (!isOK) {
                    continue;
                }
                int[] to = normalize(map[1]);
                trans[ci][p] = idmap[encode(to)];
            }
        }


        long[][] dp = new long[w+1][cn];
        int[] ix = new int[h];
        ix[0] = 1;
        dp[0][idmap[encode(ix)]] = 1;
        for (int i = 0; i < w ; i++) {
            for (int j = 0; j < cn ; j++) {
                if (dp[i][j] == 0) {
                    continue;
                }
                long base = dp[i][j];
                for (int p = 0; p < (1<<h); p++) {
                    if (trans[j][p] != -1) {
                        dp[i+1][trans[j][p]] += base;
                        dp[i+1][trans[j][p]] %= MOD;
                    }
                }
            }
        }

        long sum = 0;
        for (int i = 0; i < cn ; i++) {
            if (codes[i][h-1] == 1) {
                sum += dp[w][i];
            }
        }
        sum %= MOD;
        out.println(sum);
        out.flush();
    }

    static int[] idmap;
    static int[][] codes;
    static int cn;

    static int h;
    static int w;
    static void dfs(int idx, int[] val) {
        if (idx == h) {
            int[] nm = normalize(val);
            if (!isValid(nm)) {
                return;
            }
            int id = encode(nm);
            if (idmap[id] != -1) {
                return;
            }
            idmap[id] = cn;
            codes[cn] = nm;
            cn++;
            return;
        }
        for (int i = 0; i <= 4; i++) {
            val[idx] = i;
            dfs(idx+1, val);
        }
    }

    static boolean isValid(int[] data) {
        return isValid(new int[][]{data});
    }

    static boolean isValid(int[][] data) {
        for (int i = 0; i < data.length ; i++) {
            for (int j = 0; j < data[0].length ; j++) {
                for (int d = 0; d < 4 ; d++) {
                    int ti = i+dy[d];
                    int tj = j+dx[d];
                    if (ti < 0 || tj < 0 || ti >= data.length || tj >= data[0].length) {
                        continue;
                    }
                    if (data[i][j] >= 1 && data[ti][tj] >= 1 && data[i][j] != data[ti][tj]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    static int[] normalize(int[] data) {
        int[] ret = new int[data.length];
        int[] map = new int[20];
        int id = 2;
        for (int i = 0; i < data.length ; i++) {
            if (data[i] >= 2) {
                if (map[data[i]] == 0) {
                    map[data[i]] = id;
                    id++;
                }
                ret[i] = map[data[i]];
            } else {
                ret[i] = data[i];
            }
        }
        return ret;
    }

    static int encode(int[] data) {
        int ret = 0;
        for (int i = 0; i < data.length ; i++) {
            ret <<= 3;
            ret |= data[i];
        }
        return ret;
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
