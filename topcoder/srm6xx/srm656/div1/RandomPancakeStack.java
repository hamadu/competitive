package topcoder.srm6xx.srm656.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/06/17.
 */
public class RandomPancakeStack {
    int n;
    int[] D;
    double[][] memo;

    public double dfs(int used, int last) {
        if (used == n || last == n) {
            return 0;
        }
        if (memo[used][last] >= 0) {
            return memo[used][last];
        }
        int total = n - used;
        double per = 1.0d / total;
        double sum = 0;
        for (int t = last ; t < n ; t++) {
            sum += per * (dfs(used+1, t+1) + D[t]);
        }
        memo[used][last] = sum;
        return sum;
    }

    public double expectedDeliciousness(int[] d) {
        n = d.length;
        for (int i = 0; i < n / 2; i++) {
            int t = d[i];
            d[i] = d[n-1-i];
            d[n-1-i] = t;
        }
        D = d;
        memo = new double[n+1][n+1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 0);
    }
}
