package gcj2015.qual;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class B {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            int d = in.nextInt();
            int[] p = new int[d];
            for (int i = 0; i < d ; i++) {
                p[i] = in.nextInt();
            }
            out.println(String.format("Case #%d: %d", cs, solve(p)));
        }
        out.flush();
    }

    private static int solve(int[] p) {
        int min = Integer.MAX_VALUE;
        for (int max = 1 ; max <= 1000 ; max++) {
            int d = 0;
            for (int pi : p) {
                if (pi > max) {
                    d += ((pi + max - 1) / max) - 1;
                }
            }
            min = Math.min(min, d+max);
        }
        return min;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



