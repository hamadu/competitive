package topcoder.srm6xx.srm666.div1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by hama_du on 2016/05/28.
 */
public class CountBinarySequences {
    private static final long MOD = (long)1e9+7;

    public int countSequences(int n, int k, int[] l, int[] r) {
        int m = l.length;
        k++;
        N = n;
        M = m;
        K = k;
        L = l;
        R = r;
        tbl = new long[k*2][k*2];
        for (int i = 0; i < k ; i++) {
            tbl[i][0] = 1;
            tbl[k+i][k] = 1;
        }
        for (int i = 0; i < k-1 ; i++) {
            tbl[i][k+i+1] = 1;
            tbl[k+i][i+1] = 1;
        }

        par = new int[m];
        Arrays.fill(par, m);
        for (int i = 0; i < m ; i++) {
            int smallest = n+1;
            for (int j = 0; j < m ; j++) {
                int d = R[j] - L[j] + 1;
                if (L[j] <= L[i] && R[i] <= R[j] && smallest > d && i != j) {
                    smallest = d;
                    par[i] = j;
                }
            }
        }

        memo = new long[m+1][][];
        long[][] f = new long[1][K*2];
        f[0][0] = 1;
        long[][] ans = mul(f, solve(m), MOD);
        long ret = 0;
        for (int i = 0; i < K*2 ; i++) {
            ret += ans[0][i];
        }
        return (int)(ret % MOD);
    }


    int N;
    int M;
    int K;
    int[] L;
    int[] R;
    int[] par;
    long[][][] memo;
    long[][] solve(int now) {
        if (memo[now] != null) {
            return memo[now];
        }
        memo[now] = E(2*K);
        List<int[]> cl = new ArrayList<>();
        for (int to = 0 ; to < par.length ; to++) {
            if (par[to] != now) {
                continue;
            }
            cl.add(new int[]{to, L[to], R[to]});
        }
        Collections.sort(cl, (o1, o2) -> o1[1] - o2[1]);
        int left = now == M ? 1 : L[now];
        for (int[] tw : cl) {
            int ld = tw[1] - left;
            memo[now] = mul(memo[now], step(ld), MOD);
            memo[now] = mul(memo[now], solve(tw[0]), MOD);
            left = tw[2] + 1;
        }
        memo[now] = mul(memo[now], step((now == M ? N : R[now]) - left + 1), MOD);
        if (now != M) {
            memo[now] = filterOdd(memo[now]);
        }
        return memo[now];
    }

    long[][] tbl;

    public long[][] filterOdd(long[][] l) {

        long[][] ee = new long[2*K][2*K];
        for (int i = 0; i < 2*K ; i++) {
            for (int j = 0; j < 2*K ; j++) {
                if ((i < K && j < K) || (i >= K && j >= K)) {
                    ee[i][j] = l[i][j];
                }
            }
        }
        return ee;
    }
    
    public long[][] step(int length) {
        return pow(tbl, length, MOD);
    }

    public static long[][] pow(long[][] a, long n, long mod) {
        long i = 1;
        long[][] res = E(a.length);
        long[][] ap = mul(E(a.length), a, mod);
        while (i <= n) {
            if ((n & i) >= 1) {
                res = mul(res, ap, mod);
            }
            i *= 2;
            ap = mul(ap, ap, mod);
        }
        return res;
    }

    public static long[][] E(int n) {
        long[][] a = new long[n][n];
        for (int i = 0 ; i < n ; i++) {
            a[i][i] = 1;
        }
        return a;
    }

    public static long[][] mul(long[][] a, long[][] b, long mod) {
        long[][] c = new long[a.length][b[0].length];
        if (a[0].length != b.length) {
            System.err.print("err");
        }
        for (int i = 0 ; i < a.length ; i++) {
            for (int j = 0 ; j < b[0].length ; j++) {
                long sum = 0;
                for (int k = 0 ; k < a[0].length ; k++) {
                    sum = (sum + a[i][k] * b[k][j]) % mod;
                }
                c[i][j] = sum;
            }
        }
        return c;
    }

    public static void main(String[] args) {
        CountBinarySequences binarySequences = new CountBinarySequences();

        // 5
        // debug(binarySequences.countSequences(4, 2, new int[]{2}, new int[]{3}));
        // 444743885
        // debug(binarySequences.countSequences(1000, 4, new int[]{10, 101, 201, 110, 121}, new int[]{100, 200, 300, 120, 130}));
        // 420889003
        //debug(binarySequences.countSequences(998, 2,
        //        new int[]{313,365,189,913,334,360,160,931,313,402,389,328,376,47,906,383,381,927,338,178,934,933,162,332,191,188,380,912,970,360,161,179,966,405,971,381},
        //        new int[]{313,365,189,913,334,362,160,931,340,405,389,329,398,425,907,383,382,927,338,178,934,933,162,332,191,188,384,912,970,365,161,179,966,405,971,381})
        //);
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
