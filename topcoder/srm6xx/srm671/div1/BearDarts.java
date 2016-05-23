package topcoder.srm6xx.srm671.div1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by hama_du on 2016/05/23.
 */
public class BearDarts {
    public class Rational implements Comparable<Rational> {
        long a;
        long b;

        public Rational(long x, long y) {
            a = x;
            b = y;
        }

        @Override
        public int compareTo(Rational o) {
            return Long.compare(a * o.b, b * o.a);
        }
    }

    public long count(int[] w) {
        long sum = 0;
        int n = w.length;
        Map<Rational,Long> right = new TreeMap<>();
        for (int i = 1 ; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                Rational rat = new Rational(w[j], w[i]);
                right.put(rat, right.getOrDefault(rat, 0L)+1);
            }
        }

        for (int bidx = 1 ; bidx <= n - 3 ; bidx++) {
            // adding to left
            Map<Rational,Long> left = new TreeMap<>();
            for (int ai = 0 ; ai < bidx ; ai++) {
                Rational rat = new Rational(w[ai], w[bidx]);
                left.put(rat, left.getOrDefault(rat, 0L)+1);
            }

            // removing from right
            for (int di = bidx+1 ; di < n ; di++) {
                Rational rat = new Rational(w[di], w[bidx]);
                right.put(rat, right.getOrDefault(rat, 0L)-1);
            }

            for (Rational rat : left.keySet()) {
                sum += left.get(rat) * right.getOrDefault(rat, 0L);
            }
        }
        return sum;
    }
}
