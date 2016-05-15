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

        long[][][] dp = new long[2][2][n+50];
        dp[0][0][0] = 1;
        dp[0][1][0] = k;
        for (int pos = 0; pos < n-1 ; pos++) {
            for (int parity = 0; parity <= 1 ; parity++) {
                for (int flg = 0; flg <= 1 ; flg++) {
                    long base = dp[parity][flg][pos];
                    if (base == 0) {
                        continue;
                    }
                    if (flg == 0) {
                        // flg = 0 : must use NG ptn
                        for (int l = 1 ; l <= 20 ; l++) {
                            int tpar = (parity + l) % 2;
                            dp[tpar][1][pos+l] += base * prec[l] % MOD;
                            dp[tpar][1][pos+l] %= MOD;
                        }
                    } else {
                        // flg = 1 : must use free ptn
                        dp[parity][0][pos+1] += base;
                        dp[parity][0][pos+1] %= MOD;

                        dp[parity][1][pos+1] += base * k % MOD;
                        dp[parity][1][pos+1] %= MOD;
                    }
                }
            }
        }

        long ans = (dp[0][1][n-1]) - (dp[1][1][n-1]) + MOD * 10;
        return (int)(ans % MOD);
    }

    public static void main(String[] args) {
        DivFree f= new DivFree();
        debug(f.dfcount(2, 2)); // 3
        debug(f.dfcount(42, 23)); // 301026516
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
