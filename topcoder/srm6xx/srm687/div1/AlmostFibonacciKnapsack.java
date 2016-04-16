package topcoder.srm6xx.srm687.div1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hama_du on 4/16/16.
 */
public class AlmostFibonacciKnapsack {
    public int[] getIndices(long x) {
        long[] afib = new long[100];
        afib[0] = 0;
        afib[1] = 2;
        afib[2] = 3;
        for (int i = 3 ; afib[i-1] < 2e18 ; i++) {
            afib[i] = afib[i-1] + afib[i-2] - 1;
        }
        List<Integer> idx = new ArrayList<>();
        for (int i = afib.length-1 ; i >= 1 ; i--) {
            if (afib[i] == 0) {
                continue;
            }
            if (x >= afib[i] && x != afib[i] + 1) {
                idx.add(i);
                x -= afib[i];
            }
        }
        int[] ret = new int[idx.size()];
        for (int i = 0; i < idx.size(); i++) {
            ret[i] = idx.get(i);
        }
        // debug(Arrays.stream(ret).mapToLong(i -> afib[i]).toArray());
        return ret;
    }

    public static void main(String[] args) {
        AlmostFibonacciKnapsack solution = new AlmostFibonacciKnapsack();
        debug(solution.getIndices(100));
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
