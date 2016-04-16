package topcoder.srm6xx.srm688.div1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hama_du on 4/15/16.
 */
public class ParenthesesDiv1Easy {

    public List<Integer> solve(String s) {
        char[] c = s.toCharArray();
        int n = c.length;
        if (n % 2 == 1) {
            return null;
        }

        int[] diff = new int[n+1];
        for (int i = 0; i < n ; i++) {
            diff[i+1] = diff[i] + (c[i] == '(' ? 1 : -1);
        }
        int min = n+1;
        int minX = -1;
        for (int i = 0 ; i <= n ; i++) {
            if (min > diff[i]) {
                min = diff[i];
                minX = i;
            }
        }

        char[] c1 = c.clone();
        List<Integer> ret = new ArrayList<>();
        if (minX >= 1) {
            c1 = flip(c, 0, minX);
            ret.add(0);
            ret.add(minX-1);
        }

        for (int i = 0; i <= n ; i++) {
            char[] c2 = flip(c1, i, n);
            if (isOK(c2)) {
                ret.add(i);
                ret.add(n-1);
                return ret;
            }
        }
        return null;
    }

    private boolean isOK(char[] c2) {
        int k = 0;
        int n = c2.length;
        for (int i = 0; i < n ; i++) {
            k += c2[i] == '(' ? 1 : -1;
            if (k < 0) {
                return false;
            }
        }
        return k == 0;
    }
    private char[] flip(char[] c, int fr, int to) {
        char[] ret = c.clone();
        int len = to - fr;
        for (int i = 0 ; i < len; i++) {
            ret[fr+i] = c[fr+len-1-i];
        }
        for (int i = fr ; i < to ; i++) {
            ret[i] = (ret[i] == ')') ? '(' : ')';
        }
        return ret;
    }


    public int[] correct(String s) {
        List<Integer> f = solve(s);
        if (f == null) {
            return new int[]{-1};
        }
        int[] ret = new int[f.size()];
        for (int i = 0; i < f.size() ; i++) {
            ret[i] = f.get(i);
        }
        return ret;
    }

    public static void main(String[] args) {

        ParenthesesDiv1Easy solution = new ParenthesesDiv1Easy();

        for (int l = 2 ; l <= 28 ; l += 2) {
            for (int k = 0 ; k < (1<<l) ; k++) {
                String s = "";
                for (int i = 0; i < l ; i++) {
                    if ((k & (1<<i)) >= 1) {
                        s += "(";
                    } else {
                        s += ")";
                    }
                }
                if (solution.correct(s).length == 1) {
                    debug(s, solution.correct(s));
                } else {
                    int[] f = solution.correct(s);
                    for (int i = 0; i < f.length; i++) {
                        if (f[i] <= -1 || f[i] >= l) {
                            debug(s, solution.correct(s));
                        }
                    }
                }
            }
            debug("done",l);
        }
        debug("done");
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
