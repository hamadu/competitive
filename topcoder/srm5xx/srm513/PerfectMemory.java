package topcoder.srm5xx.srm513;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/07.
 */
public class PerfectMemory {
    public double getExpectation(int N, int M) {
        nm = N * M;
        memo = new double[nm+1][nm/2+1];
        for (int i = 0; i < nm+1; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(nm, 0);
    }

    int nm;
    double[][] memo;

    public double dfs(int left, int one) {
        if (left == 0) {
            return 0;
        }
        if (memo[left][one] != -1) {
            return memo[left][one];
        }

        double rate = 0;
        if (one >= 1) {
            double pick = 1.0d * one / (left - one);
            if (pick > 0) {
                rate += pick * (dfs(left - 2, one - 1) + 1.0d);
            }
            double upick = 1.0d - pick;
            if (upick > 0 && left - one - 1 >= 1) {
                double pick2 = 1.0d * one / (left - one - 1);
                if (pick2 > 0) {
                    rate += upick * pick2 * (dfs(left - 2, one) + 2.0d);
                }
                double pickS = 1.0d / (left - one - 1);
                if (pickS > 0) {
                    rate += upick * pickS * (dfs(left - 2, one) + 1.0d);
                }
                double other = 1.0d - pick2 - pickS;
                if (one + 1 < left - one - 1 && other > 0) {
                    rate += upick * other * (dfs(left, one + 2) + 1.0d);
                }
            }
        } else {
            int pair = left * (left - 1) / 2;
            int same = left / 2;
            double s2 = 1.0d * same / pair;
            double o2 = 1.0d - s2;
            if (s2 > 0) {
                rate += s2 * (dfs(left - 2, one) + 1.0d);
            }
            if (o2 > 0) {
                rate += o2 * (dfs(left, one + 2) + 1.0d);
            }
        }
        memo[left][one] = rate;
        return rate;
    }
}
