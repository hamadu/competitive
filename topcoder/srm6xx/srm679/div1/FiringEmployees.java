package topcoder.srm6xx.srm679.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/01/20.
 */
public class FiringEmployees {
    public int fire(int[] manager, int[] salary, int[] productivity) {
        int n = manager.length;
        int[] profit = new int[n+1];
        for (int i = n ; i >= 1 ; i--) {
            profit[i] += productivity[i-1] - salary[i-1];
            profit[i] = Math.max(0, profit[i]);
            profit[manager[i-1]] += profit[i];
        }
        return profit[0];
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}