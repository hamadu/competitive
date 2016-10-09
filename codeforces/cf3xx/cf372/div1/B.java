package codeforces.cf3xx.cf372.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class B {
    private static final long INF = (long)1e16;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int L = in.nextInt();
        int s = in.nextInt();
        int t = in.nextInt();

        int[][][] graph = buildWeightedGraph(in, n, m);
        long[] dp = new long[n];
        Arrays.fill(dp, INF);
        Queue<State> q = new PriorityQueue<>();
        q.add(new State(s, 0, 0));
        dp[s] = 0;
        while (q.size() >= 1) {
            State state = q.poll();
            if (dp[state.now] < state.time) {
                continue;
            }

            for (int[] e : graph[state.now]) {
                int to = e[0];
                long tt = state.time + e[1];
                if (e[1] == 0) {
                    continue;
                }
                if (dp[to] > tt) {
                    dp[to] = tt;
                    q.add(new State(to, 0, tt));
                }
            }
        }
        if (dp[t] < L) {
            out.println("NO");
            out.flush();
            return;
        }
        for (int i = 0; i < n ; i++) {
            for (int[] e : graph[i]) {
                if (e[1] == 0) {
                    e[1] = -1;
                }
            }
        }

        boolean fillINF = false;
        int[] from = new int[n];
        while (true) {
            Arrays.fill(dp, INF);
            dp[s] = 0;
            q.clear();
            q.add(new State(s, 0, 0));
            while (q.size() >= 1) {
                State state = q.poll();
                if (dp[state.now] < state.time) {
                    continue;
                }

                for (int[] e : graph[state.now]) {
                    int to = e[0];
                    long tt = state.time + Math.abs(e[1]);
                    if (e[1] == 0) {
                        continue;
                    }
                    if (dp[to] > tt) {
                        from[to] = state.now;
                        dp[to] = tt;
                        q.add(new State(to, 0, tt));
                    }
                }
            }
            if (dp[t] > L) {
                out.println("NO");
                out.flush();
                return;
            } else if (dp[t] == L) {
                break;
            }

            if (!fillINF) {
                for (int i = 0; i < n ; i++) {
                    for (int[] e : graph[i]) {
                        if(e[1] == -1) {
                            e[1] = 0;
                        }
                    }
                }
                int now = t;
                while (now != s) {
                    int tn = from[now];
                    for (int[] e : graph[now]) {
                        if (e[0] == tn && e[1] == 0) {
                            e[1] = -1;
                        }
                    }
                    for (int[] e : graph[tn]) {
                        if (e[0] == now && e[1] == 0) {
                            e[1] = -1;
                        }
                    }
                    now = tn;
                }
                fillINF = true;
            }

            int freedom = (int)(L-dp[t]);
            int now = t;
            boolean found = false;
            while (now != s) {
                int tn = from[now];
                for (int[] e : graph[now]) {
                    if (e[0] == tn && e[1] == -1) {
                        found = true;
                        e[1] = freedom+1;
                        break;
                    }
                }
                for (int[] e : graph[tn]) {
                    if (e[0] == now && e[1] == -1) {
                        found = true;
                        e[1] = freedom+1;
                        break;
                    }
                }
                now = tn;
                if (found) {
                    break;
                }
            }
        }

        out.println("YES");
        for (int i = 0 ; i < n ; i++) {
            for (int[] e : graph[i]) {
                if (i < e[0]) {
                    long len = Math.abs(e[1]);
                    if (len == 0) {
                        len = INF;
                    }
                    out.println(String.format("%d %d %d", i, e[0], len));
                }
            }
        }
        out.flush();
    }

    static Queue<State> qq = new PriorityQueue<>();
    static long[] qdp = new long[1000];

    private static long bfs(int s, int t, int[][][] graph) {
        qq.clear();
        qq.add(new State(s, 0, 0));
        Arrays.fill(qdp, Long.MAX_VALUE);

        while (qq.size() >= 1) {
            State state = qq.poll();
            if (qdp[state.now] < state.time) {
                continue;
            }
            for (int[] e : graph[state.now]) {
                int to = e[0];
                long cost = Math.abs(e[1]);
                if (cost == 0) {
                    continue;
                }
                long tt = state.time + cost;
                if (qdp[to] > tt) {
                    qdp[to] = tt;
                    qq.add(new State(to, 0, tt));
                }
            }
        }
        return qdp[t];
    }

    static class State implements Comparable<State> {
        int now;
        int zero;
        long time;

        public State(int n, int u, long t) {
            now = n;
            zero = u;
            time = t;
        }

        @Override
        public int compareTo(State o) {
            return Long.compare(time, o.time);
        }
    }

    static int[][][] buildWeightedGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][][] graph = new int[n][][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
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
