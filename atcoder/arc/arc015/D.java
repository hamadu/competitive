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
public class D {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = in.nextInt();
        int N = in.nextInt();
        double P = in.nextDouble();
        double[] tbl = new double[100010];
        tbl[0] = 1 - P;
        for (int i = 0; i < N; i++) {
            double q = in.nextDouble();
            int x = in.nextInt();
            double mul = q * P * x;
            double one = q * P;
            int ti = in.nextInt();
            tbl[0] += mul;
            tbl[ti] -= mul;
            tbl[ti] += one;
        }
        for (int i = 1 ; i < tbl.length ; i++) {
            tbl[i] += tbl[i-1];
        }

        double[] logtbl = new double[tbl.length];
        for (int i = 0; i < tbl.length ; i++) {
            logtbl[i] = Math.log(tbl[i]);
        }
        double logsum = 0;
        double ans = 0;
        for (int i = 0 ; i < T ; i++) {
            ans += Math.exp(logsum);
            logsum += logtbl[i];
        }

        out.println(String.format("%.9f", ans));
        out.flush();
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
