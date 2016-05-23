package topcoder.srm6xx.srm670.div1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hama_du on 2016/05/23.
 */
public class Bracket107 {
    public int yetanother(String s) {
        int n = s.length();
        char[] c = s.toCharArray();

        Set<String> set = new HashSet<>();
        int max = -1;
        for (int i = 0 ; i < n ; i++) {
            String line = "";
            for (int j = 0; j < n ; j++) {
                if (i == j) {
                    continue;
                }
                line += c[j];
            }
            for (char sup : new char[]{'(', ')'}) {
                for (int j = 0; j <= n-1 ; j++) {
                    String to = line.substring(0, j) + sup + line.substring(j, n-1);
                    char[] tc = to.toCharArray();
                    if (!check(tc)) {
                        continue;
                    }
                    int lc = lcs(c, tc);
                    if (lc == n) {
                        continue;
                    }
                    if (max < lc) {
                        max = lc;
                        set.clear();
                        set.add(String.valueOf(tc));
                    }  else if (max == lc) {
                        set.add(String.valueOf(tc));
                    }
                }
            }
        }
        return set.size();
    }
    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }


    public static void main(String[] args) {
        Bracket107 bracket107 = new Bracket107();
        debug(bracket107.yetanother("((())())"));
    }

    private int lcs(char[] c, char[] tc) {
        int n = c.length;
        int[][] dp = new int[n+1][n+1];
        for (int i = 1; i <= n ; i++) {
            for (int j = 1; j <= n ; j++) {
                dp[i][j] = Math.max(dp[i][j], dp[i-1][j]);
                dp[i][j] = Math.max(dp[i][j], dp[i][j-1]);
                if (c[i-1] == tc[j-1]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i-1][j-1]+1);
                }
            }
        }
        return dp[n][n];
    }

    public boolean check(char[] c) {
        int d = 0;
        for (char ci : c) {
            if (ci == '(') {
                d++;
            } else {
                d--;
            }
            if (d < 0) {
                return false;
            }
        }
        return d == 0;
    }
}
