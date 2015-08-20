package topcoder.srm5xx.srm529;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/19.
 */
public class MinskyMystery {
    private static final long MOD = 1000000009;

    public int computeAnswer(long N) {
        if (N <= 1) {
            return -1;
        }
        long div = N;
        for (int i = 2 ; i <= 1000000; i++) {
            if (N % i == 0) {
                div = i;
                break;
            }
        }
        long modN = N % MOD;
        long ans = (((((div - 2) * 4) % MOD) * modN) % MOD + (3 * modN) % MOD + div % MOD) % MOD;
        ans %= MOD;
        ans += (N / div) % MOD;
        ans %= MOD;

        long now = div;
        for (long d = (N / div) + 1 ;  ; d++) {
            long to = (N + d - 1) / d;
            long cnt = now - to;
            if (cnt <= 0 || to <= 1) {
                for (long f = 2 ; f < now ; f++) {
                    ans += ((N + f - 1) / f) % MOD;
                    ans %= MOD;
                }
                break;
            } else {
                ans += ((d % MOD) * (cnt % MOD)) % MOD;
                ans %= MOD;
                now = to;
            }
        }
        return (int)ans;
    }

    public static void main(String[] args) {
        MinskyMystery mm = new MinskyMystery();
        debug(mm.computeAnswer(999663912463L));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
