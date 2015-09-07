package topcoder.srm5xx.srm546;

/**
 * Created by hama_du on 15/08/27.
 */
public class FleaCircus {
    static final long MOD = 1000000009;

    public int countArrangements(String[] afterFourClicks) {
        int[] perm = gen(afterFourClicks);
        int n = perm.length;
        return 0;
    }

    private int[] gen(String[] afterFourClicks) {
        String line = "";
        for (String a : afterFourClicks) {
            line += a;
        }
        String[] part = line.split(" ");
        int[] ret = new int[part.length];
        for (int i = 0; i < part.length; i++) {
            ret[i] = Integer.valueOf(part[i]);
        }
        return ret;
    }
}
