package topcoder.srm5xx.srm548;

/**
 * Created by hama_du on 15/08/28.
 */
public class KingdomAndTrees {
    public int minLevel(int[] heights) {
        int n = heights.length;
        long[] a = new long[n];
        for (int i = 0; i < n ; i++) {
            a[i] = heights[i];
        }

        long can = (long)1e10;
        long not = -1;
        while (can - not > 1) {
            long med = (can + not) / 2;
            if (isPossible(med, a.clone())) {
                can = med;
            } else {
                not = med;
            }
        }
        return (int)can;
    }

    private boolean isPossible(long med, long[] a) {
        int n = a.length;
        a[0] = Math.max(1, a[0] - med);
        for (int i = 1 ; i < n ; i++) {
            if (a[i] > a[i-1]) {
                a[i] = Math.max(a[i-1]+1, a[i]-med);
            } else {
                long need = a[i-1] - a[i] + 1;
                if (need > med) {
                    return false;
                }
                a[i] = a[i-1]+1;
            }
        }
        return true;
    }
}
