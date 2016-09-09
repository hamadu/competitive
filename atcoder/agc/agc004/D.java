package atcoder.agc.agc004;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 2016/09/04.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        int[] a = in.nextInts(n);
        for (int i = 0; i < n ; i++) {
            a[i]--;
        }
        if (k >= n) {
            out.println(a[0] == 0 ? 0 : 1);
            out.flush();
            return;
        }

        int[][] edges = new int[n-1][2];
        for (int i = 1 ; i < n ; i++) {
            edges[i-1][0] = a[i];
            edges[i-1][1] = i;
        }
        int[][] graph = buildGraph(n, edges);
        int[][] pco = parentCountOrder(graph, 0);


        int[] par = pco[0];
        int[] ord = pco[2];

        int ans = (a[0] == 0) ? 0 : 1;
        int[] maxDepth = new int[n];
        for (int i = n-1 ; i >= 0 ; i--) {
            int now = ord[i];
            int p = par[now];
            if (p == -1) {
                continue;
            }
            if (maxDepth[now] >= k-1) {
                if (p != 0) {
                    ans++;
                }
            } else {
                maxDepth[p] = Math.max(maxDepth[p], maxDepth[now]+1);
            }
        }

        out.println(ans);
        out.flush();
    }


    public static int[][] parentCountOrder(int[][] graph, int root) {
        int n = graph.length;
        int[] que = new int[2*n];
        int[] parent = new int[n];
        int[] bfsOrd = new int[n];
        int[] cnt = new int[n];
        int qh = 0, qt = 0;
        que[qh++] = root;
        que[qh++] = -1;
        int vi = 0;
        while (qt < qh) {
            int now = que[qt++];
            int par = que[qt++];
            parent[now] = par;
            bfsOrd[vi++] = now;
            for (int to : graph[now]) {
                if (to == par) {
                    continue;
                }
                que[qh++] = to;
                que[qh++] = now;
            }
        }
        for (int i = n-1 ; i >= 0 ; i--) {
            int now = bfsOrd[i];
            cnt[now]++;
            if (parent[now] != -1) {
                cnt[parent[now]] += cnt[now];
            }
        }
        return new int[][]{ parent, cnt, bfsOrd };
    }

    static int[][] buildGraph(int n, int[][] edges) {
        int m = edges.length;
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            deg[a]++;
            deg[b]++;
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

        private int[] nextInts(int n) {
            int[] ret = new int[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextInt();
            }
            return ret;
        }


        private int[][] nextIntTable(int n, int m) {
            int[][] ret = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextInt();
                }
            }
            return ret;
        }

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
            }
            return ret;
        }

        private long[][] nextLongTable(int n, int m) {
            long[][] ret = new long[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextLong();
                }
            }
            return ret;
        }

        private double[] nextDoubles(int n) {
            double[] ret = new double[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextDouble();
            }
            return ret;
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
            return res*sgn;
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
            return res*sgn;
        }

        public double nextDouble() {
            return Double.valueOf(nextToken());
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
