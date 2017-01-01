package topcoder.srm6xx.srm698.div1;

import java.io.*;
import java.util.*;

public class RepeatString {
    public static final int INF = 1000000;

    public int minimalModify(String s) {
        int best = INF;
        for (int l = 0 ; l <= s.length() ; l++) {
            best = Math.min(best, solve(s.substring(0, l), s.substring(l)));
        }
        return best;
    }

    private int solve(String a, String b) {
        int n = a.length();
        int m = b.length();
        char[] c0 = a.toCharArray();
        char[] c1 = b.toCharArray();

        int[][] dp = new int[n+1][m+1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], INF);
        }

        dp[0][0] = 0;
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                int base = dp[i][j];
                if (i < n) {
                    dp[i+1][j] = Math.min(dp[i+1][j], base+1);
                }
                if (j < m) {
                    dp[i][j+1] = Math.min(dp[i][j+1], base+1);
                }
                if (i < n && j < m) {
                    int cost = c0[i] == c1[j] ? 0 : 1;
                    dp[i+1][j+1] = Math.min(dp[i+1][j+1], base+cost);
                }
            }
        }
        return dp[n][m];
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
