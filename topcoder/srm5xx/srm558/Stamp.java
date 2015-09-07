package topcoder.srm5xx.srm558;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/02.
 */
public class Stamp {
    private static final int INF = 114514;

    public int getMinimumCost(String desiredColor, int stampCost, int pushCost) {
        char[] wantColor = desiredColor.toCharArray();
        int n = desiredColor.length();
        int min = Integer.MAX_VALUE;
        int[] colorCode = new int[255];
        Arrays.fill(colorCode, -1);
        colorCode['R'] = 0;
        colorCode['G'] = 1;
        colorCode['B'] = 2;
        for (int l = 1; l <= n ; l++) {
            int[] dp = new int[n+1];
            for (int i = 0; i <= n; i++) {
                Arrays.fill(dp, INF);
            }
            dp[0] = 0;
            for (int i = 0; i < n ; i++) {
                if (dp[i] == INF) {
                    continue;
                }
                for (int uc = 0; uc <= 2; uc++) {
                    for (int t = i ; t < n; t++) {
                        if (wantColor[t] != '*' && colorCode[wantColor[t]] != uc) {
                            break;
                        }
                        int tc = t+1;
                        int len = tc-i;
                        if (len >= l) {
                            dp[tc] = Math.min(dp[tc], dp[i] + (len+l-1)/l);
                        }
                    }
                }
            }
            if (dp[n] < INF) {
                min = Math.min(min, stampCost * l+dp[n] * pushCost);
            }
        }
        return min;
    }
}
