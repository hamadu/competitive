package aoj.vol22;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/25.
 */
public class P2249 {
    private static final int INF = 500000000;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            int m = in.nextInt();
            if (n + m == 0) {
                break;
            }
            int[][][] graph = buildWeightedGraph(in, n, m);
            out.println(solve(graph));
        }
        out.flush();
    }

    private static int solve(int[][][] graph) {
        int n = graph.length;
        int[] dp = new int[n];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        Queue<State> q = new PriorityQueue<State>();
        q.add(new State(0, 0));
        while (q.size() >= 1) {
            State st = q.poll();
            for (int[] ed : graph[st.now]) {
                int to = ed[0];
                int time = st.time + ed[1];
                if (dp[to] > time) {
                    dp[to] = time;
                    q.add(new State(to, time));
                }
            }
        }

        int[] tocost = new int[n];
        Arrays.fill(tocost, INF);
        for (int i = 0; i < n ; i++) {
            for (int[] ed : graph[i]) {
                if (dp[i] < dp[ed[0]] && dp[i] + ed[1] == dp[ed[0]]) {
                    tocost[ed[0]] = Math.min(tocost[ed[0]], ed[2]);
                }
            }
        }
        int total = 0;
        for (int i = 1; i < n; i++) {
            total += tocost[i];
        }
        return total;
    }

    static class State implements Comparable<State> {
        int now;
        int time;

        State(int a, int t) {
            now = a;
            time = t;
        }

        @Override
        public int compareTo(State o) {
            return time - o.time;
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
            int h = in.nextInt();
            deg[a]++;
            deg[b]++;
            edges[i] = new int[]{a, b, w, h};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]][3];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            int w = edges[i][2];
            int h = edges[i][3];
            graph[a][--deg[a]][0] = b;
            graph[b][--deg[b]][0] = a;
            graph[a][deg[a]][1] = w;
            graph[b][deg[b]][1] = w;
            graph[a][deg[a]][2] = h;
            graph[b][deg[b]][2] = h;
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
