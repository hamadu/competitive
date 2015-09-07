package topcoder.srm5xx.srm566;

/**
 * Created by hama_du on 15/09/03.
 */
public class PenguinSledding {
    public long countDesigns(int n, int[] checkpoint1, int[] checkpoint2) {
        int[] deg = new int[n];
        boolean[][] graph = new boolean[n][n];
        for (int i = 0; i < checkpoint1.length ; i++) {
            deg[checkpoint1[i]-1]++;
            deg[checkpoint2[i]-1]++;
            graph[checkpoint1[i]-1][checkpoint2[i]-1] = true;
            graph[checkpoint2[i]-1][checkpoint1[i]-1] = true;
        }

        long ans = 0;
        for (int i = 0; i < n ; i++) {
            if (deg[i] >= 1) {
                ans += (1L<<deg[i])-deg[i]-1;
            }
        }
        ans += checkpoint1.length;
        ans += 1;
        for (int i = 0; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                for (int k = j+1 ; k < n ; k++) {
                    if (graph[i][j] && graph[j][k] && graph[k][i]) {
                        ans++;
                    }
                }
            }
        }
        return ans;
    }
}
