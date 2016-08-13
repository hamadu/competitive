package csacademy.round008;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/08/13.
 */
public class E {
    private static final long INF = (long)1e18;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] score = in.nextInts(n);
        int[][] graph = buildGraph(in, n, n-1);

        int[] par = new int[n];
        Arrays.fill(par, -1);
        int[] que = new int[n];
        int qh = 0;
        int qt = 0;
        que[qh++] = 0;
        while (qt < qh) {
            int now = que[qt++];
            for (int to : graph[now]) {
                if (to != par[now]) {
                    par[to] = now;
                    que[qh++] = to;
                }
            }
        }

        long[][] dp = new long[n][2];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(dp[i], -INF);
        }

        for (int i = n-1 ; i >= 0 ; i--) {
            int v = que[i];
            int ct = (par[i] == -1) ? graph[v].length : graph[v].length-1;
            if (ct == 0) {
                dp[v][0] = score[0];
                if (score.length > 1) {
                    dp[v][1] = score[1];
                }
                continue;
            }

            long[] con = new long[ct];
            int ci = 0;
            for (int to : graph[v]) {
                if (to == par[v]) {
                    continue;
                }
                con[ci++] = dp[to][1];
            }
            Arrays.sort(con);


            // connected with par
            if (par[v] != -1) {
                long sum = 0;
                for (int deg = 1 ; deg <= 1+ct ; deg++) {
                    dp[v][1] = Math.max(dp[v][1], score[deg] + sum);
                    if (ct-deg >= 0) {
                        sum += con[ct-deg];
                    }
                }
            }

            // distconnectd with par
            long sum = 0;
            for (int deg = 0 ; deg <= ct ; deg++) {
                dp[v][0] = Math.max(dp[v][0], score[deg] + sum);
                if (ct-deg-1 >= 0) {
                    sum += con[ct-deg-1];
                }
            }
        }

        long max = -INF;
        for (int i = 0 ; i < n ; i++) {
            max = Math.max(max, dp[i][0]);
        }
        out.println(max);
        out.flush();
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
