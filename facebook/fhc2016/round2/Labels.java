package facebook.fhc2016.round2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/01/10.
 */
public class Labels {
    private static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = in.nextInt();
        for (int c = 1 ; c <= T ; c++) {
            int n = in.nextInt();
            int k = in.nextInt();
            long p = in.nextInt();
            long[][] table = new long[n][k];
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < k ; j++) {
                    table[i][j] = in.nextInt();
                }
            }
            int[][] graph = buildGraph(in, n, n-1);


            out.println(String.format("Case #%d: %d", c, solve(table, graph, p)));
        }
        out.flush();
    }

    private static long solve(long[][] t, int[][] g, long _p) {
        table = t;
        graph = g;
        p = _p;
        n = graph.length;
        k = table[0].length;
        memo = new long[n][k+1][k+1][2];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j <= k; j++) {
                for (int l = 0; l <= k; l++) {
                    Arrays.fill(memo[i][j][l], -1);
                }
            }
        }

        long best = Long.MAX_VALUE;
        for (int i = 0; i < k ; i++) {
            best = Math.min(best, dfs(0, -1, i, k, 0));
        }
        return best;
    }

    static int n;
    static int k;
    static int[][] graph;
    static long[][] table;
    static long p;
    static long[][][][] memo;

    static long dfs(int now, int par, int c1, int c2, int f) {
        if (memo[now][c1][c2][f] != -1) {
            return memo[now][c1][c2][f];
        }
        long res = table[now][c1];
        int cn = par == -1 ? graph[now].length : graph[now].length-1;

        long[][] tbl = new long[cn][k];
        int ci = 0;
        for (int to : graph[now]) {
            if (to == par) {
                continue;
            }
            for (int c = 0 ; c < k ; c++) {
                tbl[ci][c] = dfs(to, now, c, c1, 0);
            }
            ci++;
        }
        if (cn >= 1) {
            long ptn1 = p;
            for (int i = 0; i < cn ; i++) {
                long min = Long.MAX_VALUE;
                for (int j = 0; j < k ; j++) {
                    min = Math.min(min, tbl[i][j]);
                }
                ptn1 += min;
            }
            if (cn >= k+1) {
                res += ptn1;
            } else {
                for (int i = 0 ; i < cn ; i++) {
                    if (c2 < k) {
                        tbl[i][c2] = Long.MAX_VALUE / 1000;
                    }
                }
                int[] match = hungarian(tbl);
                long ptn2 = 0;
                if (match != null) {
                    for (int i = 0; i < cn ; i++) {
                        ptn2 += tbl[i][match[i]];
                    }
                } else {
                    ptn2 = Long.MAX_VALUE;
                }
                res += Math.min(ptn1, ptn2);
            }
        }
        memo[now][c1][c2][f] = res;
        return res;
    }


    public static int[] hungarian(long[][] table) {
        int n = table.length;
        int m = table[0].length;
        int[] toright = new int[n];
        int[] toleft = new int[m];
        int[] ofsleft = new int[n];
        int[] ofsright = new int[m];
        Arrays.fill(toright, -1);
        Arrays.fill(toleft, -1);

        for (int r = 0 ; r < n ; r++) {
            boolean[] left = new boolean[n];
            boolean[] right = new boolean[m];
            int[] trace = new int[m];
            int[] ptr = new int[m];
            Arrays.fill(trace, -1);
            Arrays.fill(ptr, r);
            left[r] = true;
            while (true) {
                long d = Long.MAX_VALUE;
                for (int j = 0 ; j < m ; j++) {
                    if (!right[j]) {
                        d = Math.min(d, table[ptr[j]][j] + ofsleft[ptr[j]] + ofsright[j]);
                    }
                }
                for (int i = 0 ; i < n ; i++) {
                    if (left[i]) {
                        ofsleft[i] -= d;
                    }
                }
                for (int j = 0 ; j < m ; j++) {
                    if (right[j]) {
                        ofsright[j] += d;
                    }
                }
                int b = -1;
                for (int j = 0 ; j < m ; j++) {
                    if (!right[j] && table[ptr[j]][j] + ofsleft[ptr[j]] + ofsright[j] == 0) {
                        b = j;
                    }
                }
                if (b == -1) {
                    return null;
                }
                trace[b] = ptr[b];
                int c = toleft[b];
                if (c < 0) {
                    while (b >= 0) {
                        int a = trace[b];
                        int z = toright[a];
                        toleft[b] = a;
                        toright[a] = b;
                        b = z;
                    }
                    break;
                }
                right[b] = true;
                left[c] = true;
                for (int j = 0 ; j < m ; j++) {
                    if (table[c][j] + ofsleft[c] + ofsright[j] < table[ptr[j]][j] + ofsleft[ptr[j]] + ofsright[j]) {
                        ptr[j] = c;
                    }
                }
            }
        }
        return toright;
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
