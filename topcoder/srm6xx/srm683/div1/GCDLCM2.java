package topcoder.srm6xx.srm683.div1;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hama_du on 2016/02/29.
 */
public class GCDLCM2 {
    private static final long MOD = 1000000007;

    public int getMaximalSum(int[] start, int[] d, int[] cnt) {
        int n = 0;
        for (int c : cnt) {
            n += c;
        }
        long[] num = new long[n];
        int ni = 0;
        for (int i = 0 ; i < start.length ; i++) {
            for (long j = 0 ; j <= cnt[i]-1 ; j++) {
                num[ni++] = start[i] + j * d[i];
            }
        }
        Arrays.sort(num);

        return solve(num);
    }

    private int solve(long[] num) {
        int n = num.length;
        int[] pr = generatePrimes(3200);

        long[] table = new long[n];
        Arrays.fill(table, 1);

        for (int p : pr) {
            int[] ctmap = new int[50];
            for (int i = 0; i < n ; i++) {
                int ct = 0;
                while (num[i] % p == 0) {
                    num[i] /= p;
                    ct++;
                }
                ctmap[ct]++;
            }
            long pw = 1;
            int head = ctmap[0];
            for (int i = 1 ; i < 50 ; i++) {
                pw *= p;
                pw %= MOD;
                for (int idx = 0 ; idx < ctmap[i] ; idx++) {
                    table[head] *= pw;
                    table[head] %= MOD;
                    head++;
                }
            }
        }


        Map<Long,Integer> leftCnt = new HashMap<>();
        for (int i = 0; i < n ; i++) {
            if (num[i] >= 2) {
                leftCnt.put(num[i], leftCnt.getOrDefault(num[i], 0)+1);
            }
        }
        // debug(leftCnt);
        for (long k : leftCnt.keySet()) {
            int val = leftCnt.get(k);
            for (int idx = n-1 ; idx >= n-val ; idx--) {
                table[idx] *= k;
                table[idx] %= MOD;
            }
        }
        long ret = 0;
        for (int i = 0; i < n ; i++) {
            ret += table[i];
        }
        return (int)(ret % MOD);
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

    public static void main(String[] args) {
        GCDLCM2 solution = new GCDLCM2();
        debug(solution.getMaximalSum(new int[]{5,6}, new int[]{23,45}, new int[]{50000, 50000}));
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
