package atcoder.other2016.codefestival2016.quala;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class D {
    private static final long INF = (long)1e18;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int r = in.nextInt();
        int c = in.nextInt();
        int n = in.nextInt();
        int[][] num = in.nextIntTable(n, 3);
        for (int i = 0; i < n; i++) {
            num[i][0]--;
            num[i][1]--;
        }
        out.println(solve(r, c, num) ? "Yes" : "No");
        out.flush();
    }

    public static boolean solve(int r, int c, int[][] num) {
        long[][] row = solveSub(buildGraph(num, r, 1, 0));
        long[][] col = solveSub(buildGraph(num, c, 0, 1));
        if (row == null || col == null) {
            return false;
        }

        int n = num.length;
        for (int i = 0; i < n ; i++) {
            if (num[i][2] - row[0][num[i][0]] + row[1][num[i][0]] < 0) {
                return false;
            }
            if (num[i][2] - col[0][num[i][1]] + col[1][num[i][1]] < 0) {
                return false;
            }
        }
        return true;
    }

    static int[] que = new int[200000];

    private static List<int[]>[] buildGraph(int[][] num, int c, int primary, int secondary) {
        int n = num.length;
        Arrays.sort(num, (a, b) -> (a[primary] == b[primary]) ? a[secondary] - b[secondary] : a[primary] - b[primary]);
        List<int[]>[] graph = new List[c];
        for (int i = 0; i < c ; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < n; ) {
            int j = i;
            while (j < n && num[i][primary] == num[j][primary]) {
                j++;
            }
            for (int k = i+1 ; k < j ; k++) {
                int c1 = num[k-1][secondary];
                int c2 = num[k][secondary];
                int diff = num[k][2] - num[k-1][2];
                graph[c1].add(new int[]{c2, diff});
                graph[c2].add(new int[]{c1, -diff});
            }
            i = j;
        }
        return graph;
    }

    private static long[][] solveSub(List<int[]>[] graph) {
        int n = graph.length;
        long[] dp = new long[n];
        long[] minValue = new long[n];
        Arrays.fill(dp, INF);
        Arrays.fill(minValue, INF);

        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n ; i++) {
            if (dp[i] != INF) {
                continue;
            }
            int qh = 0;
            int qt = 0;
            que[qh++] = i;
            dp[i] = 0;
            long wrst = 0;
            while (qt < qh) {
                int now = que[qt++];
                for (int[] e : graph[now]) {
                    int to = e[0];
                    int diff = e[1];
                    long tv = dp[now] + diff;
                    uf.unite(now, to);
                    if (dp[to] == INF) {
                        wrst = Math.min(wrst, tv);
                        que[qh++] = to;
                        dp[to] = tv;
                    } else if (dp[to] != tv) {
                        return null;
                    }
                }
            }
            minValue[uf.find(i)] = wrst;
        }
        for (int i = 0; i < n ; i++) {
            minValue[i] = minValue[uf.find(i)];
        }
        return new long[][]{dp, minValue};
    }

    static class UnionFind {
        int[] rank;
        int[] parent;
        int[] cnt;

        public UnionFind(int n) {
            rank = new int[n];
            parent = new int[n];
            cnt = new int[n];
            for (int i = 0; i < n ; i++) {
                parent[i] = i;
                cnt[i] = 1;
            }
        }

        public int find(int a) {
            if (parent[a] == a) {
                return a;
            }
            parent[a] = find(parent[a]);
            return parent[a];
        }

        public void unite(int a, int b) {
            a = find(a);
            b = find(b);
            if (a == b) {
                return;
            }
            if (rank[a] < rank[b]) {
                parent[a] = b;
                cnt[b] += cnt[a];
                cnt[a] = cnt[b];
            } else {
                parent[b] = a;
                cnt[a] += cnt[b];
                cnt[b] = cnt[a];
                if (rank[a] == rank[b]) {
                    rank[a]++;
                }
            }
        }

        public int groupCount(int a) {
            return cnt[find(a)];
        }

        private boolean issame(int a, int b) {
            return find(a) == find(b);
        }
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
