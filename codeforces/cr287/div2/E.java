package codeforces.cr287.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by dhamada on 15/05/18.
 */
public class E {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        int[][][] graph = buildGraph(in, n, m);

        Dijkstra dijk = new Dijkstra(graph);
        dijk.doit(0);

        Set<Long> touched = new HashSet<>();
        int now = n-1;
        while (now > 0) {
            int tnow = dijk.rev[now];
            long a = Math.min(now, tnow);
            long b = Math.max(now, tnow);
            touched.add((a<<20L)+b);
            now = tnow;
        }

        List<int[]> repair = new ArrayList<>();
        for (int i = 0 ; i < n ; i++) {
            for (int[] e : graph[i]) {
                int t = e[0];
                if (i < t) {
                    long eid = ((i * 1L) << 20L) + t;
                    if (touched.contains(eid)) {
                        if (e[1] == 0) {
                            repair.add(new int[]{ i+1, t+1, 1 });
                        }
                    } else {
                        if (e[1] == 1) {
                            repair.add(new int[]{ i+1, t+1, 0 });
                        }
                    }
                }
            }
        }

        out.println(repair.size());
        for (int[] x : repair) {
            out.println(x[0] + " " + x[1] + " " + x[2]);
        }
        out.flush();
    }

    static class Dijkstra {
        int[][][] graph;

        int[][] dp;

        int[] rev;

        public Dijkstra(int[][][] graph) {
            this.graph = graph;
        }

        static class State implements Comparable<State> {
            int time;
            int wrongEdge;
            int now;

            public State(int n, int t, int e) {
                now = n;
                time = t;
                wrongEdge = e;
            }

            @Override
            public int compareTo(State o) {
                if (time == o.time) {
                    return wrongEdge - o.wrongEdge;
                }
                return time - o.time;
            }
        }

        public void doit(int start) {
            Queue<State> q = new PriorityQueue<>();
            q.add(new State(start, 0, 0));

            int n = graph.length;
            dp = new int[n][2];
            rev = new int[n];
            for (int i = 0 ; i < n ; i++) {
                Arrays.fill(dp[i], Integer.MAX_VALUE);
            }
            dp[0][0] = 0;
            dp[0][1] = 0;

            while (q.size() >= 1) {
                State s = q.poll();
                for (int[] edge : graph[s.now]) {
                    int to = edge[0];
                    int tt = s.time + 1;
                    int te = s.wrongEdge + 1 - edge[1];
                    if (dp[to][0] > tt || (dp[to][0] == tt && dp[to][1] > te)) {
                        dp[to][0] = tt;
                        dp[to][1] = te;
                        rev[to] = s.now;
                        q.add(new State(to, tt, te));
                    }
                }
            }
        }
    }

    static int[][][] buildGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][][] graph = new int[n][][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            int stat = in.nextInt();
            deg[a]++;
            deg[b]++;
            edges[i] = new int[]{a, b, stat};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]][];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            int da = --deg[a];
            int db = --deg[b];
            graph[a][da] = new int[]{b, edges[i][2]};
            graph[b][db] = new int[]{a, edges[i][2]};
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
                return (char)c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char)c;
            }
            throw new InputMismatchException();
        }

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char)c);
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
