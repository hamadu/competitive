package topcoder.srm5xx.srm514;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/08.
 */
public class MagicalGirlLevelTwoDivOne {
    private static final long MOD = 1000000007;

    int N;
    int M;
    int n;
    int m;

    long[] pow4;
    long[] pow5;

    private long dfs(int i, int j, int par, int ptn) {
        if (i == n) {
            return (ptn & ((1<<m)-1)) == (1<<m)-1 ? 1 : 0;
        }
        if (j == m) {
            if (par == 0) {
                return 0;
            }
            return dfs(i+1, 0, 0, ptn);
        }
        if (memo[i][j][par][ptn] != -1) {
            return memo[i][j][par][ptn];
        }

        // even
        long ret = 0;
        if (evenPtn[i][j] >= 1) {
            ret += (evenPtn[i][j] * dfs(i, j+1, par, ptn)) % MOD;
        }
        if (oddPtn[i][j] >= 1) {
            ret += (oddPtn[i][j] * dfs(i, j+1, par ^ 1, ptn ^ (1<<j))) % MOD;
        }
        ret %= MOD;
        memo[i][j][par][ptn] = ret;
        return ret;
    }

    long[][][][] memo;

    long[][] oddPtn;
    long[][] evenPtn;

    public int theCount(String[] palette, int n, int m) {
        N = palette.length;
        M = palette[0].length();
        this.n = n;
        this.m = m;
        pow4 = new long[3000];
        pow5 = new long[3000];
        pow4[0] = pow5[0] = 1;
        for (int i = 1; i < 3000 ; i++) {
            pow4[i] = (pow4[i-1] * 4) % MOD;
            pow5[i] = (pow5[i-1] * 5) % MOD;
        }

        oddPtn = new long[n][m];
        evenPtn = new long[n][m];
        memo = new long[n][m][2][1<<10];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                Arrays.fill(memo[i][j][0], -1);
                Arrays.fill(memo[i][j][1], -1);
            }
        }

        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                boolean isOdd = true;
                boolean isEven = true;
                int un = 0;
                for (int k = i; k < N; k += n) {
                    for (int l = j; l < M ; l += m) {
                        char c = palette[k].charAt(l);
                        if (c == '.') {
                            un++;
                        } else if ((c - '0') % 2 == 0) {
                            isOdd = false;
                        } else {
                            isEven = false;
                        }
                    }
                }
                if (isOdd) {
                    oddPtn[i][j] = pow5[un];
                }
                if (isEven) {
                    evenPtn[i][j] = pow4[un];
                }
            }
        }
        return (int)dfs(0, 0, 0, 0);
    }

    public static void main(String[] args) {
        MagicalGirlLevelTwoDivOne mag = new MagicalGirlLevelTwoDivOne();
        debug(mag.theCount(new String[]{"..",".."}, 2, 2));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
