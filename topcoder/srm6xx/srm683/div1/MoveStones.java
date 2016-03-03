package topcoder.srm6xx.srm683.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/02/29.
 */
public class MoveStones {

    public long get(int[] a, int[] b) {
        if (sum(a) != sum(b)) {
            return -1;
        }
        int[] ra = reverse(a);
        int[] rb = reverse(b);
        return Math.min(solvePart(a, b), solvePart(ra, rb));
    }

    private int[] reverse(int[] b) {
        int n = b.length;
        int[] ret = new int[n];
        for (int i = 0; i < n ; i++) {
            ret[i] = b[n-1-i];
        }
        return ret;
    }

    public long solvePart(int[] a, int[] b) {
        long best = Long.MAX_VALUE;
        int n = a.length;
        for (int p = 0 ; p < n ; p++) {
            long[] from = build(a, p);
            long[] to = build(b, p);
            best = Math.min(best, solve(from, to));
        }
        return best;
    }

    private long solve(long[] stones, long[] to) {
        int n = stones.length;
        long moved = 0;
        for (int i = 0 ; i < n ; i++) {
            long need = Math.abs(stones[i] - to[i]);
            moved += need;
            if (stones[i] < to[i]) {
                stones[(i+1)%n] -= need;
                stones[i] += need;
            } else {
                stones[(i+1)%n] += need;
                stones[i] -= need;
            }
        }
        for (int i = 0 ; i < n ; i++) {
            if (stones[i] != to[i]) {
                return Long.MAX_VALUE;
            }
        }
        return moved;
    }

    private long[] build(int[] a, int p) {
        int n = a.length;
        long[] ret = new long[n];
        for (int i = p ; i < p + n ; i++) {
            ret[i-p] = a[i%n];
        }
        return ret;
    }

    private long sum(int[] a) {
        long sum = 0;
        for (long l : a) {
            sum += l;
        }
        return sum;
    }


    public static void main(String[] args) {
        MoveStones solution = new MoveStones();
        solution.get(new int[]{7, 7, 7, 0, 2, 0}, new int[]{7, 7, 7, 1, 0, 1});
        // debug(solution.someMethod());
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
