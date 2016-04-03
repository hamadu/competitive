package atcoder.arc.arc041;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/08.
 */
public class D {


    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int m = in.nextInt();
        graph = buildGraph(in, n, m);

        boolean isOK = false;
        for (int i = 0 ; i < n ; i++) {
            isOK |= solve(i, 0);
            isOK |= solve(i, 1);
            if (isOK) {
                break;
            }
        }
        out.println(isOK ? "Yes" : "No");
        out.flush();
    }

    static int[] que = new int[10000];

    private static boolean solve(int start, int color) {
        int ecnt = 0;
        for (Edge[] edges : graph) {
            for (Edge edge : edges) {
                edge.painted = -1;
                ecnt++;
            }
        }
        int n = graph.length;

        int qh = 0;
        int qt = 0;
        que[qh++] = start;
        que[qh++] = color;

        boolean[][] visited = new boolean[n][2];
        while (qt < qh) {
            int now = que[qt++];
            int col = que[qt++];
            for (Edge e : graph[now]) {
                if (e.painted != -1 || e.col == col) {
                    if (e.painted == -1) {
                        e.painted = col;
                        ecnt -= 2;
                    }
                    int tcol = 1 - col;
                    int to = e.a + e.b - now;
                    if (!visited[to][tcol]) {
                        visited[to][tcol] = true;
                        que[qh++] = to;
                        que[qh++] = tcol;
                    }
                }
            }
        }
        if (ecnt == 0) {
            return true;
        }

        hasFlower = false;
        subVisited = new int[n];
        Arrays.fill(subVisited, Integer.MAX_VALUE);
        for (int i = 0 ; i < n ; i++) {
            if (subVisited[i] == Integer.MAX_VALUE) {
                dfs(i, -1, 0);
            }
        }

        return hasFlower;
    }

    static boolean hasFlower = false;
    static int[] subVisited;

    static void dfs(int now, int par, int depth) {
        if (subVisited[now] != Integer.MAX_VALUE) {
            int d = depth - subVisited[now];
            hasFlower |= d % 2 == 1;
            return;
        }
        subVisited[now] = depth;
        for (Edge e : graph[now]) {
            if (e.painted != -1) {
                int to = e.a + e.b - now;
                if (to != par) {
                    dfs(to, now, depth+1);
                }
            }
        }
    }

    static Edge[][] graph;

    static class Edge {
        int a;
        int b;
        int col;
        int painted;

        Edge(int _a, int _b, int _c) {
            a = _a;
            b = _b;
            col = _c;
            painted = 0;
        }
    }


    static Edge[][] buildGraph(InputReader in, int n, int m) {
        Edge[] edges = new Edge[m];
        Edge[][] graph = new Edge[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            deg[a]++;
            deg[b]++;
            edges[i] = new Edge(a, b, in.nextChar() == 'b' ? 1 : 0);
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new Edge[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            Edge e = edges[i];
            graph[e.a][--deg[e.a]] = e;
            graph[e.b][--deg[e.b]] = e;
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
