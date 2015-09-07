package topcoder.srm5xx.srm561;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/02.
 */
public class ICPCBalloons {
    private static final int INF = 114514810;

    public int minRepaintings(int[] balloonCount, String balloonSize, int[] maxAccepted) {
        char[] size = balloonSize.toCharArray();
        int M = 0;
        int L = 0;
        for (int i = 0; i < size.length; i++) {
            if (size[i] == 'M') {
                M++;
            } else {
                L++;
            }
        }
        int[] medium = new int[M];
        int[] large = new int[L];
        for (int i = 0; i < size.length ; i++) {
            if (size[i] == 'M') {
                medium[--M] = balloonCount[i];
            } else {
                large[--L] = balloonCount[i];
            }
        }
        Arrays.sort(medium);
        Arrays.sort(large);

        int ret = INF;
        int pn = maxAccepted.length;
        for (int p = 0; p < 1 <<pn ; p++) {
            int mn = Integer.bitCount(p);
            int ln = pn - mn;
            int[] needM = new int[mn];
            int[] needL = new int[ln];
            for (int i = 0; i < pn ; i++) {
                if ((p & (1<<i)) >= 1) {
                    needM[--mn] = maxAccepted[i];
                } else {
                    needL[--ln] = maxAccepted[i];
                }
            }
            Arrays.sort(needM);
            Arrays.sort(needL);
            int repaint = paint(needM, medium) + paint(needL, large);
            ret = Math.min(ret, repaint);
        }
        return ret >= INF ? -1 : ret;
    }

    private int paint(int[] need, int[] balloons) {
        int bn = balloons.length;
        int totalBalloon = 0;
        for (int i = 0; i < bn ; i++) {
            totalBalloon += balloons[i];
        }
        for (int ni : need) {
            totalBalloon -= ni;
        }
        if (totalBalloon < 0) {
            return INF;
        }
        int total = 0;
        int bi = balloons.length-1;
        for (int i = need.length - 1 ; i >= 0 ; i--) {
            if (bi >= 0) {
                total += Math.max(0, need[i] - balloons[bi]);
            } else {
                total += need[i];
            }
            bi--;
        }
        return total;
    }
}
