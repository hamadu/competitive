package topcoder.srm6xx.srm681.div1;

import java.util.Arrays;

/**
 * Created by hamada on 2016/05/17.
 */
public class FleetFunding {
    public int maxShips(int m, int[] k, int[] a, int[] b) {
        int max = 1_000_000_000;
        int min = 0;
        int n = k.length;
        int[][] maker = new int[n][4];
        for (int i = 0; i < n ; i++) {
            maker[i][0] = a[i]-1;
            maker[i][1] = b[i]-1;
            maker[i][2] = k[i];
            maker[i][3] = 0;
        }
        Arrays.sort(maker, (o1, o2) -> o1[1] - o2[1]);

        while (max - min > 1) {
            int med = (max + min) / 2;
            if (isOK(med, m, maker)) {
                min = med;
            } else {
                max = med;
            }
        }
        return min;
    }

    private boolean isOK(int build, int m, int[][] maker) {
        int n = maker.length;
        for (int i = 0; i < n ; i++) {
            maker[i][3] = 0;
        }
        for (int i = 0; i < m ; i++) {
            int need = build;
            for (int j = 0; j < n ; j++) {
                if (maker[j][0] <= i && i <= maker[j][1]) {
                    int has = maker[j][2] - maker[j][3];
                    int use = Math.min(need, has);
                    need -= use;
                    maker[j][3] += use;
                }
                if (need == 0) {
                    break;
                }
            }
            if (need >= 1) {
                return false;
            }

        }
        return true;
    }
}