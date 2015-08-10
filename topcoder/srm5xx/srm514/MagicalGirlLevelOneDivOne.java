package topcoder.srm5xx.srm514;

/**
 * Created by hama_du on 15/08/08.
 */
public class MagicalGirlLevelOneDivOne {
    public String isReachable(int[] jumpTypes, int x, int y) {
        long d = Math.abs(x) + Math.abs(y);
        if (d % 2 == 0) {
            return "YES";
        }
        for (int k : jumpTypes) {
            if (k % 2 == 0) {
                return "YES";
            }
        }
        return "NO";
    }
}
