package topcoder.srm6xx.srm664.div1;

/**
 * Created by hama_du on 2016/05/28.
 */
public class BearPlays {
    public int pileSize(int A, int B, int K) {
        long sum = A+B;
        long p2 = pow(2, K, sum);
        long la = A * p2 % sum;
        return (int)Math.min(la, sum-la);
    }

    static long pow(long a, long x, long MOD) {
        long res = 1;
        while (x > 0) {
            if (x % 2 != 0) {
            res = (res * a) % MOD;
            }
            a = (a * a) % MOD;
            x /= 2;
        }
        return res;
    }
}
