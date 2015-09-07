package topcoder.srm5xx.srm534;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hama_du on 15/08/23.
 */
public class EllysNumbers {
    public long getSubsets(long n, String[] special) {
        long[] sp = build(special);
        int m = sp.length;

        int[] ps = generatePrimes(32000);
        Map<Integer,Integer> pmap = new HashMap<>();
        for (int i = 0; i < m ; i++) {
            int S = (int)sp[i];
            if (n % S != 0) {
                continue;
            }
            for (int p : ps) {
                if (S % p == 0) {
                    if (!pmap.containsKey(p)) {
                        pmap.put(p, pmap.size());
                    }
                    while (S % p == 0) {
                        S /= p;
                    }
                }
            }
            if (S != 1) {
                if (!pmap.containsKey(S)) {
                    pmap.put(S, pmap.size());
                }
            }
        }

        int pn = pmap.size();
        long[] base = new long[pn];
        long[] want = new long[pn];
        long NN = n;
        for (int pi : pmap.keySet()) {
            long v = pi;
            int idx = pmap.get(pi);
            want[idx] = 1;
            base[idx] = v;
            while (NN % v == 0) {
                NN /= v;
                want[idx] *= v;
            }
        }
        if (NN != 1) {
            return 0;
        }

        int mi = 0;
        int[] mask = new int[m];
        for (int i = 0; i < m ; i++) {
            if (n % sp[i] == 0) {
                int msk = 0;
                boolean isValid = true;
                for (int k = 0; k < pn ; k++) {
                    if (sp[i] % base[k] == 0 && sp[i] % want[k] != 0) {
                        isValid = false;
                        break;
                    }
                    if (sp[i] % base[k] == 0) {
                        msk |= 1<<k;
                    }
                }
                if (isValid) {
                    mask[mi++] = msk;
                }
            }
        }

        long[][] dp = new long[2][1<<pn];
        dp[0][0] = 1;
        for (int i = 0; i < mi ; i++) {
            int fr = i % 2;
            int to = 1 - fr;
            Arrays.fill(dp[to], 0);
            for (int j = 0; j < (1<<pn); j++) {
                if (dp[fr][j] == 0) {
                    continue;
                }
                dp[to][j] += dp[fr][j];
                if ((j & mask[i]) == 0) {
                    dp[to][j|mask[i]] += dp[fr][j];
                }
            }
        }
        return dp[mi%2][(1<<pn)-1];
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

    private long[] build(String[] special) {
        String s = "";
        for (String l : special) {
            s += l;
        }
        String[] nums = s.split(" ");
        long[] ret = new long[nums.length];
        for (int i = 0; i < ret.length ; i++) {
            ret[i] = Long.valueOf(nums[i]);
        }
        return ret;
    }

    public static void main(String[] args) {
        EllysNumbers en = new EllysNumbers();
        debug(en.getSubsets(499999924000000966L, new String[]{"2 499999931 499999993 999999862 999999986"}));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
