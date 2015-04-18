package codeforces.cr286;

import java.io.PrintWriter;
import java.util.*;

public class A {

    static int[] gem;

    static int D;
    static int GETA = 256;

    static int[][] memo;

    public static int dfs(int now, int speed) {
        if (now > 30000) {
            return 0;
        }
        if (memo[now][speed] >= 0) {
            return memo[now][speed];
        }

        int real_speed = D + speed - GETA;
        int max = 0;
        for (int dv = -1 ; dv <=  1; dv++) {
            int ts = speed + dv;
            int to = now + real_speed + dv;
            if (real_speed + dv <= 0 || to > 30000) {
                continue;
            }
            max = Math.max(max, dfs(to, ts));
        }
        memo[now][speed] = max + gem[now];
        return memo[now][speed];
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int d = in.nextInt();
        D = d;

        gem = new int[30001];
        for (int i = 0 ; i < n ; i++) {
            gem[in.nextInt()] += 1;
        }

        memo = new int[30001][512];
        for (int i = 0 ; i < memo.length ; i++) {
            Arrays.fill(memo[i], -1);
        }

        out.println(dfs(d, GETA));
        out.flush();
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



