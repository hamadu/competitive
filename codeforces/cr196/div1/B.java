package codeforces.cr196.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/09/10.
 */
public class B {
    private static final int INF = 114514;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int d = in.nextInt();
        isEvil = new boolean[n];
        for (int i = 0; i < m ; i++) {
            isEvil[in.nextInt()-1] = true;
        }
        graph = buildGraph(in, n, n-1);
        mark = new boolean[n];
        far = new int[n];
        farto = new int[n];
        dfs(0, -1);
        dfs2(0, -1, -INF, d);

        int cnt = 0;
        for (int i = 0; i < n ; i++) {
            if (mark[i]) {
                cnt++;
            }
        }
        out.println(cnt);
        out.flush();
    }

    static boolean[] mark;
    static boolean[] isEvil;
    static int[][] graph;
    static int[] far;
    static int[] farto;

    static int dfs(int now, int par) {
        int f = isEvil[now] ? 0 : -INF;
        int best = -1;
        for (int to : graph[now]) {
            if (to != par) {
                int re = dfs(to, now);
                if (re >= 0 && f < re+1) {
                    f = re+1;
                    best = to;
                }
            }
        }
        far[now] = f;
        farto[now] = best;
        return f;
    }

    static void dfs2(int now, int par, int val, int D) {
        if (isEvil[now] && val <= -1) {
            val = 0;
        }
        int max = Math.max(far[now], val);
        if (max <= D) {
            mark[now] = true;
        }
        for (int to : graph[now]) {
            if (to == par) {
                continue;
            }
            int rev = val+1;
            if (to != farto[now]) {
                rev = Math.max(rev, far[now]+1);
            } else {
                for (int to2 : graph[now]) {
                    if (to == to2 || to2 == par) {
                        continue;
                    }
                    rev = Math.max(rev, far[to2]+2);
                }
            }
            dfs2(to, now, rev, D);
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
