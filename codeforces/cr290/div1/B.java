package codeforces.cr290.div1;

import java.io.PrintWriter;
import java.util.*;

public class B {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long[] l = new long[n];
        for (int i = 0; i < n ; i++) {
            l[i] = in.nextInt();
        }
        int[] c = new int[n];
        for (int i = 0; i < n ; i++) {
            c[i] = in.nextInt();
        }

        int min = Integer.MAX_VALUE;
        int[] pr = gp(32000);
        for (int bi = 0 ; bi < n ; bi++) {
            int[] pom = new int[21];
            int pi = 0;
            long ll = l[bi];
            for (int p : pr) {
                if (ll % p == 0) {
                    while (ll % p == 0) {
                        ll /= p;
                    }
                    pom[pi++] = p;
                }
            }
            if (ll >= 2) {
                pom[pi++] = (int)ll;
            }
            int[] mask = new int[n];
            for (int j = 0 ; j < n ; j++) {
                for (int p = 0 ; p < pi ; p++) {
                    if (l[j] % pom[p] == 0) {
                        mask[j] |= 1<<p;
                    }
                }
            }

            int[][] dp = new int[n+1][1<<pi];
            for (int j = 0; j <= n ; j++) {
                Arrays.fill(dp[j], Integer.MAX_VALUE);
            }
            dp[0][(1<<pi)-1] = 0;
            for (int i = 0; i < n ; i++) {
                for (int p = 0; p < (1<<pi) ; p++) {
                    if (dp[i][p] == Integer.MAX_VALUE) {
                        continue;
                    }
                    // use
                    int tp = p & mask[i];
                    dp[i+1][tp] = Math.min(dp[i+1][tp], dp[i][p] + c[i]);

                    // or not
                    if (bi != i) {
                        dp[i+1][p] = Math.min(dp[i+1][p], dp[i][p]);
                    }
                }
            }
            min = Math.min(min, dp[n][0]);
        }
        out.println(min == Integer.MAX_VALUE ? -1 : min);
        out.flush();
    }

    static int[] gp(int max) {
        boolean[] isp = new boolean[max+1];
        Arrays.fill(isp, true);
        isp[1] = false;

        int cnt = 0;
        for (int i = 2 ; i <= max ; i++) {
            if (isp[i]) {
                cnt++;
                for (int j = i*2; j <= max ; j += i) {
                    isp[j] = false;
                }
            }
        }
        int[] prime = new int[cnt];
        int pi = 0;
        for (int i = 2 ; i <= max ; i++) {
            if (isp[i]) {
                prime[pi++] = i;
            }
        }
        return prime;
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



