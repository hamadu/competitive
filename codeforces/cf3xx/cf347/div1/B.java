package codeforces.cf3xx.cf347.div1;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class B {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        in.nextLine();
        for (int i = 0; i < n ; i++) {
            String suffix = in.nextLine().split("'")[1];
            long Y = Long.valueOf(suffix);
            int l = suffix.length();
            long pw = (long)Math.pow(10L, l);
            long pk = 0;
            for (int j = 1 ; j < l; j++) {
                pk += Math.pow(10L, j);
            }
            while (Y < 1989+pk) {
                Y += pw;
            }
            out.println(Y);
        }
        out.flush();
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
