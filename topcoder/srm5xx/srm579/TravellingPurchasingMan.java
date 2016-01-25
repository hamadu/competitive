package topcoder.srm5xx.srm579;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by hama_du on 15/09/13.
 */
public class TravellingPurchasingMan {
    private static final int INF = 114514810;

    public int maxStores(int n, String[] interestingStores, String[] roads) {
        int[][] graph = new int[n][n];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(graph[i], INF);
        }
        for (String r : roads) {
            String[] part = r.split(" ");
            int a = Integer.valueOf(part[0]);
            int b = Integer.valueOf(part[1]);
            int cost = Integer.valueOf(part[2]);
            graph[a][b] = graph[b][a] = cost;
        }
        for (int i = 0; i < n ; i++) {
            graph[i][i] = 0;
        }
        for (int k = 0; k < n ; k++) {
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < n ; j++) {
                    graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
                }
            }
        }

        int m = interestingStores.length;
        int[][] stores = new int[m][3];
        for (int i = 0; i < m ; i++) {
            String[] part = interestingStores[i].split(" ");
            for (int j = 0; j < 3 ; j++) {
                stores[i][j] = Integer.valueOf(part[j]);
            }
        }

        Queue<State> q = new PriorityQueue<>();
        int[][] dp = new int[m][1<<m];
        for (int i = 0; i < m ; i++) {
            Arrays.fill(dp[i], INF);
            dp[i][0] = graph[n-1][i];
            q.add(new State(i, 0, graph[n-1][i]));
        }
        while (q.size() >= 1) {
            State st = q.poll();
            int now = st.now;
            int ptn = st.ptn;
            int tim = st.time;
            for (int i = 0; i < m ; i++) {
                if (tim + graph[now][i] <= stores[i][1] && (ptn & (1<<i)) == 0) {
                    int toTime = Math.max(stores[i][0], tim+graph[now][i])+stores[i][2];
                    int tptn = ptn|(1<<i);
                    if (dp[i][tptn] > toTime) {
                        dp[i][tptn] = toTime;
                        q.add(new State(i, tptn, toTime));
                    }
                }
            }
        }
        int max = 0;
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < (1<<m) ; j++) {
                if (dp[i][j] < INF) {
                    max = Math.max(max, Integer.bitCount(j));
                }
            }
        }
        return max;
    }

    static class State implements Comparable<State> {
        int now;
        int ptn;
        int time;

        public State(int a, int b, int t) {
            now = a;
            ptn = b;
            time = t;
        }

        @Override
        public int compareTo(State o) {
            return time - o.time;
        }
    }
}
