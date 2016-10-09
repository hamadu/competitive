package codeforces.cf3xx.cf372.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int M = in.nextInt();
        graph = buildWeightedGraph(in, n, n-1);

        CenteroidDecomposition decomp = new CenteroidDecomposition(unweightGraph(graph));
        MOD = M;
        decompTree = buildRootedTreeFromPar(decomp.par);
        done = new boolean[n];
        up = new long[n];
        dw = new long[n];
        que = new int[4*n];
        ord = new int[n];
        depth = new int[n];
        pow10 = new long[n+1];
        pow10[0] = 1;
        inv10 = new long[n+1];
        inv10[0] = 1;
        for (int i = 1; i <= n; i++) {
            pow10[i] = (pow10[i-1] * 10) % M;
        }

        long T = totient(MOD);
        for (int i = 1 ; i <= n ; i++) {
            inv10[i] = inv(pow10[i], T);
        }

        int root = -1;
        for (int i = 0; i < n ; i++) {
            if (decomp.par[i] == -1) {
                root = i;
                break;
            }
        }
        out.println(solve(root));
        out.flush();
    }

    static long pow(long a, long x) {
        long res = 1;
        while (x > 0) {
            if (x % 2 != 0) {
            res = (res * a) % MOD;
            }
            a = (a * a) % MOD;
            x /= 2;
        }
        return res;
    }

    static int[] generatePrimes(int upto) {
        boolean[] isp = new boolean[upto];
        Arrays.fill(isp, true);
        isp[0] = isp[1] = false;

        int pi = 0;
        for (int i = 2; i < upto ; i++) {
            if (isp[i]) {
                pi++;
                for (int j = i * 2; j < upto; j += i) {
                    isp[j] = false;
                }
            }
        }

        int[] ret = new int[pi];
        int ri = 0;
        for (int i = 2 ; i < upto ; i++) {
            if (isp[i]) {
                ret[ri++] = i;
            }
        }
        return ret;
    }

    static long totient(long num) {
        int[] primes = generatePrimes((int)Math.sqrt(num)+10);

        long ans = 1;
        for (long p : primes) {
            if (num < p) {
                break;
            }
            long pn = 1;
            while (num % p == 0) {
                pn *= p;
                num /= p;
            }
            if (pn >= p) {
                ans *= (pn/p)*(p-1);
            }
        }
        if (num >= 2) {
            ans *= num - 1;
        }
        return ans;
    }


    static long inv(long a, long tot) {
        return pow(a, tot - 1) % MOD;
    }

    static long MOD;
    static int[][][] graph;
    static int[][] decompTree;
    static boolean[] done;
    static long[] up;
    static long[] dw;
    static int[] que;
    static int[] ord;
    static int[] depth;

    static long[] pow10, inv10;
    static int on;
    static int head;
    static List<Integer> li = new ArrayList<>();

    static void dfs(int now, int par, int dep, int w) {
        ord[on++] = now;
        depth[now] = dep;
        if (par != -1) {
            dw[now] = (dw[par] * 10 + w) % MOD;
            up[now] = (up[par] + pow10[dep-1] * w) % MOD;
        } else {
            dw[now] = up[now] = 0;
        }

        if (par == head) {
            li.add(on-1);
        }
        for (int[] e : graph[now]) {
            if (e[0] == par || done[e[0]]) {
                continue;
            }
            dfs(e[0], now, dep+1, e[1]);
        }
    }

    static long solve(int hd) {
        head = hd;
        on = 0;
        li.clear();
        dfs(head, -1, 0, 0);
        li.add(on);

        long ans = 0;
        for (int i = 1 ; i < on ; i++) {
            int idx = ord[i];
            if (dw[idx] == 0) {
                ans++;
            }
            if (up[idx] == 0) {
                ans++;
            }
        }

        Map<Long,Integer> availableDeg = new HashMap<>();
        for (int i = 1 ; i < on ; i++) {
            int idx = ord[i];
            int cnt = availableDeg.getOrDefault(up[idx], 0) + 1;
            availableDeg.put(up[idx], cnt);
        }

        int ci = 0;
        for (int i = 1 ; i < on ; i++) {
            int idx = ord[i];
            if (ci < li.size() && li.get(ci) == i) {
                for (int f = 0 ; f <= 1 ; f++) {
                    if (f == 1 && ci == 0) {
                        continue;
                    }
                    int fr = li.get(ci-f);
                    int to = li.get(ci+1-f);
                    for (int o = fr; o < to; o++) {
                        int cidx = ord[o];
                        int cnt = availableDeg.getOrDefault(up[cidx], 0)-1+2*f;
                        availableDeg.put(up[cidx], cnt);
                    }
                }
                ci++;
            }

            // UP * 10^D + DW = 0 (mod M)
            // UP = -DW * 10-^D
            long wantUP = (MOD - dw[idx]) % MOD * inv10[depth[idx]] % MOD;
            ans += availableDeg.getOrDefault(wantUP, 0);
        }

        done[head] = true;
        for (int to : decompTree[head]) {
            ans += solve(to);
        }
        return ans;
    }


    static int[][] unweightGraph(int[][][] weightGraph) {
        int n = weightGraph.length;
        int[][] graph = new int[n][];
        for (int i = 0; i < n ; i++) {
            graph[i] = new int[weightGraph[i].length];
            for (int j = 0 ; j < weightGraph[i].length ; j++) {
                graph[i][j] = weightGraph[i][j][0];
            }
        }
        return graph;
    }

    // par[i] = -1 := vertex i is the root
    static int[][] buildRootedTreeFromPar(int[] par) {
        int n = par.length;
        int[][] edges = new int[n-1][2];
        int ei = 0;
        for (int i = 0; i < n ; i++) {
            if (par[i] != -1) {
                edges[ei][0] = i;
                edges[ei][1] = par[i];
                ei++;
            }
        }

        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < ei ; i++) {
            int b = edges[i][1];
            deg[b]++;
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < ei ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[b][--deg[b]] = a;
        }
        return graph;
    }



    public static class CenteroidDecomposition {
        int n;
        int[][] graph;
        int[] removed;
        int[] que;
        int[] parent;
        int[] children;
        int[] ord;
        boolean[] visited;

        int[] par;

        public CenteroidDecomposition(int[][] g) {
            graph = g;
            n = graph.length;
            removed = new int[n];

            que = new int[2*n];
            parent = new int[n];
            children = new int[n];
            ord = new int[n];
            visited = new boolean[n];

            int[] q = new int[2*n];
            int qh = 0, qt = 0;
            q[qh++] = 0;
            q[qh++] = -1;

            par = new int[n];
            while (qt < qh) {
                int now = q[qt++];
                int center = doit(now);
                par[center] = q[qt++];
                visited[center] = true;
                for (int to : graph[center]) {
                    if (visited[to]) {
                        continue;
                    }
                    q[qh++] = to;
                    q[qh++] = center;
                }
            }
        }

        private int doit(int root) {
            int qh = 0, qt = 0;
            que[qh++] = root;
            que[qh++] = -1;

            int on = 0;
            while (qt < qh) {
                int now = que[qt++];
                int par = que[qt++];
                parent[now] = par;
                children[now] = 1;
                ord[on++] = now;

                int cn = graph[now].length;
                for (int i = 0 ; i < cn ; i++) {
                    int to = graph[now][i];
                    if (to == par || visited[to]) {
                        continue;
                    }
                    que[qh++] = to;
                    que[qh++] = now;
                }
            }

            for (int i = on-1 ; i >= 0 ; i--) {
                int v = ord[i];
                if (parent[v] == -1) {
                    continue;
                }
                children[parent[v]] += children[v];
            }

            int best = on+1;
            int bestV = -1;
            for (int i = 0 ; i < on ; i++) {
                int v = ord[i];
                int max = 0;
                for (int ci = 0 ; ci < graph[v].length ; ci++) {
                    int to = graph[v][ci];
                    if (parent[v] == to || visited[to]) {
                        continue;
                    }
                    max = Math.max(max, children[to]);
                }
                max = Math.max(max, on-1-max);
                if (best > max) {
                    best = max;
                    bestV = v;
                }
            }
            return bestV;
        }
    }


    static int[][][] buildWeightedGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][][] graph = new int[n][][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int w = in.nextInt();
            deg[a]++;
            deg[b]++;
            edges[i] = new int[]{a, b, w};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]][2];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            int w = edges[i][2];
            graph[a][--deg[a]][0] = b;
            graph[b][--deg[b]][0] = a;
            graph[a][deg[a]][1] = w;
            graph[b][deg[b]][1] = w;
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
