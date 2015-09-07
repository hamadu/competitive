package topcoder.srm5xx.srm562;

/**
 * Created by hama_du on 15/09/03.
 */
import java.util.Arrays;

public class PastingPaintingDivOne {
    public long[] countColors(String[] clipboard, int T) {
        long[][] dcolor = new long[201][4];
        int[][] canvas = new int[301][301];
        int w = clipboard[0].length();
        int h = clipboard.length;

        for (int t = 0 ; t < 200 ; t++) {
            for (int i = 0 ; i < h ; i++) {
                for (int j = 0 ; j < w ; j++) {
                    if (clipboard[i].charAt(j) == '.') {
                        continue;
                    }
                    char c = clipboard[i].charAt(j);
                    int cidx = (c == 'R') ? 1 : (c == 'G') ? 2 : 3;
                    int ti = t+i;
                    int tj = t+j;
                    if (canvas[ti][tj] == 0) {
                        dcolor[t][cidx]++;
                    } else {
                        dcolor[t][canvas[ti][tj]]--;
                        dcolor[t][cidx]++;
                    }
                    canvas[ti][tj] = cidx;
                }
            }
        }

        if (T < 100) {
            long[] color = new long[3];
            for (int i = 0 ; i < T ; i++) {
                for (int j = 1 ; j <= 3 ; j++) {
                    color[j-1] += dcolor[i][j];
                }
            }
            return color;
        }


        long[] color = new long[3];
        for (int i = 0 ; i < 90 ; i++) {
            for (int j = 1 ; j <= 3 ; j++) {
                color[j-1] += dcolor[i][j];
            }
        }
        long left = T - 90;
        for (int j = 1 ; j <= 3 ; j++) {
            color[j-1] += dcolor[90][j] * left;
        }
        return color;
    }

    public static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
}