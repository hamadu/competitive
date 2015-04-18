package codeforces.zepto2015;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class C {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        long C = in.nextLong();
        long vr = in.nextLong();
        long vb = in.nextLong();
        long wr = in.nextLong();
        long wb = in.nextLong();
        long ans = 0;
        ans = Math.max(ans, solve(C, vb, vr, wb, wr));
        ans = Math.max(ans, solve(C, vr, vb, wr, wb));

        out.println(ans);
        out.flush();
    }

    private static long solve(long c, long vr, long vb, long wr, long wb) {
        // wr <= wb
        long B = c / wb;
        long left = c - B * wb;
        long A = left / wr;
        left -= A * wr;
        long max = B * vb + A * vr;

        long now = max;
        int ct = 0;
        while (B >= 1 && ct < 10000000) {
            B -= 1;
            now -= vb;
            left += wb;
            long addA = left / wr;
            A += addA;
            now += addA * vr;
            left -= addA * wr;
            ct++;
            max = Math.max(max, now);
        }


        return max;
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



