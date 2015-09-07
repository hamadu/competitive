package topcoder.srm6xx.srm666;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hama_du on 15/08/26.
 */
public class WalkOverATree {
    public int maxNodesVisited(int[] parent, int L) {
        int n = parent.length+1;
        graph = new List[n];
        for (int i = 0; i < n ; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < parent.length ; i++) {
            int fr = parent[i];
            int to = i+1;
            graph[fr].add(to);
            graph[to].add(fr);
        }

        this.L = L;
        int max = 0;
        int[][] r = dfs(0, -1);
        for (int f = 0; f <= 1; f++) {
            for (int i = 0; i <= L; i++) {
                max = Math.max(max, r[f][i]);
            }
        }
        return max;
    }

    List<Integer>[] graph;
    int L;

    private int[][] dfs(int now, int par) {
        // flg = 1 : gone

        int ci = 0;
        int cn = graph[now].size();
        if (par != -1) {
            cn--;
        }
        int[][][] res = new int[cn][2][L+1];
        for (int to : graph[now]) {
            if (to == par) {
                continue;
            }
            res[ci] = dfs(to, now);
            ci++;
        }

        int[][][] dp = new int[cn+1][2][L+1];
        dp[0][0][0] = 1;
        dp[0][1][0] = 1;
        for (int i = 0; i < cn ; i++) {
            for (int f = 0; f <= 1; f++) {
                for (int j = 0; j <= L; j++) {
                    int base = dp[i][f][j];

                    dp[i+1][f][j] = Math.max(dp[i+1][f][j], base);
                    for (int k = 0; k <= L; k++) {
                        if (f == 0) {
                            int tj = j+1+k;
                            if (tj <= L) {
                                dp[i+1][1][tj] = Math.max(dp[i+1][1][tj], base+res[i][1][k]);
                            }
                        }
                        int tj = j+2+k;
                        if (tj <= L) {
                            dp[i+1][f][tj] = Math.max(dp[i+1][f][tj], base+res[i][0][k]);
                        }

                    }
                }
            }
        }
        return dp[cn];
    }

    public static void main(String[] args) {
        WalkOverATree tree = new WalkOverATree();
        tree.maxNodesVisited(new int[]{0, 0}, 3);
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }



}
