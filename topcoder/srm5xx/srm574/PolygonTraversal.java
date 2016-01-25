package topcoder.srm5xx.srm574;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/05.
 */
public class PolygonTraversal {
    public long count(int n, int[] points) {
        int p = points.length;
        N = n;
        point = points;
        for (int i = 0; i < p ; i++) {
            point[i]--;
        }
        memo = new long[N][1<<N];
        for (int i = 0; i < N ; i++) {
            Arrays.fill(memo[i], -1);
        }
        int done = 0;
        for (int i = 0; i < p ; i++) {
            done |= 1<<point[i];
        }
        return dfs(done, points[p-1]);
    }

    int N;
    int[] point;
    long[][] memo;

    long dfs(int ptn, int now) {
        if (memo[now][ptn] != -1) {
            return memo[now][ptn];
        }
        long res = 0;
        if (ptn == (1<<N)-1) {
            res += ((now+1)%N == point[0]) || ((point[0]+1)%N == now) ? 0 : 1;
        } else {
            int rng = ptn^(1<<now);
            boolean[] canVisit = new boolean[N];
            Arrays.fill(canVisit, true);
            for (int t = (now+1)%N ; ; t=(t+1)%N) {
                if ((rng & (1<<t)) == 0) {
                    canVisit[t] = false;
                } else {
                    break;
                }
            }
            for (int t = (now-1+N)%N ; ; t=(t-1+N)%N) {
                if ((rng & (1<<t)) == 0) {
                    canVisit[t] = false;
                } else {
                    break;
                }
            }
            for (int i = 0; i < N ; i++) {
                if ((ptn & (1<<i)) == 0 && canVisit[i]) {
                    res += dfs(ptn|(1<<i), i);
                }
            }
        }
        memo[now][ptn] = res;
        return res;
    }
}
