package facebook.fhc2017.qual;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;

public class Zombie {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = in.nextInt();
        for (int c = 1 ; c <= T ; c++) {
            int h = in.nextInt();
            int s = in.nextInt();
            Spell[] sp = new Spell[s];
            for (int i = 0; i < s; i++) {
                sp[i] = new Spell(in.next());
            }
            out.println(String.format("Case #%d: %.12f", c, solve(h, sp)));
        }
        out.flush();
    }

    static class Spell {
        int x;
        int y;
        int z;

        Spell(String description) {
            int n = description.length();
            int head = 0;
            while (head < n && description.charAt(head) != 'd') {
                head++;
            }
            x = Integer.valueOf(description.substring(0, head));
            head++;

            int yh = head;
            while (head < n && description.charAt(head) != '+' && description.charAt(head) != '-') {
                head++;
            }
            y = Integer.valueOf(description.substring(yh, head));
            if (head < n) {
                int sign = description.charAt(head) == '+' ? 1 : -1;
                head++;
                z = Integer.valueOf(description.substring(head)) * sign;
            }
            // debug(description, x, y, z);
        }

        public double[] roll() {
            double per = 1.0d / y;
            double[][] dp = new double[2][500];
            dp[0][0] = 1.0;
            for (int i = 0 ; i < x ; i++) {
                int fr = i % 2;
                int to = 1 - fr;
                Arrays.fill(dp[to], 0);
                for (int j = 0; j < dp[0].length ; j++) {
                    if (dp[fr][j] == 0) {
                        continue;
                    }
                    for (int k = 1 ; k <= y ; k++) {
                        dp[to][j+k] += dp[fr][j] * per;
                    }
                }
            }
            return dp[x%2];
        }
    }

    private static double solve(int hp, Spell[] spells) {
        BigDecimal max = BigDecimal.ZERO;
        for (Spell s : spells) {
            double[] dp = s.roll();
            int atLeast = hp - s.z;
            BigDecimal rate = BigDecimal.ZERO;
            for (int x = dp.length-1 ; x >= atLeast ; x--) {
                if (x >= 0) {
                    rate = rate.add(BigDecimal.valueOf(dp[x]));
                    // rate += dp[x];
                }
            }
            max = max.max(rate);
        }
        return max.doubleValue();
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
