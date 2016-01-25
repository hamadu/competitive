package topcoder.srm6xx.srm668;

/**
 * Created by hama_du on 15/10/13.
 */
public class WalkingToSchool {
    public String canWalkExactly(int n, int[] from, int[] to) {
        int m = from.length;
        int[][] edges = new int[m][2];
        for (int i = 0; i < m ; i++) {
            edges[i][0] = from[i];
            edges[i][1] = to[i];
        }
        int[][] graph = buildDirectedGraph(n, edges);

        boolean[] zeroOne = findN(0, 1, 2100, graph);
        boolean[] oneZero = findN(1, 0, 2100, graph);

        boolean[] loop = new boolean[4201];
        for (int i = 0; i < zeroOne.length; i++) {
            if (zeroOne[i]) {
                for (int j = 0; j < oneZero.length ; j++) {
                    if (oneZero[j]) {
                        loop[i+j] = true;
                    }
                }
            }
        }

        int gcd = -1;
        for (int i = 2 ; i < loop.length ; i++) {
            if (loop[i]) {
                if (gcd == -1) {
                    gcd = i;
                } else {
                    gcd = gcd(gcd, i);
                }
            }
        }
        return gcd == 1 ? "Freedom" : "Chores";
    }

    private int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a%b);
    }

    static int[] que = new int[2200*2200*2];

    static boolean[] findN(int start, int to, int max, int[][] graph) {
        int n = graph.length;
        boolean[][] memo = new boolean[n][max];
        memo[start][0] = true;

        int qh = 0;
        int qt = 0;
        que[qh++] = start;
        que[qh++] = 0;
        while (qt < qh) {
            int now = que[qt++];
            int time = que[qt++]+1;
            if (time == max) {
                continue;
            }
            for (int next : graph[now]) {
                if (!memo[next][time]) {
                    memo[next][time] = true;
                    que[qh++] = next;
                    que[qh++] = time;
                }
            }
        }
        return memo[to];
    }


    static int[][] buildDirectedGraph(int n, int[][] edges) {
        int m = edges.length;
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            deg[a]++;
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
        }
        return graph;
    }
}
