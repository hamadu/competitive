package facebook.fhc2017.round1;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Pie {
    private static final long INF = 1_000000000_000000000L;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = in.nextInt();
        for (int c = 1 ; c <= T ; c++) {
            int n = in.nextInt();
            int m = in.nextInt();
            long[][] table = new long[n][m];
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < m ; j++) {
                    table[i][j] = in.nextInt();
                }
                Arrays.sort(table[i]);
            }
            out.println(String.format("Case #%d: %d", c, solve(table)));
        }
        out.flush();
    }

    private static long solve(long[][] table) {
        int n = table.length;
        int m = table[0].length;
        long[][] dp = new long[n+1][n+1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[0][0] = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j <= n; j++) {
                if (dp[i][j] == INF) {
                    continue;
                }
                long base = dp[i][j];
                if (j >= i+1) {
                    dp[i+1][j] = Math.min(dp[i+1][j], base);
                }

                long cost = 0;
                for (int buy = 1 ; buy+j <= n ; buy++) {
                    if (buy >= m+1) {
                        break;
                    }
                    cost += table[i][buy-1];
                    if (j+buy >= i+1) {
                        dp[i+1][buy+j] = Math.min(dp[i+1][buy+j], base+cost+buy*buy);
                    }
                }
            }
        }
        return dp[n][n];
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
