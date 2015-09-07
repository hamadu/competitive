package topcoder.srm5xx.srm573;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by hama_du on 15/09/05.
 */
public class SkiResorts {
    public long minCost(String[] road, int[] a) {
        int n = road.length;
        long[] la = new long[n];
        for (int i = 0 ; i < n ; i++) {
            la[i] = a[i];
        }
        boolean[][] graph = new boolean[n][n];
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < n ; j++) {
                if (road[i].charAt(j) == 'Y') {
                    graph[i][j] = true;
                } else {
                    graph[i][j] = false;
                }
            }
        }

        long[][] dp = new long[n][n];
        for (int i = 0 ; i < n ; i++) {
            Arrays.fill(dp[i], Long.MAX_VALUE);
        }
        Queue<State> q = new PriorityQueue<State>();
        for (int i = 0 ; i < n ; i++) {
            dp[0][i] = Math.abs(a[0] - a[i]);
            q.add(new State(0, i, dp[0][i]));
        }

        while (q.size() >= 1) {
            State s = q.poll();
            for (int i = 0 ; i < n ; i++) {
                if (graph[s.now][i]) {
                    for (int j = 0 ; j < n ; j++) {
                        long tcost = s.cost;
                        if (la[j] <= la[s.nh]) {
                            tcost += Math.abs(la[i] - la[j]);
                            if (dp[i][j] > tcost) {
                                dp[i][j] = tcost;
                                q.add(new State(i, j, tcost));
                            }
                        }
                    }
                }
            }
        }
        long min = Long.MAX_VALUE;
        for (int i = 0 ; i < n ; i++) {
            min = Math.min(min, dp[n-1][i]);
        }
        return (min == Long.MAX_VALUE) ? -1 : min;
    }

    static class State implements Comparable<State> {
        int now;
        int nh;
        long cost;
        State(int i, int j, long c) {
            now = i;
            nh = j;
            cost = c;
        }
        public int compareTo(State arg0) {
            return Long.signum(cost - arg0.cost);
        }
    }
}
