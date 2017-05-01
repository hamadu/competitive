package hackerrank.codesprint10;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class MaximumDisjointSubtreeProduct {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        w = in.nextInts(n);
        graph = buildGraph(in, n, n-1);

        cids = new int[n][];
        gMin = new int[n][];
        gMax = new int[n][];
        gHasmin = new int[n][];
        gHasmax = new int[n][];
        for (int i = 0; i < graph.length; i++) {
            int cn = graph[i].length-((i != 0) ? 1 : 0);
            gMin[i] = new int[cn];
            gMax[i] = new int[cn];
            gHasmin[i] = new int[cn+1];
            gHasmax[i] = new int[cn+1];
            cids[i] = new int[cn];
        }

        ans = Long.MIN_VALUE;
        hasmin = new int[n];
        hasmax = new int[n];
        min = new int[n];
        max = new int[n];
        parhasmin = new int[n];
        parhasmax = new int[n];
        parmin = new int[n];
        parmax = new int[n];

        dfs(0, -1);
        dfs2(0);

        out.println(ans);
        out.flush();
    }

    static void dfs(int now, int par) {
        for (int to : graph[now]) {
            if (to == par) {
                continue;
            }
            dfs(to, now);
        }

        int ci = 0;
        hasmin[now] = w[now];
        hasmax[now] = w[now];
        for (int to : graph[now]) {
            if (to == par) {
                continue;
            }
            cids[now][ci++] = to;
            min[now] = Math.min(min[now], min[to]);
            max[now] = Math.max(max[now], max[to]);
            if (hasmin[to] < 0) {
                hasmin[now] += hasmin[to];
            }
            if (hasmax[to] > 0) {
                hasmax[now] += hasmax[to];
            }
        }
        min[now] = Math.min(min[now], hasmin[now]);
        max[now] = Math.max(max[now], hasmax[now]);
    }

    static void dfs2(int now) {
        int cn = cids[now].length;


        int ghasmax = 0;
        int ghasmin = 0;
        int[] gmin = gMin[now];
        int[] gmax = gMax[now];
        for (int i = 0 ; i < cn ; i++) {
            int c = cids[now][i];
            ghasmax += Math.max(hasmax[c], 0);
            ghasmin += Math.min(hasmin[c], 0);
            gmin[i] = min[c];
            gmax[i] = max[c];
        }
        Arrays.sort(gmin);
        Arrays.sort(gmax);

        for (int i = 0 ; i < cn ; i++) {
            int c = cids[now][i];

            {
                int ghmax = ghasmax - Math.max(hasmax[c], 0) + parhasmax[now] + w[now];
                int ghmin = ghasmin - Math.min(hasmin[c], 0) + parhasmin[now] + w[now];
                ghmax = Math.max(ghmax, ghasmax - Math.max(hasmax[c], 0) + w[now]);
                ghmin = Math.min(ghmin, ghasmin - Math.min(hasmin[c], 0) + w[now]);

                int ggmax = Math.max(ghmax, parmax[now]);
                int ggmin = Math.min(ghmin, parmin[now]);
                if (cn >= 2) {
                    ggmax = Math.max(ggmax, max[c] == gmax[cn-1] ? gmax[cn-2] : gmax[cn-1]);
                    ggmin = Math.min(ggmin, min[c] == gmin[0] ? gmin[1] : gmin[0]);
                }
                parhasmax[c] = ghmax;
                parhasmin[c] = ghmin;
                parmax[c] = ggmax;
                parmin[c] = ggmin;

                ans = Math.max(ans, (long)parmax[c] * max[c]);
                ans = Math.max(ans, (long)parmin[c] * min[c]);
            }
            dfs2(c);
        }
    }



    static int[] parmin;
    static int[] parmax;
    static int[] parhasmin;
    static int[] parhasmax;


    static long ans = 0;
    static int[] hasmin;
    static int[] hasmax;
    static int[] min;
    static int[] max;
    static int[] w;
    static int[][] graph;
    static int[][] gMin;
    static int[][] gMax;
    static int[][] gHasmin;
    static int[][] gHasmax;
    static int[][] cids;


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
