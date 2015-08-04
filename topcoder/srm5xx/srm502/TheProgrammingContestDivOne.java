package topcoder.srm5xx.srm502;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/04.
 */
public class TheProgrammingContestDivOne {
    public int find(int T, int[] maxPoints, int[] pointsPerMinute, int[] requiredTime) {
        int n = maxPoints.length;

        Problem[] ps = new Problem[n];
        for (int i = 0; i < n ; i++) {
            ps[i] = new Problem(maxPoints[i], pointsPerMinute[i], requiredTime[i]);
        }
        Arrays.sort(ps);

        long[][] dp = new long[2][T+1];
        Arrays.fill(dp[0], -1);
        dp[0][0] = 0;
        for (int i = 0; i < n ; i++) {
            int fr = i % 2;
            int to = 1 - fr;
            Arrays.fill(dp[to], -1);
            for (int t = 0; t <= T ; t++) {
                if (dp[fr][t] < 0) {
                    continue;
                }
                long base = dp[fr][t];
                dp[to][t] = Math.max(dp[to][t], base);

                if (t + ps[i].req <= T) {
                    int tt = t + (int)ps[i].req;
                    dp[to][tt] = Math.max(dp[to][tt], base + ps[i].max - tt * ps[i].per);
                }
            }
        }

        long max = 0;
        for (int t = 0; t <= T; t++) {
            max = Math.max(max, dp[n%2][t]);
        }
        return (int)max;
    }

    static class Problem implements Comparable<Problem> {
        long max;
        long per;
        long req;

        Problem(int m, int p, int r) {
            max = m;
            per = p;
            req = r;
        }
        @Override
        public int compareTo(Problem o) {
            return Long.signum(o.per * req - per * o.req);
        }
    }

}
