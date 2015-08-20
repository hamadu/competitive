package topcoder.srm5xx.srm529;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/19.
 */
public class BigAndSmallAirports {
    public long solve(int Nlo, int Nhi, int Blo, int Bhi, int Slo, int Shi) {
        long ans = 0;
        for (int s = Slo ; s <= Shi; s++) {
            for (int b = Math.max(s+1, Blo) ; b <= Bhi; b++) {
                int minN = b+1;
                if (Nhi < minN) {
                    if (s >= 2) {
                        ans += Nhi - Nlo + 1;
                    } else {
                        if (Nlo == 1) {
                            ans++;
                        }
                    }
                    continue;
                }



                long min = Math.max(Nlo, minN);
                long max = Nhi;
                debug(min,max);
                if (s >= 2) {
                    min++;
                    max++;
                }

                ans += (min + max) * (max - min + 1) / 2;
            }
        }
        return ans;
    }

    public static void main(String[] args) {

        BigAndSmallAirports airports = new BigAndSmallAirports();
        debug(airports.solve(10, 10, 8, 8, 3, 3));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
