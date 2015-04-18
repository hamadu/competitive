package codeforces.zepto2015;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class A {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        char[] c = in.next().toCharArray();

        out.println(isgood(c) ? "yes" : "no");
        out.flush();
    }

    private static boolean isgood(char[] c) {
        int n = c.length;
        for (int i = 0 ; i < n ; i++) {
            if (c[i] == '*') {
                for (int j = 1; j <= n; j++) {
                    if (dfs(c, j, i, 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean dfs(char[] c, int k, int now, int time) {
        if (now >= c.length || c[now] == '.') {
            return false;
        }
        if (time == 4) {
            return true;
        }
        return dfs(c, k, now+k, time+1);
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



