package atcoder.other2015.codefestival2015.finale;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/11/14.
 */
public class H {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[][] niku = new int[n][2];
        for (int i = 0; i < n ; i++) {
            niku[i][0] = in.nextInt();
            niku[i][1] = niku[i][0] + in.nextInt();
        }
        List<int[]> edges = new ArrayList<>();
        for (int i = 0 ; i < n ; i++) {
            edges.add(new int[]{niku[i][0], niku[i][1], 0});
        }
        for (int i = 1 ; i <= m ; i++) {
            edges.add(new int[]{i-1, i, 1});
            edges.add(new int[]{i, i-1, 1});
        }
        int[][][] graph = buildWeightedDirectedGraph(m+1, edges);
        long[] dp = new Dijkstra(graph).doit(0);

        out.println(m - dp[m]);
        out.flush();
    }

    static class Dijkstra {
        int n;
        int[][][] graph;

        class State implements Comparable<State> {
            int now;
            long time;

            State(int n, long t) {
                now = n;
                time = t;
            }

            @Override
            public int compareTo(State o) {
                return Long.compare(time, o.time);
            }
        }

        public Dijkstra(int[][][] graph) {
            this.n = graph.length;
            this.graph = graph;
        }

        long[] doit(int from) {
            long[] dp = new long[n];
            Arrays.fill(dp, Long.MAX_VALUE / 10);
            Queue<State> q = new PriorityQueue<>();
            q.add(new State(from, 0));
            dp[0] = 0;
            while (q.size() >= 1) {
                State st = q.poll();
                for (int[] e : graph[st.now]) {
                    long time = st.time + e[1];
                    if (dp[e[0]] > time) {
                        dp[e[0]] = time;
                        q.add(new State(e[0], time));
                    }
                }
            }
            return dp;
        }
    }

    static int[][][] buildWeightedDirectedGraph(int n, List<int[]> edges) {
        int m = edges.size();
        int[][][] graph = new int[n][][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = edges.get(i)[0];
            deg[a]++;
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]][2];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges.get(i)[0];
            int b = edges.get(i)[1];
            int w = edges.get(i)[2];
            graph[a][--deg[a]][0] = b;
            graph[a][deg[a]][1] = w;
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
                res += c-'0';
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
                res += c-'0';
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
