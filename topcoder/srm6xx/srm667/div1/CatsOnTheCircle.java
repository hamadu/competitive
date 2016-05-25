package topcoder.srm6xx.srm667.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/05/25.
 */
public class CatsOnTheCircle {
    public double getProb(int N, int K, int p) {
        int left = K;
        int right = N-K;
        double rate = p / 1e9;

        return gamble(left-1, right, rate) * gamble(N-1, 1, 1-rate) + gamble(right-1, left, 1-rate) * gamble(N-1, 1, rate);
    }

    private double gamble(int one, int two, double rate) {
        if (rate == 0.5) {
            return 1.0d * two / (one + two);
        }
        double r = (1.0d - rate) / rate;
        double upper = Math.log(r) * two;
        double downr = Math.log(r) * (one + two);
        if (downr > 80) {
            return Math.exp(upper - downr);
        } else {
            return (1.0d - Math.exp(upper)) / (1.0d - Math.exp(downr));
        }
    }
}

