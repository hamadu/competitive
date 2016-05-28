package topcoder.srm6xx.srm666.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/05/28.
 */
public class SumOverPermutations {
    long MOD = (long )1e9+7;
    long C;
    int[][] comb;

    public int findSum(int n) {
        C = n;
        comb = new int[n+1][n+1];
        for (int i = 0; i <= n; i++) {
            comb[i][0] = comb[i][i] = 1;
            for (int j = 1 ; j < i ; j++) {
                comb[i][j] = (int)((comb[i-1][j-1] + comb[i-1][j]) % MOD);
            }
        }

        memo = new long[n+1];
        Arrays.fill(memo, -1);
        memo[1] = n;
        for (int i = 2 ; i <= n ; i++) {
            dfs(i);
        }
        return (int)dfs(n);
    }



    long[] memo;
    public long dfs(int n) {
        if (memo[n] != -1) {
            return memo[n];
        }
        long sum = 0;
        for (int lastPos = 1 ; lastPos <= n ; lastPos++) {
            long pl = (lastPos == 1 || lastPos == n) ? C-1 : C-2;
            int left = lastPos-1;
            int right = n-lastPos;
            long lp = (left >= 1) ? dfs(left) : 1;
            long rp = (right >= 1) ? dfs(right) : 1;
            sum += lp * rp % MOD * pl % MOD * comb[n-1][left] % MOD;
        }
        sum %= MOD;
        memo[n] = sum;
        return sum;
    }
}
