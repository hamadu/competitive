package topcoder.srm6xx.srm686.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/05/14.
 */
public class BracketSequenceDiv1 {
    char[] c;

    public long count(String s) {
        int n = s.length();
        c = s.toCharArray();
        memo = new long[n+1][n+1];
        for (int i = 0; i <= n ; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, n-1);
    }


    long[][] memo;
    public long dfs(int left, int right) {
        if (left > right) {
            return (right + 1 == left) ? 1 : 0;
        }
        if (memo[left][right] != -1) {
            return memo[left][right];
        }
        long ans = 0;
        if (c[left] == '(' || c[left] == '[') {
            char counter = (c[left] == '(') ? ')' : ']';
            for (int i = left+1 ; i <= right ; i++) {
                if (c[i] == counter) {
                    ans += dfs(left+1, i-1) * dfs(i+1, right);
                }
            }
        }
        ans += dfs(left+1, right);
        memo[left][right] = ans;
        return ans;
    }
}
