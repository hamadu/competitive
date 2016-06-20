package topcoder.srm6xx.srm653.div1;

/**
 * Created by hama_du on 2016/06/20.
 */
public class CountryGroupHard {
    public String solve(int[] a) {
        int n = a.length;
        int[] dp = new int[n+1];
        dp[0] = 1;

        for (int i = 0; i < n ; i++) {
            for (int j = 1 ; i+j <= n ; j++) {
                int num = -1;
                boolean valid = true;
                for (int k = i ; k < i+j ; k++) {
                    if (a[k] != 0) {
                        if (num != -1 && num != a[k]) {
                            valid = false;
                            break;
                        }
                        num = a[k];
                    }
                }
                if (valid && (num == -1 || num == j)) {
                    dp[i+j] = Math.min(2, dp[i+j] + dp[i]);
                }
            }
        }
        return dp[n] == 1 ? "Sufficient" : "Insufficient";
    }

}
