package topcoder.srm5xx.srm507;

/**
 * Created by hama_du on 15/08/05.
 */
public class CubeBuilding {
    private static final int MOD = 1000000007;

    public int getCount(int R, int G, int B, int n) {
        int[][][] sub = new int[n+1][26][76];
        sub[0][0][0] = 1;
        for (int i = 0; i < n ; i++) {
            for (int h = 0; h <= 25 ; h++) {
                for (int used = 0; used <= 75; used++) {
                    if (sub[i][h][used] == 0) {
                        continue;
                    }
                    int base = sub[i][h][used];
                    for (int pl = 0 ; pl <= 25 ; pl++) {
                        int th = Math.max(h, pl);
                        int tused = used + pl;
                        if (tused <= 75) {
                            sub[i+1][th][tused] += base;
                            sub[i+1][th][tused] -= (sub[i+1][th][tused] >= MOD) ? MOD : 0;
                        }
                    }
                }
            }
        }

        int[][][] dp = new int[n+1][26][76];
        dp[0][0][0] = 1;
        for (int i = 0; i < n ; i++) {
            for (int f = 0; f <= 25 ; f++) {
                for (int used = 0; used <= 75 ; used++) {
                    if (dp[i][f][used] == 0) {
                        continue;
                    }
                    long base = dp[i][f][used];
                    for (int pf = 0; f+pf <= 25 ; pf++) {
                        for (int pu = 0; used+pu <= 75 ; pu++) {
                            if (sub[n][pf][pu] == 0) {
                                continue;
                            }
                            long pl = (base * sub[n][pf][pu]) % MOD;
                            dp[i+1][f+pf][used+pu] += pl;
                            dp[i+1][f+pf][used+pu] -= (dp[i+1][f+pf][used+pu] >= MOD) ? MOD : 0;
                        }
                    }
                }
            }
        }

        long[][] ncr = new long[101][101];
        for (int i = 0; i < 101 ; i++) {
            ncr[i][0] = ncr[i][i] = 1;
            for (int j = 1; j < i ; j++) {
                ncr[i][j] = (ncr[i-1][j] + ncr[i-1][j-1]) % MOD;
            }
        }


        long total = 0;
        int bc = R + G + B;
        for (int f = 0; f <= 25 ; f++) {
            if (dp[n][f][bc] == 0) {
                continue;
            }
            int hidden = bc - f;
            if (f <= R) {
                int hiddenR = R - f;
                int hiddenG = G;
                long coloring = (ncr[hidden][hiddenR] * ncr[hidden-hiddenR][hiddenG]) % MOD;
                long add = (dp[n][f][bc] * coloring) % MOD;
                total += add;
            }
            if (f <= G) {
                int hiddenG = G - f;
                int hiddenB = B;
                long coloring = (ncr[hidden][hiddenG] * ncr[hidden-hiddenG][hiddenB]) % MOD;
                long add = (dp[n][f][bc] * coloring) % MOD;
                total += add;
            }
            if (f <= B) {
                int hiddenB = B - f;
                int hiddenR = R;
                long coloring = (ncr[hidden][hiddenB] * ncr[hidden-hiddenB][hiddenR]) % MOD;
                long add = (dp[n][f][bc] * coloring) % MOD;
                total += add;
            }
        }
        return (int)(total % MOD);
    }
}
