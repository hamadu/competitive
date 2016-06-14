package gcj.gcj2014.round3;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by hama_du on 2016/06/10.
 */
public class DSMALL {
    private static final int INF = 1145141919;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            int n = in.nextInt();
            int[] coin = new int[n];
            for (int i = 0; i < n ; i++) {
                coin[i] = in.nextInt();
            }
            int[][] edge = new int[n-1][2];
            for (int i = 0; i < n-1 ; i++) {
                edge[i][0] = i;
                edge[i][1] = in.nextInt()-1;
            }
            int[][] graph = buildGraph(n, edge);
            out.println(String.format("Case #%d: %d", cs, solve(coin, graph)));
        }
        out.flush();
    }

    private static int solve(int[] coin, int[][] graph) {
        n = coin.length;
        C = coin;
        G = graph;
        destroyed = new int[n][n];

        int best = -INF;
        for (int i = 0; i < n ; i++) {
            int worst = INF;
            for (int j = 0; j < n ; j++) {
                worst = Math.min(worst, dfs(i, j, 0));
            }
            best = Math.max(best, worst);
        }
        return best;
    }

    private static int dfs(int my, int op, int flg) {
        int theCoin = C[my];
        C[my] = 0;
        boolean has = false;
        int best = -INF;
        for (int i = 0 ; i < G[my].length ; i++) {
            int to = G[my][i];
            if (destroyed[my][to] >= 1 || destroyed[to][my] >= 1) {
                continue;
            }
            has = true;
            destroyed[my][to]++;
            destroyed[to][my]++;
            best = Math.max(best, -dfs(op, to, 0));
            destroyed[my][to]--;
            destroyed[to][my]--;
        }
        if (!has) {
            if (flg == 0) {
                best = -dfs(op, my, 1);
            } else {
                best = 0;
            }
        }
        best += theCoin;
        C[my] = theCoin;
        return best;
    }


    static int n;
    static int[] C;
    static int[][] G;
    static int[][] destroyed;

    static int[][] buildGraph(int n, int[][] edges) {
        int m = edges.length;
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            deg[a]++;
            deg[b]++;
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
            graph[b][--deg[b]] = a;
        }
        return graph;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
