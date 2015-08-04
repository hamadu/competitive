package topcoder.srm5xx.srm504;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/04.
 */
public class Stackol {
    private static final int MOD = 1000000007;

    public int countPrograms(String[] outputFragments, int k) {
        StringBuilder line = new StringBuilder();
        for (String o : outputFragments) {
            line.append(o);
        }
        char[] out = line.toString().toCharArray();
        int n = out.length;

        int[] imosA = new int[n+1];
        int[] imosB = new int[n+1];
        for (int i = 0; i < n; i++) {
            imosA[i+1] = imosA[i] + ((out[i] == 'A') ? 1 : 0);
            imosB[i+1] = imosB[i] + ((out[i] == 'B') ? 1 : 0);
        }

        int[][] sub = new int[n+1][n+1];
        for (int fr = 0; fr <= n ; fr++) {
            for (int to = fr+1 ; to <= n ; to++) {
                int A = imosA[to] - imosA[fr];
                for (int sep = Math.max(A, 1); sep <= A+1 && sep <= to - fr ; sep++) {
                    int leftB = imosB[fr+sep] - imosB[fr];
                    int rightA = imosA[to] - imosA[fr+sep];
                    boolean leftEndWithB = out[fr] == 'B';
                    boolean rightEndWithA = (sep == to-fr) || out[fr+sep] == 'A';
                    if (isOK(leftB, rightA, leftEndWithB, rightEndWithA)) {
                        sub[fr][to]++;
                    }
                }
            }
            sub[fr][fr] = 1;
        }

        int[][] dp = new int[k+1][n+1];
        dp[0][0] = 1;
        for (int ki = 0; ki < k ; ki++) {
            for (int f = 0; f <= n; f++) {
                if (dp[ki][f] == 0) {
                    continue;
                }
                int base = dp[ki][f];
                for (int t = f ; t <= n ; t++) {
                    dp[ki+1][t] += (base * sub[f][t]) % MOD;
                    dp[ki+1][t] -= (dp[ki+1][t] >= MOD) ? MOD : 0;
                }
            }
        }
        long ret = 0;
        for (int i = 0; i <= k; i++) {
            ret += dp[i][n];
        }
        return (int)(ret % MOD);
    }

    private boolean isOK(int leftB, int rightA, boolean leftEndWithB, boolean rightEndWithA) {
        if (rightA == leftB) {
            // *B......B* | A.......A*
            //  --leftB--   --rightA--
            return rightEndWithA;
        } else if (rightA + 1 == leftB) {
            // B.......B* | *A......A*
            // ---leftB--   --rightA--
            return leftEndWithB;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Stackol stackol = new Stackol();
        debug(stackol.countPrograms(new String[]{"AB"}, 2));
        debug(stackol.countPrograms(new String[]{"AAABABABAABAAABBAB"}, 3));
        debug(stackol.countPrograms(new String[]{"AAAAAAAAAAAAB"}, 4));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
