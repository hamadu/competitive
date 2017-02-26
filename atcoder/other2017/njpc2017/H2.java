package atcoder.other2017.njpc2017;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class H2 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        parent = new int[18][n];
        for (int i = 1 ; i < n ; i++) {
            parent[0][i] = in.nextInt()-1;
        }
        int[] initialColor = in.nextInts(n);

        graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 1 ; i < n ; i++) {
            deg[parent[0][i]]++;
        }
        for (int i = 0; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 1 ; i < n ; i++) {
            int p = parent[0][i];
            graph[p][--deg[p]] = i;
        }
        range = new int[n][2];

        depth = new int[n];
        dfs(0);
        for (int k = 1 ; k < parent.length ; k++) {
            for (int i = 0; i < n ; i++) {
                parent[k][i] = parent[k-1][parent[k-1][i]];
            }
        }

        int Q = in.nextInt();
        queries = new int[Q][];
        for (int i = 0; i < Q ; i++) {
            int t = in.nextInt();
            queries[i] = new int[t];
            for (int j = 0; j < t; j++) {
                queries[i][j] = in.nextInt()-1;
            }
        }
        answers = new int[Q];
        Arrays.fill(answers, -1);
        out.flush();
    }

    static int[][] queries;
    static int[] answers;

    static int n;
    static int[][] graph;
    static int[][] parent;
    static int[][] range;
    static int[] depth;
    static int cnt;

    static int lca(int a, int b) {
        if (a == b) {
            return a;
        }
        if (depth[a] < depth[b]) {
            return lca(b, a);
        }
        for (int ki = parent.length-1 ; ki >= 0 ; ki--) {
            int ta = parent[ki][a];
            if (depth[ta] >= depth[b]) {
                a = ta;
            }
        }
        for (int ki = parent.length-1 ; ki >= 0 ; ki--) {
            int ta = parent[ki][a];
            int tb = parent[ki][b];
            if (ta != tb) {
                a = ta;
                b = tb;
            }
        }
        return parent[0][a];
    }

    static void dfs(int now) {
        range[now][0] = cnt;
        cnt++;
        for (int to : graph[now]) {
            depth[to] = depth[now] + 1;
            dfs(to);
        }
        range[now][1] = cnt;
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
