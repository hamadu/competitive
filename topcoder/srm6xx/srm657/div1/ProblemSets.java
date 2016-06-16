package topcoder.srm6xx.srm657.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/06/16.
 */
public class ProblemSets {
    public long maxSets(long E, long EM, long M, long MH, long H) {
        long best = 0;
        long min = 0;
        long max = EM;
        for (int i = 0 ; i < 200 ; i++) {
            long m1 = (min * 2 + max) / 3;
            long m2 = (min + max * 2) / 3;
            long v1 = doit(E, EM, M, MH, H, m1);
            long v2 = doit(E, EM, M, MH, H, m2);
            if (v1 < v2) {
                min = m1;
            } else {
                max = m2;
            }
            best = max(best, v1, v2);
        }
        for (long f = min-5 ; f <= max+5 ; f++) {
            if (f < 0 || f > EM) {
                continue;
            }
            best = max(best, doit(E, EM, M, MH, H, f));
        }
        return best;
    }

    private long doit(long e, long em, long m, long mh, long h, long f) {
        long E = e + f;
        long M = m + em - f;
        long left = min(M, h);
        long right = max(M, h);
        long diff = right - left;
        long use = min(mh, diff);
        left += use;
        mh -= use;
        return min(E, left + mh / 2);
    }

    private long max(Long... x) {
        return Arrays.stream(x).max(Long::compareTo).get();
    }

    private long min(Long... x) {
        return Arrays.stream(x).min(Long::compareTo).get();
    }
}
