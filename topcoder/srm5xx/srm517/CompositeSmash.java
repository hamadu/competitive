package topcoder.srm5xx.srm517;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/14.
 */
public class CompositeSmash {
    private static final String YES = "Yes";
    private static final String NO = "No";

    public String thePossible(int N, int target) {
        if (N == target) {
            return YES;
        }
        if (N % target != 0) {
            return NO;
        }

        int[] p = generatePrimes(1000000);
        int cnt = 0;
        int kl = 0;
        for (int pl : p) {
            int ct = 0;
            while (target % pl == 0) {
                ct++;
                target /= pl;
            }
            if (ct >= 1) {
                kl = ct;
                cnt++;
            }
        }

        int nl = 0;
        for (int pl : p) {
            int ct = 0;
            while (N % pl == 0) {
                ct++;
                N /= pl;
            }
            if (ct >= 1) {
                nl++;
            }
        }
        if (cnt >= 2) {
            return NO;
        }
        if (nl >= 2) {
            return kl == 1 ? YES : NO;
        }
        return kl <= 2 ? YES : NO;
    }

    static int[] generatePrimes(int upto) {
        boolean[] isp = new boolean[upto];
        Arrays.fill(isp, true);
        isp[0] = isp[1] = false;

        int pi = 0;
        for (int i = 2; i < upto ; i++) {
            if (isp[i]) {
                pi++;
                for (int j = i * 2; j < upto; j += i) {
                    isp[j] = false;
                }
            }
        }

        int[] ret = new int[pi];
        int ri = 0;
        for (int i = 2 ; i < upto ; i++) {
            if (isp[i]) {
                ret[ri++] = i;
            }
        }
        return ret;
    }
}
