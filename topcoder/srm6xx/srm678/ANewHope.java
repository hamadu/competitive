package topcoder.srm6xx.srm678;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/01/26.
 */
public class ANewHope {
    public int count(int[] firstWeek, int[] lastWeek, int D) {
        int n = firstWeek.length;
        int canMove = n - D;
        int max = 0;
        for (int i = 0; i < n  ; i++) {
            for (int j = 0; j < n ; j++) {
                if (firstWeek[i] == lastWeek[j]) {
                    int needMove = (i <= j) ? 0 : i - j;
                    max = Math.max(max, (needMove + canMove - 1) / canMove);
                }
            }
        }
        return max + 1;
    }
}
