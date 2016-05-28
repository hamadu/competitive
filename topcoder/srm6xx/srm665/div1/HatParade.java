package topcoder.srm6xx.srm665.div1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hama_du on 2016/05/28.
 */
public class HatParade {
    int MOD = (int)1e9+7;

    public int getPermutation(int[] value, int[] sum) {
        int n = value.length;
        long[] lv = new long[n];
        long[] ls = new long[n];
        for (int i = 0; i < n ; i++) {
            lv[i] = value[i];
        }
        for (int i = 0; i < n ; i++) {
            ls[i] = sum[i];
        }
        return solve(lv, ls);
    }

    private int solve(long[] lv, long[] ls) {
        int n = lv.length;
        long total = Arrays.stream(lv).sum();
        long[][] pairs = new long[n][2];
        for (int i = 0; i < n ; i++) {
            long l = ls[i];
            long r = total - ls[i] + lv[i];
            pairs[i][0] = Math.min(l, r);
            pairs[i][1] = Math.max(l, r);
        }

        int ptn = 1;
        boolean[] used = new boolean[n];
        long left = 0;
        long right = 0;
        int leftHat = n;
        while (leftHat >= 1) {
            long max = -1;
            List<Integer> maxIds = new ArrayList<>();
            for (int i = 0 ; i < n ; i++) {
                if (used[i]) {
                    continue;
                }
                if (max < pairs[i][1]) {
                    max = pairs[i][1];
                    maxIds.clear();
                    maxIds.add(i);
                } else if (max == pairs[i][1]) {
                    maxIds.add(i);
                }
            }
            if (maxIds.size() > 2) {
                return 0;
            }

            if (left == right) {
                if (Math.min(leftHat, 2) != maxIds.size()) {
                    return 0;
                }
                long want = total - left;
                if (max != want) {
                    return 0;
                }
                ptn *= maxIds.size();
                left += lv[maxIds.get(0)];
                if (maxIds.size() == 2) {
                    right += lv[maxIds.get(1)];
                }
            } else {
                if (maxIds.size() != 1) {
                    return 0;
                }
                long want = total - Math.min(left, right);
                if (max != want) {
                    return 0;
                }
                if (left < right) {
                    left += lv[maxIds.get(0)];
                } else {
                    right += lv[maxIds.get(0)];
                }
            }
            leftHat -= maxIds.size();
            for (int id : maxIds) {
                used[id] = true;
            }
            ptn %= MOD;
        }
        return ptn;
    }
}
