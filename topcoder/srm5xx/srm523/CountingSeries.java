package topcoder.srm5xx.srm523;

/**
 * Created by hama_du on 15/08/17.
 */
public class CountingSeries {
    public long countThem(long a, long b, long c, long d, long upperBound) {
        long cnt = 0;
        if (a <= upperBound) {
            cnt = (upperBound - a) / b + 1;
        }
        long cd = c;
        while (cd <= upperBound) {
            if (a <= cd && (cd - a) % b == 0) {
                // skip
            } else {
                cnt++;
            }
            if (d == 1) {
                break;
            }
            cd = cd * d;
        }
        return cnt;
    }
}
