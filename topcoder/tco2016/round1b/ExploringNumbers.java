package topcoder.tco2016.round1b;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hama_du on 4/13/16.
 */
public class ExploringNumbers {
    public int numberOfSteps(int n) {
        Set<Integer> alreadyHave = new HashSet<>();

        int now = n;
        int seq = 1;
        while (seq <= n) {
            if (alreadyHave.contains(now)) {
                break;
            }
            alreadyHave.add(now);

            boolean isPrime = (now != 1);
            for (int i = 2 ; i * i <= now ; i++) {
                if (now % i == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                return seq;
            }
            now = compute(now);
            seq++;
        }
        return -1;
    }

    private int compute(int now) {
        char[] c = String.valueOf(now).toCharArray();
        int sum = 0;
        for (char ci : c) {
            sum += (ci - '0') * (ci - '0');
        }
        return sum;
    }

    public static void main(String[] args) {
        ExploringNumbers solution = new ExploringNumbers();
        for (int i = 1 ; i < 100000 ; i++) {
            debug(solution.numberOfSteps(i));
            debug("===");
        }
        //debug(solution.numberOfSteps(100100100));
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
