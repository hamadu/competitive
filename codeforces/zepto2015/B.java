package codeforces.zepto2015;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class B {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int k = in.nextInt();
        int n = (1<<k)*2-1;
        int[] path = new int[n];
        for (int i = 1 ; i < n ; i++) {
            path[i] = in.nextInt();
        }

        int[] light = new int[n];
        dfs(light, path, 0);

        int low = (1<<k)-1;
        int max = 0;
        int[] add = new int[n];
        for (int l = low ; l < n ; l++) {
            max = Math.max(max, light[l]);
        }
        for (int l = low ; l < n ; l++) {
            add[l] = max - light[l];
        }

        for (int i = low-1 ; i >= 1 ; i--) {
            int take = Math.min(add[i * 2 + 1], add[i * 2 + 2]);
            add[i] = take;
            add[i*2+1] -= take;
            add[i*2+2] -= take;
        }

        int ans = 0;
        for (int i = 1 ; i < n ; i++) {
            ans += add[i];
        }
        out.println(ans);
        out.flush();
    }

    private static void dfs(int[] l, int[] path, int now) {
        if (now >= l.length) {
            return;
        }
        l[now] += path[now];
        if (now >= 1) {
            l[now] += l[(now-1)/2];
        }
        dfs(l, path, now*2+1);
        dfs(l, path, now*2+2);
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



