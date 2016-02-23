package hackerrank.worldcodesprint2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/01/30.
 */
public class Bus {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] graph = buildGraph(in, n, n-1);
        LCA lca = new LCA(graph);
        if (n > 10000) {
            throw new RuntimeException();
        }

        bitvec = new long[n+1][200];
        int[][] minmax = new int[n][2];
        for (int i = 0 ; i < n-1 ; i++) {
            setvec(i, i);
            setvec(i, i+1);
            int l = lca.lca(i, i+1);
            int left = i;
            int right = i+1;
            int min = i;
            int max = i+1;
            while (left != l) {
                left = lca.parent[0][left];
                setvec(i, left);
                min = Math.min(min, left);
                max = Math.max(max, left);
            }
            while (right != l) {
                right = lca.parent[0][right];
                setvec(i, right);
                min = Math.min(min, right);
                max = Math.max(max, right);
            }
            minmax[i][0] = min;
            minmax[i][1] = max;
        }


        long cnt = 0;
        for (int i = 0 ; i < n ; i++) {
            cnt++;
            Arrays.fill(bitvec[n], 0);
            setvec(n, i);
            int max = i;
            for (int head = i+1; head < n ; head++) {
                if (getvec(n, head)) {
                    continue;
                }
                int bitcount = mergevec(n, head-1);
                if (minmax[head-1][0] < i) {
                    break;
                }
                max = Math.max(max, minmax[head-1][1]);
                if (max - i + 1 == bitcount) {
                    cnt++;
                }
            }
        }
        out.println(cnt);
        out.flush();
    }

    private static void setvec(int i, int p) {
        int idx = p / 50;
        int pos = p % 50;
        bitvec[i][idx] |= 1L<<pos;
    }

    private static boolean getvec(int i, int p) {
        int idx = p / 50;
        int pos = p % 50;
        return (bitvec[i][idx] & (1L<<pos)) >= 1;
    }

    private static int mergevec(int i, int j) {
        // i <- j
        int bitcnt = 0;
        for (int k = 199 ; k >= 0 ; k--) {
            bitvec[i][k] |= bitvec[j][k];
            bitcnt += Long.bitCount(bitvec[i][k]);
        }
        return bitcnt;
    }


    static long[][] bitvec;

    static class LCA {
        int[][] graph;
        int[][] parent;
        int[] depth;

        public LCA(int[][] graph) {
            int n = graph.length;
            this.graph = graph;
            init(n);
        }

        void dfs(int now, int from, int dep) {
            parent[0][now] = from;
            depth[now] = dep;
            for (int to : graph[now]) {
                if (to != from) {
                    dfs(to, now, dep+1);
                }
            }
        }

        void init(int n) {
            int log = 1;
            int nn = n;
            while (nn >= 1) {
                nn /= 2;
                log++;
            }
            parent = new int[log+1][n];
            for (int i = 0 ; i <= log ; i++) {
                Arrays.fill(parent[i], -1);
            }
            depth = new int[n];

            dfs(0, -1, 0);

            for (int k = 0 ; k < log ; k++) {
                for (int v = 0 ; v < n ; v++) {
                    if (parent[k][v] < 0) {
                        parent[k+1][v] = -1;
                    } else {
                        parent[k+1][v] = parent[k][parent[k][v]];
                    }
                }
            }
        }

        int lca(int u, int v) {
            int loglen = parent.length;
            if (depth[u] > depth[v]) {
                int tmp = u;
                u = v;
                v = tmp;
            }
            for (int k = 0 ; k < loglen ; k++) {
                if (((depth[v] - depth[u]) >> k) % 2 == 1) {
                    v = parent[k][v];
                }
            }
            if (u == v) {
                return u;
            }

            for (int k = loglen-1 ; k >= 0 ; k--) {
                if (parent[k][u] != parent[k][v]) {
                    u = parent[k][u];
                    v = parent[k][v];
                }
            }
            return parent[0][u];
        }

        int dist(int x, int y) {
            int l = lca(x, y);
            return depth[x] + depth[y] - depth[l] * 2;
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
