package topcoder.srm5xx.srm518;

/**
 * Created by hama_du on 15/08/14.
 */
public class ConvexSequence {
    public long getMinimum(int[] a) {
        long cost = 0;
        int n = a.length;
        while (true) {
            boolean finished = true;
            for (int i =  1; i <= n-2 ; i++) {
                if (a[i-1] + a[i+1] < 2*a[i]) {
                    cost += a[i] - (a[i-1] + a[i+1]) / 2;
                    a[i] = (a[i-1] + a[i+1]) / 2;
                    finished = false;
                }
            }
            if (finished) {
                break;
            }
        }
        return cost;
    }
}
