package codeforces.other2016.wunderfund2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/01/30.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        n = in.nextInt();
        x = in.nextLong();
        y = in.nextLong();
        graph = buildGraph(in, n, n-1);
        memo = new long[2][n];
        for (int i = 0; i < 2 ; i++) {
            Arrays.fill(memo[i], -1);
        }

        if (x >= y) {
            long ans = 0;
            int maxDeg = 0;
            for (int i = 0 ; i < n ; i++) {
                maxDeg = Math.max(maxDeg, graph[i].length);
            }
            if (maxDeg == n-1) {
                ans = y * (n - 2) + x;
            } else {
                ans = y * (n - 1);
            }
            out.println(ans);
        } else {
            out.println(dfs(0, -1, 1));
        }
        out.flush();
    }

    static long dfs(int now, int par, int flg) {
        if (memo[flg][now] >= 0) {
            return memo[flg][now];
        }
        int cn = graph[now].length;
        if (par != -1) {
            cn--;
        }
        if (cn == 0) {
            memo[flg][now] = 0;
            return memo[flg][now];
        }
        int ci = 0;
        long[][] ret = new long[cn][2];
        for (int to : graph[now]) {
            if (to == par) {
                continue;
            }
            ret[ci][0] = dfs(to, now, 0);
            ret[ci][1] = dfs(to, now, 1);
            ci++;
        }

        long best = Long.MAX_VALUE;
        Arrays.sort(ret, (o1, o2) -> Long.compare(o1[0] - o1[1], o2[0] - o2[1]));
        int mink = 1;
        int maxk = 2;
        if (flg == 1) {
        } else {
            mink = 0;
            maxk = 1;
        }
        for (int k = mink ; k <= maxk ; k++) {
            if (cn < k) {
                break;
            }
            long cost = 0;
            for (int i = 0; i < cn; i++) {
                if (i < k) {
                    cost += ret[i][0];
                } else {
                    cost += ret[i][1];
                }
            }
            cost += k * x;
            cost += (cn - k) * y;
            best = Math.min(best, cost);
        }
        memo[flg][now] = best;
        return best;
    }

    static long[][] memo;

    static int n;
    static long x, y;
    static int[][] graph;

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
