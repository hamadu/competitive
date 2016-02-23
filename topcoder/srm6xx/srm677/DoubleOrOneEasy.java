package topcoder.srm6xx.srm677;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/01/31.
 */
public class DoubleOrOneEasy {

    private static final long INF = 1000000000000L;

    public int minimalSteps(int a, int b, int newA, int newB) {
        long ans = (a == b) ? solveSame(a, b, newA, newB) : solveDiff(a, b, newA, newB);
        if (ans >= INF/10) {
            return -1;
        }
        return (int)ans;
    }

    private long solveDiff(long a, long b, long newA, long newB) {
        if (newA == newB) {
            return INF;
        }
        long diffBefore = b - a;
        long diffAfter = newB - newA;
        if (!isSameSign(diffBefore, diffAfter)) {
            return INF;
        }
        long fr = Math.abs(diffBefore);
        long to = Math.abs(diffAfter);
        if (to % fr >= 1 || Long.bitCount(to / fr) != 1) {
            return INF;
        }
        long mul = to / fr;
        int cnt = 0;
        while (mul >= 2) {
            cnt++;
            mul /= 2;
        }
        long ma = a * (to / fr);
        long mb = b * (to / fr);
        if (ma > newA || mb > newB || Math.abs(ma - newA) !=  Math.abs(mb - newB)) {
            return INF;
        }
        return count(a, newA, cnt);
    }

    private boolean isSameSign(long A, long B) {
        return Long.signum(A) == Long.signum(B);
    }

    private long solveSame(long a, long b, long newA, long newB) {
        if (newA != newB) {
            return INF;
        }
        // a to newA;
        if (a > newA) {
            return INF;
        }
        return count(a, newA, -1);
    }

    private long count(long fr, long to, long leftMul) {
        if (to == fr) {
            return 0;
        }
        if (leftMul == 0) {
            return fr <= to ? to - fr : INF;
        }
        if (to % 2 == 0) {
            if (fr <= to / 2 && leftMul != 0) {
                return count(fr, to / 2, leftMul-1) + 1;
            } else {
                return to - fr;
            }
        } else {
            return count(fr, to-1, leftMul)+1;
        }
    }


    public static void main(String[] args) {
        DoubleOrOneEasy solution = new DoubleOrOneEasy();
        // debug(solution.someMethod());
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
