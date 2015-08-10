package topcoder.srm5xx.srm512;

import java.util.*;

/**
 * Created by hama_du on 15/08/07.
 */
public class SubFibonacci {
    public int maxElements(int[] S) {
        if (S.length <= 4) {
            return S.length;
        }
        Arrays.sort(S);
        map = new HashMap<>();
        for (int i = 0; i < S.length ; i++) {
            map.put(S[i] * 1L, i);
        }
        fib0 = new long[64];
        fib1 = new long[64];
        fib0[0] = 1;
        fib1[1] = 1;
        for (int i = 2 ; i < fib0.length ; i++) {
            fib0[i] = fib0[i-2] + fib0[i-1];
            fib1[i] = fib1[i-2] + fib1[i-1];
        }

        Set<Long> mset = new HashSet<>();
        int n = S.length;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (i == j) {
                    continue;
                }
                for (int d = 1; d <= 60 ; d++) {
                    long mask = findMask(S[i], S[j], d);
                    if (mask == -1) {
                        continue;
                    }
                    mset.add(mask);
                }
            }
        }

        int max = 0;
        int[] left = new int[n+1];
        int[] right = new int[n+1];
        for (long m0 : mset) {
            for (int k = 0; k <= n; k++) {
                long l = m0 & ((1L<<k)-1);
                long r = m0 - l;
                left[k] = Math.max(left[k], Long.bitCount(l));
                right[k] = Math.max(right[k], Long.bitCount(r));
            }
        }
        for (int i = 0; i <= n; i++) {
            max = Math.max(max, left[i] + right[i]);
        }
        return max;
    }

    long[] fib0;
    long[] fib1;

    // find sequence (and return mask) that satisfies f[0] = a, f[d] = b
    public long findMask(long a, long b, int d) {
        long a0 = a;
        long a1 = b - a0 * fib0[d];
        if (a1 <= 0 || a1 % fib1[d] != 0) {
            return -1;
        }
        a1 /= fib1[d];
        if (a1 <= 0) {
            return -1;
        }

        long mask = 0;
        long last = -1;
        if (map.containsKey(a0)) {
            mask |= 1L<<map.get(a0);
            last = a0;
        }
        while (a1 <= 1e9) {
            if (map.containsKey(a1)) {
                if (last <= a1) {
                    mask |= 1L << map.get(a1);
                    last = a1;
                }
            }
            long t = a0 + a1;
            a0 = a1;
            a1 = t;
        }

        return mask;
    }

    Map<Long,Integer> map;

    public static void main(String[] args) {
        SubFibonacci sub = new SubFibonacci();

        int[] a = new int[50];
        for (int i = 0; i < a.length; i++) {
            a[i] = i+1;
        }
        debug(sub.maxElements(a));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
