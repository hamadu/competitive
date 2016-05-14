package topcoder.srm6xx.srm684.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/05/14.
 */
public class DivFree {
    long MOD = 1000000007;

    public int dfcount(int n, int k) {
        long[][] predp = new long[21][k+1];
        Arrays.fill(predp[0], 1);
        long cnt = 0;
        for (int i = 0 ; i < 20 ; i++) {
            for (int j = 1 ; j <= k ; j++) {
                for (int l = j*2 ; l <= k ; l += j) {
                    predp[i+1][l] += predp[i][j];
                    if (predp[i+1][l] >= MOD) {
                        predp[i+1][l] -= MOD;
                    }
                    cnt++;
                }
            }
        }

        long[] prec = new long[21];
        for (int i = 1 ; i <= 20; i++) {
            for (int j = 1 ; j <= k; j++) {
                prec[i] += predp[i][j];
            }
            prec[i] %= MOD;
        }

        long[][][] dp = new long[2][2][n];
        dp[0][0][0] = 1;
        for (int pos = 0; pos < n-1 ; pos++) {
            for (int parity = 0; parity <= 1 ; parity++) {
                for (int flg = 0; flg <= 1 ; flg++) {
                    if (dp[parity][flg][pos] >= 1) {
                        long base = dp[parity][flg][pos];
                        // ?
                        // ?
                    }
                }
            }
        }




        return -1;
    }

    public static void main(String[] args) {
        DivFree f= new DivFree();
        debug(f.dfcount(50000, 50000));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
