package topcoder.srm6xx.srm649.div1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hama_du on 2016/06/22.
 */
public class XorSequence {
    public long getmax(int N, int sz, int A0, int A1, int P, int Q, int R) {
        long[] a = make(N, sz, A0, A1, P, Q, R);
        List<long[]> i = new ArrayList<>();
        i.add(a);
        return solve(i, N / 2);
    }

    private long solve(List<long[]> aa, int n) {
        if (n == 0) {
            return 0;
        }
        long ret0 = 0;
        long ret1 = 0;
        List<long[]> next = new ArrayList<>();
        for (long[] a : aa) {
            int zero = 0;
            int one = 0;
            for (int i = a.length-1; i >= 0; i--) {
                if ((a[i]&n) == 0) {
                    zero++;
                    ret1 += one;
                } else {
                    one++;
                    ret0 += zero;
                }
            }
            long[] b0 = new long[zero];
            long[] b1 = new long[one];
            zero = 0;
            one = 0;
            for (int i = 0; i < a.length; i++) {
                if ((a[i]&n) == 0) {
                    b0[zero++] = a[i];
                } else {
                    b1[one++] = a[i];
                }
            }
            if (zero > 1) {
                next.add(b0);
            }
            if (one > 1) {
                next.add(b1);
            }
        }
        return Math.max(ret0, ret1) + solve(next, n/2);
    }

    private long[] make(int n, int sz, long a0, long a1, long p, long q, long r) {
        long[] ret = new long[sz];
        ret[0] = a0;
        ret[1] = a1;
        for (int i = 2 ; i < sz; i++) {
            ret[i] = (ret[i-2] * p + ret[i-1] * q + r) % n;
        }
        return ret;
    }
}
