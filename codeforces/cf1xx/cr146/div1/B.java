package codeforces.cf1xx.cr146.div1;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by hama_du on 15/09/10.
 */
public class B {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        double[] p = new double[n];
        for (int i = 0; i < n ; i++) {
            p[i] = in.nextDouble();
        }
        double sum = 0;
        double L = 0;
        for (int i = 0; i < n ; i++) {
            L *= p[i];
            L += p[i];
            sum += L;
        }
        L = 0;
        for (int i = n-1; i >= 0 ; i--) {
            L *= p[i];
            L += p[i];
            sum += L;
        }
        for (int i = 0; i < n ; i++) {
            sum -= p[i];
        }
        out.println(sum);
        out.flush();
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
