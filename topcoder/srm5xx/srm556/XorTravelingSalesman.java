package topcoder.srm5xx.srm556;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 15/09/01.
 */
public class XorTravelingSalesman {
    public int maxProfit(int[] cityValues, String[] roads) {
        int n = cityValues.length;
        boolean[][] graph = new boolean[n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                graph[i][j] = roads[i].charAt(j) == 'Y';
            }
        }
        boolean[][] visited = new boolean[n][2048];
        Queue<Integer> q = new ArrayBlockingQueue<>(50*248*50);
        visited[0][cityValues[0]] = true;
        q.add(0);
        q.add(cityValues[0]);
        while (q.size() >= 1) {
            int now = q.poll();
            int val = q.poll();
            for (int i = 0; i < n ; i++) {
                if (graph[now][i]) {
                    int tv = val ^ cityValues[i];
                    if (!visited[i][tv]) {
                        visited[i][tv] = true;
                        q.add(i);
                        q.add(tv);
                    }
                }
            }
        }
        for (int i = 2047 ; i >= 0 ; i--) {
            for (int j = 0; j < n ; j++) {
                if (visited[j][i]) {
                    return i;
                }
            }
        }
        return -1;
    }
}
