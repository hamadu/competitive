package topcoder.srm6xx.srm664;


import java.util.Arrays;

/**
 * Created by hama_du on 15/08/03.
 */
public class BearPlays {
    public int pileSize(int A, int B, int K) {
        long a = Math.min(A, B);
        long mod = A+B;
        long pl = pow(2, K, mod);
        long ans = (a * pl) % mod;
        return (int)Math.min(ans, mod-ans);
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

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    public static void main(String[] args) {
        BearPlays b = new BearPlays();

        System.out.println(b.pileSize(212580698, 108313881, 1596161260));
    }

}
