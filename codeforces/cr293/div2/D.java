package codeforces.cr293.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by hama_du on 15/08/01.
 */
public class D {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        double d = in.nextDouble();
        int t = in.nextInt();

        double[][] dp = new double[t+1][n+1];
        dp[0][0] = 1.0d;
        for (int i = 0; i < t; i++) {
            for (int c = 0; c <= n; c++) {
                if (dp[i][c] == 0) {
                    continue;
                }
                double base = dp[i][c];
                if (c < n) {
                    dp[i+1][c+1] += base * d;
                    dp[i+1][c] += base * (1.0d - d);
                } else {
                    dp[i+1][c] += base;
                }
            }
        }

        double exp = 0;
        for (int c = 0; c <= n; c++) {
            exp += dp[t][c] * c;
        }
        out.println(String.format("%.12f", exp));
        out.flush();
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
