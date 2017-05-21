package codeforces.cf4xx.cf414;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.*;

public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[][] graph = buildGraph(in, n, m);

        hashSeed = new long[n];
        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < n ; i++) {
            hashSeed[i] = rand.nextLong();
        }

        long[][] vType = new long[n][2];
        for (int i = 0; i < n ; i++) {
            vType[i][0] = i;
            for (int to : graph[i]) {
                vType[i][1] ^= hashSeed[to];
            }
            vType[i][1] ^= hashSeed[i];
        }

        Arrays.sort(vType, (a, b) -> Long.compare(a[1], b[1]));

        int[] group = new int[n];
        int gn = 0;
        for (int i = 0 ; i < n ; ) {
            int j = i;
            while (j < n && vType[i][1] == vType[j][1]) {
                group[(int)vType[j][0]] = gn;
                j++;
            }
            gn++;
            i = j;
        }

        cgraph = new HashSet[gn];
        for (int i = 0; i < gn ; i++) {
            cgraph[i] = new HashSet<>();
        }
        for (int i = 0 ; i < n ; i++) {
            for (int to : graph[i]) {
                int u = group[i];
                int v = group[to];
                if (u != v) {
                    cgraph[u].add(v);
                    cgraph[v].add(u);
                }
            }
        }

        visited = new boolean[gn];
        values = new int[gn];
        valid = true;
        vid = 1;
        for (int i = 0; i < gn ; i++) {
            if (visited[i] || cgraph[i].size() >= 2) {
                continue;
            }
            dfs(i, -1);
            vid += 5;
        }

        for (int i = 0; i < gn ; i++) {
            if (!visited[i]) {
                valid = false;
            }
        }

        if (valid) {
            out.println("YES");
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < n ; i++) {
                int gid = group[i];
                line.append(' ').append(values[gid]);
            }
            out.println(line.substring(1));
        } else {
            out.println("NO");
        }
        out.flush();
    }

    static boolean valid;

    static int vid;

    static int[] values;

    static boolean[] visited;

    static long[] hashSeed;

    static Set<Integer>[] cgraph;

    static void dfs(int now, int par) {
        if (visited[now]) {
            return;
        }
        values[now] = vid;
        visited[now] = true;
        vid++;
        int cnt = 0;
        for (int to : cgraph[now]) {
            if (to == par) {
                continue;
            }
            cnt++;
            dfs(to, now);
        }
        if (cnt >= 2) {
            valid = false;
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
