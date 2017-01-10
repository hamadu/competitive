package facebook.fhc2017.qual;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Progress {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = in.nextInt();
        for (int c = 1 ; c <= T ; c++) {
            int p = in.nextInt();
            int x = in.nextInt();
            int y = in.nextInt();
            out.println(String.format("Case #%d: %s", c, solve(p, x, y)));
        }
        out.flush();
    }

    private static String solve(int p, int x, int y) {
        if (p == 0) {
            return "white";
        }
        int d2 = (x-50)*(x-50)+(y-50)*(y-50);
        if (d2 > 2500) {
            return "white";
        }
        if (p == 100) {
            return "black";
        }

        double t2 = Math.atan2(x-50, y-50);
        if (t2 < 0) {
            t2 += Math.PI * 2;
        }
        double per = Math.PI * 2 * p / 100;
        return  (t2 <= per) ? "black" : "white";
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
