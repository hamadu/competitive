package topcoder.srm6xx.srm659;

import java.util.Arrays;

public class CampLunch {

    static final int MOD = 1000000007;

    static int N;

    static int M;

    static int[][] table;

    static int[][][][] memo;

    static int[][] converter;

    public int dfs(int state, int day, int std, int prev) {
        if (day == N) {
            return 1;
        }
        if (std == M) {
            // convert states
            return dfs(converter[day][state], day+1, 0, 0);
        }
        if (memo[prev][day][std][state] != -1) {
            return memo[prev][day][std][state];
        }

        int ct = 0;
        if (prev == 1 || (state & 1) == 1) {
            // he has already lunch
            ct += dfs(state>>1, day, std+1, 0);
            ct -= (ct >= MOD) ? MOD : 0;
        } else {
            // single lunch
            ct += dfs(state>>1, day, std+1, 0);
            ct -= (ct >= MOD) ? MOD : 0;

            // two-day lunch
            if (day < N-1) {
                ct += dfs((1<<(M-1))|(state>>1), day, std+1, 0);
                ct -= (ct >= MOD) ? MOD : 0;
            }

            // pair lunch
            if (std < M-1 && (state & 2) == 0) {
                ct += dfs(state>>1, day, std+1, 1);
                ct -= (ct >= MOD) ? MOD : 0;
            }
        }

        memo[prev][day][std][state] = ct;
        return ct;
    }

    public int count(int n, int m, String[] a) {
        N = n;
        M = m;
        table = new int[N][M];
        for (int i = 0 ; i < N ; i++) {
            for (int j = 0 ; j < M ; j++) {
                table[i][j] = a[i].charAt(j) - 'A';
            }
        }
        memo = new int[2][16][16][1<<16];
        for (int k = 0 ; k <= 1 ; k++) {
            for (int i = 0 ; i < N ; i++) {
                for (int j = 0 ; j < M ; j++) {
                    Arrays.fill(memo[k][i][j], -1);
                }
            }
        }

        converter = new int[16][1<<16];
        for (int i = 0 ; i < N-1 ; i++) {
            int[] c = new int[M];
            for (int k1 = 0 ; k1 < M ; k1++) {
                for (int k2 = 0 ; k2 < M ; k2++) {
                    if (table[i][k1] == table[i+1][k2]) {
                        c[k1] = k2;
                        break;
                    }
                }
            }
            for (int j = 0 ; j < (1<<16) ; j++) {
                int to = 0;
                for (int k = 0 ; k < M ; k++) {
                    if ((j & (1<<k)) >= 1) {
                        to |= 1<<c[k];
                    }
                }
                converter[i][j] = to;
            }
        }

        return dfs(0, 0, 0, 0);
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
