package gcj2015.round1a;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class A {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            int b = in.nextInt();
            long n = in.nextInt();
            long[] time = new long[b];
            for (int i = 0; i < n ; i++) {
                time[i] = in.nextInt();
            }
            int ret = solve(time, n);
            out.println(String.format("Case #%d: %d", cs, ret));
        }
        out.flush();
    }

    private static int solve(long[] time, long n) {
        int b = time.length;


        long min = 0;
        long max = Long.MAX_VALUE / 20;
        while (max - min > 0) {
            long med = (min + max) / 2;
            long cut = 0;


            long done = 0;
            for (int i = 0 ; i < b ; i++) {
                done += med / time[i];
            }
            if (done >= n-1) {
                max = med;
            } else {
                min = med;
            }
        }

        long pt = max;
        for (int i = 0 ; i < b ; i++) {
            if (pt % time[i] == 0) {
                return i+1;
            }
        }
        return b;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



