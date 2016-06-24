package topcoder.srm6xx.srm647.div1;

/**
 * Created by hama_du
 */
import java.io.*;
import java.util.*;

public class CtuRobots {
    public double maxDist(int B, int[] cost, int[] cap) {
        int n = cost.length;
        int[][] robot = new int[n][2];
        for (int i = 0; i < n ; i++) {
            robot[i][0] = cost[i];
            robot[i][1] = cap[i];
        }
        Arrays.sort(robot, (r0, r1) -> r0[1] - r1[1]);

        double ret = 0;
        double[][] dp = new double[n+1][B+1];
        for (int i = 0; i <= n ; i++) {
            Arrays.fill(dp[i], -1);
        }
        dp[0][0] = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j <= B; j++) {
                if (dp[i][j] < 0) {
                    continue;
                }
                double haveGone = dp[i][j];
                dp[i+1][j] = Math.max(dp[i+1][j], haveGone);
                if (j+robot[i][0] > B) {
                    continue;
                }
                int tj = j+robot[i][0];
                if (haveGone <= robot[i][1]) {
                    double to = (haveGone + robot[i][1]) / 3.0d;
                    dp[i+1][tj] = Math.max(dp[i+1][tj], to);
                    ret = Math.max(ret, (haveGone + robot[i][1]) / 2.0d);
                }
            }
        }
        return ret;
    }
}
