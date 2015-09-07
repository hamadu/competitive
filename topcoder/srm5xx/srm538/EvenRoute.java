package topcoder.srm5xx.srm538;

/**
 * Created by hama_du on 15/08/24.
 */
import java.util.Arrays;

public class EvenRoute {

    public String isItPossible(int[] x, int[] y, int wantedParity) {
        int n = x.length;
        boolean found = false;
        int done = 0;
        int px = 0;
        int py = 0;
        for (int i = 0 ; i < n ; i++) {
            if (wantedParity == (Math.abs(x[i]) + Math.abs(y[i])) % 2) {
                found = true;
            }
        }
        return found ? "CAN" : "CANNOT";
    }

    public static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
}