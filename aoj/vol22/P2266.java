package aoj.vol22;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/31.
 */
public class P2266 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int m = in.nextInt();
        int n = in.nextInt();
        int k = in.nextInt();
        int[] w = new int[n];
        for (int i = 0; i < n ; i++) {
            w[i] = in.nextInt();
        }
        init(k+2);
        int[] last = new int[n];
        Arrays.fill(last, -1);

        long sum = 0;
        for (int i = 0; i < k ; i++) {
            int ni = in.nextInt()-1;
            sum += w[ni];
            if (last[ni] != -1) {
                if (last[ni]+1 >= i) {
                    sum -= w[ni];
                } else {
                    edge(last[ni] + 1, i, 1, -w[ni]);
                }
            }
            last[ni] = i;
            if (i >= 1) {
                edge(i-1, i, 100000, 0);
            }
        }

        out.println(sum + min_cost_flow_be(0, k-1, m-1));
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

    public static void edge(int from, int to, int cap, int cost) {
        graph[from].add(new Edge(to, cap, cost, graph[to].size()));
        graph[to].add(new Edge(from, 0, -cost, graph[from].size() - 1));
    }


    public static long min_cost_flow_be(int s, int t, int f) {
        long res = 0;
        Arrays.fill(h, 0);

        // make sure that topo-sorted
        for (int i = 0; i < V ; i++) {
            for (Edge e : graph[i]) {
                if (e.cap >= 1) {
                    h[e.to] = Math.min(h[e.to], h[i] + e.cost);
                }
            }
        }

        Queue<State> q = new PriorityQueue<State>();
        while (f > 0) {
            q.clear();
            Arrays.fill(dist, INF);
            dist[s] = 0;
            q.add(new State(s, 0));
            while (q.size() >= 1) {
                State stat = q.poll();
                int v = stat.now;
                if (dist[v] < stat.dist) {
                    continue;
                }
                for (int i = 0 ; i < graph[v].size(); i++) {
                    Edge e = graph[v].get(i);
                    if (e.cap > 0 && dist[e.to] > dist[v] + e.cost + h[v] - h[e.to]) {
                        dist[e.to] = dist[v] + e.cost + h[v] - h[e.to];
                        prevv[e.to] = v;
                        preve[e.to] = i;
                        q.add(new State(e.to, dist[e.to]));
                    }
                }
            }
            if (dist[t] == INF) {
                return res;
            }
            for (int v = 0 ; v < V ; v++) {
                h[v] += dist[v];
            }
            long d = f;
            for (int v = t ; v != s ; v = prevv[v]) {
                d = Math.min(d, graph[prevv[v]].get(preve[v]).cap);
            }
            f -= d;
            res += d * h[t];
            for (int v = t ; v != s ; v = prevv[v]) {
                Edge e = graph[prevv[v]].get(preve[v]);
                e.cap -= d;
                Edge rev = graph[v].get(e.rev);
                rev.cap += d;
            }
        }
        return res;
    }

    public static long min_cost_flow(int s, int t, int f) {
        long res = 0;
        Arrays.fill(h, 0);
        Queue<State> q = new PriorityQueue<State>();
        while (f > 0) {
            q.clear();
            Arrays.fill(dist, INF);
            dist[s] = 0;
            q.add(new State(s, 0));
            while (q.size() >= 1) {
                State stat = q.poll();
                int v = stat.now;
                if (dist[v] < stat.dist) {
                    continue;
                }
                for (int i = 0 ; i < graph[v].size(); i++) {
                    Edge e = graph[v].get(i);
                    if (e.cap > 0 && dist[e.to] > dist[v] + e.cost + h[v] - h[e.to]) {
                        dist[e.to] = dist[v] + e.cost + h[v] - h[e.to];
                        prevv[e.to] = v;
                        preve[e.to] = i;
                        q.add(new State(e.to, dist[e.to]));
                    }
                }
            }
            if (dist[t] == INF) {
                return res;
            }
            for (int v = 0 ; v < V ; v++) {
                h[v] += dist[v];
            }
            long d = f;
            for (int v = t ; v != s ; v = prevv[v]) {
                d = Math.min(d, graph[prevv[v]].get(preve[v]).cap);
            }
            f -= d;
            res += d * h[t];
            for (int v = t ; v != s ; v = prevv[v]) {
                Edge e = graph[prevv[v]].get(preve[v]);
                e.cap -= d;
                Edge rev = graph[v].get(e.rev);
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
