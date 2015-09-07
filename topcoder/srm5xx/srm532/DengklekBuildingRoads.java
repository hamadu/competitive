package topcoder.srm5xx.srm532;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/23.
 */
public class DengklekBuildingRoads {

    private static final int MOD = 1000000007;
    int n;
    int k;
    int[][][][][] memo;

    public int dfs(int idx, int flg, int ki, int left, int ptn) {
        if (memo[idx][flg][ki][left][ptn] != -1) {
            return memo[idx][flg][ki][left][ptn];
        }
        int ret = 0;
        if (idx == n) {
            ret = (left == 0 && ptn == 0) ? 1 : 0;
        } else {
            if (ki == k || idx <= ki) {
                if ((ptn & 1) == 1) {
                    ret = 0;
                } else {
                    ret = dfs(idx+1, 0, 0, left, (ptn >> 1) | (flg << k));
                }
            } else {
                if (left >= 1) {
                    ret = dfs(idx, flg ^ 1, ki, left-1, ptn ^ (1 << (k-ki)));
                }
                ret += dfs(idx, flg, ki+1, left, ptn);
                if (ret >= MOD) {
                    ret -= MOD;
                }
            }
        }
        memo[idx][flg][ki][left][ptn] = ret;
        return ret;
    }

    public int numWays(int N, int M, int K) {
        memo = new int[N+1][2][K+1][M+1][1<<(K+1)];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j < 2 ; j++) {
                for (int l = 0; l <= K ; l++) {
                    for (int m = 0; m <= M; m++) {
                        Arrays.fill(memo[i][j][l][m], -1);
                    }
                }
            }
        }
        n = N;
        k = K;
        return dfs(0, 0, 0, M, 0);
    }
}
