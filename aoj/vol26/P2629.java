package aoj.vol26;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by hama_du on 15/07/11.
 */
public class P2629 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        double d = in.nextDouble();
        double max = d;
        for (int dy = 1 ; dy <= 10 ; dy++) {
            double dx2 = d * d - dy * dy;
            if (dx2 < 0) {
                break;
            }
            double dx = Math.sqrt(dx2);
            double cost = (dx <= 1) ? dy + 1 : dy + dx;
            max = Math.max(max, cost);
        }

        double half = Math.sqrt(d * d / 2) * 2;
        max = Math.max(max, half);

        out.println(max);
        out.flush();
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
