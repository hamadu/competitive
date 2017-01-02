package topcoder.srm6xx.srm692.div1;

import java.io.*;
import java.util.*;

public class HardProof {
    private static final int INF = 1000000000;

    public int minimumCost(int[] D) {
        n = (int)Math.sqrt(D.length);
        if (n == 1) {
            return 0;
        }

        visited = new boolean[n];
        graph = new List[n];
        r_graph = new List[n];
        for (int i = 0; i < n ; i++) {
            graph[i] = new ArrayList<>();
            r_graph[i] = new ArrayList<>();
        }

        Set<Integer> diffSet = new HashSet<>();
        for (int i = 0; i <n* n ; i++) {
            diffSet.add(D[i]);
        }
        List<Integer> ds = new ArrayList<>(diffSet);
        Collections.sort(ds);
        ds.add(INF);

        int[][] g = new int[n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                g[i][j] = D[i*n+j];
            }
        }

        int dn = ds.size();
        int best = INF;
        for (int i = 0 ; i < dn ; i++) {
            int fr = i;
            int to = dn;
            while (to - fr > 1) {
                int med = (to + fr) / 2;
                int w = isEq(g, ds.get(i), ds.get(med));
                if (w < INF) {
                    best = Math.min(best, w);
                    to = med;
                } else {
                    fr = med;
                }
            }
        }
        return best;
    }

    int n;
    boolean[] visited;
    List<Integer>[] graph;
    List<Integer>[] r_graph;
    List<Integer> rev;
    int[] node_id;

    int isEq(int[][] g, int fr, int to) {
        int n = g.length;
        Arrays.fill(visited, false);
        for (int i = 0; i < n ; i++) {
            graph[i].clear();
            r_graph[i].clear();
        }
        int min = INF;
        int max = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (i != j && fr <= g[i][j] && g[i][j] < to) {
                    min = Math.min(min, g[i][j]);
                    max = Math.max(max, g[i][j]);
                    graph[i].add(j);
                    r_graph[j].add(i);
                }
            }
        }
        if (scc() == 1) {
            return max - min;
        }
        return INF;
    }

    public int scc() {
        visited = new boolean[n];
        rev = new ArrayList<>();
        for (int i = 0; i< n; i++) {
            if (!visited[i]) {
                dfs(i);
            }
        }
        int id = 0;
        node_id = new int[n];
        visited = new boolean[n];
        for (int i = rev.size()-1; i>=0; i--) {
            if (!visited[rev.get(i)]) {
                rdfs(rev.get(i), id);
                id++;
            }
        }
        return id;
    }

    private void dfs(int i) {
        visited[i] = true;
        for (int next : graph[i]) {
            if (!visited[next]) {
                dfs(next);
            }
        }
        rev.add(i);
    }

    private void rdfs(int i, int id) {
        visited[i] = true;
        node_id[i] = id;
        for (int next : r_graph[i]) {
            if (!visited[next]) {
                rdfs(next, id);
            }
        }
    }
}
