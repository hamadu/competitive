package gcj.gcj2014.round3;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Created by hama_du on 2016/06/10.
 */
public class A {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            int n = in.nextInt();
            int p = in.nextInt();
            int q = in.nextInt();
            int r = in.nextInt();
            int s = in.nextInt();
            long[] devices = build(n, p, q, r, s);

            out.println(String.format("Case #%d: %.9f", cs, solve(devices)));
        }
        out.flush();
    }

    private static double solve(long[] devices) {
        int n = devices.length;
        long[] imos = new long[n+1];
        for (int i = 1 ; i <= n ; i++) {
            imos[i] = imos[i-1] + devices[i-1];
        }

        long best = 0;
        int l = 0;
        int r = 0;
        while (l < n) {
            // C < R
            if (r < n && imos[r] - imos[l] < imos[n] - imos[r]) {
                r++;
            } else {
                l++;
            }
            long max = Math.max(imos[l], Math.max(imos[r]-imos[l], imos[n]-imos[r]));
            best = Math.max(best, imos[n] - max);
        }
        return 1.0d * best / imos[n];
    }

    private static long[] build(int n, int p, int q, int r, int s) {
        long[] res = new long[n];
        for (int i = 0; i < n ; i++) {
            res[i] = ((long)i * p + q) % r + s;
        }
        return res;
    }
}
