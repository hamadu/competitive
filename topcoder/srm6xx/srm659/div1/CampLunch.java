package topcoder.srm6xx.srm659.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/06/15.
 */
public class CampLunch {
    private static final long MOD = 1000000007;
    int[][] map;
    int n;
    int m;
    int MASK;

    int[][][][] memo;

    public long dfs(int day, int col, int two, int filled) {
        if (day == n) {
            return 1;
        }
        if (col == m) {
            return dfs(day+1, 0, 0, filled);
        }
        if (memo[two][day][col][filled] >= 0) {
            return memo[two][day][col][filled];
        }

        long ret = 0;
        int s = 1<<map[day][col];
        int mask = MASK^s;
        if (two == 1 || (filled&s) >= 1) {
            ret += dfs(day, col+1, 0, filled&mask);
        } else {
            // single
            ret += dfs(day, col+1, 0, filled&mask);

            // double col
            if (col+1 < m && (filled&(1<<map[day][col+1])) == 0) {
                ret += dfs(day, col+1, 1, filled&mask);
            }

            // double row
            if (day+1 < n) {
                ret += dfs(day, col+1, 0, filled|s);
            }
        }
        ret %= MOD;
        memo[two][day][col][filled] = (int)ret;
        return ret;
    }

    public int count(int N, int M, String[] a) {
        n = N;
        m = M;
        MASK = (1<<m)-1;
        map = new int[N][M];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                map[i][j] = a[i].charAt(j) - 'A';
            }
        }
        memo = new int[2][N+1][M+1][1<<M];
        for (int i = 0; i < 2 ; i++) {
            for (int j = 0; j <= N; j++) {
                for (int k = 0; k <= M; k++) {
                    Arrays.fill(memo[i][j][k], -1);
                }
            }
        }
        return (int)dfs(0, 0, 0, 0);
    }

    public static void main(String[] args) {
        CampLunch lunch = new CampLunch();
        debug(lunch.count(2, 3, new String[]{"ABC","BAC"}));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
