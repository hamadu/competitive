package topcoder.srm6xx.srm669;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hama_du on 15/10/07.
 */
public class PowerPartition {
    private static final long MOD = 1000000007;

    public int count(int M, long X) {
        List<Long> iori = new ArrayList<>();
        iori.add(1L);
        while (iori.get(iori.size()-1) * M <= X) {
            iori.add(iori.get(iori.size()-1) * M);
        }

        int in = iori.size();
        long[][] yayoi = E(in);
        for (int i = in-1 ; i >= 0 ; i--) {
            long[][] yayo = buildYayo(iori, iori.get(i));
            long cnt = X / iori.get(i);
            yayoi = mul(yayoi, pow(yayo, cnt, MOD), MOD);
            X %= iori.get(i);
        }
        long ans = 0;
        for (int i = 0; i < in ; i++) {
            for (int j = 0; j < in ; j++) {
                ans += yayoi[i][j];
            }
        }
        return (int)(ans % MOD);
    }

    long[][] buildYayo(List<Long> iori, long M) {
        int in = iori.size();
        long[][] yayo = new long[in][in];

        // yayoi[max][last] := ways
        // max < last := 0
        yayo[0][0] = 1;

        // [max][last] = [max-1][last]
        // use n time



        return yayo;
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

}
