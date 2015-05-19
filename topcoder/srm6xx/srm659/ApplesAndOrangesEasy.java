package topcoder.srm6xx.srm659;

import java.util.Arrays;

public class ApplesAndOrangesEasy {
    public int maximumApples(int N, int K, int[] info) {
        int[] table = new int[N];
        for (int t : info) {
            table[t-1] = 1;
        }

        boolean[] initial = new boolean[N];
        for (int t : info) {
            initial[t-1] = true;
        }

        for (int i = 0 ; i < N ; i++) {
            int apl = 0;
            for (int k = i ; k < Math.min(N, i+K) ; k++) {
                apl += Math.max(0, table[k]);
            }
            for (int k = i ; k < Math.min(N, i+K) ; k++) {
                if (table[k] == 0) {
                    if (apl < K/2) {
                        apl++;
                        table[k] = 1;
                    } else {
                        table[k] = -1;
                    }
                }

            }
            if (apl > K/2) {
                for (int k = Math.min(N, i+K) - 1 ; k >= i ; k--) {
                    if (table[k] == 1 && !initial[k] && apl > K/2) {
                        table[k] = -1;
                        apl--;
                    }
                }
            }
        }

        int cnt = 0;
        for (int t : table) {
            cnt += Math.max(0, t);
        }
        return cnt;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
