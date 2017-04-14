package gcj.gcj2017.qual;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class B {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            long n = in.nextLong();
            out.println(String.format("Case #%d: %d", cs, solve(n)));
        }
        out.flush();
    }

    private static long solve(long n) {
        char[] digits = String.valueOf(n).toCharArray();
        int failIndex = -1;
        for (int i = 0; i+1 < digits.length ; i++) {
            if (digits[i] > digits[i+1]) {
                failIndex = i;
                break;
            }
        }
        if (failIndex == -1) {
            return n;
        }

        int changeFrom = failIndex;
        while (changeFrom >= 1 && digits[changeFrom] == digits[changeFrom-1]) {
            changeFrom--;
        }
        digits[changeFrom] -= 1;
        for (int l = changeFrom+1 ; l < digits.length ; l++) {
            digits[l] = '9';
        }
        return Long.valueOf(String.valueOf(digits));
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
