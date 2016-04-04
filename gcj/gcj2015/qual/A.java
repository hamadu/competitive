package gcj.gcj2015.qual;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class A {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            in.nextInt();
            char[] c = in.next().toCharArray();
            out.println(String.format("Case #%d: %d", cs, solve(c)));
        }
        out.flush();
    }

    private static int solve(char[] c) {
        int n = c.length;
        int sum = 0;
        int invite = 0;
        for (int i = 0 ; i < n ; i++) {
            if (sum < i) {
                invite += i - sum;
                sum = i;
            }
            sum += c[i] - '0';
        }
        return invite;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



