package topcoder.srm5xx.srm526;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by hama_du on 15/08/18.
 */
public class DucksAlignment {
    public int minimumTime(String[] grid) {
        int n = grid.length;
        int m = grid[0].length();
        char[][] map = new char[n][];
        for (int i = 0; i < n ; i++) {
            map[i] = grid[i].toCharArray();
        }
        int d = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (map[i][j] == 'o') {
                    d++;
                }
            }
        }

        int[][] ducks = new int[d][2];
        int di = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (map[i][j] == 'o') {
                    ducks[di][0] = i;
                    ducks[di++][1] = j;
                }
            }
        }

        int min = Integer.MAX_VALUE;
        {
            Arrays.sort(ducks, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    return o1[1] - o2[1];
                }
            });
            for (int i = 0; i < n; i++) {
                for (int j = 0; j + d <= m; j++) {
                    // align ducks to [i][j,j+d)
                    int cost = 0;
                    for (int k = 0; k < d ; k++) {
                        int y = ducks[k][0];
                        int x = ducks[k][1];
                        cost += Math.abs(y - i) + Math.abs(x - (j+k));
                    }
                    min = Math.min(min, cost);
                }
            }
        }

        {
            Arrays.sort(ducks, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    return o1[0] - o2[0];
                }
            });
            for (int j = 0; j < m; j++) {
                for (int i = 0; i+d <= n; i++) {
                    // align ducks to [i,i+d)[j]
                    int cost = 0;
                    for (int k = 0; k < d ; k++) {
                        int y = ducks[k][0];
                        int x = ducks[k][1];
                        cost += Math.abs(x - j) + Math.abs(y - (i+k));
                    }
                    min = Math.min(min, cost);
                }
            }
        }
        return min;
    }
}
