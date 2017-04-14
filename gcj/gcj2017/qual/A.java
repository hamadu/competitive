package gcj.gcj2017.qual;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class A {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            char[] pancakes = in.next().toCharArray();
            int K = in.nextInt();
            out.println(String.format("Case #%d: %s", cs, solve(pancakes, K)));
        }
        out.flush();
    }

    private static String solve(char[] c, int k) {
        int n = c.length;

        boolean[] isHappy = new boolean[n];
        for (int i = 0; i < n ; i++) {
            isHappy[i] = c[i] == '+';
        }

        int operations = 0;
        for (int i = 0 ; i <= c.length-k ; i++) {
            if (!isHappy[i]) {
                operations++;
                for (int j = i; j < i+k; j++) {
                    isHappy[j] = !isHappy[j];
                }
            }
        }

        for (int i = 0; i < n ; i++) {
            if (!isHappy[i]) {
                return "IMPOSSIBLE";
            }
        }
        return operations + "";
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



