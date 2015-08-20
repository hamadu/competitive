package topcoder.srm5xx.srm530;

/**
 * Created by hama_du on 15/08/19.
 */
public class GogoXMarisaKirisima {
    public int solve(String[] choices) {
        int n = choices.length;
        boolean[][] graph = new boolean[n][n];
        boolean[][] cango = new boolean[n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                graph[i][j] = cango[i][j] = choices[i].charAt(j) == 'Y';
            }
            cango[i][i] = true;
        }

        for (int k = 0; k < n ; k++) {
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < n ; j++) {
                    cango[i][j] |= cango[i][k] && cango[k][j];
                }
            }
        }
        if (!cango[0][n-1]) {
            return 0;
        }

        int v = 0;
        boolean[] onPath = new boolean[n];
        for (int i = 0; i < n ; i++) {
            if (cango[0][i] && cango[i][n-1]) {
                v++;
                onPath[i] = true;
            }
        }
        int e = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (i == j) {
                    continue;
                }
                if (onPath[i] && onPath[j] && graph[i][j]) {
                    e++;
                }
            }
        }
        return Math.max(0, e - v + 2);
    }
}
