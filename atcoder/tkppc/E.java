package atcoder.tkppc;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by hama_du on 15/08/01.
 */
public class E {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        tree = buildWeightedGraph(in, n, n-1);
        ans = new int[n];
        if (n == 1) {
            ans[0] = 0;
        } else {
            int[] t = diameter(0);
            if (t[0] > t[1]) {
                int tm = t[0];
                t[0] = t[1];
                t[1] = tm;
            }
            long[] A = bfs(t[0]);
            long[] B = bfs(t[1]);
            for (int i = 0; i < n ; i++) {
                if (A[i] >= B[i]) {
                    ans[i] = t[0];
                } else {
                    ans[i] = t[1];
                }
            }
        }

        for (int i = 0; i < n ; i++) {
            out.println(ans[i]+1);
        }
        out.flush();
    }

    static int[][][] tree;
    static int[] ans;

    static int[] que = new int[400000];
    static long[] ql = new long[400000];

    static long[] bfs(int root) {
        int qh = 0;
        int qt = 0;
        int n = tree.length;
        long[] dp = new long[n];
        Arrays.fill(dp, Long.MAX_VALUE);
        dp[root] = 0;
        que[qh] = root;
        ql[qh] = 0;
        qh++;
        while (qt < qh) {
            int now = que[qt];
            long dis = ql[qt];
            qt++;
            for (int[] t : tree[now]) {
                int to = t[0];
                long td = dis + t[1];
                if (dp[to] > td) {
                    dp[to] = td;
                    que[qh] = to;
                    ql[qh] = td;
                    qh++;
                }
            }
        }
        return dp;
    }


    static int[] diameter(int i) {
        long[] tan = dfs(i, -1);
        long[] tan2 = dfs((int)tan[0], -1);
        return new int[]{(int)tan[0], (int)tan2[0]};
    }

    static long[] dfs(int i, int parent) {
        long max = 0;
        long maxi = i;
        for (int[] e : tree[i]) {
            int to = e[0];
            if (to != parent) {
                long[] ret = dfs(to, i);
                ret[1] += e[1];
                if (max < ret[1]) {
                    max = ret[1];
                    maxi = ret[0];
                } else if (max == ret[1] && maxi > ret[0]) {
                    maxi = ret[0];
                }
            }
        }
        return new long[]{maxi, max};
    }


    static int[][][] buildWeightedGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][][] graph = new int[n][][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            int w = in.nextInt();
            deg[a]++;
            deg[b]++;
            edges[i] = new int[]{a, b, w};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]][2];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            int w = edges[i][2];
            graph[a][--deg[a]][0] = b;
            graph[b][--deg[b]][0] = a;
            graph[a][deg[a]][1] = w;
            graph[b][deg[b]][1] = w;
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
