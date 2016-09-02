package codeforces.cf3xx.cf332.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/08/31.
 */
public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        out.println(solve(in));
        out.flush();
    }

    static long solve(InputReader in) {
        n = in.nextInt();
        int m = in.nextInt();
        int q = in.nextInt();

        subgraph = buildGraph(in, n, m);
        edge = new boolean[n][n];
        for (int i = 0; i < n ; i++) {
            for (int j : subgraph[i]) {
                edge[i][j] = edge[j][i] = true;
            }
        }

        lca = new int[n][n];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(lca[i], -1);
        }
        for (int i = 0; i < q ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            int c = in.nextInt()-1;
            if (a == b && a != c) {
                return 0;
            }
            if (lca[a][b] >= 0 && lca[a][b] != c) {
                return 0;
            }
            lca[a][b] = lca[b][a] = c;
        }


        memo = new long[n][1<<n];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, (1<<n)-2);
    }

    static int n;


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

    static int[][] subgraph;
    static long[][] memo;
    static int[][] lca;
    static boolean[][] edge;

    static long dfs(int idx, int available) {
        if (memo[idx][available] != -1) {
            return memo[idx][available];
        }
        if (available == 0) {
            return 1;
        }

        for (int i = 0; i < n ; i++) {
            if ((available & (1<<i)) == 0) {
                continue;
            }
            int L = lca[idx][i];
            if (L != -1 && (available&(1<<L)) >= 1) {
                memo[idx][available] = 0;
                return 0;
            }
        }

        int star;
        for (star = 0; star < n ; star++) {
            if ((available & (1<<star)) >= 1) {
                break;
            }
        }

        long ret = 0;
        for (int left = available ; left > 0; left = (left-1)&available) {
            if ((left & (1<<star)) == 0) {
                continue;
            }

            boolean isOK = true;
            int[] li = new int[n];
            int[] ri = new int[n];
            int ln = 0;
            int rn = 0;
            for (int i = 0; i < n ; i++) {
                if ((available & (1<<i)) == 0) {
                    continue;
                }
                if ((left & (1<<i)) >= 1) {
                    li[ln++] = i;
                } else {
                    ri[rn++] = i;
                }
            }

            int count = 0;
            int last = -1;
            for (int i = 0; i < ln ; i++) {
                if (edge[idx][li[i]]) {
                    count++;
                    last = i;
                }
            }
            if (count >= 2) {
                continue;
            }

            int right = available ^ left;

            check: for (int l = 0; l < ln ; l++) {
                for (int r = 0; r < rn ; r++) {
                    int L = lca[li[l]][ri[r]];
                    if (L != -1 && L != idx) {
                        isOK = false;
                        break check;
                    }
                    if (edge[li[l]][ri[r]]) {
                        isOK = false;
                        break check;
                    }
                }
            }
            if (isOK) {
                check: for (int l1 = 0; l1 < ln ; l1++) {
                    for (int l2 = l1+1; l2 < ln ; l2++) {
                        int L = lca[li[l1]][li[l2]];
                        if (L != -1) {
                            if ((right & (1<<L)) >= 1 || L == idx) {
                                isOK = false;
                                break check;
                            }
                        }
                    }
                }
            }
            if (isOK) {
                check: for (int r1 = 0; r1 < rn ; r1++) {
                    for (int r2 = r1+1; r2 < rn ; r2++) {
                        int L = lca[ri[r1]][ri[r2]];
                        if (L != -1) {
                            if ((left & (1<<L)) >= 1) {
                                isOK = false;
                                break check;
                            }
                        }
                    }
                }
            }

            if (isOK) {
                for (int i = 0; i < ln ; i++) {
                    if (last != -1 && last != i) {
                        continue;
                    }

                    int ltop = li[i];

                    if (lca[idx][ltop] != -1 && lca[idx][ltop] != idx) {
                        continue;
                    }
                    ret += dfs(ltop, left^(1<<ltop))*dfs(idx, right);
                }
            }
        }
        memo[idx][available] = ret;
        return ret;
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
