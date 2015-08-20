package topcoder.srm5xx.srm527;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/18.
 */
public class P8XGraphBuilder {
    public int solve(int[] scores) {
        n = scores.length + 1;
        score = new int[n];
        for (int i = 1; i < n; i++) {
            score[i] = scores[i-1];
        }
        memo = new int[n+1][2];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(n-1, 0);
    }

    int n;
    int[] score;
    int[][] memo;

    public int dfs(int left, int p) {
        if (memo[left][p] != -1) {
            return memo[left][p];
        }
        if (left == 0) {
            return score[p];
        }

        int max = 0;
        int cs = left;
        for (int cn = 0 ; cn <= cs ; cn++) {
            int L = left - cn;
            int[][] dp = new int[cn+1][L+1];
            for (int i = 0; i <= cn; i++) {
                Arrays.fill(dp[i], -1);
            }
            dp[0][0] = 0;
            for (int i = 0; i < cn ; i++) {
                for (int j = 0; j <= L ; j++) {
                    if (dp[i][j] == -1) {
                        continue;
                    }
                    for (int k = 0; j+k <= L ; k++) {
                        dp[i+1][j+k] = Math.max(dp[i+1][j+k], dp[i][j] + dfs(k, 1));
                    }
                }
            }
            if (dp[cn][L] >= 0) {
                max = Math.max(max, dp[cn][L] + score[cn+p]);
            }
        }
        memo[left][p] = max;
        return max;
    }
}
