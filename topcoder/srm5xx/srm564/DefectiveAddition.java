package topcoder.srm5xx.srm564;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/11.
 */
public class DefectiveAddition {
    private static final long MOD = 1000000007;

    public int count(int[] cards, int n) {
        long[] c = new long[cards.length];
        for (int i = 0; i < cards.length ; i++) {
            c[i] = cards[i];
        }
        this.n = cards.length;
        return (int)dfs(c, n);
    }

    int n;

    public long dfs(long[] cards, long X) {
        Arrays.sort(cards);
        if (cards[n-1] == 0) {
            return (X == 0) ? 1 : 0;
        }

        long hi = Long.highestOneBit(cards[n-1]);
        if (X >= hi*2) {
            return 0;
        }

        long[][] dp = new long[n][2];
        dp[0][0] = 1;
        for (int i = 0; i < n-1 ; i++) {
            for (int f = 0; f <= 1 ; f++) {
                dp[i+1][f] += (dp[i][f] * Math.min(hi, cards[i]+1)) % MOD;
                dp[i+1][f] %= MOD;
                dp[i+1][f^1] += (dp[i][f] * Math.max(0, cards[i]-hi+1)) % MOD;
                dp[i+1][f^1] %= MOD;
            }
        }

        long ans = (X & hi) == hi ? dp[n-1][1] : dp[n-1][0];

        if (cards[n-1] >= hi) {
            long[] tc = cards.clone();
            tc[n-1] -= hi;
            ans += dfs(tc, X ^ hi);
        }
        ans %= MOD;
        return ans;
    }

    public static void main(String[] args) {
        DefectiveAddition da = new DefectiveAddition();
        debug(da.count(new int[]{1}, 1));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
