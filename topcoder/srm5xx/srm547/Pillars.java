package topcoder.srm5xx.srm547;

/**
 * Created by hama_du on 15/08/28.
 */
public class Pillars {
    public double getExpectedLength(int w, int x, int y) {
        if (x > y) {
            return getExpectedLength(w, y, x);
        }
        double sum = 0;
        for (long d = -x ; d <= y ; d++) {
            double len = Math.sqrt(w * w + d * d);
            long from = 1 + d;
            long to = x + d;
            if (from > y || to < 1) {
                continue;
            }
            to = Math.min(to, y);
            from = Math.max(from, 1);
            sum += len * (to - from + 1);
        }
        return sum / x / y;
    }
}
