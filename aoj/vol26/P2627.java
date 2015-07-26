package aoj.vol26;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/24.
 */
public class P2627 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        
        init(n+2);
        long edgeCosts = 0;
        int[][] graph = new int[n][n];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(graph[i], IINF);
        }


        int[] indeg = new int[n];
        int[] outdeg = new int[n];
        for (int i = 0; i < n ; i++) {
            int deg = in.nextInt();
            for (int j = 0; j < deg; j++) {
                int id = in.nextInt()-1;
                int co = in.nextInt();
                indeg[id]++;
                edgeCosts += co;
                graph[i][id] = Math.min(graph[i][id], co);
            }
            outdeg[i] = deg;
        }

        for (int i = 0; i < n ; i++) {
            if (i == 0) {
                edge(n, i, CINF, 0);
            } else if (indeg[i] > outdeg[i]) {
                edge(n, i, indeg[i] - outdeg[i], 0);
            } else if (indeg[i] < outdeg[i]) {
                edge(i, n+1, outdeg[i] - indeg[i], 0);
            }
        }
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (graph[i][j] < IINF) {
                    edge(i, j, CINF, graph[i][j]);
                }
            }
        }

        long total = min_cost_flow(n, n+1, CINF);
        out.println(total + edgeCosts);
        out.flush();
    }


    public static class State implements Comparable<State> {
        long dist;
        int now;
        public State(int _n, long _d) {
            now = _n;
            dist = _d;
        }

        @Override
        public int compareTo(State o) {
            return Long.signum(dist - o.dist);
        }
    }
    public static class Edge {
        int to;
        int cap;
        int rev;
        long cost;
        boolean isImportant;
        public Edge(int _to, int _cap, long _cost, int _rev) {
            to = _to;
            cap = _cap;
            rev = _rev;
            cost = _cost;
        }
    }

    public static int CINF = 300000;
    public static int IINF = 100000000;
    public static long LINF = 10000000000L;
    public static int V;
    public static long[] h;
    public static long[] dist;
    public static int[] prevv, preve;
    public static Map<Integer, List<Edge>> graph = new HashMap<Integer, List<Edge>>();
    public static void init(int size) {
        for (int i = 0 ; i < size ; i++) {
            graph.put(i, new ArrayList<Edge>());
        }
        dist = new long[size];
        prevv = new int[size];
        preve = new int[size];
        h = new long[size];
        V = size;
    }

    public static void edge(int from, int to, int cap, long cost) {
        graph.get(from).add(new Edge(to, cap, cost, graph.get(to).size()));
        graph.get(to).add(new Edge(from, 0, -cost, graph.get(from).size() - 1));
    }

    public static long min_cost_flow(int s, int t, int f) {
        long res = 0;
        Arrays.fill(h, 0);
        Queue<State> q = new PriorityQueue<State>();
        while (f > 0) {
            q.clear();
            Arrays.fill(dist, LINF);
            dist[s] = 0;
            q.add(new State(s, 0));
            while (q.size() >= 1) {
                State stat = q.poll();
                int v = stat.now;
                if (dist[v] < stat.dist) {
                    continue;
                }
                for (int i = 0 ; i < graph.get(v).size() ; i++) {
                    Edge e = graph.get(v).get(i);
                    if (e.cap > 0 && dist[e.to] > dist[v] + e.cost + h[v] - h[e.to]) {
                        dist[e.to] = dist[v] + e.cost + h[v] - h[e.to];
                        prevv[e.to] = v;
                        preve[e.to] = i;
                        q.add(new State(e.to, dist[e.to]));
                    }
                }
            }
            if (dist[t] == LINF) {
                return res;
            }
            for (int v = 0 ; v < V ; v++) {
                h[v] += dist[v];
            }
            long d = f;
            for (int v = t ; v != s ; v = prevv[v]) {
                d = Math.min(d, graph.get(prevv[v]).get(preve[v]).cap);
            }
            f -= d;
            res += d * h[t];
            for (int v = t ; v != s ; v = prevv[v]) {
                Edge e = graph.get(prevv[v]).get(preve[v]);
                e.cap -= d;
                Edge rev = graph.get(v).get(e.rev);
                rev.cap += d;
            }
        }
        return res;
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
