package topcoder.srm6xx.srm669;

import java.util.Arrays;

/**
 * Created by hama_du on 15/10/03.
 */
public class SubdividedSlimes {
    public int needCut(int S, int M) {
        for (int c = 1 ; c <= S ; c++) {
            int big = S / c + 1;
            int small = big - 1;
            int bigCount = S % c;
            int smallCount = c - bigCount;

            int total = S * S;
            for (int i = 0 ; i < bigCount ; i++) {
                total -= big * big;
            }
            for (int i = 0 ; i < smallCount ; i++) {
                total -= small * small;
            }
            total /= 2;
            if (total >= M) {
                return c - 1;
            }
        }
        return -1;
    }
}
