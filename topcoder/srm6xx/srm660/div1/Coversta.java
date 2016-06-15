package topcoder.srm6xx.srm660.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/06/15.
 */
public class Coversta {
    public int place(String[] a, int[] x, int[] y) {
        int n = a.length;
        int m = a[0].length();
        int[][] map = new int[n][m];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                map[i][j] = a[i].charAt(j)-'0';
            }
        }

        int k = x.length;
        int[][] thesum = new int[n*m][3];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m ; j++) {
                int id = i*m+j;
                thesum[id][0] = i;
                thesum[id][1] = j;
                for (int l = 0; l < k ; l++) {
                    int ti = i + x[l];
                    int tj = j + y[l];
                    if (ti < 0 || ti >= n || tj < 0 || tj >= m) {
                        continue;
                    }
                    thesum[id][2] += map[ti][tj];
                }
            }
        }
        Arrays.sort(thesum, (a1, a2) -> a2[2] - a1[2]);

        int best = 0;
        int[][] filled = new int[n][m];
        int FID = 0;
        for (int first = 0 ; first < n*m ; first++) {
            int ex = 0;
            for (int second = first+1 ; second < n*m ; second++) {
                FID++;

                int total = 0;
                int[][] pos = new int[][]{ thesum[first].clone(), thesum[second].clone() };
                for (int[] p : pos) {
                    for (int l = 0; l < k ; l++) {
                        int ti = p[0] + x[l];
                        int tj = p[1] + y[l];
                        if (ti < 0 || ti >= n || tj < 0 || tj >= m) {
                            continue;
                        }
                        if (filled[ti][tj] == FID) {
                            continue;
                        }
                        total += map[ti][tj];
                        filled[ti][tj] = FID;
                    }
                }
                best = Math.max(best, total);

                ex++;
                if (ex >= 210) {
                    break;
                }
            }
        }
        return best;
    }
}
