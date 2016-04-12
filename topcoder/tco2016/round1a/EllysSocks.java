package topcoder.tco2016.round1a;

import java.util.Arrays;

/**
 * Created by hama_du on 4/12/16.
 */
public class EllysSocks {
    public int getDifference(int[] S, int P) {
        Arrays.sort(S);

        int n = S.length;
        int[][] dp = new int[n+1][P+1];
        for (int i = 0; i <= n ; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[0][0] = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j <= P; j++) {
                if (dp[i][j] == Integer.MAX_VALUE) {
                    continue;
                }
                int base = dp[i][j];

                // skip i.
                dp[i+1][j] = Math.min(dp[i+1][j], base);

                // pair i and i+1.
                if (i+1 < n && j+1 <= P) {
                    int diff = S[i+1]-S[i];
                    dp[i+2][j+1] = Math.min(dp[i+2][j+1], Math.max(base, diff));
                }
            }
        }
        return dp[n][P];
    }

    public static void main(String[] args) {
        EllysSocks solution = new EllysSocks();
        // debug(solution.someMethod());
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
