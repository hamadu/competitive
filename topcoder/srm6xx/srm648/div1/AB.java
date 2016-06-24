package topcoder.srm6xx.srm648.div1;

/**
 * Created by hama_du
 */
public class AB {
    public String createString(int N, int K) {
        int[][][] dp = new int[N+1][N+1][K+1];
        int[][][][] dpf = new int[N+1][N+1][K+1][2];
        dp[0][0][0] = 1;
        for (int i = 0; i < N ; i++) {
            for (int j = 0; j <= N ; j++) {
                for (int k = 0; k <= K ; k++) {
                    if (dp[i][j][k] == 0) {
                        continue;
                    }
                    // a
                    if (j+1 <= N) {
                        dp[i+1][j+1][k] = 1;
                        dpf[i+1][j+1][k][0] = j;
                        dpf[i+1][j+1][k][1] = k;
                    }

                    // b
                    if (k+j <= K) {
                        dp[i+1][j][k+j] = 1;
                        dpf[i+1][j][k+j][0] = j;
                        dpf[i+1][j][k+j][1] = k;
                    }
                }
            }
        }

        int nn = N;
        int nj = -1;
        int nk = K;
        for (int i = 0; i <= N ; i++) {
            if (dp[N][i][K] == 1) {
                nj = i;
                break;
            }
        }
        if (nj == -1) {
            return "";
        }

        char[] ret = new char[N];
        while (nn >= 1) {
            int tj = dpf[nn][nj][nk][0];
            int tk = dpf[nn][nj][nk][1];
            if (nj == tj) {
                ret[nn-1] = 'B';
            } else {
                ret[nn-1] = 'A';
            }
            nn--;
            nj = tj;
            nk = tk;
        }
        return String.valueOf(ret);
    }
}
