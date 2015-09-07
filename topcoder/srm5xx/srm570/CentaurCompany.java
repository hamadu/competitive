package topcoder.srm5xx.srm570;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/04.
 */
public class CentaurCompany {
    public double getvalue(int[] a, int[] b) {
        int n = a.length+1;
        int[][] edge = new int[n-1][2];
        for (int i = 0; i < n-1 ; i++) {
            edge[i][0] = a[i]-1;
            edge[i][1] = b[i]-1;
        }
        graph = buildGraph(n, edge);

        double[][][] ret = dfs(0, -1);

        double ans = 0;
        for (int f = 0; f <= 1 ; f++) {
            for (int vs = 0; vs <= n; vs++) {
                for (int cs = 0; cs <= n; cs++) {
                    if (ret[f][vs][cs] > 0) {
                        ans += 0.5 * ret[f][vs][cs] * Math.max(0, 2 * (cs-1)-vs);
                    }
                }
            }
        }
        return ans * 2;
    }

    int[][] graph;

    double[][][] dfs(int now, int par) {
        // dp[flg][vsize][csize] : probability of subtree of now
        int cn = (par == -1) ? graph[now].length : graph[now].length-1;
        if (cn == 0) {
            double[][][] result = new double[2][40][40];
            result[0][0][0] = 1.0d;
            result[1][1][1] = 1.0d;
            return result;
        }

        double[][][] sub = new double[cn][][];
        int ci = 0;

        double[][][][] dp = new double[40][2][40][40];
        dp[0][0][0][0] = 1.0d;
        dp[0][1][1][1] = 1.0d;
        for (int ch : graph[now]) {
            if (ch == par) {
                continue;
            }
            double[][][] res = dfs(ch, now);
            int fr = ci;
            int to = ci+1;
            for (int f = 0; f <= 1 ; f++) {
                for (int vs = 0; vs <= 36; vs++) {
                    for (int cs = 0; cs <= 36; cs++) {
                        if (dp[fr][f][vs][cs] == 0) {
                            continue;
                        }
                        double base = dp[fr][f][vs][cs];
                        for (int cvs = 0; cvs <= 36; cvs++) {
                            for (int ccs = 0; ccs <= 36; ccs++) {
                                if (res[0][cvs][ccs] > 0) {
                                    dp[to][f][vs+cvs][cs+ccs] += base * res[0][cvs][ccs] * 0.5;
                                }
                                if (res[1][cvs][ccs] > 0) {
                                    int tvs = vs+cvs;
                                    int tcs = cs+ccs-((f==1)?1:0);
                                    dp[to][f][tvs][tcs] += base * res[1][cvs][ccs] * 0.5;
                                }
                            }
                        }
                    }
                }
            }
            ci++;
        }
        return dp[cn];
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

    public static void main(String[] args) {
        CentaurCompany company = new CentaurCompany();
        debug(company.getvalue(
                new int[]{10, 7, 2, 5, 6, 2, 4, 9, 7},
                new int[]{8, 10, 10, 4, 1, 6, 2, 2, 3}
        ));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
