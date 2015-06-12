package codechef.snackdown2015.round1a;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by dhamada on 15/06/12.
 */
public class CHEFVOTE {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = in.nextInt();
        while (--T >= 0) {
            int n = in.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n ; i++) {
                a[i] = in.nextInt();
            }
            out.println(solve(a));
        }
        out.flush();
    }

    private static String solve(int[] a) {
        int sum = 0;
        int n = a.length;
        for (int i = 0 ; i < n ; i++) {
            sum += a[i];
            if (a[i] >= n) {
                return "-1";
            }
        }
        if (sum > n) {
            return "-1";
        }


        MaxFlowDinic dinic = new MaxFlowDinic();
        int source = 2*n;
        int sink = 2*n+1;
        dinic.init(2*n+2);
        for (int i = 0; i < n ; i++) {
            dinic.edge(source, i, 1);
            dinic.edge(n+i, sink, a[i]);
        }
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    dinic.edge(i, n+j, 1);
                }
            }
        }

        int flw = dinic.max_flow(source, sink);
        if (flw != n) {
            return "-1";
        }


        StringBuilder b = new StringBuilder();
        for (int i = 0 ; i < n ; i++) {
            for (MaxFlowDinic.Edge e : dinic.graph.get(i)) {
                if (n <= e.to && e.to < 2*n && e.cap == 0) {
                    b.append(' ').append(e.to-n+1);
                }
            }
        }
        return b.substring(1);
    }


    static class MaxFlowDinic {
        public class Edge {
            int to;
            int cap;
            int rev;
            public Edge(int _to, int _cap, int _rev) {
                to = _to;
                cap = _cap;
                rev = _rev;
            }
        }
        public Map<Integer, List<Edge>> graph = new HashMap<Integer, List<Edge>>();
        public int[] level;
        public int[] itr;
        public void init(int size) {
            for (int i = 0 ; i < size ; i++) {
                graph.put(i, new ArrayList<Edge>());
            }
            level = new int[size];
            itr = new int[size];
        }
        public void edge(int from, int to, int cap) {
            graph.get(from).add(new Edge(to, cap, graph.get(to).size()));
            graph.get(to).add(new Edge(from, 0, graph.get(from).size() - 1));
        }
        public int dfs(int v, int t, int f) {
            if (v == t) return f;
            for (int i = itr[v] ; i < graph.get(v).size() ; i++) {
                itr[v] = i;
                Edge e = graph.get(v).get(i);
                if (e.cap > 0 && level[v] < level[e.to]) {
                    int d = dfs(e.to, t, Math.min(f, e.cap));
                    if (d > 0) {
                        e.cap -= d;
                        graph.get(e.to).get(e.rev).cap += d;
                        return d;
                    }
                }
            }
            return 0;
        }

        public void bfs(int s) {
            Arrays.fill(level, -1);
            Queue<Integer> q = new ArrayBlockingQueue<Integer>(graph.size()+10);
            level[s] = 0;
            q.add(s);
            while (q.size() >= 1) {
                int v = q.poll();
                for (int i = 0; i < graph.get(v).size() ; i++) {
                    Edge e = graph.get(v).get(i);
                    if (e.cap > 0 && level[e.to] < 0) {
                        level[e.to] = level[v] + 1;
                        q.add(e.to);
                    }
                }
            }
        }

        public int max_flow(int s, int t) {
            int flow = 0;
            while (true) {
                bfs(s);
                if (level[t] < 0) {
                    return flow;
                }
                Arrays.fill(itr, 0);
                while (true) {
                    int f = dfs(s, t, Integer.MAX_VALUE);
                    if (f <= 0) {
                        break;
                    }
                    flow += f;
                }
            }
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
