package aoj.vol25;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 15/07/23.
 */
@SuppressWarnings("unchecked")
public class P2594 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            int m = in.nextInt();
            int s = in.nextInt();
            int t = in.nextInt();
            if (n + m + s + t == 0) {
                break;
            }
            s--;
            t--;

            int[][] edges = new int[m][2];

            MaxFlowDinic dinic = new MaxFlowDinic();
            dinic.init(n);
            for (int i = 0; i < m ; i++) {
                int a = in.nextInt()-1;
                int b = in.nextInt()-1;
                edges[i][0] = a;
                edges[i][1] = b;
                dinic.edge(a, b, 1);
            }
            int[] ret = solve(s, t, edges, dinic);
            out.println(String.format("%d %d", ret[0], ret[1]));

        }

        out.flush();
    }

    private static int[] solve(int s, int t, int[][] edges, MaxFlowDinic dinic) {
        int flow = dinic.max_flow(s, t);
        int n = dinic.graph.length;

        int[][] graph = new int[n][];
        int[][] rgraph = new int[n][];
        int[] in = new int[n];
        int[] ou = new int[n];
        for (int i = 0; i < n ; i++) {
            for (int[] g : dinic.graph[i]) {
                if (g[1] >= 1) {
                    in[g[0]]++;
                    ou[i]++;
                }
            }
        }
        for (int i = 0; i < n ; i++) {
            graph[i] = new int[ou[i]];
            rgraph[i] = new int[in[i]];
        }
        for (int i = 0; i < n ; i++) {
            for (int[] g : dinic.graph[i]) {
                if (g[1] >= 1) {
                    int a = i;
                    int b = g[0];
                    graph[a][--ou[a]] = b;
                    rgraph[b][--in[b]] = a;
                }
            }
        }


        int[] mark = new int[n];
        for (int x = 1 ; x <= 2; x++) {
            int head = (x == 1) ? s : t;
            int[][] g = (x == 1) ? graph : rgraph;
            int qh = 0;
            int qt = 0;
            _que[qh++] = head;
            mark[head] |= x;
            while (qt < qh) {
                int now = _que[qt++];
                for (int to : g[now]) {
                    if ((mark[to] & x) == 0) {
                        mark[to] |= x;
                        _que[qh++] = to;
                    }
                }
            }
        }

        int oc = 0;
        for (int i = 0; i < edges.length; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            if ((mark[a] & 2) == 2 && (mark[b] & 1) == 1) {
                oc++;
            }
        }
        if (oc >= 1) {
            flow++;
        }
        return new int[]{flow, oc};
    }

    static int[] _que = new int[100000];

    static class MaxFlowDinic {
        public List<int[]>[] graph;
        public int[] deg;

        public int[] level;
        public int[] itr;

        public int[] que;

        @SuppressWarnings("unchecked")
        public void init(int size) {
            graph = new List[size];
            for (int i = 0; i < size ; i++) {
                graph[i] = new ArrayList<int[]>();
            }
            deg = new int[size];
            level = new int[size];
            itr = new int[size];
            que = new int[size+10];
        }
        public void edge(int from, int to, int cap) {
            int fdeg = deg[from];
            int tdeg = deg[to];
            graph[from].add(new int[]{to, cap, tdeg});
            graph[to].add(new int[]{from, 0, fdeg});
            deg[from]++;
            deg[to]++;
        }

        public int dfs(int v, int t, int f) {
            if (v == t) return f;
            for (int i = itr[v] ; i < graph[v].size() ; i++) {
                itr[v] = i;
                int[] e = graph[v].get(i);
                if (e[1] > 0 && level[v] < level[e[0]]) {
                    int d = dfs(e[0], t, Math.min(f, e[1]));
                    if (d > 0) {
                        e[1] -= d;
                        graph[e[0]].get(e[2])[1] += d;
                        return d;
                    }
                }
            }
            return 0;
        }

        public void bfs(int s) {
            Arrays.fill(level, -1);
            int qh = 0;
            int qt = 0;
            level[s] = 0;
            que[qh++] = s;
            while (qt < qh) {
                int v = que[qt++];
                for (int i = 0; i < graph[v].size() ; i++) {
                    int[] e = graph[v].get(i);
                    if (e[1] > 0 && level[e[0]] < 0) {
                        level[e[0]] = level[v] + 1;
                        que[qh++] = e[0];
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
