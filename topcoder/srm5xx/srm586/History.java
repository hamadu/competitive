package topcoder.srm5xx.srm586;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/16.
 */
public class History {
    private static final int INF = 100000000;

    public String verifyClaims(String[] dynasties, String[] battles, String[] queries) {
        int n = dynasties.length;
        int[][][] years = new int[n][][];
        for (int i = 0; i < n ; i++) {
            years[i] = parseDynasties(dynasties[i]);
        }
        int[][] battleInfo = parseBattles(battles);
        int q = queries.length;
        int[][] query = new int[q][];
        for (int i = 0; i < q ; i++) {
            query[i] = parseSingleBattle(queries[i]);
        }

        int[][][] distance = new int[n][n][2];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                distance[i][j][0] = -INF;
                distance[i][j][1] = INF;
            }
        }
        for (int i = 0; i < n ; i++) {
            distance[i][i][0] = distance[i][i][1] = 0;
        }
        for (int[] b : battleInfo) {
            int n0 = b[0];
            int m0 = b[1];
            int n1 = b[2];
            int m1 = b[3];
            int from0 = years[n0][m0][0];
            int to0 = years[n0][m0][1];
            int from1 = years[n1][m1][0];
            int to1 = years[n1][m1][1];

            int diff01A = from1 - to0;
            int diff01B = to1 - from0;

            //         f1    t1
            //         |======|
            //  |====|
            //  f0  t0
            //  A ~ B
            distance[n0][n1][0] = Math.max(distance[n0][n1][0], diff01A);
            distance[n0][n1][1] = Math.min(distance[n0][n1][1], diff01B);

            //  f1    t1
            //  |======|
            //            |====|
            //            f0  t0
            //  -B ~ -A
            distance[n1][n0][0] = Math.max(distance[n1][n0][0], -diff01B);
            distance[n1][n0][1] = Math.min(distance[n1][n0][1], -diff01A);
        }

        for (int k = 0; k < n ; k++) {
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < n ; j++) {
                    distance[i][j][0] = Math.max(distance[i][j][0], distance[i][k][0] + distance[k][j][0]);
                    distance[i][j][1] = Math.min(distance[i][j][1], distance[i][k][1] + distance[k][j][1]);
                }
            }
        }

        String ret = "";
        for (int i = 0; i < q ; i++) {
            int n0 = query[i][0];
            int m0 = query[i][1];
            int n1 = query[i][2];
            int m1 = query[i][3];
            int fr01 = years[n0][m0][0] + distance[n0][n1][0];
            int to01 = years[n0][m0][1] + distance[n0][n1][1];
            if (to01 < years[n1][m1][0] || years[n1][m1][1] < fr01) {
                ret += "N";
            } else {
                ret += "Y";
            }
        }
        return ret;
    }

    private int[] parseSingleBattle(String query) {
        int[] ret = new int[4];
        ret[0] = query.charAt(0)-'A';
        ret[1] = query.charAt(1)-'0';
        ret[2] = query.charAt(3)-'A';
        ret[3] = query.charAt(4)-'0';
        return ret;
    }

    private int[][] parseBattles(String[] battles) {
        StringBuilder line = new StringBuilder();
        for (String b : battles) {
            line.append(b);
        }
        String[] part = line.toString().split(" ");
        int n = part.length;
        int[][] ret = new int[n][4];
        for (int i = 0; i < n ; i++) {
            ret[i] = parseSingleBattle(part[i]);
        }
        return ret;
    }

    private int[][] parseDynasties(String dynasty) {
        String[] part = dynasty.split(" ");
        int[][] e = new int[part.length-1][2];
        for (int i = 0; i < part.length-1 ; i++) {
            e[i][0] = Integer.valueOf(part[i]);
            e[i][1] = Integer.valueOf(part[i+1])-1;
        }
        int diff = e[0][0];
        for (int i = 0; i < e.length ; i++) {
            e[i][0] -= diff;
            e[i][1] -= diff;
        }
        return e;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
