package codeforces.other2016.aimtech2016.round3;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.TreeSet;

/**
 * Created by hama_du on 2016/08/25.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] graph = buildGraph(in, n, n-1);
        int[][] zpco = parentCountOrder(graph, 0);
        int[] par = zpco[0];
        int[] cnt = zpco[1];
        int rt = -1;


        boolean[] canCenter = new boolean[n];
        int[] max = new int[n];
        int[] min = new int[n];
        for (int i = 0; i < n ; i++) {
            max[i] = 0;
            min[i] = n+1;
            for (int to : graph[i]) {
                if (to != par[i]) {
                    max[i] = Math.max(max[i], cnt[to]);
                    min[i] = Math.min(min[i], cnt[to]);
                }
            }
            if (par[i] != -1) {
                max[i] = Math.max(max[i], n-cnt[i]);
                min[i] = Math.min(min[i], n-cnt[i]);
            }
            canCenter[i] = (max[i] <= n/2);
            if (canCenter[i]) {
                rt = i;
            }
        }

        int[] euler = eulerTour(graph, rt);
        par = pco[0];
        cnt = pco[1];

        long[] value = new long[n];
        long[] outvalue = new long[n];
        for (int i = 0; i < n ; i++) {
            value[i] = (((long)cnt[i])<<20L)+i;
            outvalue[i] = (((long)(n-cnt[i]))<<20L)+n+i;
        }

        for (int i = 0; i < n ; i++) {
            min[i] = n+1;
            for (int to : graph[i]) {
                if (to != par[i]) {
                    min[i] = Math.min(min[i], cnt[to]);
                }
            }
            if (min[i] == n+1) {
                min[i] = 0;
            }
        }

        for (int head : new int[]{0, 2*n-1}) {
            int diff = (head == 0) ? 1 : -1;
            boolean[] visited = new boolean[n];
            TreeSet<Long> have = new TreeSet<>();
            for (int i = head ; ; i += diff) {
                if (i < 0 || i >= 2*n) {
                    break;
                }
                int meet = euler[i];
                if (!visited[meet]) {
                    if (!canCenter[meet]) {
                        long limit = (((long)(n/2))<<20L)+((1<<20)-1);
                        Long x = (have.lower(limit));
                        if (x != null) {
                            int dec = (int) (x>>20);
                            if (max[meet]-dec <= n/2) {
                                canCenter[meet] = true;
                            }
                        }
                    }
                    have.add(outvalue[meet]);
                } else {
                    have.remove(outvalue[meet]);
                    have.add(value[meet]);
                }
                visited[meet] = true;
            }
        }

        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < n ; i++) {
            ans.append(' ');
            ans.append(canCenter[i] ? 1 : 0);
        }
        out.println(ans.substring(1));
        out.flush();
    }

    static int[][] pco;

    public static int[][] parentCountOrder(int[][] graph, int root) {
        int n = graph.length;
        int[] que = new int[2*n];
        int[] parent = new int[n];
        int[] bfsOrd = new int[n];
        int[] cnt = new int[n];
        int qh = 0, qt = 0;
        que[qh++] = root;
        que[qh++] = -1;
        int vi = 0;
        while (qt < qh) {
            int now = que[qt++];
            int par = que[qt++];
            parent[now] = par;
            bfsOrd[vi++] = now;
            for (int to : graph[now]) {
                if (to == par) {
                    continue;
                }
                que[qh++] = to;
                que[qh++] = now;
            }
        }
        for (int i = n-1 ; i >= 0 ; i--) {
            int now = bfsOrd[i];
            cnt[now]++;
            if (parent[now] != -1) {
                cnt[parent[now]] += cnt[now];
            }
        }
        return new int[][]{ parent, cnt, bfsOrd };
    }

    public static int[] eulerTour(int[][] graph, int root) {
        pco = parentCountOrder(graph, root);

        int n = graph.length;
        int[] parent = pco[0];
        int[] cnt = pco[1];
        int[] euler = new int[2*n];

        Arrays.fill(euler, -1);
        int ei = 0;
        int[] stk = new int[n];
        int head = 0;
        stk[head++] = root;
        while (head > 0) {
            int now = stk[--head];
            while (euler[ei] != -1) {
                ei++;
            }
            euler[ei++] = now;
            euler[ei+(cnt[now]-1)*2] = now;
            for (int to : graph[now]) {
                if (to == parent[now]) {
                    continue;
                }
                stk[head++] = to;
            }
        }
        return euler;
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
