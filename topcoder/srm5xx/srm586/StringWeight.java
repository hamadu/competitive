package topcoder.srm5xx.srm586;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/25.
 */
public class StringWeight {

    static final int INF = 100000000;

    public int getMinimum(int[] L) {
        this.L = L;
        n = L.length;
        memo = new int[51][27][27];
        for (int i = 0; i < 51 ; i++) {
            for (int j = 0; j < 27; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }
        return dfs(0, 26, 0);
    }

    int dfs(int now, int left, int open) {
        if (now == n) {
            return (open == 0) ? 0 : INF;
        }
        if (memo[now][left][open] != -1) {
            return memo[now][left][open];
        }

        int sum = 0;
        for (int i = 0; i < now ; i++) {
            sum += L[i];
        }

        int ret = INF;
        int max = Math.min(26, L[now]);
        for (int n1 = 0; n1 <= max ; n1++) {
            for (int n2 = 0; n1+n2 <= max ; n2++) {
                if (n1 + n2 > left) {
                    break;
                }
                int tl = left - n1 - n2;
                for (int u1 = 0; n1+n2+u1 <= max ; u1++) {
                    int u2 = max-n1-n2-u1;
                    if (open < u2+u1) {
                        continue;
                    }
                    int to = open-u2+n2;

                    int cost = dfs(now+1, tl, to);
                    cost += sum*u2+(u2*(u2-1)/2);
                    cost -= ((sum+L[now])*n2)-(n2*(n2+1)/2);

                    int needToFill = L[now]-n1-n2-u1-u2;
                    if (needToFill >= 1 && u1 == 0) {
                        cost += needToFill;
                    }

                    ret = Math.min(ret, cost);
                }
            }
        }
        memo[now][left][open] = ret;
        return ret;
    }

    int n;
    int[] L;
    int[][][] memo;

    public static void main(String[] args) {
        StringWeight weight = new StringWeight();
        debug(weight.getMinimum(new int[]{26,2}));

    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
