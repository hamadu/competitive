package topcoder.srm5xx.srm533;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/23.
 */
public class CasketOfStar {
    public int maxEnergy(int[] weight) {
        int n = weight.length;
        memo = new int[n][n];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(memo[i], -1);
        }
        w = weight;
        return dfs(0, n-1);
    }

    int[] w;
    int[][] memo;

    public int dfs(int left, int right) {
        if (left + 1 == right) {
            return 0;
        }
        if (memo[left][right] != -1) {
            return memo[left][right];
        }
        int ret = w[left] * w[right];
        int max = 0;
        for (int k = left+1 ; k <= right-1 ; k++) {
            max = Math.max(max, dfs(left, k) + dfs(k, right));
        }
        ret += max;
        memo[left][right] = ret;
        return ret;
    }
}
