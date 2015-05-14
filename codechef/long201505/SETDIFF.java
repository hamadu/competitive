package codechef.long201505;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by hama_du on 15/05/09.
 */
public class SETDIFF {
    static final long MOD = 1000000007;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int T = in.nextInt();
        while (--T >= 0) {
            int n = in.nextInt();
            long[] a = new long[n];
            for (int i = 0; i < n ; i++) {
                a[i] = in.nextInt();
            }
            out.println(solve(a));
        }
        out.flush();
    }

    private static long solve(long[] a) {
        long[] p2 = new long[200000];
        p2[0] = 1;
        for (int i = 1 ; i < p2.length ; i++) {
            p2[i] = (p2[i-1]<<1) % MOD;
        }

        int n = a.length;
        Arrays.sort(a);

        long maxSum = 0;
        long minSum = 0;
        for (int i = 0; i < n ;) {
            int ti = i;
            while (ti < n && a[ti] == a[i]) {
                ti++;
            }
            int small = i;
            int same = ti - i;
            int large = n - small - same;

            long maxAdd = (((p2[same] - 1 + MOD) % MOD) * p2[small]) % MOD;
            maxAdd *= a[i];
            maxAdd %= MOD;
            maxSum += maxAdd;
            maxSum %= MOD;

            long minAdd = (((p2[same] - 1 + MOD) % MOD) * p2[large]) % MOD;
            minAdd *= a[i];
            minAdd %= MOD;
            minSum += minAdd;
            minSum %= MOD;

            i = ti;
        }
        return (maxSum - minSum + MOD) % MOD;
    }
}
