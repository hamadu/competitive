package topcoder.srm6xx.srm680.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/02/05.
 */
public class BearFair {
    public String isFair(int n, int b, int[] upTo, int[] quantity) {
        int[][][] dp = new int[n+1][b+2][n+1];
        int q = upTo.length;


        int[] numTable = new int[b+1];
        Arrays.fill(numTable, -1);
        for (int i = 0 ; i < q ; i++) {
            int nu = upTo[i];
            int qu = quantity[i];
            if (numTable[nu] != -1 && numTable[nu] != qu) {
                return "unfair";
            }
            numTable[nu] = qu;
        }
        for (int s = 1 ; s <= b ; s++) {
            for (int t = s+1 ; t <= b ; t++) {
                if (numTable[s] != -1 && numTable[t] != -1 && numTable[s] > numTable[t]) {
                    return "unfair";
                }
            }
        }


        dp[0][0][0] = 1;
        for (int idx = 0 ; idx < n ; idx++) {
            for (int num = 0 ; num < b; num++) {
                for (int even = 0 ; even <= n ; even++) {
                    if (dp[idx][num][even] == 0) {
                        continue;
                    }

                    // use "num"
                    int next = num+1;
                    int te = even + ((next % 2 == 0) ? 1 : 0);
                    if (te <= n) {
                        if (numTable[next] == -1 || numTable[next] == idx+1) {
                            dp[idx+1][next][te] = 1;
                        }
                    }

                    // dont use "num"
                    if (numTable[next] == -1 || numTable[next] == idx) {
                        dp[idx][next][even] = dp[idx][num][even];
                    }
                }
            }
        }
        for (int i = 1 ; i <= b ; i++) {
            debug(i, dp[n][i]);
            if (dp[n][i][n/2] == 1) {
                boolean isOK = true;
                for (int h = i ; h <= b ; h++) {
                    if (numTable[h] != -1 && numTable[h] != n) {
                        isOK = false;
                        break;
                    }
                }
                if (isOK) {
                    return "fair";
                }
            }
        }
        return "unfair";
    }

    public static void main(String[] args) {
        BearFair solution = new BearFair();
        debug(solution.isFair(2, 6, new int[]{1, 2, 3}, new int[]{1, 1, 2}));
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
