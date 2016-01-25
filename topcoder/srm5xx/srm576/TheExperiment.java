package topcoder.srm5xx.srm576;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/13.
 */
public class TheExperiment {
    private static final long MOD = 1000000009;

    public int countPlacements(String[] intensity, int M, int L, int A, int B) {
        int[] a = water(intensity);
        int n = a.length;
        int[] imos = new int[n+1];
        for (int i = 0; i < n ; i++) {
            imos[i+1] = imos[i] + a[i];
        }

        long[][][] dp = new long[n+2][M+1][2];
        for (int i = 0; i < n; i++) {
            dp[i][0][0] = 1;
        }
        for (int i = 0; i <= n ; i++) {
            for (int j = 0; j <= M ; j++) {
                for (int f = 0; f <= 1; f++) {
                    if (dp[i][j][f] == 0) {
                        continue;
                    }
                    long base = dp[i][j][f];
                    if (f == 1) {
                        for (int ti = i+1 ; ti <= n+1 ; ti++) {
                            // pass
                            dp[ti][j][0] += base;
                            dp[ti][j][0] %= MOD;
                        }
                    }

                    if (j+1 <= M) {
                        for (int k = 1; k <= L; k++) {
                            if (i+k > n) {
                                break;
                            }
                            int drop = imos[i+k]-imos[i];
                            if (A <= drop && drop <= B) {
                                int ti = i+k;
                                int tj = j+1;
                                int tf = (f == 1 || k == L) ? 1 : 0;
                                dp[ti][tj][tf] += base;
                                dp[ti][tj][tf] %= MOD;
                            }
                        }
                    }
                }
            }
        }
        return (int)dp[n+1][M][0];
    }

    public int[] water(String[] intensity) {
        StringBuilder line = new StringBuilder();
        for (String i : intensity) {
            line.append(i);
        }
        char[] c = line.toString().toCharArray();
        int[] ret = new int[c.length];
        for (int i = 0; i < c.length ; i++) {
            ret[i] = c[i]-'0';
        }
        return ret;
    }

    public static void main(String[] args) {
        TheExperiment experiment = new TheExperiment();
        debug(experiment.countPlacements(new String[]{"114"}, 1, 1, 3, 4));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
