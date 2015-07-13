package aoj.vol23;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/13.
 */
public class P2376 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        char[][] graph = new char[n][];
        for (int i = 0; i < n ; i++) {
            graph[i] = in.nextToken().toCharArray();
        }

        UnionFind uf = new UnionFind(n);
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (graph[i][j] == 'Y') {
                    uf.unite(i, j);
                }
            }
        }

        int[] gcnt = new int[n];
        for (int i = 0 ; i < n ; i++) {
            gcnt[uf.find(i)]++;
        }

        int odd = 0;
        int even = 0;
        for (int i = 0 ; i < n ; i++) {
            if (gcnt[i] >= 1) {
                if (gcnt[i] % 2 == 1) {
                    odd++;
                } else {
                    even++;
                }
            }
        }

        int firstParity = 0;
        boolean[] done = new boolean[n];
        for (int i = 0; i < n ; i++) {
            if (done[i]) {
                continue;
            }
            int[] de = new int[n];
            int di = 0;
            for (int j = 0; j < n ; j++) {
                if (uf.issame(i, j)) {
                    de[di++] = j;
                    done[j] = true;
                }
            }

            int all = di * (di - 1) / 2;
            for (int a = 0; a < di ; a++) {
                for (int b = a+1; b < di ; b++) {
                    int ai = de[a];
                    int bi = de[b];
                    if (graph[ai][bi] == 'Y') {
                        all--;
                    }
                }
            }
            if (all % 2 == 1) {
                firstParity = 1 - firstParity;
            }
        }

        dp = new int[2][1010][1010];
        dp[0][1][0] = dp[0][0][1] = dp[1][1][0] = dp[1][0][1] = -1;
        for (int i = 0; i < 2; i++) {
            for (int o = 0 ; o <= 2 ; o++) {
                for (int e = 0; e <= 2; e++) {
                    if (o+e == 2) {
                        dp[i][o][e] = (i == 1) ? 1 : -1;
                    }
                }
            }
        }

        out.println(dfs(firstParity, odd, even) == 1 ? "Taro" : "Hanako");
        out.flush();
    }

    static int[][][] dp;

    static int dfs(int parity, int odd, int even) {
        if (dp[parity][odd][even] != 0) {
            return dp[parity][odd][even];
        }

        boolean canWin = false;

        // odd,odd
        if (odd >= 2) {
            canWin |= dfs(parity, odd-2, even+1) == -1;
        }

        // odd,even
        if (odd >= 1 && even >= 1) {
            canWin |= dfs(1-parity, odd, even-1) == -1;
        }

        // even,even
        if (even >= 2) {
            canWin |= dfs(1-parity, odd, even-1) == -1;
        }

        if (canWin) {
            dp[parity][odd][even] = 1;
        } else {
            dp[parity][odd][even] = -1;
        }
        return dp[parity][odd][even];
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
