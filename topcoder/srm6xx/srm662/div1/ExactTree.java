package topcoder.srm6xx.srm662.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/06/07.
 */
public class ExactTree {
    int INF = (int)1e8;
    int[][] memo;
    int M;
    int N;

    public int dfs(int n, int want) {
        if (memo[n][want] <= INF) {
            return memo[n][want];
        }
        int best = INF;
        if (n == 1) {
            best = (want == 0) ? 0 : INF;
        } else {
            for (int a = 1; a <= n-1; a++) {
                int b = a*(N-a)%M;
                for (int l = 0; l < M; l++) {
                    int leftCost = dfs(a, l);
                    int r = (2*M+want-b-l)%M;
                    int rightCost = dfs(n-a, r);
                    best = Math.min(best, a*(N-a)+leftCost+rightCost);
                }
            }
        }
        memo[n][want] = best;
        return best;
    }

    public int getTree(int n, int m, int r) {
        M = m;
        N = n;
        memo = new int[n+1][m];
        for (int i = 0; i <= n ; i++) {
            Arrays.fill(memo[i], INF+1);
        }
        for (int i = 1; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                dfs(i, j);
            }
        }
        int ret = dfs(n, r);
        if (ret == INF) {
            return -1;
        }
        return ret;
    }
}
