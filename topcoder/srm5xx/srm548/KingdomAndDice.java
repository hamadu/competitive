package topcoder.srm5xx.srm548;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hama_du on 15/08/28.
 */
public class KingdomAndDice {
    public double newFairness(int[] firstDie, int[] secondDie, int X) {
        int n = firstDie.length;
        Set<Integer> have = new HashSet<>();
        for (int i = 0; i < n ; i++) {
            have.add(firstDie[i]);
            have.add(secondDie[i]);
        }

        int[] limit = new int[n+1];
        limit[0] = n;
        Arrays.sort(secondDie);
        for (int i = 0; i < n ; i++) {
            int from = secondDie[i]+1;
            int to = i == n-1 ? X : secondDie[i+1]-1;

            int cnt = 0;
            for (int f = from ; f <= to ; f++) {
                if (!have.contains(f)) {
                    cnt++;
                }
                if (cnt >= n) {
                    break;
                }
            }
            limit[i+1] = cnt;
        }

        int left = 0;
        for (int i = 0; i < n ; i++) {
            if (firstDie[i] == 0) {
                left++;
            }
        }


        boolean[][][] dp = new boolean[n+2][left+1][2501];
        dp[0][0][0] = true;
        for (int i = 0; i <= n ; i++) {
            for (int u = 0; u <= left; u++) {
                for (int j = 0; j <= 2500; j++) {
                    if (!dp[i][u][j]) {
                        continue;
                    }
                    for (int k = 0; k <= limit[i] && j+i*k <= 2500 && u+k <= left ; k++) {
                        dp[i+1][u+k][j+i*k] = true;
                    }
                }
            }
        }

        
        int n2 = n * n * 2;
        int win = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n ; j++) {
                if (firstDie[i] != 0 && firstDie[i] > secondDie[j]) {
                    win++;
                }
            }
        }

        int best = 1000000;
        for (int u = 0; u <= 2500 ; u++) {
            if (dp[n+1][left][u]) {
                int mk = (win + u) * 2;
                int center = n2 / 2;
                if (Math.abs(best - center) > Math.abs(mk - center)) {
                    best = mk;
                } else if (Math.abs(best - center) == Math.abs(mk - center) && best > mk) {
                    best = mk;
                }
            }
        }
        return 1.0d * best / n / n / 2;
    }
}
