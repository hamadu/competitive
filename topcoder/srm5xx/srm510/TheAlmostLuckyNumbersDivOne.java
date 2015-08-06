package topcoder.srm5xx.srm510;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/07.
 */
public class TheAlmostLuckyNumbersDivOne {
    public long find(long a, long b) {
        return solve(0, 0, b) - solve(0, 0, a-1);
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    private long solve(long now, int flg, long limit) {
        if (now > limit) {
            return 0;
        }
        long res = 1;
        for (int d = (now == 0) ? 1 : 0 ; d <= 9 ; d++) {
            if (d == 4 || d == 7) {
                res += solve(now*10+d, flg, limit);
            } else if (flg == 0) {
                res += solve(now*10+d, 1, limit);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        TheAlmostLuckyNumbersDivOne a = new TheAlmostLuckyNumbersDivOne();
        debug(a.find(4,7));
    }
}
