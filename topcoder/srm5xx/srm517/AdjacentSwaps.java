package topcoder.srm5xx.srm517;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/14.
 */
public class AdjacentSwaps {
    private static final long MOD = 1000000007;

    public int theCount(int[] p) {
        n = p.length;
        this.p = p;
        memo = new long[n][n];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(memo[i], -1);
            memo[i][i] = 1;
        }
        C = new long[200][200];
        for (int i = 0; i < 200 ; i++) {
            C[i][0] = C[i][i] = 1;
            for (int j = 1; j < i; j++) {
                C[i][j] = (C[i-1][j-1] + C[i-1][j]) % MOD;
            }
        }

        return (int)dfs(0, n-1);
    }

    public int n;
    public int[] p;

    public long[][] C;
    public long[][] memo;

    public long dfs(int l, int r) {
        if (memo[l][r] != -1) {
            return memo[l][r];
        }

        int[] sub = p.clone();
        Arrays.sort(sub, l, r+1);
        long ret = 0;
        for (int k = l; k < r; k++) {
            // at last, swap [k, k+1]
            boolean[] has = new boolean[n];
            for (int i = l; i < k ; i++) {
                has[sub[i]] = true;
            }
            has[sub[k+1]] = true;
            boolean canMake = true;
            for (int i = l ; i <= k; i++) {
                if (!has[p[i]]) {
                    canMake = false;
                    break;
                }
            }
            if (canMake) {
                long add = dfs(l, k) * dfs(k+1, r) % MOD;
                add *= C[r-l-1][k-l];
                add %= MOD;
                ret += add;
            }
        }
        ret %= MOD;
        memo[l][r] = ret;
        return ret;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    public static void main(String[] args) {
        AdjacentSwaps swaps = new AdjacentSwaps();
        debug(swaps.theCount(new int[]{1, 3, 0, 5, 2, 7, 4, 8, 10, 6, 12, 9, 14, 11, 16, 13, 18, 15, 19, 17}));
    }
}
