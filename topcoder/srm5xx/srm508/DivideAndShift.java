package topcoder.srm5xx.srm508;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/05.
 */
public class DivideAndShift {
    public int getLeast(int N, int M) {
        p = generatePrimes(1000010);
        memo = new int[1000010];
        Arrays.fill(memo, -1);

        return doit(N, M-1);
    }

    int[] p;
    boolean[] isp;
    int[] memo;

    public int doit(int N, int M) {
        if (memo[N] != -1) {
            return memo[N];
        }
        int best = Math.min(M, N-M);
        if (isp[N]) {
            best = Math.min(best, 1);
        }
        for (int i = 0; p[i] <= N ; i++) {
            if (N % p[i] == 0) {
                int nn = N / p[i];
                best = Math.min(best, doit(nn, M % nn) + 1);
                if (isp[nn]) {
                    best = Math.min(best, doit(p[i], M % p[i]) + 1);
                }
            }
        }
        memo[N] = best;
        return best;
    }

    int[] generatePrimes(int upto) {
        isp = new boolean[upto];
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
