package topcoder.srm5xx.srm519;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by hama_du on 15/08/16.
 */
public class VerySmoothDecompositions {
    public static final int MOD = 1000000009;

    public int solve(String[] digits) {
        int n = digits.length;
        BigInteger X = BigInteger.ZERO;
        for (int i = 0 ; i < n  ; i++) {
            char[] c = digits[i].toCharArray();
            for (int j = 0 ; j < c.length ; j++) {
                long d = c[j]-'0';
                X = X.multiply(BigInteger.TEN);
                X = X.add(BigInteger.valueOf(d));
            }
        }

        int[] pr = new int[]{2,3,5,7,11,13};
        int[] pc = new int[6];
        for (int i = 0; i < 6 ; i++) {
            BigInteger p = BigInteger.valueOf(pr[i]);
            while (X.mod(p).equals(BigInteger.ZERO)) {
                pc[i]++;
                X = X.divide(p);
            }
        }
        if (!X.equals(BigInteger.ONE)) {
            return 0;
        }

        int n2 = pc[0];
        int n3 = pc[1];
        int n5 = pc[2];
        int[][] use = {
                {0, 1}, // 3
                {0, 2}, // 9
                {1, 0}, // 2
                {1, 1}, // 6
                {2, 0}, // 4
                {2, 1}, // 12
                {3, 0}, // 8
                {4, 0}, // 16
        };

        int[][] dp = new int[n2+2][n3+1];
        dp[0][0] = 1;
        for (int i = 0; i < use.length ; i++) {
            for (int u2 = 0; u2 <= n2; u2++) {
                for (int u3 = 0 ; u3 <= n3; u3++) {
                    int fu2 = u2 - use[i][0];
                    int fu3 = u3 - use[i][1];
                    if (fu2 < 0 || fu3 < 0) {
                        continue;
                    }
                    dp[u2][u3] += dp[fu2][fu3];
                    dp[u2][u3] %= MOD;
                }
            }
        }

        for (int i = 0; i <= n3; i++) {
            for (int j = 0; j <= n2; j++) {
                dp[j+1][i] += dp[j][i];
                dp[j+1][i] %= MOD;
            }
        }

        long ret = 0;
        int m15 = Math.min(pc[1], pc[2]);
        int m14 = Math.min(pc[0], pc[3]);
        for (int u15 = 0; u15 <= m15 ; u15++) {
            for (int u14 = 0; u14 <= m14; u14++) {
                int l2 = n2 - u14;
                int l3 = n3 - u15;
                int l5 = n5 - u15;
                if (l2 < 0 || l3 < 0 || l5 < 0) {
                    break;
                }
                int u10Max = Math.min(l2, l5);
                int l2Min = l2 - u10Max;
                int l2Max = l2;
                ret += dp[l2Max][l3];
                if (l2Min >= 1) {
                    ret += MOD - dp[l2Min-1][l3];
                }
                ret %= MOD;
            }
        }
        return (int)ret;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
