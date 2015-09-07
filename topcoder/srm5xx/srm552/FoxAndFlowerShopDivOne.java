package topcoder.srm5xx.srm552;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/30.
 */
public class FoxAndFlowerShopDivOne {
    public int theMaxFlowers(String[] flowers, int maxDiff) {
        int n = flowers.length;
        char[][] table = new char[n][];
        for (int i = 0; i < n ; i++) {
            table[i] = flowers[i].toCharArray();
        }
        int ret = -1;
        for (int i = 0; i < 4 ; i++) {
            ret = Math.max(ret, solve(table, maxDiff));
            table = rotate(table);
        }
        return ret;
    }

    private int solve(char[][] table, int maxDiff) {
        int n = table.length;
        int m = table[0].length;
        int GETA = n*m+10;

        int[][] imosL = new int[n+1][m+1];
        int[][] imosP = new int[n+1][m+1];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                imosL[i+1][j+1] = imosL[i+1][j] + imosL[i][j+1] - imosL[i][j] + ((table[i][j] == 'L') ? 1 : 0);
                imosP[i+1][j+1] = imosP[i+1][j] + imosP[i][j+1] - imosP[i][j] + ((table[i][j] == 'P') ? 1 : 0);
            }
        }

        int[][] left = new int[m+1][GETA*2+1];
        int[][] right = new int[m+1][GETA*2+1];
        for (int i = 0; i <= m; i++) {
            Arrays.fill(left[i], -1);
            Arrays.fill(right[i], -1);
        }

        for (int i = 0; i < n ; i++) {
            for (int j = i+1 ; j <= n; j++) {
                for (int k = 0; k < m ; k++) {
                    for (int l = k+1; l <= m; l++) {
                        int ln = imosL[j][l] - imosL[i][l] - imosL[j][k] + imosL[i][k];
                        int pn = imosP[j][l] - imosP[i][l] - imosP[j][k] + imosP[i][k];
                        int flowerCount = ln + pn;
                        int diffL = GETA+ln-pn;
                        int diffR = GETA+pn-ln;
                        left[l][diffL] = Math.max(left[l][diffL], flowerCount);
                        right[k][diffR] = Math.max(right[k][diffR], flowerCount);
                    }
                }
            }
        }
        for (int i = 1 ; i <= m ; i++) {
            for (int d = 0; d <= GETA*2; d++) {
                left[i][d] = Math.max(left[i][d], left[i-1][d]);
            }
        }
        for (int i = m-1 ; i >= 0 ; i--) {
            for (int d = 0; d <= GETA*2; d++) {
                right[i][d] = Math.max(right[i][d], right[i+1][d]);
            }
        }


        int max = -1;
        for (int c = 0; c <= m; c++) {
            for (int lg = 0; lg <= GETA*2; lg++) {
                int minRG = Math.max(0, lg - maxDiff);
                int maxRG = Math.min(GETA*2, lg + maxDiff);
                for (int rg = minRG ; rg <= maxRG; rg++) {
                    if (left[c][lg] == -1 || right[c][rg] == -1) {
                        continue;
                    }
                    max = Math.max(max, left[c][lg] + right[c][rg]);
                }
            }
        }
        return max;
    }

    char[][] rotate(char[][] x) {
        int n = x.length;
        int m = x[0].length;
        char[][] ret = new char[m][n];
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < n ; j++) {
                ret[i][n-1-j] = x[j][i];
            }
        }
        return ret;
    }
}
