package codeforces.cf3xx.cf349.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/08/06.
 */
public class B {
    private static final int INF = 114514;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        boolean[][] has = new boolean[n][n];
        for (int i = 0; i < m ; i++) {
            int u = in.nextInt()-1;
            int v = in.nextInt()-1;
            has[u][v] = true;
        }

        int[][] graph = new int[n][];
        for (int i = 0; i < n ; i++) {
            int deg = 0;
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    continue;
                }
                if (has[i][j]) {
                    deg++;
                }
            }
            graph[i] = new int[deg];
            for (int j = 0; j < n ; j++) {
                if (i == j) {
                    continue;
                }
                if (has[i][j]) {
                    graph[i][--deg] = j;
                }
            }
        }


        int[][] cost = build(graph);
        long[][] comingLarge = new long[n][n];
        long[][] leavingLarge = new long[n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (cost[i][j] < INF && i != j) {
                    leavingLarge[i][j] = ((long) cost[i][j]<<15)+j;
                } else {
                    leavingLarge[i][j] = -1;
                }
                if (cost[j][i] < INF && i != j) {
                    comingLarge[i][j] = ((long) cost[j][i]<<15)+j;
                } else {
                    comingLarge[i][j] = -1;
                }
            }
        }
        for (int i = 0; i < n ; i++) {
            Arrays.sort(comingLarge[i]);
            Arrays.sort(leavingLarge[i]);
        }

        int MASK = (1<<15)-1;
        int best = 0;
        int[] bestOrd = new int[]{};
        for (int b = 0 ; b < n ; b++) {
            for (int c = 0 ; c < n ; c++) {
                if (b == c || cost[b][c] == INF) {
                    continue;
                }
                // a->d
                for (int ord = 0 ; ord <= 1 ; ord++) {
                    int a = -1, d = -1;
                    boolean isOK = true;
                    for (int cur = 0 ; cur <= 1 ; cur++) {
                        boolean coming = (ord ^ cur) == 0;
                        long[] reftbl = coming ? comingLarge[b] : leavingLarge[c];
                        for (int i = n-1 ; i >= 0; i--) {
                            if (reftbl[i] == -1) {
                                isOK = false;
                                break;
                            }
                            int idx = (int) ((reftbl[i])&MASK);
                            if (idx == b || idx == c || idx == a || idx == d) {
                                continue;
                            }
                            if (coming) {
                                a = idx;
                            } else {
                                d = idx;
                            }
                            break;
                        }
                    }
                    if (isOK) {
                        int length = cost[a][b] + cost[b][c] + cost[c][d];
                        if (best < length) {
                            best = length;
                            bestOrd = new int[]{a, b, c, d};
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 4 ; i++) {
            bestOrd[i]++;
        }
        out.println(String.format("%d %d %d %d", bestOrd[0], bestOrd[1], bestOrd[2], bestOrd[3]));
        out.flush();
    }

    private static int[][] build(int[][] graph) {
        int n = graph.length;
        int[][] dp = new int[n][n];
        int[] que = new int[4*n];
        for (int head = 0; head < n ; head++) {
            Arrays.fill(dp[head], INF);
            int qh = 0;
            int qt = 0;
            que[qh++] = head;
            que[qh++] = 0;
            dp[head][head] = 0;
            while (qt < qh) {
                int now = que[qt++];
                int tim = que[qt++]+1;
                for (int to : graph[now]) {
                    if (dp[head][to] > tim) {
                        dp[head][to] = tim;
                        que[qh++] = to;
                        que[qh++] = tim;
                    }
                }
            }
        }
        return dp;
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
