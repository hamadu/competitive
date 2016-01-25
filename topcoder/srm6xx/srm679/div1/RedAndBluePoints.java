package topcoder.srm6xx.srm679.div1;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Arrays;

/**
 * Created by hama_du on 2016/01/20.
 */
public class RedAndBluePoints {
    public int find(int[] blueX, int[] blueY, int[] redX, int[] redY) {


        n = blueX.length;
        int m = redX.length;
        int[][] bl = new int[n][2];
        int[][] rl = new int[m][2];
        for (int i = 0; i < n ; i++) {
            bl[i][0] = blueX[i];
            bl[i][1] = blueY[i];
        }
        for (int i = 0; i < m ; i++) {
            rl[i][0] = redX[i];
            rl[i][1] = redY[i];
        }
        for (int i = 0 ; i < n ; i++) {
            int k = (int)(Math.random() * n);
            int t1 = bl[k][0];
            int t2 = bl[k][1];
            bl[k][0] = bl[i][0];
            bl[k][1] = bl[i][1];
            bl[i][0] = t1;
            bl[i][1] = t2;
        }
        for (int i = 0 ; i < m ; i++) {
            int k = (int)(Math.random() * m);
            int t1 = rl[k][0];
            int t2 = rl[k][1];
            rl[k][0] = rl[i][0];
            rl[k][1] = rl[i][1];
            rl[i][0] = t1;
            rl[i][1] = t2;
        }

        ng = new long[n][n];
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                for (int k = 0; k < n ; k++) {
                    if (i == j || j == k || i == k) {
                        continue;
                    }
                    if (containRed(bl[i], bl[j], bl[k], rl)) {
                        ng[i][j] |= 1L<<k;
                    }
                }
            }
        }
        cont = new long[n][n][n];
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                for (int k = 0; k < n ; k++) {
                    if (i == j || j == k || i == k) {
                        continue;
                    }
                    cont[i][j][k] |= 1L<<i;
                    cont[i][j][k] |= 1L<<j;
                    cont[i][j][k] |= 1L<<k;
                    for (int l = 0 ; l < n ; l++) {
                        if (containPoint(bl[i], bl[j], bl[k], bl[l])) {
                            cont[i][j][k] |= 1L<<l;
                        }
                    }
                }
            }
        }


        blue = bl;
        red = rl;
        max = 0;
        dfs(0, 0);

        return max;
    }

    long[][] ng;
    int[][] blue;
    int[][] red;
    long[][][] cont;
    int max;
    int n;
    static long nodes = 0;

    private void dfs(int head, long ptn) {
        int k = Long.bitCount(ptn);
        if (k > max) {
            max = k;
        }
        if (head == n) {
            return;
        }
        int done = Long.bitCount(ptn & (~((1L<<head)-1)));
        int foe = (n - head) - done;
        if (k + foe < max) {
            return;
        }
        nodes++;

        if ((ptn & (1L<<head)) == 0) {
            for (int f = 0 ; f < n ; f++) {
                if ((ptn & (1L<<f)) >= 1) {
                    if ((ng[f][head] & ptn) >= 1) {
                        return;
                    }
                }
            }
            long tptn = ptn|(1L<<head);
            for (int f = 0 ; f < n ; f++) {
                for (int g = f+1 ; g < n ; g++) {
                    if ((ptn & (1L<<f)) >= 1 && (ptn & (1L<<g)) >= 1) {
                        tptn |= cont[f][g][head];
                    }
                }
            }
            dfs(head+1, tptn);
        }
        dfs(head+1, ptn);
    }

    private boolean containRed(int[] b1, int[] b2, int[] b3, int[][] rl) {
        for (int i = 0 ; i < rl.length ; i++) {
            if (containPoint(b1, b2, b3, rl[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean containPoint(int[] b1, int[] b2, int[] b3, int[] red) {
        Polygon p = new Polygon(new int[]{b1[0], b2[0], b3[0]}, new int[]{b1[1], b2[1], b3[1]}, 3);
        return p.contains(red[0], red[1]);
    }

    public static void main(String[] args) {
        long f = System.currentTimeMillis();
        RedAndBluePoints solution = new RedAndBluePoints();
        debug(solution.find(
            new int[]{0,10,0,10},
            new int[]{0,0,10,10},
            new int[]{1,3},
            new int[]{1,7}
        ));
        debug(System.currentTimeMillis()-f,"ms");
        debug(nodes);
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
