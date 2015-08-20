package topcoder.srm5xx.srm528;

/**
 * Created by hama_du on 15/08/18.
 */
public class SPartition {
    public char[] c;

    public long doit(char[] S) {
        int n = S.length;
        long[][] dp = new long[n+1][n+1];
        dp[0][0] = 1;
        for (int i = 0; i <= n ; i++) {
            for (int j = 0; j <= n ; j++) {
                if (dp[i][j] == 0) {
                    continue;
                }
                long base = dp[i][j];
                int idx = i + j;
                if (i < n && S[i] == c[idx]) {
                    dp[i+1][j] += base;
                }
                if (j < n && S[j] == c[idx]) {
                    dp[i][j+1] += base;
                }
            }
        }
        return dp[n][n];
    }

    public long getCount(String s) {
        c = s.toCharArray();
        int n = s.length();
        int on = 0;
        for (int i = 0; i < n ; i++) {
            if (c[i] == 'o') {
                on++;
            }
        }
        on /= 2;

        long ret = 0;
        for (int p = 0; p < (1<<(n/2)) ; p++) {
            if (Integer.bitCount(p) == on) {
                char[] sub = new char[n/2];
                for (int i = 0; i < n/2 ; i++) {
                    sub[i] = (p & (1<<i)) >= 1 ? 'o' : 'x';
                }
                ret += doit(sub);
            }
        }
        return ret;
    }
}
