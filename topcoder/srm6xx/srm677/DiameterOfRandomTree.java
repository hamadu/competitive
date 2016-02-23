package topcoder.srm6xx.srm677;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/01/31.
 */
public class DiameterOfRandomTree {
    public double getExpectation(int[] a, int[] b) {
        graph = buildGraph(a, b);
        int n = graph.length;
        memo = new long[n][2*n+1][2*n+1];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < memo[i].length ; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }
        double ans = 0.0d;
        long all = 1L<<(n-1);
        long prev = 0;

        for (int d = 1 ; d <= 2*n-2 ; d++) {
            long ptn = dfs(0, -1, d, 2*n);
            ans += (ptn - prev) * d;
            debug(d, ptn - prev);
            prev = ptn;
        }
        return ans / all;
    }

    long[][][] memo;


    private long dfs(int head, int par, int diam, int depth) {
        if (memo[head][diam][depth] != -1) {
            return memo[head][diam][depth];
        }

        int cn = graph[head].length - ((par != -1) ? 1 : 0);
        if (cn == 0) {
            memo[head][diam][depth] = 1;
            return 1;
        }

        long[][] info = new long[cn][depth+2];
        int ci = 0;
        for (int to : graph[head]) {
            if (to == par) {
                continue;
            }
            for (int de = 0 ; de <= depth ; de++) {
                info[ci][de] = dfs(to, head, diam, de);
            }
            info[ci][depth+1] = info[ci][depth];
            ci++;
        }

        long[][] dp = new long[cn+1][diam+1];
        dp[0][0] = 1;
        for (int i = 0 ; i < cn ; i++) {
            for (int d = 0 ; d <= diam ; d++) {
                if (dp[i][d] == 0) {
                    continue;
                }
                long base = dp[i][d];
                for (int de = 0 ; de + d <= diam ; de++) {
                    long ptn = 0;
                    for (int e = 1 ; e <= 2 ; e++) {
                        if (de - e < 0) {
                            continue;
                        }
                        int len = de - e;
                        ptn += info[i][len+1] - info[i][len];
                    }
                    int tw = Math.max(d, de);
                    dp[i+1][tw] += base * ptn;
                }
            }
        }
        long sum = 0;
        for (int i = 0 ; i <= diam ; i++) {
            sum += dp[cn][i];
        }

        long ret = 0;
        for (int baseDepth = 1; baseDepth <= diam && baseDepth <= depth ; baseDepth++) {
            for (int baseIdx = 0 ; baseIdx < ci ; baseIdx++) {
                for (int baseEdge = 1 ; baseEdge <= 2 ; baseEdge++) {
                    if (baseDepth < baseEdge) {
                        continue;
                    }
                    long ptn = info[baseIdx][baseDepth-baseEdge];
                    if (baseDepth-baseEdge-1 >= 0) {
                        ptn -= info[baseIdx][baseDepth-baseEdge-1];
                    }

                    for (int i = 0 ; i < ci ; i++) {
                        if (i == baseIdx) {
                            continue;
                        }
                        long foe = 0;
                        int limit = diam - baseDepth;
                        if (i < baseIdx) {
                            limit = Math.min(limit, baseDepth-1);
                        } else if (i > baseIdx) {
                            limit = Math.min(limit, baseDepth);
                        }
                        for (int e = 1 ; e <= 2 ; e++) {
                            if (limit - e >= 0) {
                                foe += info[i][limit-e];
                            }
                        }
                        ptn *= foe;
                    }
                    ret += ptn;
                }
            }
        }
        memo[head][diam][depth] = ret;

        debug(sum, ret);
        return ret;
    }

    static int[][] graph;

    public static void main(String[] args) {
        DiameterOfRandomTree solution = new DiameterOfRandomTree();

        debug(solution.getExpectation(new int[]{0,0,0}, new int[]{1,2,3}));
    }


    static int[][] buildGraph(int[] l, int[] r) {
        int n = 0;
        for (int i = 0 ; i < l.length ; i++) {
            n = Math.max(n, l[i]);
            n = Math.max(n, r[i]);
        }
        n++;
        int m = l.length;
        int[][] edges = new int[m][];
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            deg[l[i]]++;
            deg[r[i]]++;
            edges[i] = new int[]{l[i], r[i]};
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


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
