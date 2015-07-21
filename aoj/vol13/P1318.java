package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/21.
 */
public class P1318 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int m = in.nextInt();
            int gas = in.nextInt();
            int cap = in.nextInt();
            if (m + gas + cap == 0) {
                break;
            }
            String src = in.nextToken();
            String dst = in.nextToken();
            Map<String,Integer> cityToID = new HashMap<String,Integer>();
            cityToID.put(src, 0);
            cityToID.put(dst, 1);
            int[][] edges = new int[m][];
            for (int i = 0; i < m ; i++) {
                String c1 = in.nextToken();
                String c2 = in.nextToken();
                if (!cityToID.containsKey(c1)) {
                    cityToID.put(c1, cityToID.size());
                }
                if (!cityToID.containsKey(c2)) {
                    cityToID.put(c2, cityToID.size());
                }
                edges[i] = new int[]{cityToID.get(c1), cityToID.get(c2), in.nextInt()};
            }

            int n = cityToID.size();
            int[][][] graph = buildWeightedGraph(n, edges);
            boolean[] hasGas = new boolean[n];
            for (int i = 0; i < gas ; i++) {
                String city = in.nextToken();
                if (cityToID.containsKey(city)) {
                    hasGas[cityToID.get(city)] = true;
                }
            }
            out.println(solve(graph, cap * 10, hasGas));
        }

        out.flush();
    }

    private static int solve(int[][][] graph, int cap, boolean[] hasGas) {
        int n = graph.length;
        int[][] dp = new int[n][cap+1];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[0][cap] = 0;
        Queue<State> q = new PriorityQueue<State>();
        q.add(new State(0, cap, 0));
        while (q.size() >= 1) {
            State st = q.poll();
            for (int[] ed : graph[st.now]) {
                int to = ed[0];
                int time = ed[1];
                if (st.gas >= time) {
                    int tgas = hasGas[to] ? cap : st.gas - time;
                    int ttime = st.time + time;
                    if (dp[to][tgas] > ttime) {
                        dp[to][tgas] = ttime;
                        q.add(new State(to, tgas, ttime));
                    }
                }
            }
        }
        int time = Integer.MAX_VALUE;
        for (int i = 0; i <= cap; i++) {
            time = Math.min(time, dp[1][i]);
        }
        return time == Integer.MAX_VALUE ? -1 : time;
    }

    static class State implements Comparable<State> {
        int now;
        int gas;
        int time;

        State(int c, int g, int t) {
            now = c;
            gas = g;
            time = t;
        }

        @Override
        public int compareTo(State o) {
            return time - o.time;
        }
    }

    static int[][][] buildWeightedGraph(int n, int[][] edges) {
        int m = edges.length;
        int[][][] graph = new int[n][][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            deg[edges[i][0]]++;
            deg[edges[i][1]]++;
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
