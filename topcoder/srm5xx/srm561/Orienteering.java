package topcoder.srm5xx.srm561;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 15/09/03.
 */
public class Orienteering {
    public double expectedLength(String[] field, int K) {
        int n = field.length;
        int m = field[0].length();

        char[][] map = new char[n][m];
        for (int i = 0; i < n ; i++) {
            map[i] = field[i].toCharArray();
        }

        int[][] ids = new int[n][m];
        int idx = 0;
        for (int i = 0; i < n ; i++) {
            Arrays.fill(ids[i], -1);
            for (int j = 0; j < m; j++) {
                if (map[i][j] != '#') {
                    ids[i][j] = idx++;
                }
            }
        }
        mark = new boolean[idx];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == '*') {
                    M++;
                    mark[ids[i][j]] = true;
                }
            }
        }

        int[][] edges = new int[idx-1][2];
        int eid = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (map[i][j] == '#') {
                    continue;
                }
                if (i+1 < n && map[i+1][j] != '#') {
                    edges[eid][0] = ids[i][j];
                    edges[eid][1] = ids[i+1][j];
                    eid++;
                }
                if (j+1 < m && map[i][j+1] != '#') {
                    edges[eid][0] = ids[i][j];
                    edges[eid][1] = ids[i][j+1];
                    eid++;
                }
            }
        }
        N = idx;
        graph = buildGraph(idx, edges);
        parent = new int[idx];
        count = new int[idx];
        dfs(0, -1);

        return expectedLengthOfMST(graph, K) * 2 - expectedLengthOfDiameter(graph, K);
    }

    int N;
    int M;
    int[][] graph;
    int[] count;
    int[] parent;
    boolean[] mark;

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

    public int dfs(int now, int par) {
        parent[now] = par;
        int ret = mark[now] ? 1 : 0;
        for (int to : graph[now]) {
            if (to == par) {
                continue;
            }
            ret += dfs(to, now);

        }
        count[now] = ret;
        return ret;
    }

    public double expectedLengthOfMST(int[][] graph, int K) {
        double sum = 0;
        for (int fr = 0; fr < graph.length ; fr++) {
            for (int to : graph[fr]) {
                if (parent[fr] == to) {
                    continue;
                }
                int[] ab = new int[]{ count[to], M-count[to] };
                double ngRate = 0.0d;
                for (int a : ab) {
                    // subtracting pattern s.t. pick K from a
                    double sub = 1.0d;
                    for (int i = 0; i < K; i++) {
                        sub *= (a - i) * 1.0d / (M - i);
                    }
                    ngRate += sub;
                }
                sum += 1.0d - ngRate;
            }
        }
        return sum;
    }

    public int[][] bfs(int[][] graph) {
        int n = graph.length;
        int[][] ret = new int[n][n];

        Queue<Integer> q = new ArrayBlockingQueue<>(10000);
        for (int i = 0; i < n ; i++) {
            Arrays.fill(ret[i], Integer.MAX_VALUE);
            ret[i][i] = 0;
            q.add(i);
            q.add(0);
            while (q.size() >= 1) {
                int now = q.poll();
                int tim = q.poll();
                for (int to : graph[now]) {
                    if (ret[i][to] > tim+1) {
                        ret[i][to] = tim+1;
                        q.add(to);
                        q.add(tim+1);
                    }
                }
            }
        }
        return ret;
    }

    public double expectedLengthOfDiameter(int[][] graph, int K) {
        int[][] table = bfs(graph);
        double sum = 0.0d;

        for (int a = 0; a < graph.length ; a++) {
            for (int b = a+1 ; b < graph.length ; b++) {
                if (!mark[a] || !mark[b]) {
                    continue;
                }
                int[] diameterPair = new int[]{a, b};
                int D = table[a][b];
                int candidates = 0;
                for (int c = 0; c < graph.length; c++) {
                    if (a == c || b == c || !mark[c]) {
                        continue;
                    }
                    if (table[a][c] > D || table[c][b] > D) {
                        continue;
                    }
                    int[] anotherPairA = new int[]{a, c};
                    int[] anotherPairB = new int[]{b, c};
                    if (table[a][c] == D && lexSmallerPair(anotherPairA, diameterPair)) {
                        continue;
                    }
                    if (table[c][b] == D && lexSmallerPair(anotherPairB, diameterPair)) {
                        continue;
                    }
                    candidates++;
                }
                // adding pattern s.t. pick K-2 from candidates
                double sub = 1.0d;
                for (int i = 0; i < K-2; i++) {
                    sub *= (candidates - i) * 1.0d / (M - 2 - i);
                }

                // pick place a and b from K : K * (K - 1) / 2
                sum += table[a][b] * sub * (K * (K - 1) / 2);
            }
        }
        // divide to obtain average
        return sum / (M * (M - 1) / 2);
    }

    public boolean lexSmallerPair(int[] a, int[] b) {
        int a0 = Math.min(a[0], a[1]);
        int a1 = Math.max(a[0], a[1]);
        int b0 = Math.min(b[0], b[1]);
        int b1 = Math.max(b[0], b[1]);
        if (a0 < b0) {
            return true;
        }
        if (a0 == b0 && a1 < b1) {
            return true;
        }
        return false;
    }
}
