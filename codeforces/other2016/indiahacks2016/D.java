package codeforces.other2016.indiahacks2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/03/19.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int bear = in.nextInt();
        int[][] graph = new int[n][n];
        for (int i = 0; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            int c = in.nextInt();
            graph[a][b] = c;
        }


        MaxFlowFord flow = new MaxFlowFord();

        double min = 0;
        double max = 1000000;
        for (int cur = 0; cur < 70; cur++) {
            double med = (min + max) / 2;

            flow.init(n);
            for (int i = 0 ; i < n ; i++) {
                for (int j = 0; j < n; j++) {
                    if (graph[i][j] >= 1) {
                        flow.edge(i, j, (long)(graph[i][j] / med));
                    }
                }
            }

            if (flow.max_flow(0, n-1, bear)) {
                min = med;
            } else {
                max = med;
            }
        }
        out.println(max * bear);
        out.flush();
    }

    public static class State implements Comparable<State> {
        int dist;
        int now;
        public State(int _n, int _d) {
            now = _n;
            dist = _d;
        }

        @Override
        public int compareTo(State o) {
            return dist - o.dist;
        }
    }

    public static class Edge {
        int to;
        int cap;
        int rev;
        int cost;
        public Edge(int _to, int _cap, int _cost, int _rev) {
            to = _to;
            cap = _cap;
            rev = _rev;
            cost = _cost;
        }
    }

    public static int INF = 1000000000;
    public static int V;
    public static int[] h;
    public static int[] dist;
    public static int[] prevv, preve;
    public static List<Edge>[] graph;

    @SuppressWarnings("unchecked")
    public static void init(int size) {
        graph = new List[size];
        for (int i = 0 ; i < size ; i++) {
            graph[i] = new ArrayList<Edge>();
        }
        dist = new int[size];
        prevv = new int[size];
        preve = new int[size];
        h = new int[size];
        V = size;
    }

    static class MaxFlowFord {
        public class Edge {
            int to;
            long cap;
            int rev;
            public Edge(int _to, long _cap, int _rev) {
                to = _to;
                cap = _cap;
                rev = _rev;
            }
        }
        public Map<Integer, List<Edge>> graph = new HashMap<Integer, List<Edge>>();
        public boolean[] used;
        public void init(int size) {
            for (int i = 0 ; i < size ; i++) {
                graph.put(i, new ArrayList<Edge>());
            }
            used = new boolean[size];
        }
        public void edge(int from, int to, long cap) {
            graph.get(from).add(new Edge(to, cap, graph.get(to).size()));
            graph.get(to).add(new Edge(from, 0, graph.get(from).size() - 1));
        }
        public long dfs(int v, int t, long f) {
            if (v == t) return f;
            used[v] = true;
            for (int i = 0 ; i < graph.get(v).size() ; i++) {
                Edge e = graph.get(v).get(i);
                if (!used[e.to] && e.cap > 0) {
                    long d = dfs(e.to, t, Math.min(f, e.cap));
                    if (d > 0) {
                        e.cap -= d;
                        graph.get(e.to).get(e.rev).cap += d;
                        return d;
                    }
                }
            }
            return 0;
        }
        public boolean max_flow(int s, int t, long bear) {
            long flow = 0;
            while (true) {
                used = new boolean[graph.size()];
                long f = dfs(s, t, Long.MAX_VALUE);
                if (f == 0) {
                    break;
                }
                flow += f;
                if (flow >= bear) {
                    return true;
                }
            }
            return false;
        }
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
