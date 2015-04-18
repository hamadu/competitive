package gcj2015.round1a;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class B {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            int b = in.nextInt();
            long n = in.nextInt();
            long[] time = new long[b];
            for (int i = 0; i < b ; i++) {
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
        while (max - min > 1) {
            long med = (min + max) / 2;
            long done = 0;
            for (int i = 0 ; i < b ; i++) {
                done += (med + time[i] - 1) / time[i];
                if (done >= n) {
                    break;
                }
            }
            if (done >= n) {
                max = med;
            } else {
                min = med;
            }
        }

        long cut = 0;
        for (int i = 0 ; i < b ; i++) {
            cut += ((max-1)+time[i]-1) / time[i];
        }
        for (int i = 0 ; i < b ; i++) {
            if ((max-1) % time[i] == 0) {
                cut++;
                if (cut == n) {
                    return i+1;
                }
            }
        }
        return b;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



