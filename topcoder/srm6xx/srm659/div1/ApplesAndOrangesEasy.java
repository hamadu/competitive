package topcoder.srm6xx.srm659.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/06/15.
 */
public class ApplesAndOrangesEasy {
    public int maximumApples(int N, int K, int[] info) {
        boolean[] isapple = new boolean[N];
        for (int i = 0; i < info.length ; i++) {
            isapple[info[i]-1] = true;
        }

        int[] bag = new int[N];
        int have = 0;
        for (int i = 0 ; i < N ; i++) {
            if (i - K >= 0) {
                have -= bag[i-K];
            }
            if (have+1 <= K/2 || isapple[i]) {
                bag[i] = 1;
                have++;
            }
            if (have > K/2 && isapple[i]) {
                for (int j = i-1 ; j >= 0 ; j--) {
                    if (bag[j] == 1 && !isapple[j]) {
                        bag[j] = 0;
                        break;
                    }
                }
                have--;
            }
        }

        return Arrays.stream(bag).sum();
    }
}
