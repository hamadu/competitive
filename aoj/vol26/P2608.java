package aoj.vol26;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/25.
 */
public class P2608 {
    private static final int INF = 100000000;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int s = in.nextInt()-1;
        int t = in.nextInt()-1;

        int[][] graph = buildGraph(in, n, m);
        int[] l1 = bfs(s, graph);
        int[] l2 = bfs(t, graph);
        long[] deg1 = new long[n+10];
        long[] deg2 = new long[n+10];
        for (int i = 0; i < n ; i++) {
            if (l1[i] < INF) {
                deg1[l1[i]]++;
            }
            if (l2[i] < INF) {
                deg2[l2[i]]++;
            }
        }

        int ord = l1[t];
        long ans = 0;
        for (int i = 0; i < deg1.length ; i++) {
            int want = ord - 2 - i;
            if (want >= 0 && want < deg2.length) {
                ans += deg1[i] * deg2[want];
            }
        }
        out.println(ans);
        out.flush();
    }

    static int[] _que = new int[1000000];

    static int[] bfs(int start, int[][] graph) {
        int n = graph.length;
        int[] dp = new int[n];
        Arrays.fill(dp, INF);
        int qh = 0;
        int qt = 0;
        dp[start] = 0;
        _que[qh++] = start;
        _que[qh++] = 0;
        while (qt < qh) {
            int now = _que[qt++];
            int time = _que[qt++];
            for (int to : graph[now]) {
                if (dp[to] > time+1) {
                    dp[to] = time+1;
                    _que[qh++] = to;
                    _que[qh++] = time+1;
                }
            }
        }
        return dp;
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
