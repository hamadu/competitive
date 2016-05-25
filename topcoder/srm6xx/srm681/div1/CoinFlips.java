package topcoder.srm6xx.srm681.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/05/17.
 */
public class CoinFlips {
    double[][] table;
    double[][] tableSum;
    double[][][] memo;

    public double dfs(int a, int b, int c) {
        if (memo[a][b][c] >= 0) {
            return memo[a][b][c];
        }
        double res = 0;
        if (b == 0) {
            res = 1;
        } else {
            int nc = a + 1 + b + 1 + c;
            if (a >= 1) {
                // debug("rateA",nc,a,b,c,tableSum[nc][a - 1]);
                res += tableSum[nc][a - 1] * dfs(a - 1, b, c);
            }
            if (b >= 1) {
                // debug("rateB",nc,a,b,c,(tableSum[nc][a + b] - tableSum[nc][a]));
                res += (tableSum[nc][a + b] - tableSum[nc][a]) * dfs(a, b - 1, c);
            }
            if (c >= 1) {
                // debug("rateC",nc,a,b,c,(1.0d - tableSum[nc][a + b + 1]));
                res += (1.0d - tableSum[nc][a + b + 1]) * dfs(a, b, c - 1);
            }
        }
        memo[a][b][c] = res;
        return res;
    }

    public double getExpectation(int[] vals, int prob) {
        int n = vals.length;
        double p = prob / 1e9;
        memo = new double[n][n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }
        table = new double[n+1][n];
        tableSum = new double[n+1][n];

        for (int u = 1 ; u <= n ; u++) {
            double rate = 1.0d;
            for (int i = 0; i < u ; i++) {
                table[u][i] = rate * p;
                rate *= 1.0d - p;
            }
            table[u][0] += rate;

            tableSum[u][0] = table[u][0];
            for (int i = 1 ; i < n ; i++) {
                tableSum[u][i] = tableSum[u][i-1] + table[u][i];
            }
        }

        double ans = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = i+2 ; j < n ; j++) {
                ans += vals[i] * vals[j] * dfs(i, j - i - 1, n - j - 1);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        CoinFlips flips = new CoinFlips();
        System.err.println(flips.getExpectation(
                new int[]{3,1,4,1,5,9,2,6,5,3,5,8,9,7,9,3,2,3,8,5,6,2,6,4,3,3,8,3,2,7,9,5},
                123456789
        ));
    }

    private static void debug(Object... obj) {
        System.err.println(Arrays.deepToString(obj));
    }
}