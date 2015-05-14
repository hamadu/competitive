package codechef.long201505;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by hama_du on 15/05/09.
 */
public class CHEFRP {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int T = in.nextInt();
        while (--T >= 0) {
            int n = in.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n ; i++) {
                a[i] = in.nextInt();
            }
            out.println(solve(a));
        }
        out.flush();
    }

    private static int solve(int[] a) {
        int n = a.length;
        int min = Integer.MAX_VALUE;
        int sum = 0;
        for (int i = 0; i < n ; i++) {
            min = Math.min(min, a[i]);
            sum += a[i];
        }
        if (min <= 1) {
            return -1;
        }
        return sum - min + 2;
    }
}
