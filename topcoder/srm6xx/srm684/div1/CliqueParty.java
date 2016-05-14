package topcoder.srm6xx.srm684.div1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hama_du on 2016/05/14.
 */
public class CliqueParty {
    public int maxsize(int[] a, int k) {
        int n = a.length;
        long[] b = new long[n];
        long K = k;
        for (int i = 0; i < n ; i++) {
            b[i] = a[i];
        }
        return solve(b, K);
    }

    private int solve(long[] a, long S) {
        int ans = 0;
        int n = a.length;
        Arrays.sort(a);
        for (int i = 0 ; i < n ; i++) {
            for (int j = i ; j < n; j++) {
                Set<Long> subset = new HashSet<>();
                long min = a[i];
                long max = a[j];
                long diff = a[j] - a[i];
                long last = min;
                subset.add(min);
                subset.add(max);
                for (int k = i+1 ; k <= j-1 ; k++) {
                    if (Math.abs(a[k] - last) * S >= diff && Math.abs(a[k] - max) * S >= diff) {
                        subset.add(a[k]);
                        last = a[k];
                    }
                }
                ans = Math.max(ans, subset.size());
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        CliqueParty solution = new CliqueParty();
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
