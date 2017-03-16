package codeforces.cf4xx.cf402.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class C  {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        graph = new int[n][26];
        for (int i = 0; i < n; i++) {
            Arrays.fill(graph[i], -1);
        }
        parent = new int[n];
        parent[0] = -1;
        for (int i = 0; i < n-1 ; i++) {
            int u = in.nextInt()-1;
            int v = in.nextInt()-1;
            int ci = in.nextChar()-'a';
            graph[u][ci] = v;
            parent[v] = u;
        }
        depth = new int[n];
        depthV = new List[n];
        for (int i = 0; i < n ; i++) {
            depthV[i] = new ArrayList<>();
        }
        weight = new int[n];
        dfs(0);
        for (int i = 0; i < n ; i++) {
            depthV[depth[i]].add(i);
            maxDepth = Math.max(maxDepth, depth[i]);
        }

        int best = 0;
        int bestD = 0;
        for (int rm = 1 ; rm <= maxDepth ; rm++) {
            int value = 0;
            for (int vd : depthV[rm-1]) {
                List<Integer> mg = new ArrayList<>();
                for (int i = 0; i < 26 ; i++) {
                    if (graph[vd][i] >= 0) {
                        mg.add(graph[vd][i]);
                    }
                }
                if (mg.size() >= 1) {
                    value += merge(mg)+1;
                }
            }
            if (best < value) {
                best = value;
                bestD = rm;
            }
        }

        out.println(n-best);
        out.println(bestD);
        out.flush();
    }

    static int merge(List<Integer> mg) {
        if (mg.size() <= 1) {
            return 0;
        }

        List<Integer>[] next = new List[26];
        for (int i = 0; i < 26 ; i++) {
            next[i] = new ArrayList<>();
        }
        for (int v : mg) {
            for (int i = 0; i < 26 ; i++) {
                int to = graph[v][i];
                if (to >= 0) {
                    next[i].add(to);
                }
            }
        }

        int ans = mg.size() - 1;
        for (int i = 0; i < 26 ; i++) {
            ans += merge(next[i]);
        }
        return ans;
    }

    static void dfs(int now) {
        depth[now] = parent[now] == -1 ? 0 : depth[parent[now]] + 1;
        int cnt = 1;
        for (int i = 0 ; i < 26 ; i++) {
            if (graph[now][i] >= 0) {
                dfs(graph[now][i]);
                cnt += weight[graph[now][i]];
            }
        }
        weight[now] = cnt;
    }

    static int maxDepth;
    static int[] weight;
    static int[] parent;
    static int[] depth;
    static List<Integer>[] depthV;
    static int[][] graph;

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
