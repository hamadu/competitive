package codeforces.cf4xx.cf411.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[][] icecreams = new int[n][];
        for (int i = 0; i < n ; i++) {
            int s = in.nextInt();
            icecreams[i] = new int[s];
            for (int j = 0; j < s ; j++) {
                icecreams[i][j] = in.nextInt();
                icecreams[i][j]--;
            }
        }

        max = 1;
        for (int i = 0; i < n ; i++) {
            max = Math.max(max, icecreams[i].length);
        }

        graph = buildGraph(in, n, n-1);
        ord = new int[n];
        dfs(0, -1);

        List<Integer> cols = new ArrayList<>();
        List<Integer> uns = new ArrayList<>();
        int[] colors = new int[m];
        for (int ri = 0; ri < n ; ri++) {
            int i = ord[ri];
            cols.clear();
            uns.clear();
            for (int j = 0 ; j < icecreams[i].length ; j++) {
                if (colors[icecreams[i][j]] == 0) {
                    // dec
                    uns.add(icecreams[i][j]);
                } else {
                    cols.add(colors[icecreams[i][j]]);
                }
            }
            Collections.sort(cols);

            int paint = 1;
            int ci = 0;
            for (int j = 0 ; j < uns.size() ; j++) {
                while (ci < cols.size() && paint == cols.get(ci)) {
                    paint++;
                    ci++;
                }
                colors[uns.get(j)] = paint;
                paint++;
            }
        }

        for (int i = 0; i < m ; i++) {
            if (colors[i] == 0) {
                colors[i] = 1;
            }
        }

        out.println(max);
        for (int i = 0; i < m ; i++) {
            if (i >= 1) {
                out.print(' ');
            }
            out.print(colors[i]);
        }
        out.println();
        out.flush();
    }

    static int[] ord;
    static int oi = 0;

    static void dfs(int now, int par) {
        ord[oi++] = now;
        for (int to : graph[now]) {
            if (to == par) {
                continue;
            }
            dfs(to, now);
        }
    }

    static int[][] buildGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            deg[a]++;
            deg[b]++;
            edges[i] = new int[]{a, b};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
            graph[b][--deg[b]] = a;
        }
        return graph;
    }

    static int[][] graph;
    static int[][] icecreams;
    static int max;
    static int[] color;

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
                res += c-'0';
                c = next();
            } while (!isSpaceChar(c));
            return res*sgn;
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
            return res*sgn;
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
