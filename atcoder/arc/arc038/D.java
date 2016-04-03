package atcoder.arc.arc038;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/03/03.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        num = new int[n];
        for (int i = 0; i < n ; i++) {
            num[i] = in.nextInt();
        }
        Set<Integer> nu = new HashSet<>();
        for (int ni : num) {
            nu.add(ni);
        }
        values = new int[nu.size()];
        int nii = 0;
        for (int ni : nu) {
            values[nii++] = ni;
        }
        Arrays.sort(values);

        graph = buildDirectedGraph(in, n, m);
        rgraph = reverseDirectedGraph(graph);
        game = new int[2][n];
        ldeg = new int[2][n];

        int min = 0;
        int max = nii;
        while (max - min > 1) {
            int med = (max + min) / 2;
            if (isPossible(values[med])) {
                min = med;
            } else {
                max = med;
            }
        }
        out.println(values[min]);
        out.flush();
    }

    static int[] q = new int[800000];
    static int[][] game;
    static int[][] ldeg;
    static int[] values;


    private static boolean isPossible(int med) {
        int n = graph.length;
        for (int i = 0 ; i <= 1 ; i++) {
            Arrays.fill(game[i], 0);
            Arrays.fill(ldeg[i], 0);
        }

        int qh = 0;
        int qt = 0;
        for (int i = 0 ; i < n ; i++) {
            if (num[i] >= med) {
                q[qh++] = 0;
                q[qh++] = i;
                q[qh++] = 1;
                game[0][i] = 1;
                if (graph[i].length == 0) {
                    q[qh++] = 1;
                    q[qh++] = i;
                    q[qh++] = -1;
                    game[1][i] = -1;
                }
            } else {
                q[qh++] = 1;
                q[qh++] = i;
                q[qh++] = 1;
                game[1][i] = 1;
                if (graph[i].length == 0) {
                    q[qh++] = 0;
                    q[qh++] = i;
                    q[qh++] = -1;
                    game[0][i] = -1;
                }
            }
        }
        while (qt < qh) {
            int pl = q[qt++];
            int v = q[qt++];
            int val = q[qt++];
            int op = 1 - pl;
            if (val == 1) {
                for (int to : rgraph[v]) {
                    ldeg[op][to]++;
                    if (graph[to].length == ldeg[op][to] && game[op][to] == 0) {
                        game[op][to] = -1;
                        q[qh++] = op;
                        q[qh++] = to;
                        q[qh++] = -1;
                    }
                }
            } else {
                for (int to : rgraph[v]) {
                    if (game[op][to] == 0) {
                        game[op][to] = 1;
                        q[qh++] = op;
                        q[qh++] = to;
                        q[qh++] = 1;
                    }
                }
            }
        }
        return game[0][0] == 1;
    }

    static int[][] rgraph;
    static int[][] graph;
    static int[] num;

    static int[][] reverseDirectedGraph(int[][] graph) {
        int n = graph.length;
        int[] deg = new int[n];

        List<int[]> edges = new ArrayList<>();
        for (int i = 0 ; i < n ; i++) {
            for (int j : graph[i]) {
                edges.add(new int[]{j, i});
                deg[j]++;
            }
        }

        int[][] ret = new int[n][];
        for (int i = 0 ; i < n ; i++) {
            ret[i] = new int[deg[i]];
        }
        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            ret[a][--deg[a]] = b;
        }
        return ret;
    }

    static int[][] buildDirectedGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            deg[a]++;
            edges[i] = new int[]{a, b};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
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
