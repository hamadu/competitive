package topcoder.srm5xx.srm531;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/20.
 */
public class MonsterFarm {
    private static final long MOD = 1000000007;

    public int numMonsters(String[] transforms) {
        int n = transforms.length;

        long[][] mul = new long[n][n];
        boolean[][] graph = new boolean[n][n];
        int[] outdeg = new int[n];
        for (int i = 0; i < n ; i++) {
            String[] c = transforms[i].split(" ");
            outdeg[i] = c.length;
            for (String t : c) {
                int to = Integer.valueOf(t)-1;
                graph[i][to] = true;
                mul[i][to]++;
            }
        }

        for (int k = 0; k < n ; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n ; j++) {
                    graph[i][j] |= graph[i][k] && graph[k][j];
                }
            }
        }

        for (int i = 0; i < n ; i++) {
            if (graph[0][i] && graph[i][i] && outdeg[i] >= 2) {
                return -1;
            }
        }

        long[][] cnt = new long[2][n];
        cnt[0][0] = 1;
        for (int c = 0; c < 10000 ; c++) {
            int fr = c % 2;
            int to = 1 - fr;
            Arrays.fill(cnt[to], 0);
            for (int i = 0; i < n ; i++) {
                if (cnt[fr][i] >= 1) {
                    for (int j = 0; j < n ; j++) {
                        cnt[to][j] += mul[i][j] * cnt[fr][i];
                    }
                }
            }
            for (int j = 0; j < n ; j++) {
                cnt[to][j] %= MOD;
            }
        }

        long sum = 0;
        for (int i = 0; i < n ; i++) {
            sum += cnt[0][i];
        }
        return (int)(sum % MOD);
    }
}
