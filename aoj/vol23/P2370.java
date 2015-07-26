package aoj.vol23;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/26.
 */
public class P2370 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        graph = buildGraph(in, n, m);

        out.println(solve(n, m));
        out.flush();
    }

    static long solve(int n, int m) {
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n ; i++) {
            for (int to : graph[i]) {
                uf.unite(i, to);
            }
        }
        color = new int[n];
        for (int i = 0; i < n ; i++) {
            if (color[i] == 0) {
                dfs(i, -1, 1);
            }
        }
        if (!isOK) {
            return -1;
        }

        int[] gids = new int[n];
        for (int i = 0; i < n ; i++) {
            gids[uf.find(i)]++;
        }
        int[][] gv = new int[n][];
        for (int i = 0; i < n ; i++) {
            gv[i] = new int[gids[i]];
        }
        for (int i = 0; i < n ; i++) {
            int id = uf.find(i);
            gv[id][--gids[id]] = i;
        }


        int gi = 0;
        int[][] groups = new int[n][2];
        for (int i = 0; i < n ; i++) {
            int white = 0;
            int black = 0;
            for (int v : gv[i]) {
                if (color[v] == 1) {
                    white++;
                } else {
                    black++;
                }
            }
            groups[gi][0] = white;
            groups[gi][1] = black;
            gi++;
        }

        int kinds = 0;
        int[] deg = new int[n+1];
        for (int i = 0; i < gi ; i++) {
            int diff =  Math.abs(groups[i][0] - groups[i][1]);
            deg[diff]++;
            if (deg[diff] == 1) {
                kinds++;
            }
        }
        int[][] items = new int[kinds][3];
        int ki = 0;
        int minus = 0;
        for (int i = 0; i <= n ; i++) {
            if (deg[i] >= 1) {
                items[ki][0] = 1;
                items[ki][1] = i * 2;
                items[ki][2] = deg[i];
                minus += i * deg[i];
                ki++;
            }
        }

        long max = 0;
        int[] dp = new int[2*n+10];
        Arrays.fill(dp, -10000000);
        dp[0] = 0;
        knapsack(dp, items, 2*n);

        for (int i = 0; i <= 2*n; i++) {
            if (i - minus >= 0 && dp[i] >= 0) {
                int diff = i - minus;
                if ((diff + n) % 2 == 0) {
                    int w = (diff + n) / 2;
                    int b = n - w;
                    if (w >= 0 && b >= 0) {
                        max = Math.max(max, 1L * w * b);
                    }
                }
            }
        }
        return max - m;
    }

    static void knapsack(int[] dp, int[][] item, int maxW) {
        int n = item.length;
        int[] deqIdx = new int[maxW+1];
        int[] deqVal = new int[maxW+1];
        for (int i = 0; i < n ; i++) {
            int v = item[i][0]; // value
            int w = item[i][1]; // weight
            int m = item[i][2]; // limit
            for (int a = 0 ; a < w ; a++) {
                int s = 0;
                int t = 0;
                for (int j = 0; j * w + a <= maxW ; j++) {
                    int val = dp[j * w + a] - j * v;
                    while (s < t && deqVal[t-1] <= val) {
                        t--;
                    }
                    deqIdx[t] = j;
                    deqVal[t] = val;
                    t++;
                    dp[j * w + a] = Math.max(dp[j * w + a], deqVal[s] + j * v);
                    if (deqIdx[s] == j - m) {
                        s++;
                    }
                }
            }
        }
    }

    static int[] color;
    static boolean isOK = true;
    static int[][] graph;

    static void dfs(int now, int par, int col) {
        if (color[now] != 0) {
            if (color[now] != col) {
                isOK = false;
            }
            return;
        }
        color[now] = col;
        for (int to : graph[now]) {
            if (to != par) {
                dfs(to, now, -col);
            }
        }
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

    static int[][] buildGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            deg[a]++;
            deg[b]++;
            edges[i] = new int[]{a, b};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
            graph[b][--deg[b]] = a;
        }
        return graph;
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
