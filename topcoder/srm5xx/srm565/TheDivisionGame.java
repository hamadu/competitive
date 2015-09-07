package topcoder.srm5xx.srm565;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/26.
 */
public class TheDivisionGame {
    public long countWinningIntervals(int L, int R) {
        int[] primes = generatePrimes(32000);

        int d = R-L+1;
        int[] nums = new int[d];
        for (int i = 0; i < d ; i++) {
            nums[i] = L+i;
        }
        int[] cnt = new int[d];
        for (int p : primes) {
            int f = L + (p - L % p) % p;
            int idx = f - L;
            while (idx < d) {
                while (nums[idx] % p == 0) {
                    nums[idx] /= p;
                    cnt[idx]++;
                }
                idx += p;
            }
        }
        for (int i = 0; i < d ; i++) {
            if (nums[i] >= 2) {
                cnt[i]++;
            }
        }

        int[] imos = new int[d+1];
        for (int i = 0; i < d ; i++) {
            imos[i+1] = imos[i] ^ cnt[i];
        }

        long ans = 0;
        long[] prv = new long[128];
        for (int i = 0; i <= d ; i++) {
            ans += prv[imos[i]];
            prv[imos[i]]++;
        }
        return 1L * d * (d + 1) / 2 - ans;
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
