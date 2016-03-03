package topcoder.srm6xx.srm683.div1;

import java.math.BigInteger;
import java.util.Arrays;

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
        BigInteger sum = BigInteger.ZERO;
        long gcd = num[0];
        BigInteger lcm = BigInteger.valueOf(num[0]);
        for (int i = 1 ; i < n ; i++) {
            gcd = gcd(gcd, num[i]);
            BigInteger bgcd = gcd(lcm, BigInteger.valueOf(num[i]));
            sum = sum.add(bgcd);
            sum = sum.mod(BigInteger.valueOf(MOD));
            lcm = lcm.multiply(BigInteger.valueOf(num[i]));
            lcm = lcm.divide(bgcd);
        }
        BigInteger lw = lcm.mod(BigInteger.valueOf(MOD));
        return (int)((sum.longValue() + lw.longValue() + gcd) % MOD);
    }

    private long gcd(long a, long b) {
        return (b == 0) ? a : gcd(b, a%b);
    }

    private BigInteger gcd(BigInteger a, BigInteger b) {
        return (b.compareTo(BigInteger.ZERO) == 0) ? a : gcd(b, a.mod(b));
    }

    public static void main(String[] args) {
        GCDLCM2 solution = new GCDLCM2();
        debug(solution.solve(new long[]{2,3,4,5}));
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
