package topcoder.srm6xx.srm649.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/06/22.
 */
public class Decipherability {
    int n;
    char[] c;

    int[][][][] memo;

    int dfs(int p1, int p2, int left, int flg) {
        if (left == 0) {
            return flg;
        }
        if (p1 >= n || p2 >= n) {
            return 0;
        }
        if (memo[p1][p2][left][flg] != -1) {
            return memo[p1][p2][left][flg];
        }
        int mk = 0;
        mk = Math.max(mk, dfs(p1+1, p2, left, flg));
        mk = Math.max(mk, dfs(p1, p2+1, left, flg));
        if (c[p1] == c[p2]) {
            int tflg = Math.max(flg, p1 != p2 ? 1 : 0);
            mk = Math.max(mk, dfs(p1+1, p2+1, left-1, tflg));
        }
        memo[p1][p2][left][flg] = mk;
        return mk;
    }

    public String check(String s, int K) {
        int left = s.length() - K;
        n = s.length();
        c = s.toCharArray();
        memo = new int[n+1][n+1][left+1][2];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                for (int k = 0; k <= left; k++) {
                    Arrays.fill(memo[i][j][k], -1);
                }
            }
        }
        return dfs(0, 0, left, 0) == 1 ? "Uncertain" : "Certain";
    }
}
