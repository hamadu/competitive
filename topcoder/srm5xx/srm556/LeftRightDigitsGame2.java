package topcoder.srm5xx.srm556;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/01.
 */
public class LeftRightDigitsGame2 {

    public String minNumber(String digits, String lowerBound) {
        n = digits.length();
        di = digits.toCharArray();
        lb = lowerBound.toCharArray();

        memo = new String[n+1][n+1][2][3];

        String ans = dfs(0, n-1, 0, 0);
        if (ans.equals("~")) {
            return "";
        }
        return ans;
   }

    int n;
    char[] di;
    char[] lb;
    String[][][][] memo;

    public String dfs(int li, int ri, int lf, int rf) {
        if (memo[li][ri][lf][rf] != null) {
            return memo[li][ri][lf][rf];
        }
        char c = di[ri-li];
        if (li == ri) {
            String ret = "";
            if (lf == 0) {
                if (rf == 2) {
                    ret = (c > lb[li]) ? (c + "") : "~";
                } else {
                    ret = (c >= lb[li]) ? (c + "") : "~";
                }
            } else {
                ret = c+"";
            }
            memo[li][ri][lf][rf] = ret;
            return ret;
        }
        String best = "~";
        {
            if (lf == 0 && c < lb[li]) {
            } else {
                int tlf = (lf == 0 && c > lb[li]) ? 1 : lf;
                String left = dfs(li+1, ri, tlf, rf);
                if (!left.equals("~")) {
                    left = c + left;
                    if (left.compareTo(best) < 0) {
                        best = left;
                    }
                }
            }
        }
        {
            int trf = (c == lb[ri]) ? rf : (c > lb[ri]) ? 1 : 2;
            String right = dfs(li, ri-1, lf, trf);
            if (!right.equals("~")) {
                right = right + c;
                if (right.compareTo(best) < 0) {
                    best = right;
                }
            }
        }
        memo[li][ri][lf][rf] = best;
        return best;
    }
}
