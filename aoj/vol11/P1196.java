package aoj.vol11;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/13.
 */
public class P1196 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            if (n == 0) {
                break;
            }
            int[][] edge = new int[n-1][3];
            for (int i = 0 ; i < n-1 ; i++) {
                edge[i][0] = in.nextInt()-1;
                edge[i][1] = i+1;
            }
            for (int i = 0; i < n-1 ; i++) {
                edge[i][2] = in.nextInt();
            }
            int[][][] graph = buildGraph(n, edge);
            out.println(solve(graph));
        }

        out.flush();
    }

    static int[][][] dp = new int[810][810][2];

    private static int solve(int[][][] graph) {
        int n = graph.length;
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j <= n ; j++) {
                Arrays.fill(dp[i][j], Integer.MAX_VALUE);
            }
        }
        G = graph;
        for (int i = 0 ; i < n ; i++) {
            best = Math.min(best, dfs(i, n, 1));
        }

        int bridgeCost = 0;
        for (int i = 0 ; i < n ; i++) {
            for (int[] to : graph[i]) {
                bridgeCost += to[1];
            }
        }
        return best + bridgeCost / 2;
    }

    static int[][][] G;

    private static int dfs(int now, int par, int way) {
        if (dp[now][par][way] != Integer.MAX_VALUE) {
            return dp[now][par][way];
        }

        int best = Integer.MAX_VALUE;
        if (way == 0) {
            int cost = 0;
            for (int[] to : G[now]) {
                if (to[0] != par) {
                    if (G[to[0]].length > 1) {
                        cost += dfs(to[0], now, 0) + to[1] * 2;
                    }
                }
            }
            best = cost;
        } else {
            for (int[] to : G[now]) {
                if (to[0] == par) {
                    continue;
                }
                int cost = 0;
                for (int[] to2 : G[now]) {
                    if (to2[0] == par || G[to2[0]].length == 1) {
                        continue;
                    }
                    if (to[0] == to2[0]) {
                        // one way
                        cost += dfs(to2[0], now, 1) + to2[1];
                    } else {
                        // go and back
                        cost += dfs(to2[0], now, 0) + to2[1] * 2;
                    }
                }
                best = Math.min(best, cost);
            }
        }
        dp[now][par][way] = best;
        return best;
    }

    static int[][][] buildGraph(int n, int[][] edges) {
        int m = edges.length;
        int[] deg = new int[n];
        for (int i = 0; i < m ; i++) {
            deg[edges[i][0]]++;
            deg[edges[i][1]]++;
        }
        int[][][] graph = new int[n][][];
        for (int i = 0; i < n ; i++) {
            graph[i] = new int[deg[i]][];
        }
        for (int[] ed : edges) {
            int a = ed[0];
            int b = ed[1];
            int d = ed[2];
            graph[a][--deg[a]] = new int[]{b, d};
            graph[b][--deg[b]] = new int[]{a, d};
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
