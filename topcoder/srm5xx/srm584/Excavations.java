package topcoder.srm5xx.srm584;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by hama_du on 15/09/14.
 */
public class Excavations {
    public long count(int[] kind, int[] depth, int[] found, int K) {
        n = kind.length;
        dn = new int[n][2];
        for (int i = 0; i < n ; i++) {
            dn[i][0] = kind[i];
            dn[i][1] = depth[i];
        }
        need = new boolean[52];
        for (int i : found) {
            need[i] = true;
        }
        Arrays.sort(dn, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (need[o1[0]] != need[o2[0]]) {
                    return need[o1[0]] ? -1 : 1;
                }
                return o1[0]-o2[0];
            }
        });
        int vec = 0;
        for (int i = 0; i < n ; i++) {
            if (need[dn[i][0]]) {
                vec++;
            }
        }

        long[][][][] dp = new long[2][51][52][52];
        dp[0][K][51][51] = 1;
        for (int i = 0; i < vec ; i++) {
            int fr = i % 2;
            int to = 1 - fr;
            for (int k = 0; k <= K; k++) {
                for (int mi = 0; mi <= 51; mi++) {
                    Arrays.fill(dp[to][k][mi], 0);
                }
            }
            for (int k = 0; k <= K; k++) {
                for (int mi = 0; mi <= 51; mi++) {
                    for (int ma = 0; ma <= 51 ; ma++) {
                        long base = dp[fr][k][mi][ma];
                        if (base == 0) {
                            continue;
                        }
                        boolean canZero = true;
                        boolean canOne = (k >= 1);
                        boolean change = (i+1 == vec || dn[i+1][0] != dn[i][0]);
                        if (change) {
                            if (mi == 51) {
                                canZero = false;
                            }
                        }
                        if (canZero) {
                            int nextMin = mi;
                            int nextMax = ma;
                            if (change) {
                                nextMin = 51;
                                nextMax = (ma == 51 || (dn[ma][1] < dn[mi][1])) ? mi : ma;
                            }
                            dp[to][k][nextMin][nextMax] += base;
                        }
                        if (canOne) {
                            int nextMin = (mi == 51 || dn[i][1] < dn[mi][1]) ? i : mi;
                            int nextMax = ma;
                            if (change) {
                                nextMax = (ma == 51 || (dn[ma][1] < dn[nextMin][1])) ? nextMin : ma;
                                nextMin = 51;
                            }

                            dp[to][k-1][nextMin][nextMax] += base;
                        }
                    }
                }
            }
        }

        long[][] ncr = new long[52][52];
        for (int i = 0; i <= 51; i++) {
            ncr[i][0] = ncr[i][i] = 1;
            for (int j = 1 ; j < i; j++) {
                ncr[i][j] = (ncr[i-1][j-1] + ncr[i-1][j]);
            }
        }

        long ans = 0;
        int fr = vec % 2;
        for (int k = 0; k <= K; k++) {
            for (int mi = 51 ; mi <= 51; mi++) {
                for (int ma = 0; ma < vec ; ma++) {
                    if (dp[fr][k][mi][ma] == 0) {
                        continue;
                    }
                    long base = dp[fr][k][mi][ma];
                    int available = 0;
                    for (int i = vec ; i < n ; i++) {
                        if (dn[i][1] > dn[ma][1]) {
                            available++;
                        }
                    }
                    ans += base * ncr[available][k];
                }
            }
        }
        return ans;
    }

    boolean[] need;
    int[][] dn;
    int n;

    public static void main(String[] args) {
        Excavations excavations = new Excavations();
        debug(excavations.count(new int[]{1, 1, 2, 2}, new int[]{10, 15, 10, 20}, new int[]{1,2}, 2));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
