package atcoder.arc036;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class C {
    private static final long MOD = 1000000007;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        char[] c = in.next().toCharArray();

        int[][][] dp = new int[n+1][k+1][k+1];
        dp[0][0][0] = 1;
        for (int i = 0; i < n ; i++) {
            for (int r0 = 0; r0 <= k ; r0++) {
                for (int r1 = 0; r1 <= k ; r1++) {
                    if (dp[i][r0][r1] == 0) {
                        continue;
                    }
                    int base = dp[i][r0][r1];
                    for (char ch : "01".toCharArray()) {
                        if (c[i] == '?' || c[i] == ch) {
                            int tr0 = r0;
                            int tr1 = r1;
                            if (ch == '0') {
                                tr0++;
                                tr1 = Math.max(0, tr1-1);
                            } else {
                                tr1++;
                                tr0 = Math.max(0, tr0-1);
                            }
                            if (tr0 <= k && tr1 <= k) {
                                dp[i+1][tr0][tr1] += base;
                                dp[i+1][tr0][tr1] -= (dp[i+1][tr0][tr1] >= MOD) ? MOD : 0;
                            }
                        }
                    }
                }
            }
        }


        long sum = 0;
        for (int i = 0; i <= k; i++) {
            for (int j = 0; j <= k ; j++) {
                sum += dp[n][i][j];
            }
        }
        out.println(sum % MOD);
        out.flush();
    }
    
    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}





