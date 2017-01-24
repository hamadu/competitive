package facebook.fhc2017.round2;

import java.io.PrintWriter;
import java.util.Scanner;

public class Sabotage {
    private static final int INF = 1000000000;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = in.nextInt();
        for (int c = 1 ; c <= T ; c++) {
            int n = in.nextInt();
            int m = in.nextInt();
            int k = in.nextInt();

            int ans = Math.min(solve(n, m, k), solve(m, n, k));
            out.println(String.format("Case #%d: %d", c, ans >= INF ? -1 : ans));
        }
        out.flush();
    }

    private static int solve(int n, int m, int k) {
        if (n <= k) {
            return INF;
        }
        int ptn1 = (m < 1+k+1+k+1) ? INF : (n+k-1)/k;
        int ptn2 = INF;
        if (n >= 1+k+1+k+1 && m >= k+1+k) {
            ptn2 = Math.min(ptn2, 2+(k+2+k-1)/k);
        }
        return Math.min(ptn1, ptn2);
    }
}
