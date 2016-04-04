package codeforces.cf1xx.cr172.div1;

import java.io.*;
import java.util.Arrays;

/**
 * Created by hama_du on 15/09/10.
 */
public class C {
    static int[][] graph;

    static double ans = 0.0d;

    static void dfs(int a, int p, int d) {
        ans += 1.0d / d;
        for (int c : graph[a]) {
            if (c != p) {
                dfs(c, a, d+1);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int n = Integer.valueOf(in.readLine());
        graph = new int[n][];
        int[][] e = new int[n-1][2];
        int[] deg = new int[n];
        for (int i = 0 ; i < n - 1 ; i++) {
            String[] edata = in.readLine().split(" ");
            int a = Integer.valueOf(edata[0]) - 1;
            int b = Integer.valueOf(edata[1]) - 1;
            deg[a]++;
            deg[b]++;
            e[i][0] = a;
            e[i][1] = b;
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        int[] eidx = new int[n];
        for (int i = 0 ; i < n - 1 ; i++) {
            int a = e[i][0];
            int b = e[i][1];
            graph[a][eidx[a]++] = b;
            graph[b][eidx[b]++] = a;
        }

        dfs(0, -1, 1);

        out.println(ans);

        out.flush();
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
