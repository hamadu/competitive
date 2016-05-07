package atcoder.arc.arc015;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by hama_du on 2016/05/07.
 */
public class B {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] ans = new int[6];
        for (int i = 0; i < n ; i++) {
            double max = in.nextDouble();
            double min = in.nextDouble();
            int[] ret = solve(max, min);
            for (int j = 0; j < 6 ; j++) {
                ans[j] += ret[j];
            }
        }
        for (int i = 0; i < 6; i++) {
            out.print(i >= 1 ? " " : "");
            out.print(ans[i]);
        }
        out.println();
        out.flush();
    }

    private static int[] solve(double max, double min) {
        return new int[]{
                is_mousho(max, min) ? 1 : 0,
                is_manatu(max, min) ? 1 : 0,
                is_natu(max, min) ? 1 : 0,
                is_nettaiya(max, min) ? 1 : 0,
                is_huyu(max, min) ? 1 : 0,
                is_mahuyu(max, min) ? 1 : 0
        };
    }

    private static boolean is_mahuyu(double max, double min) {
        return max < 0;
    }

    private static boolean is_huyu(double max, double min) {
        return min < 0 && max >= 0;
    }

    private static boolean is_nettaiya(double max, double min) {
        return min >= 25;
    }

    private static boolean is_natu(double max, double min) {
        return 25 <= max && max < 30;
    }

    private static boolean is_manatu(double max, double min) {
        return 30 <= max && max < 35;
    }

    private static boolean is_mousho(double max, double min) {
        return max >= 35;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
