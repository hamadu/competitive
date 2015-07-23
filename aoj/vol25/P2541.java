package aoj.vol25;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/22.
 */
public class P2541 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            int m = in.nextInt();
            int s1 = in.nextInt();
            int s2 = in.nextInt();
            int t = in.nextInt();
            if (n + m + s1 + s2 + t == 0) {
                break;
            }
            s1--;
            s2--;
            t--;
            int[][][] graph = buildWeightedGraph(in, n, m);
            out.println(solve(graph, s1, s2, t));
        }

        out.flush();
    }

    private static long solve(int[][][] graph, int start1, int start2, int goal) {
        long[][] dp = {doit(start1, goal, graph), doit(start2, goal, graph)};
        long min = Long.MAX_VALUE;

        int n = dp[0].length;
        Set<Long> candidates = new HashSet<Long>();
        candidates.add(0L);
        for (int i = 0; i < n ; i++) {
            for (int j = i+1 ; j < n; j++) {
                for (int k = 0; k <= 1; k++) {
                    for (int l = 0; l <= 1; l++) {
                        if (dp[k][i] == INF || dp[l][j] == INF) {
                            continue;
                        }
                        if (dp[k][i] > dp[l][j]) {
                            long dx = (dp[k][i] - dp[l][j]) / (j - i);
                            for (int d = -1; d <= 1 ; d++) {
                                if (d+dx >= 0) {
                                    candidates.add(dx + d);
                                }
                            }
                        }
                    }
                }
            }
        }

        long[] cnd = new long[candidates.size()];
        int di = 0;
        for (long c : candidates) {
            cnd[di++] = c;
        }
        Arrays.sort(cnd);

        for (int i = 0; i < cnd.length ; i++) {
            long L = cnd[i];
            long LA = INF;
            long LB = INF;
            for (int k = 0; k < n ; k++) {
                LA = Math.min(LA, dp[0][k] + k * L);
                LB = Math.min(LB, dp[1][k] + k * L);
            }
            min = Math.min(min, Math.abs(LA - LB));
        }
        return min;
    }

    static final long INF = 10000000000000L;

    static long[] doit(int start, int goal, int[][][] graph) {
        int n = graph.length;
        long[][] dp = new long[n][101];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(dp[i], INF);
        }
        Queue<State> q = new PriorityQueue<State>();
        dp[start][0] = 0;
        q.add(new State(start, 0, 0));
        while (q.size() >= 1) {
            State st = q.poll();
            for (int[] ed : graph[st.now]) {
                int to = ed[0];
                int tu = st.cnt + ((ed[1] == -1) ? 1 : 0);
                long tt = st.time + ((ed[1] == -1) ? 0 : ed[1]);
                if (tu <= 100 && dp[to][tu] > tt) {
                    dp[to][tu] = tt;
                    q.add(new State(to, tu, tt));
                }
            }
        }
        return dp[goal];
    }

    static class State implements Comparable<State> {
        int now;
        int cnt;
        long time;

        State(int a, int b, long c) {
            now = a;
            cnt = b;
            time = c;
        }

        @Override
        public int compareTo(State o) {
            return Long.signum(time - o.time);
        }
    }

    static int[][][] buildWeightedGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][][] graph = new int[n][][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            String w = in.nextToken();
            int wd = -1;
            if (!w.equals("x")) {
                wd = Integer.valueOf(w);
            }
            deg[a]++;
            deg[b]++;
            edges[i] = new int[]{a, b, wd};
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
