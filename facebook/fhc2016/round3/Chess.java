package facebook.fhc2016.round3;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by hama_du on 2016/01/31.
 */
public class Chess {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        memo = new double[10001][2];


        int T = in.nextInt();
        for (int c = 1 ; c <= T ; c++) {
            int n = in.nextInt();
            double ww = in.nextDouble();
            double wb = in.nextDouble();
            double lw = in.nextDouble();
            double lb = in.nextDouble();
            out.println(String.format("Case #%d: %.9f", c, solve(n, ww, wb, lw, lb)));
        }
        out.flush();
    }

    private static double solve(int n, double _ww, double _wb, double _lw, double _lb) {
        n = Math.min(1000 + n % 2, n);
        ww = _ww;
        wb = _wb;
        lw = _lw;
        lb = _lb;
        for (int i = 0; i <= n ; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(n, 0);
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }


    static double[][] memo;
    static double ww, wb, lw, lb;

    private static double dfs(int left, int color) {
        if (left == 1) {
            return color == 0 ? ww : wb;
        }
        if (memo[left][color] != -1) {
            return memo[left][color];
        }

        double nextWhite = dfs(left-1, 0);
        double nextBlack = dfs(left-1, 1);

        double max = 0.0d;
        for (int f = 0 ; f <= 1 ; f++) {
            double min = 1.0;
            for (int g = 0; g <= 1; g++) {
                if (f == 0) {
                    double winRate = color == 0 ? ww : wb;
                    if (g == 1) {
                        winRate = 1.0d;
                    }
                    min = Math.min(min, winRate * Math.min(nextWhite, nextBlack) + (1.0d - winRate) * Math.max(nextWhite, nextBlack));
                } else {
                    double loseRate = color == 0 ? lw : lb;
                    if (g == 0) {
                        loseRate = 1.0d;
                    }
                    min = Math.min(min, loseRate * Math.max(nextWhite, nextBlack) + (1.0d - loseRate) * Math.min(nextWhite, nextBlack));
                }
            }
            max = Math.max(max, min);
        }
        memo[left][color] = max;
        return max;
    }



}
