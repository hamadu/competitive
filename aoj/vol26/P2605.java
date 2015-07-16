package aoj.vol26;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/15.
 */
public class P2605 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int K = in.nextInt();
        graph = buildWeightedGraph(in, n, m);
        UnionFind uf = new UnionFind(n);

        int[][] edges = new int[n][n];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(edges[i], Integer.MIN_VALUE);
            for (int[] ed : graph[i]){
                edges[i][ed[0]] = ed[1];
                uf.unite(i, ed[0]);
            }
        }

        boolean[] done = new boolean[n];
        int gn = 0;
        int[][] scores = new int[n][];
        vids = new int[n];

        for (int mi = 0; mi < n ; mi++) {
            if (done[mi]) {
                continue;
            }

            looped = true;
            int head = mi;
            for (int j = 0; j < n ; j++) {
                if (uf.issame(mi, j) && graph[j].length == 1) {
                    head = j;
                    looped = false;
                    break;
                }
            }

            vn = 0;
            dfs(head, -1, done);
            scores[gn] = new int[vn+1];
            Arrays.fill(scores[gn], Integer.MIN_VALUE);
            scores[gn][0] = 0;
            if (vn == 1) {
                looped = false;
            }

            if (looped) {
                int[][][][] dp2 = new int[vn+1][vn+1][2][2];
                for (int i = 0; i <= vn ; i++) {
                    for (int j = 0; j <= vn; j++) {
                        for (int k = 0; k <= 1; k++) {
                            Arrays.fill(dp2[i][j][k], Integer.MIN_VALUE);
                        }
                    }
                }
                dp2[0][0][0][0] = 0;
                dp2[0][1][1][1] = 0;


                for (int i = 0; i < vn ; i++) {
                    int my = vids[i];
                    int ne = vids[(i + 1) % vn];
                    for (int j = 0; j <= vn; j++) {
                        for (int prev = 0; prev <= 1; prev++) {
                            for (int fst = 0; fst <= 1; fst++) {
                                if (dp2[i][j][prev][fst] == Integer.MIN_VALUE) {
                                    continue;
                                }
                                int base = dp2[i][j][prev][fst];

                                // not pick
                                if (i+1 < vn || (i+1 == vn && fst == 0)) {
                                    dp2[i + 1][j][0][fst] = Math.max(dp2[i + 1][j][0][fst], base);
                                }

                                // pick
                                if (i+1 < vn || (i+1 == vn && fst == 1)) {
                                    if (prev == 0 || (prev == 1 && edges[my][ne] != 0)) {
                                        int plus = (prev == 0) ? 0 : edges[my][ne];
                                        int tj = (i+1 == vn && fst == 1) ? j : j+1;
                                        dp2[i+1][tj][1][fst] = Math.max(dp2[i+1][tj][1][fst], base + plus);
                                    }
                                }
                            }
                        }
                    }
                }

                for (int pc = 0 ; pc <= vn ; pc++) {
                    for (int prev = 0; prev <= 1 ; prev++) {
                        for (int fst = 0; fst <= 1; fst++) {
                            scores[gn][pc] = Math.max(scores[gn][pc], dp2[vn][pc][prev][fst]);
                        }
                    }
                }

            } else {
                int[][][] dp2 = new int[vn][vn+1][2];
                for (int i = 0; i < vn ; i++) {
                    for (int j = 0; j <= vn; j++) {
                        Arrays.fill(dp2[i][j], Integer.MIN_VALUE);
                    }
                }
                dp2[0][0][0] = 0;
                dp2[0][1][1] = 0;
                for (int i = 0; i < vn-1 ; i++) {
                    int my = vids[i];
                    int ne = vids[i+1];
                    for (int j = 0; j <= vn; j++) {
                        for (int prev = 0; prev <= 1 ; prev++) {
                            if (dp2[i][j][prev] == Integer.MIN_VALUE) {
                                continue;
                            }
                            int base = dp2[i][j][prev];

                            // not pick
                            dp2[i+1][j][0] = Math.max(dp2[i+1][j][0], base);

                            if (prev == 0 || (prev == 1 && edges[my][ne] != 0)) {
                                int plus = (prev == 0) ? 0 : edges[my][ne];
                                dp2[i+1][j+1][1] = Math.max(dp2[i+1][j+1][1], base + plus);
                            }
                        }
                    }
                }

                for (int pc = 0 ; pc <= vn ; pc++) {
                    for (int prev = 0; prev <= 1 ; prev++) {
                        scores[gn][pc] = Math.max(scores[gn][pc], dp2[vn-1][pc][prev]);
                    }
                }
            }
            gn++;
        }

        int[][] dp = new int[gn+1][K+1];
        for (int i = 0 ; i <= gn ; i++) {
            Arrays.fill(dp[i], Integer.MIN_VALUE);
        }
        dp[0][0] = 0;

        for (int i = 0 ; i < gn ; i++) {
            for (int j = 0 ; j <= K ; j++) {
                if (dp[i][j] == Integer.MIN_VALUE) {
                    continue;
                }
                int base = dp[i][j];
                for (int use = 0 ; use < scores[i].length ; use++) {
                    if (scores[i][use] == Integer.MIN_VALUE || use + j > K) {
                        continue;
                    }
                    int tj = j + use;
                    dp[i+1][tj] = Math.max(dp[i+1][tj], base + scores[i][use]);
                }
            }
        }

        if (dp[gn][K] == Integer.MIN_VALUE) {
            out.println("Impossible");
        } else {
            out.println(dp[gn][K]);
        }
        out.flush();
    }

    static int[] vids;
    static int vn;
    static int[][][] graph;
    static boolean looped = false;

    static void dfs(int now, int par, boolean[] done) {
        vids[vn++] = now;
        done[now] = true;
        for (int[] ed : graph[now]) {
            int to = ed[0];
            if (to != par) {
                if (!done[to]) {
                    dfs(to, now, done);
                }
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
