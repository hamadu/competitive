package csacademy.round023;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class NoPrimeSum {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = in.nextInts(n);
        int[] even = Arrays.stream(a).filter(s -> s % 2 == 0).toArray();
        int[] odd = Arrays.stream(a).filter(s -> s % 2 == 1).toArray();


        if (even.length == 0 || odd.length == 0) {
            out.println(0);
            out.println();
            out.flush();
            return;
        }

        boolean[] isp = generatePrimes(250000);

        MaxFlowDinic di = new MaxFlowDinic();
        di.init(n+2);
        for (int i = 0; i < even.length; i++) {
            di.addEdge(n, i, 1);
        }
        for (int i = 0; i < odd.length; i++) {
            di.addEdge(even.length+i, n+1, 1);
        }
        for (int i = 0; i < even.length ; i++) {
            for (int j = 0; j < odd.length ; j++) {
                if (isp[even[i]+odd[j]]) {
                    di.addEdge(i, even.length+j, 10000);
                }
            }
        }

        int cost = di.maxFlow(n, n+1);
        int[] cover = di.getVertexCover(n, even.length, odd.length);

        StringBuilder line = new StringBuilder();
        for (int i = 0; i < cover.length ; i++) {
            if (cover[i] < even.length) {
                line.append(' ').append(even[cover[i]]);
            } else {
                line.append(' ').append(odd[cover[i]-even.length]);
            }
        }

        out.println(cost);
        if (line.length() >= 1) {
            out.println(line.substring(1));
        } else {
            out.println();
        }
        out.flush();
    }

    static boolean[] generatePrimes(int upto) {
        boolean[] isp = new boolean[upto];
        Arrays.fill(isp, true);
        isp[0] = isp[1] = false;

        int pi = 0;
        for (int i = 2; i < upto ; i++) {
            if (isp[i]) {
                pi++;
                for (int j = i * 2; j < upto; j += i) {
                    isp[j] = false;
                }
            }
        }
        return isp;
    }

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

        /**
         * Adds directed edge between from-to with specified capacity.
         *
         * @param from
         * @param to
         * @param cap  the edge capacity
         */
        public void addEdge(int from, int to, int cap) {
            int fdeg = deg[from];
            int tdeg = deg[to];
            graph[from].add(new int[]{to, cap, tdeg});
            graph[to].add(new int[]{from, 0, fdeg});
            deg[from]++;
            deg[to]++;
        }

        private int dfs(int v, int t, int f) {
            if (v == t) {
                return f;
            }
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

        private void bfs(int s) {
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

        /**
         * Computes s-t maximum flow.
         *
         * @param s source
         * @param t sink
         * @return s-t maximum flow
         */
        public int maxFlow(int s, int t) {
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

        public int[] getVertexCover(int s, int left, int right) {
            boolean[] canVisit = new boolean[graph.length];
            int[] que = new int[graph.length+10];
            int qh = 0;
            int qt = 0;
            que[qh++] = s;
            canVisit[s] = true;
            while (qt < qh) {
                int now = que[qt++];
                for (int[] e : graph[now]) {
                    if (e[1] >= 1 && !canVisit[e[0]]) {
                        canVisit[e[0]] = true;
                        que[qh++] = e[0];
                    }
                }
            }

            int[] response = new int[graph.length];
            int ri = 0;
            for (int i = 0 ; i < left ; i++) {
                if (!canVisit[i]) {
                    response[ri++] = i;
                }
            }
            for (int i = left ; i < left+right ; i++) {
                if (canVisit[i]) {
                    response[ri++] = i;
                }
            }
            return Arrays.copyOf(response, ri);
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

        private int[] nextInts(int n) {
            int[] ret = new int[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextInt();
            }
            return ret;
        }


        private int[][] nextIntTable(int n, int m) {
            int[][] ret = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextInt();
                }
            }
            return ret;
        }

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
            }
            return ret;
        }

        private long[][] nextLongTable(int n, int m) {
            long[][] ret = new long[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextLong();
                }
            }
            return ret;
        }

        private double[] nextDoubles(int n) {
            double[] ret = new double[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextDouble();
            }
            return ret;
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

        public double nextDouble() {
            return Double.valueOf(nextToken());
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
