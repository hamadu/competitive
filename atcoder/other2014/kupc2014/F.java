package atcoder.other2014.kupc2014;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 5/2/16.
 */
public class F {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] pos = new int[n][2];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 2 ; j++) {
                pos[i][j] = in.nextInt();
            }
        }
        sq2 = new int[3000];
        for (int i = 0; i < 3000 ; i++) {
            sq2[i] = i * i;
        }
        dist = new int[n][n];
        POW = new int[n];
        COST = new int[n];
        for (int i = 0; i < n ; i++) {
            POW[i] = in.nextInt();
            COST[i] = in.nextInt();
        }
        tree = buildGraph(in, n, n-1);
        for (int i = 0; i < n ; i++) {
            for (int to : tree[i]) {
                dist[i][to] = dist[to][i] = dist(pos[i], pos[to]);
            }
        }
        int[] ret = dfs(0, -1);
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < ret.length; i++) {
            best = Math.min(best, ret[i]);
        }
        out.println(best);
        out.flush();
    }

    static int[][] dist;
    static int[][] tree;
    static int[] POW;
    static int[] COST;

    static int[] dfs(int now, int par) {
        int cn = (par == -1) ? tree[now].length : tree[now].length - 1;
        int[][] cres = new int[cn][3000];
        int[] cidx = new int[cn];
        int ci = 0;
        for (int to : tree[now]) {
            if (to == par) {
                continue;
            }
            cidx[ci] = to;
            cres[ci++] = dfs(to, now);
        }
        int[] res = new int[3000];
        for (int u = 0 ; u < 3000 ; u++) {
            int sum = u * COST[now];
            for (int i = 0 ; i < cn ; i++) {
                int D = dist[now][cidx[i]];
                int need = Math.max(0, D - POW[now] - POW[cidx[i]] - u);
                sum += cres[i][need];
            }
            res[u] = sum;
        }
        for (int i = res.length - 1 ; i >= 1 ; i--) {
            res[i-1] = Math.min(res[i-1], res[i]);
        }
        return res;
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

    static int[] sq2;

    private static int dist(int[] p1, int[] p2) {
        int dx = p1[0] - p2[0];
        int dy = p1[1] - p2[1];
        int d2 = dx*dx + dy*dy;
        int min = 0;
        int max = 3000;
        while (max - min > 1) {
            int med = (max + min) / 2;
            if (sq2[med] >= d2) {
                max = med;
            } else {
                min = med;
            }
        }
        return max;
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
