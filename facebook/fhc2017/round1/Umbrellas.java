package facebook.fhc2017.round1;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Umbrellas {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        prec(1000000);

        int T = in.nextInt();
        for (int c = 1 ; c <= T ; c++) {
            int n = in.nextInt();
            int m = in.nextInt();
            int[] r = new int[n];
            for (int i = 0; i < n ; i++) {
                r[i] = in.nextInt();
            }
            out.println(String.format("Case #%d: %d", c, solve(m, r)));
        }
        out.flush();
    }

    private static long solve(int M, int[] R) {
        int n = R.length;
        if (n == 1) {
            return M % MOD;
        }
        M--;

        int sumR = 0;
        for (int i = 0; i < R.length; i++) {
            sumR += R[i] * 2;
        }

        int[] q = new int[4000000];
        int qn = 0;

        for (int i = 0; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                int M2 = M + R[i] + R[j];
                int left = M2 - sumR;
                if (left >= 0) {
                    q[qn++] = left;
                }
            }
        }

        if (qn == 0) {
            return 0;
        }

        Arrays.sort(q, 0, qn);

        int min = q[0];
        int max = q[qn-1];
        int[] deg = new int[max-min+10];
        for (int x = 0 ; x < qn ; x++) {
            deg[q[x]-min]++;
        }

        long ans = 0;
        for (int i = 0 ; i < deg.length ; i++) {
            if (deg[i] >= 1) {
                ans += deg[i]*comb(i+min+n, n) % MOD;
                ans %= MOD;
            }
        }
        ans *= _fact[n-2];
        ans %= MOD;
        ans *= 2;
        ans %= MOD;
        return ans;
    }

    static final int MOD = 1000000007;

    static long pow(long a, long x) {
        long res = 1;
        while (x > 0) {
            if (x%2 != 0) {
                res = (res*a)%MOD;
            }
            a = (a*a)%MOD;
            x /= 2;
        }
        return res;
    }

    static long inv(long a) {
        return pow(a, MOD-2)%MOD;
    }

    static long[] _fact;
    static long[] _invfact;

    static long comb(int ln, int lr) {
        if (ln < lr) {
            return 0;
        }
        long ret = 1;
        for (int i = ln ; i > ln-lr ; i--) {
            ret *= i;
            ret %= MOD;
        }
        ret *= _invfact[lr];
        ret %= MOD;
        return ret;
    }

    static void prec(int n) {
        _fact = new long[n+1];
        _invfact = new long[n+1];
        _fact[0] = 1;
        _invfact[0] = 1;
        for (int i = 1; i <= n; i++) {
            _fact[i] = _fact[i-1]*i%MOD;

        }
        _invfact[n] = inv(_fact[n]);
        for (int i = n-1 ; i >= 1 ; i--) {
            _invfact[i] = (_invfact[i+1] * (i+1)) % MOD;
        }
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
