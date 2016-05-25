package topcoder.srm6xx.srm669.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 15/10/04.
 */
public class LineMST {
    private static final long MOD = 1000000007;

    public long[][] memo;

    public int L;

    public long dfs(int n, int upto) {
        if (upto < 0) {
            return 0;
        }
        if (memo[n][upto] != -1) {
            return memo[n][upto];
        }
        long ret = 0;
        if (n == 1) {
            ret = 1;
        } else if (n == 2) {
            ret = upto;
        } else if (n >= 3) {
            ret = dfs(n, upto-1);
            for (int pos = 1; pos < n; pos++) {
                long left = dfs(pos, upto-1);
                long right = dfs(n-pos, upto);
                long between = pow(L-upto+1, pos*(n-pos)-1);
                ret += left * right % MOD * between % MOD;
            }
            ret %= MOD;
        }
        memo[n][upto] = ret;
        return ret;
    }

    static long pow(long a, long x) {
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

    public int count(int N, int L) {
        memo = new long[N+1][L+1];
        this.L = L;
        for (int i = 0; i <= N; i++) {
            Arrays.fill(memo[i], -1);
        }

        long perm = 1;
        for (int i = 3 ; i <= N ; i++) {
            perm *= i;
            perm %= MOD;
        }
        return (int)((dfs(N, L) * perm) % MOD);
    }

    public static void main(String[] args) {
        LineMST mst = new LineMST();
        debug(mst.count(200, 200));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    // 8,41
    // 655468587

    // 200,200
    // 152699064
}
