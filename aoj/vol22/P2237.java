package aoj.vol22;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by hama_du on 15/07/25.
 */
public class P2237 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        n = in.nextInt();
        m = in.nextInt();
        table = new double[n][m];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                table[i][j] = in.nextDouble();
            }
        }
        memo = new double[1<<n][m];
        for (int i = 0; i < (1 << n); i++) {
            Arrays.fill(memo[i], -1);
        }

        out.println(String.format("%.12f", dfs((1<<n)-1, 0)));
        out.flush();
    }

    static int n;
    static int m;
    static double[][] table;
    static double[][] memo;

    static double dfs(int alive, int idx) {
        if (alive == 0) {
            return 0;
        }
        if (idx == m) {
            return 1;
        }
        if (memo[alive][idx] >= 0) {
            return memo[alive][idx];
        }
        double max = 0;
        for (int i = 0; i < n; i++) {
            if ((alive & (1<<i)) >= 1) {
                double tr = 0;
                double rate = 1.0d;
                for (int j = idx ; j < m; j++) {
                    double win = rate * table[i][j];
                    tr += (rate - win) * dfs(alive^(1<<i), j);
                    rate = win;
                }
                tr += rate;
                max = Math.max(max, tr);
            }
        }
        memo[alive][idx] = max;
        return max;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
