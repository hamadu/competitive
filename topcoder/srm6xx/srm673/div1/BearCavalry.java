package topcoder.srm6xx.srm673.div1;

import java.util.Arrays;

/**
 * Created by hamada on 2016/05/20.
 */
public class BearCavalry {
    long MOD = (long)1e9+7;

    public int countAssignments(int[] warriors, int[] horses) {
        int wn = warriors.length;
        int hn = horses.length;
        int[] w = new int[wn-1];
        for (int i = 1 ; i < wn ; i++) {
            w[i-1] = warriors[i];
        }
        Arrays.sort(w);


        long sum = 0;
        for (int i = 0; i < hn ; i++) {
            // pair Bravebeart and i-th horse
            int[] h = new int[hn-1];
            for (int j = 0; j < hn ; j++) {
                if (i == j) {
                    continue;
                }
                h[j+((i<j)?-1:0)] = horses[j];
            }
            Arrays.sort(h);
            sum += solve(w, h, warriors[0] * horses[i]);
        }
        return (int)(sum % MOD);
    }

    private long solve(int[] w, int[] h, int limit) {
        int wn = w.length;
        int hn = h.length;


        long ptn = 1;
        int hi = 0;
        long used = 0;
        for (int i = wn-1 ; i >= 0 ; i--) {
            while (hi < hn && w[i] * h[hi] < limit) {
                hi++;
            }
            ptn *= Math.max(0, hi - used);
            ptn %= MOD;
            used++;
        }

        return ptn;
    }

    public static void debug(Object... ob) {
        System.err.println(Arrays.deepToString(ob));
    }

    public static void main(String[] args) {
        BearCavalry cavalry = new BearCavalry();
        cavalry.countAssignments(new int[]{5,8,4,8}, new int[]{19,40,25,20});
    }
}