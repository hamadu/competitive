package topcoder.srm6xx.srm697.div1;

/**
 * Created by hama_du
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XorOrderDiv1 {
    private static final long MOD = (long) 1e9+7;

    public int get(int m, int n, int a0, int b) {
        int[] a = new int[n];
        for (int i = 0; i < n ; i++) {
            a[i] = (int)((a0+((long)i)*b) % (1<<m));
        }
        List<Integer> ai = new ArrayList<>();
        for (int i = 0; i < n ; i++) {
            ai.add(a[i]);
        }
        ans = 0;
        mul1 = ((1<<m)/2) % MOD;
        mul2 = ((1<<m)/4) % MOD;
        dfs(ai, 1<<(m-1), 0, 0, 0);



        return (int)ans;
    }

    static long mul1, mul2;
    static long ans;

    static void dfs(List<Integer> a, int mask, long n2, long n1, long npair) {
        int n = a.size();
        if (n == 0) {
            //
        } else if (n == 1) {
            long q = ((n2 * mul1 % MOD) + (npair * mul2 % MOD)) % MOD;
            ans ^= q;
        } else {
            List<Integer> zero = new ArrayList<>();
            List<Integer> one = new ArrayList<>();
            for (int i = 0 ; i < n ; i++) {
                if ((a.get(i) & mask) == 0) {
                    zero.add(a.get(i));
                } else {
                    one.add(a.get(i));
                }
            }
            for (List<Integer> ze : new List[]{zero, one}) {
                long on = n - ze.size();
                dfs(ze, mask>>1, (n2+on*on)%MOD, (n1+on)%MOD, (npair+2*(n1*on))%MOD);
            }
        }
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
