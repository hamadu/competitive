package topcoder.srm6xx.srm690.div1;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created by hama_du on 2016/05/09.
 */
public class TreeWalker {
    public int calc(int N, int A0, int B, int C, int MOD) {
        long[] A = new long[N];
        A[0] = A0;
        for (int i = 1 ; i < N-1 ; i++) {
            A[i] = (A[i-1] * B + C) % MOD;
        }
        int[][] edges = new int[N-1][2];
        for (int i = 1 ; i <= N-1 ; i++) {
            int j = (int)(A[i-1] % i);
            edges[i-1][0] = i;
            edges[i-1][1] = j;
        }
        graph = buildGraph(N, edges);
        n = graph.length;
        return (int)solve();
    }

    public static void main(String[] args) {
        TreeWalker walker = new TreeWalker();
        //debug(walker.calc(10, 0, 0, 0, 1));
        //debug(walker.calc(16, 15, 1, 1, 16));
        debug(walker.calc(100000,
                111222333,
                444555666,
                777888999,
                123456789));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }


    private long solve() {
        pow2 = new long[n+10];
        pow2_inv = new long[n+10];
        pow2[0] = 1;
        pow2_inv[0] = 1;
        for (int i = 1; i < pow2.length ; i++) {
            pow2[i] = pow2[i-1] * 2 % MOD;
            pow2_inv[i] = inv(pow2[i]);
        }


        EulerTour tour = new EulerTour(graph);
        tour.build(0);

        // debug(graph);
        // debug(tour.ar);

        value = new long[n];
        for (int i = 0 ; i < 2*n ; i++) {
            int event = tour.ar[i];
            if (event >= 0) {
                // in
            } else {
                // out
                int v = -event-1;
                int cnt = 0;
                for (int to : graph[v]) {
                    if (to == tour.parent[v]) {
                        continue;
                    }
                    cnt++;
                    value[v] += value[to];
                }
                value[v] += cnt;
                value[v] %= MOD;
                value[v] *= pow2_inv[1];
                value[v] %= MOD;
            }
        }

        long ret = 0;
        long outer = value[0];
        Stack<Long> val = new Stack<>();

        for (int i = 0 ; i < 2*n ; i++) {
            int v = tour.ar[i];
            if (v >= 0) {
                val.push(outer);

                // in
                int par = tour.parent[v];
                if (par >= 0) {
                    long tmp = (value[v] + 1) * pow2_inv[1] % MOD;
                    long outerRight = (outer - tmp + MOD) % MOD;
                    long outerLeft = value[v];
                    outer = (outerLeft + (outerRight + 1) * pow2_inv[1] % MOD) % MOD;
                }
                ret += outer;
                ret %= MOD;
                // debug("mo", v, par, outer * pow2[n-1] % MOD);
            } else {
                // out
                outer = val.pop();
                // debug("leave ", -v-1, outer * pow2[n-1] % MOD);
            }
        }
        ret *= pow2[n-1];
        ret %= MOD;
        return ret;
    }

    static class EulerTour {
        int n;
        int[][] graph;
        int[] parent;
        int[] ar;
        int[] cn;
        int ai = 0;

        public EulerTour(int[][] graph) {
            this.graph = graph;
            this.n = graph.length;
            this.parent = new int[n];
            this.ar = new int[2*n];
            this.cn = new int[n];
        }

        private void go(int now) {
            ar[ai++] = now;
            int head = now;
            while (head != -1) {
                if (cn[head] != 0) {
                    break;
                }
                ar[ai++] = -(head+1);
                head = parent[head];
                if (head != -1) {
                    cn[head]--;
                }
            }
        }

        private void parentChild(int root) {
            int[] que = new int[4*n];
            int qh = 0;
            int qt = 0;
            que[qh++] = root;
            que[qh++] = -1;
            while (qt < qh) {
                int now = que[qt++];
                int par = que[qt++];
                parent[now] = par;
                for (int to : graph[now]) {
                    if (to != par) {
                        que[qh++] = to;
                        que[qh++] = now;
                    }
                }
            }
            for (int i = 0; i < n ; i++) {
                cn[i] = (parent[i] == -1) ? graph[i].length : graph[i].length-1;
            }
        }

        void build(int root) {
            parentChild(root);

            Stack<Integer> stk = new Stack<>();
            ai = 0;
            stk.push(root);
            stk.push(-1);
            while (stk.size() >= 1) {
                int par = stk.pop();
                int now = stk.pop();
                go(now);
                for (int to : graph[now]) {
                    if (to != par) {
                        stk.push(to);
                        stk.push(now);
                    }
                }
            }
        }
    }


    int n;
    int[][] graph;
    long[] pow2;
    long[] pow2_inv;
    long[] value;

    static final int MOD = 1000000007;

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

    static long inv(long a) {
        return pow(a, MOD - 2) % MOD;
    }


    static int[][] buildGraph(int n, int[][] edges) {
        int m = edges.length;
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            deg[a]++;
            deg[b]++;
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
}
