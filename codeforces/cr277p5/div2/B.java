package codeforces.cr277p5.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by dhamada on 15/05/24.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] b = new int[n];
        for (int i = 0; i < n ; i++) {
            b[i] = in.nextInt();
        }

        int m = in.nextInt();
        int[] g = new int[m];
        for (int i = 0; i < m ; i++) {
            g[i] = in.nextInt();
        }

        MaxFlowFord flow = new MaxFlowFord();
        flow.init(n+m+2);
        for (int i = 0; i < n ; i++) {
            flow.edge(n+m, i, 1);
        }
        for (int i = 0; i < m ; i++) {
            flow.edge(n+i, n+m+1, 1);
        }
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (Math.abs(b[i] - g[j]) <= 1) {
                    flow.edge(i, n+j, 1);
                }
            }
        }
        out.println(flow.max_flow(n+m, n+m+1));
        out.flush();
    }

    static class MaxFlowFord {
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
        public Map<Integer, List<Edge>> graph = new HashMap<>();
        public boolean[] used;
        public void init(int size) {
            for (int i = 0 ; i < size ; i++) {
                graph.put(i, new ArrayList<>());
            }
            used = new boolean[size];
        }
        public void edge(int from, int to, int cap) {
            graph.get(from).add(new Edge(to, cap, graph.get(to).size()));
            graph.get(to).add(new Edge(from, 0, graph.get(from).size() - 1));
        }
        public int dfs(int v, int t, int f) {
            if (v == t) return f;
            used[v] = true;
            for (int i = 0 ; i < graph.get(v).size() ; i++) {
                Edge e = graph.get(v).get(i);
                if (!used[e.to] && e.cap > 0) {
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
        public int max_flow(int s, int t) {
            int flow = 0;
            while (true) {
                used = new boolean[graph.size()];
                int f = dfs(s, t, Integer.MAX_VALUE);
                if (f == 0) {
                    break;
                }
                flow += f;
            }
            return flow;
        }
    }


    private static String p(int[] a) {
        int n = a.length;
        StringBuilder b = new StringBuilder();
        for (int i : a) {
            b.append(i);
        }
        return b.toString();
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
