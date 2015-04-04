package codeforces.cr292.div1;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class A {
    private static final long MOD = 1_000_000_007;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int[] pr = {2, 3, 5, 7};
        int[] cn = new int[4];
        int n = in.nextInt();
        char[] c = in.next().toCharArray();
        for (int ci : c ) {
            int d = ci - '0';
            for (int x = 2 ; x <= d ; x++) {
                for (int i = 0 ; i < 4 ; i++) {
                    int xi = x;
                    while (xi % pr[i] == 0) {
                        xi /= pr[i];
                        cn[i]++;
                    }
                }
            }
        }

        StringBuilder b = new StringBuilder();

        // 7!
        for (int i = 0 ; i < cn[3] ; i++) {
            cn[0] -= 4;
            cn[1] -= 2;
            cn[2] -= 1;
            b.append('7');
        }

        // 5!
        for (int i = 0 ; i < cn[2] ; i++) {
            cn[0] -= 3;
            cn[1] -= 1;
            b.append('5');
        }

        // 3!
        for (int i = 0 ; i < cn[1] ; i++) {
            cn[0] -= 1;
            b.append('3');
        }

        // 2!
        for (int i = 0 ; i < cn[0] ; i++) {
            b.append('2');
        }

        out.println(b.toString());
        out.flush();
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



