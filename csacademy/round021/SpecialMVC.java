package csacademy.round021;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class SpecialMVC {
    private static final int INF = 10000000;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        graph = new List[n];
        for (int i = 0; i < n ; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            Edge e = new Edge();
            e.a = a;
            e.b = b;
            graph[b].add(e);
            graph[a].add(e);
        }

        visited = new boolean[n];
        father = new Edge[n];
        depth = new int[n];
        cycles = new ArrayList<>();
        cycleHeads = new List[n];
        for (int i = 0; i < n ; i++) {
            cycleHeads[i] = new ArrayList<>();
        }
        dp = new int[n][2];
        for (int i = 0; i < n ; i++) {
            dp[i][0] = dp[i][1] = INF;
        }

        int ans = 0;
        for (int i = 0 ; i < n ; i++) {
            if (!visited[i]) {
                visited[i] = true;
                prepare(i, -1);
                solve(i, -1);
                ans += Math.min(dp[i][0], dp[i][1]);
            }
        }

        out.println(ans);
        out.flush();
    }

    static void prepare(int now, int par) {
        for (Edge e : graph[now]) {
            int to = e.a + e.b - now;
            if (to == par) {
                continue;
            }
            if (visited[to]) {
                if (e.isCycle) {
                    continue;
                }
                e.isCycle = true;
                int cid = cycles.size();
                cycleHeads[to].add(cid);
                List<Integer> cy = new ArrayList<>();

                int head = now;
                while (head != to) {
                    cy.add(head);
                    Edge ed = father[head];
                    ed.isCycle = true;
                    head = ed.a + ed.b - head;
                }
                cy.add(to);
                Collections.reverse(cy);
                cycles.add(cy);
            } else {
                visited[to] = true;
                father[to] = e;
                depth[to] = depth[now]+1;
                prepare(to, now);
            }
        }
    }

    static int[] solve(int now, int par) {
        if (dp[now][0] != INF) {
            return dp[now];
        }

        // is Cycle head?
        if (cycleHeads[now].size() >= 1) {
            int zero = 0;
            int one = 0;
            for (int cids : cycleHeads[now]) {
                List<Integer> cy = cycles.get(cids);
                int cn = cy.size();
                int[][] costs = new int[cn][2];
                for (int i = 1; i < cn ; i++) {
                    int wo = cy.get(i);
                    if (i >= 1 && cycleHeads[wo].size() >= 1) {
                        int[] co = solve(wo, cy.get(i-1));
                        costs[i] = co;
                    } else {
                        for (Edge e : graph[wo]) {
                            int to = e.a+e.b-wo;
                            if (to == par || e.isCycle) {
                                continue;
                            }
                            int[] co = solve(to, wo);
                            costs[i][0] += co[1];
                            costs[i][1] += Math.min(co[0], co[1]);
                        }
                        costs[i][1]++;
                    }
                }

                for (int c = 0 ; c <= 1 ; c++) {
                    int[] head = new int[]{INF * c, INF * (c ^ 1)};
                    for (int i = 1 ; i < cn ; i++) {
                        int[] tow = new int[]{INF, INF};
                        for (int last = 0 ; last <= 1 ; last++) {
                            int base = head[last];
                            for (int tw = 0 ; tw <= 1 ; tw++) {
                                if (last == 0 && tw == 0) {
                                    continue;
                                }
                                if (c == 0 && i == cn-1 && tw == 0) {
                                    continue;
                                }
                                tow[tw] = Math.min(tow[tw], costs[i][tw]+base);
                            }
                        }
                        head = tow;
                    }

                    if (c == 0) {
                        zero += head[1];
                    } else {
                        one += Math.min(head[0], head[1]);
                    }
                }
            }

            for (Edge e : graph[now]) {
                int to = e.a+e.b-now;
                if (to == par || e.isCycle) {
                    continue;
                }
                int[] co = solve(to, now);
                zero += co[1];
                one += Math.min(co[0], co[1]);
            }
            dp[now][0] = zero;
            dp[now][1] = one+1;
        } else {
            int zero = 0;
            int one = 1;
            for (Edge e : graph[now]) {
                int to = e.a + e.b - now;
                if (to == par) {
                    continue;
                }
                int[] t = solve(to, now);
                zero += t[1];
                one += Math.min(t[0], t[1]);
            }
            dp[now][0] = zero;
            dp[now][1] = one;
        }
        return dp[now];
    }

    static class Edge {
        int a, b;
        boolean isCycle;

        public boolean has(int now) {
            return a == now || b == now;
        }

        public String toString() {
            return String.format("%d-%d : %d", a, b, isCycle ? 1 : 0);
        }
    }

    static List<List<Integer>> cycles;
    static int[] depth;
    static Edge[] father;
    static List<Edge>[] graph;
    static boolean[] visited;
    static int[][] dp;
    static List<Integer>[] cycleHeads;


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
