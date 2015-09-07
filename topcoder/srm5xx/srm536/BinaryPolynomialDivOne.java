package topcoder.srm5xx.srm536;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hama_du on 15/08/24.
 */
public class BinaryPolynomialDivOne {
    public int findCoefficient(int[] a, long m, long k) {
        int ct = 0;
        for (int i = 0; i < a.length ; i++) {
            if (a[i] == 1) {
                ct++;
            }
        }
        pos = new long[ct];
        int ci = 0;
        for (int i = 0; i < a.length ; i++) {
            if (a[i] == 1) {
                pos[ci++] = i;
            }
        }

        memo = new Map[128];
        for (int i = 0; i < 128 ; i++) {
            memo[i] = new HashMap<>();
        }
        return dfs(0, k, m) ? 1 : 0;
    }


    long[] pos;
    Map<Long,Integer>[] memo;

    public boolean dfs(int level, long left, long M) {
        if (M == 0) {
            return left == 0;
        }
        if (memo[level].containsKey(left)) {
            return memo[level].get(left) == 1;
        }
        boolean isOK = false;
        if ((M & 1L) == 1) {
            for (long ai : pos) {
                if (left >= ai && (left-ai) % 2 == 0) {
                    isOK ^= dfs(level+1, (left-ai)/2, M/2);
                }
            }
        } else {
            if (left % 2 == 0) {
                isOK ^= dfs(level+1, left/2, M/2);
            }
        }
        memo[level].put(left, isOK ? 1 : -1);
        return isOK;
    }

    public static void main(String[] args) {
        BinaryPolynomialDivOne binaryPolynomialDivOne = new BinaryPolynomialDivOne();
        debug(binaryPolynomialDivOne.findCoefficient(
                new int[]{1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1},
                7153763972982865L, 168583835533296105L));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
