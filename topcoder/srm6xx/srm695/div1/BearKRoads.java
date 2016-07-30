package topcoder.srm6xx.srm695.div1;

/**
 * Created by hama_du
 */
import java.io.*;
import java.util.*;

public class BearKRoads {
    public int maxHappy(int[] x, int[] a, int[] b, int k) {
        K = k;
        int n = x.length;
        int m = a.length;
        int[][] e = new int[m][3];
        for (int i = 0; i < m ; i++) {
            e[i][0] = a[i];
            e[i][1] = b[i];
            e[i][2] = x[a[i]] + x[b[i]];
        }
        Arrays.sort(e, (e1, e2) -> e2[2] - e1[2]);
        edge = e;
        vsc = x;

        dfs(0, 0, 0, new int[n]);

        return max;
    }


    int max;
    int K;
    int[][] edge;
    int[] vsc;

    void dfs(int idx, int chosen, int score, int[] deg) {
        if (chosen == K) {
            max = Math.max(max, score);
            return;
        }
        if (idx > 30 || idx == edge.length) {
            return;
        }
        dfs(idx+1, chosen, score, deg);

        int a = edge[idx][0];
        int b = edge[idx][1];
        int pl = 0;
        deg[a]++;
        deg[b]++;
        if (deg[a] == 1) {
            pl += vsc[a];
        }
        if (deg[b] == 1) {
            pl += vsc[b];
        }
        dfs(idx+1, chosen+1, score+pl, deg);
        deg[a]--;
        deg[b]--;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
