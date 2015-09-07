package topcoder.srm5xx.srm553;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hama_du on 15/08/31.
 */
public class TwoConvexShapes {

    private static final long MOD = 1000000007;

    public int countWays(String[] grid) {
        n = grid.length;
        m = grid[0].length();
        map = new char[n][];
        for (int i = 0; i < n ; i++) {
            map[i] = grid[i].toCharArray();
        }

        memo = new long[n+1][m+2][m+2][3];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m+1; j++) {
                for (int k = 0; k <= m+1 ; k++) {
                    Arrays.fill(memo[i][j][k], -1);
                }
            }
        }

        long sum = dfs(0, 1, 0, 0);
        sum %= MOD;
        return (int)sum;
    }

    char[][] map;
    long[][][][] memo;
    int n;
    int m;

    // paint map[row][c1-c2] to black
    // flg = 1 : allowed to increase
    // flg = 2 : allowed to decrease
    public long dfs(int row, int c1, int c2, int flg) {
        if (row == n) {
            return 1;
        }
        if (memo[row][c1][c2][flg] != -1) {
            return memo[row][c1][c2][flg];
        }

        int range = c2 - c1 + 1;
        long ret = 0;
        List<int[]> pair = new ArrayList<>();
        pair.add(new int[]{1, 0});
        for (int t1 = 1 ; t1 <= m; t1++) {
            for (int t2 = t1 ; t2 <= m; t2++) {
                if (t1 == 1 || t2 == m) {
                    pair.add(new int[]{t1, t2});
                }
            }
        }
        for (int[] p : pair) {
            int t1 = p[0];
            int t2 = p[1];
            int toRange = t2 - t1 + 1;
            if (range < toRange && flg == 2) {
                continue;
            } else if (range > toRange && flg == 1) {
                continue;
            }
            int toFlg = flg == 0 ? ((range < toRange) ? 1 : (range > toRange) ? 2 : 0) : flg;
            if (row == 0) {
                toFlg = 0;
            }
            if (c1 == 1 && c2 == 0) {
                // pass
            } else if (t1 == 1 && t2 == 0) {
                // pass
            } else {
                if (c1 == 1 && c2 != m && t1 != 1) {
                    continue;
                }
                if (c1 != 1 && c2 == m && t2 != m) {
                    continue;
                }
            }
            if (isValidRange(row, t1, t2)) {
                ret += dfs(row+1, t1, t2, toFlg);
            }
        }
        ret %= MOD;
        memo[row][c1][c2][flg] = ret;
        return ret;
    }

    private boolean isValidRange(int row, int t1, int t2) {
        for (int i = 0; i < m ; i++) {
            if (t1-1 <= i && i <= t2-1) {
                if (map[row][i] == 'W') {
                    return false;
                }
            } else {
                if (map[row][i] == 'B') {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        TwoConvexShapes shapes = new TwoConvexShapes();
        debug(shapes.countWays(new String[]{"B.", ".."}));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
