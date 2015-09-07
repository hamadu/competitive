package topcoder.srm5xx.srm555;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/26.
 */
public class CuttingBitString {
    private static final int INF = 114514;

    public int getmin(String S) {
        int n = S.length();
        int[] dp = new int[n+1];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for (int i = 0; i < n ; i++) {
            if (dp[i] == INF) {
                continue;
            }
            if (S.charAt(i) == '0') {
                continue;
            }
            for (int j = i+1 ; j <= n; j++) {
                if (canCut(S, i, j)) {
                    dp[j] = Math.min(dp[j], dp[i]+1);
                }
            }
        }
        return dp[n] == INF ? -1 : dp[n];
    }

    public boolean canCut(String S, int from, int to) {
        return isPowerOfFive(toLong(S, from, to));
    }

    public long toLong(String S, int from, int to) {
        String z = S.substring(from, to);
        long L = 0;
        for (int i = 0; i < z.length() ; i++) {
            L <<= 1;
            L += z.charAt(i) - '0';
        }
        return L;
    }

    public boolean isPowerOfFive(long L) {
        if (L == 0) {
            return false;
        }
        while (L % 5 == 0) {
            L /= 5;
        }
        return L == 1;
    }

    public static void main(String[] args) {
        CuttingBitString bitString = new CuttingBitString();
        debug(bitString.getmin("1000101011"));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
