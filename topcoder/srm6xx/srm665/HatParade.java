package topcoder.srm6xx.srm665;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/11.
 */
public class HatParade {
    public int getPermutation(int[] value, int[] sum) {
        n = value.length;
        Hat[] h = new Hat[n];
        for (int i = 0; i < n ; i++) {
            h[i] = new Hat(value[i], sum[i]);
        }
        Arrays.sort(h);

        return 0;

    }

    int n;

    long dfs(int fr, int to, int idx) {
        if (idx == n) {
            return 1;
        }

        return 0;
    }


    static class Hat implements Comparable<Hat> {
        long red;
        long blue;

        Hat(long a, long b) {
            red = a;
            blue = b;
        }

        @Override
        public int compareTo(Hat o) {
            return Long.compare(blue, o.blue);
        }
    }
}
