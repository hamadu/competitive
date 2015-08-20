package topcoder.srm5xx.srm528;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/18.
 */
public class ColorfulCookie {
    public int getMaximum(int[] cookies, int P1, int P2) {
        int min = 0;
        int max = 2;
        while (max - min > 1) {
            int med = (max + min) / 2;
            if (isPossible(med, cookies, P1, P2)) {
                min = med;
            } else {
                max = med;
            }
        }
        return min * (P1 + P2);
    }


    public boolean isPossible(int g, int[] cookies, int P1, int P2) {
        int n = cookies.length;
        int[][] dp = new int[n+1][g+1];
        for (int i = 0; i <= n ; i++) {
            Arrays.fill(dp[i], -1);
        }
        dp[0][0] = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j <= g ; j++) {
                if (dp[i][j] == -1) {
                    continue;
                }
                for (int x = 0; x * P1 <= cookies[i] && j + x <= g ; x++) {
                    int y = Math.min(g - x, (cookies[i] - x * P1) / P2);
                    dp[i+1][j+x] = Math.max(dp[i+1][j+x], dp[i][j] + y);
                }
            }
        }
        return dp[n][g] >= g;
    }

    public static void main(String[] args) {
        ColorfulCookie cookie = new ColorfulCookie();
        debug(cookie.getMaximum(new int[]{100, 100}, 50, 50));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
