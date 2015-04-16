package atcoder.arc036;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class C {
    private static final long MOD = 1_000_000_007;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        char[] c = in.next().toCharArray();

        int offset = 300;
        int m = 605;
        long[][][] dp = new long[2][m][m];


        dp[0][offset][offset] = 1;
        for (int i = 0 ; i < n ; i++) {
            int fr = i % 2;
            int tr = 1 - fr;
            for (int cur = 0 ; cur < m ; cur++) {
                for (int min = 0 ; min < m ; min++) {
                    if (dp[fr][cur][min] == 0) {
                        continue;
                    }
                    if (c[i] == '?' || c[i] == '0') {
                        int tc = cur-1;
                        int tm = Math.min(tc, min);
                        if (tc-tm > k ) {
                            debug(i,tc,tm,'-');
                        } else {
                            dp[tr][tc][tm] += dp[fr][cur][min];
                            dp[tr][tc][tm] -= (dp[tr][tc][tm] >= MOD) ? MOD : 0;
                        }
                    }
                    if (c[i] == '?' || c[i] == '1') {
                        int tc = cur+1;
                        int tm = Math.min(tc, min);
                        if (tc-tm > k ) {
                            debug(i,'+');
                        } else {
                            dp[tr][tc][tm] += dp[fr][cur][min];
                            dp[tr][tc][tm] -= (dp[tr][tc][tm] >= MOD) ? MOD : 0;
                        }
                    }
                }
            }
            for (int j = 0 ; j < dp[0].length ; j++) {
                Arrays.fill(dp[fr][j], 0);
            }
        }

        long sum = 0;
        for (int cur = 0 ; cur < m ; cur++) {
            for (int min = 0 ; min < m; min++) {
                sum += dp[n%2][cur][min];
                sum %= MOD;
            }
        }
        out.println(sum);
        out.flush();
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



