package topcoder.srm6xx.srm697.div1;

/**
 * Created by hama_du
 */
import java.util.Arrays;

public class DivisibleSetDiv1 {
    public String isPossible(int[] b) {
        int n = b.length;
        long[] deg = new long[11];
        for (int i = 0 ; i < n ; i++) {
            deg[b[i]]++;
        }

        for (int i = 1 ; i <= 10 ; i++) {
            long cnt = deg[i];
            if (cnt == 0) {
                continue;
            }
            boolean isOK = false;
            for (long min = 1 ; min <= 100000 ; min++) {
                long have = min * i;
                long req = (min+1+min+cnt-1)*(cnt-1)/2;
                if (have < req) {
                    continue;
                }
                long amari = have - req;
                long add = 0;
                for (int ti = 1 ; ti <= 10 ; ti++) {
                    if (i == ti || deg[ti] == 0) {
                        continue;
                    }
                    add += deg[ti] * (have + min + ti) / (ti + 1);
                }
                if (add <= amari) {
                    isOK = true;
                    break;
                }
            }
            if (!isOK) {
                return "Impossible";
            }
        }
        return "Possible";
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}