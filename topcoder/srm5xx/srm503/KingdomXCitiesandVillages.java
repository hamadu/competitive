package topcoder.srm5xx.srm503;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/04.
 */
public class KingdomXCitiesandVillages {
    public double determineLength(int[] cityX, int[] cityY, int[] villageX, int[] villageY) {
        int n = villageX.length;
        int m = cityX.length;

        double[][] distToCity = new double[n][m];
        double[][] distToVillage = new double[n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                distToCity[i][j] = dist(villageX[i], villageY[i], cityX[j], cityY[j]);
            }
        }
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                distToVillage[i][j] = dist(villageX[i], villageY[i], villageX[j], villageY[j]);
            }
        }

        double exp = 0;
        for (int i = 0; i < n ; i++) {
            double minCity = distToCity[i][0];
            for (int j = 0; j < m ; j++) {
                minCity = Math.min(minCity, distToCity[i][j]);
            }
            double[] vd = new double[n-1];
            int vi = 0;
            for (int k = 0; k < n ; k++) {
                if (i != k) {
                    vd[vi++] = distToVillage[i][k];
                }
            }
            Arrays.sort(vd);

            double rate = 1.0d / n;
            for (int ct = 0; ct < n ; ct++) {
                int left = ct;
                int right = n-1-ct;
                if (ct == 0) {
                    exp += minCity * rate;
                    continue;
                }

                double expVi = 0;
                double P = 1.0d;
                for (int dx = 0; dx < n-1 ; dx++) {
                    double willSelect = vd[dx];
                    double prob = P * left / (left + right);
                    expVi += Math.min(willSelect, minCity) * prob;
                    P -= prob;
                    if (right == 0) {
                        break;
                    }
                    right--;
                }
                exp += expVi * rate;
            }
        }
        return exp;
    }

    private double dist(int x1, int y1, int x2, int y2) {
        long dx = x1-x2;
        long dy = y1-y2;
        return Math.sqrt(dx*dx+dy*dy);
    }

    public static void main(String[] args) {
        KingdomXCitiesandVillages kingdom = new KingdomXCitiesandVillages();
        debug(kingdom.determineLength(
                new int[]{2,3},
                new int[]{2,3},
                new int[]{3},
                new int[]{3}
        ));
        debug(kingdom.determineLength(
                new int[]{3},
                new int[]{0},
                new int[]{3,3},
                new int[]{2,1}
        ));
        debug(kingdom.determineLength(
                new int[]{1,2,3},
                new int[]{4,4,4},
                new int[]{4,5,6},
                new int[]{4,4,4}
        ));
    }





    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
