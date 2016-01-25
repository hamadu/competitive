package topcoder.srm5xx.srm588;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/26.
 */
public class KeyDungeonDiv1 {
    private static final int INF = 100000000;

    public int maxKeys(int[] doorR, int[] doorG, int[] roomR, int[] roomG, int[] roomW, int[] keys) {
        int n = doorR.length;
        int[][] dp = new int[1 << n][121];
        for (int i = 0; i < (1 << n); i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[0][0] = 0;

        boolean[] visited = new boolean[1 << n];
        for (int p = 0; p < (1 << n); p++) {
            for (int wr = 0; wr <= 120; wr++) {
                if (dp[p][wr] == INF) {
                    continue;
                }
                visited[p] = true;
                int wg = dp[p][wr];

                int usedR = 0;
                int usedG = 0;
                int gainR = 0;
                int gainG = 0;
                int gainW = 0;
                for (int k = 0; k < n; k++) {
                    if ((p & (1 << k)) >= 1) {
                        usedR += doorR[k];
                        usedG += doorG[k];
                        gainR += roomR[k];
                        gainG += roomG[k];
                        gainW += roomW[k];
                    }
                }
                usedR -= wr;
                usedG -= wg;

                int currentR = gainR+keys[0]-usedR;
                int currentG = gainG+keys[1]-usedG;
                int currentW = gainW+keys[2]-wr-wg;
                for (int k = 0; k < n; k++) {
                    if ((p & (1 << k)) >= 1) {
                        continue;
                    }
                    for (int ur = 0; ur <= doorR[k]; ur++) {
                        for (int ug = 0; ug <= doorG[k]; ug++) {
                            if (ur+ug > currentW) {
                                continue;
                            }
                            if (currentR < doorR[k]-ur) {
                                continue;
                            }
                            if (currentG < doorG[k]-ug) {
                                continue;
                            }
                            int twr = wr+ur;
                            int twg = wg+ug;
                            int tp = p | (1 << k);
                            dp[tp][twr] = Math.min(dp[tp][twr], twg);
                        }
                    }
                }
            }
        }

        int max = 0;
        for (int p = 0; p < (1<<n); p++) {
            if (!visited[p]) {
                continue;
            }
            int kcnt = keys[0] + keys[1] + keys[2];
            for (int k = 0; k < n ; k++) {
                if ((p & (1<<k)) == 0) {
                    continue;
                }
                kcnt -= doorR[k];
                kcnt -= doorG[k];
                kcnt += roomR[k];
                kcnt += roomG[k];
                kcnt += roomW[k];
            }
            max = Math.max(max, kcnt);
        }
        return max;
    }
}
