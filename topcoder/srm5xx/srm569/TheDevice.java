package topcoder.srm5xx.srm569;

/**
 * Created by hama_du on 15/09/04.
 */
public class TheDevice {
    public int minimumAdditional(String[] plates) {
        int m = plates[0].length();
        int max = 0;
        for (int i = 0; i < m ; i++) {
            int one = 0;
            int zero = 0;
            for (String line : plates) {
                if (line.charAt(i) == '0') {
                    zero++;
                } else {
                    one++;
                }
            }
            max = Math.max(max, Math.max(0, 2-one) +Math.max(0, 1-zero));
        }
        return max;
    }
}
