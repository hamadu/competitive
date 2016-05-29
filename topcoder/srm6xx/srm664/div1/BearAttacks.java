package topcoder.srm6xx.srm664.div1;

/**
 * Created by hama_du on 2016/05/28.
 */
public class BearAttacks {
    public int expectedValue(int N, int R0, int A, int B, int M, int LOW, int HIGH) {
        graph = build(N, R0, A, B, M, LOW, HIGH);
        return 0;
    }

    int[][] graph;

    public int[][] build(int N, long R0, long A, long B, long M, long LOW, long HIGH) {
        long r = R0;
        long BILLION = (long)1e9;
        int[][] edges = new int[N-1][2];
        for (int i = 0; i < N-1 ; i++) {
            r = (r * A + B) % M;
            long min = i * LOW / BILLION;
            long max = i * HIGH / BILLION;
            edges[i][0] = i;
            edges[i][1] = (int)(min + r % (max - min + 1));
        }
        return buildGraph(N, edges);
    }

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

}
