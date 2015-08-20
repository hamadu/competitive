package topcoder.srm5xx.srm521;

/**
 * Created by hama_du on 15/08/17.
 */
public class MissingParentheses {
    public int countCorrections(String par) {
        char[] c = par.toCharArray();
        int cnt = 0;
        int deg = 0;
        for (int i = 0; i < c.length ; i++) {
            if (c[i] == '(') {
                deg++;
            } else {
                if (deg <= 0) {
                    cnt += -deg+1;
                    deg = 0;
                } else {
                    deg--;
                }
            }
        }
        cnt += deg;
        return cnt;
    }
}
