package codeforces.cr309.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 15/07/09.
 */
public class C {

    static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        List<int[]> love = new ArrayList<>();
        List<int[]> hate = new ArrayList<>();
        for (int i = 0; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            int c = in.nextInt();
            if (c == 0) {
                hate.add(new int[]{a, b});
            } else {
                love.add(new int[]{a, b});
            }
        }

        out.println(solve(n, love, hate));
        out.flush();
    }

    private static long solve(int n, List<int[]> love, List<int[]> hate) {
        UnionFind uf = new UnionFind(n);
        for (int[] edge : love) {
            uf.unite(edge[0], edge[1]);
        }

        int[] gid = new int[n];
        for (int i = 0 ; i < n ; i++) {
            gid[i] = uf.find(i);
        }

        List<Integer>[] hateGraph = new List[n];
        for (int i = 0; i < n ; i++) {
            hateGraph[i] = new ArrayList<>();
        }

        for (int[] edge : hate) {
            if (uf.issame(edge[0], edge[1])) {
                return 0;
            }
            int a = gid[edge[0]];
            int b = gid[edge[1]];
            hateGraph[a].add(b);
            hateGraph[b].add(a);
        }

        if (!isBiperate(hateGraph)) {
            return 0;
        }

        for (int[] edge : hate) {
            uf.unite(edge[0], edge[1]);
        }

        boolean[] foe = new boolean[n];
        int ren = 0;
        for (int i = 0 ; i < n ; i++) {
            int id = uf.find(i);
            if (!foe[id]) {
                foe[id] = true;
                ren++;
            }
        }

        long ret = 1;
        for (int i = 0 ; i < ren - 1 ; i++) {
            ret *= 2;
            ret %= MOD;
        }
        return ret;
    }

    static boolean isBiperate(List<Integer>[] g) {
        int n = g.length;
        isOK = true;
        color = new int[n];
        graph = g;
        for (int i = 0 ; i < n ; i++) {
            if (color[i] == 0) {
                dfs(i, -1, 1);
            }
        }
        return isOK;
    }

    static boolean isOK = true;
    static List<Integer>[] graph;
    static int[] color;

    static void dfs(int now, int par, int col) {
        if (color[now] != 0) {
            isOK &= color[now] == col;
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
