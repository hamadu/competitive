package topcoder.srm5xx.srm590;

import java.util.Arrays;

/**
 * Created by hama_du on 15/10/10.
 */
public class FoxAndCity {
    private static final int INF = 114514;

    public int minimalCost(String[] linked, int[] want) {
        n = want.length;
        dist = new int[n][n];
        this.want = want;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                dist[i][j] = linked[i].charAt(j) == 'Y' ? 1 : INF;
            }
            dist[i][i] = 0;
        }
        for (int k = 0; k < n ; k++) {
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < n ; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        int[][] lr = new int[n][2];
        for (int i = 1 ; i < n ; i++) {
            lr[i][0] = 1;
            lr[i][1] = dist[0][i];
        }
        dfs(1, 0, lr);
        return best;
    }

    int n;
    int[] want;
    int[][] dist;
    int best = INF;

    void dfs(int now, int sum, int[][] lr) {
        if (now == n) {
            best = Math.min(best, sum);
            return;
        }
        int requiredSum = sum;
        for (int i = now ; i < n ; i++) {
            int d = 0;
            if (want[i] < lr[i][0]) {
                d = lr[i][0] - want[i];
            } else if (want[i] > lr[i][1]) {
                d = want[i] - lr[i][1];
            }
            requiredSum += d * d;
        }

        if (requiredSum >= best) {
            return;
        }
        for (int t = lr[now][0] ; t <= lr[now][1] ; t++) {
            int[][] nextLR = new int[n][2];
            for (int i = now+1 ; i < n ; i++) {
                nextLR[i][0] = Math.max(lr[i][0], t-dist[now][i]);
                nextLR[i][1] = Math.min(lr[i][1], t+dist[now][i]);
            }
            int D = Math.abs(t - want[now]);
            dfs(now+1, sum + D*D, nextLR);
        }
    }

    public static void main(String[] args) {
        FoxAndCity city = new FoxAndCity();
        debug(city.minimalCost(new String[]{"NYN",
                "YNY",
                "NYN"}, new int[]{0, 1, 1}));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }


}
