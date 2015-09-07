package topcoder.srm5xx.srm559;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/02.
 */
public class HatRack {
    public long countWays(int[] knob1, int[] knob2) {
        n = knob1.length+1;
        graph = new boolean[n][n];
        for (int i = 0; i < n-1 ; i++) {
            int a = knob1[i]-1;
            int b = knob2[i]-1;
            graph[a][b] = graph[b][a] = true;
        }

        memo = new long[n][n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }
        long sum = 0;
        for (int i = 0; i < n ; i++) {
            sum += dfs(i, 0, i, -1);
        }
        return sum;
    }

    int n;
    boolean[][] graph;
    long[][][] memo;

    public long dfs(int root, int nodeID, int now, int par) {
        if (memo[root][nodeID][now] != -1) {
            return memo[root][nodeID][now];
        }

        int left = -1;
        int right = -1;
        int cnt = 0;
        for (int i = 0; i < n ; i++) {
            if (graph[now][i] && i != par) {
                cnt++;
                if (left == -1) {
                    left = i;
                } else {
                    right = i;
                }
            }
        }
        long ret = 0;
        int needChild = 0;
        for (int i = 1 ; i <= 2; i++) {
            if (nodeID * 2 + i < n) {
                needChild++;
            }
        }
        if (cnt == needChild) {
            if (cnt == 1) {
                ret += dfs(root, nodeID*2+1, left, now);
            } else if (cnt == 2) {
                ret += dfs(root, nodeID*2+1, left, now) * dfs(root, nodeID*2+2, right, now);
                ret += dfs(root, nodeID*2+1, right, now) * dfs(root, nodeID*2+2, left, now);
            } else {
                ret = 1;
            }
        }
        memo[root][nodeID][now] = ret;
        return ret;
    }
}
