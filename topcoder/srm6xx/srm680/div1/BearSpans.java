package topcoder.srm6xx.srm680.div1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hama_du on 2016/02/05.
 */
public class BearSpans {
    public int[] findAnyGraph(int n, int m, int k) {
        try {
            dfs(0, n, k);
        } catch (Exception e) {
            return new int[]{-1};
        }


        int[][] graph = new int[n][n];
        for (int i = 0 ; i < edges.size() ; i += 2) {
            int a = edges.get(i);
            int b = edges.get(i+1);
            graph[a][b] = graph[b][a] = 1;
        }
        for (int i = 0 ; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                if (graph[i][j] == 0 && edges.size() < m*2) {
                    graph[i][j] = graph[j][i] = 1;
                    edges.add(i);
                    edges.add(j);
                }
            }
        }
        int[] ret = new int[m*2];
        for (int i = 0 ; i < m*2 ; i++) {
            ret[i] = edges.get(i)+1;
        }
        return ret;
    }


    List<Integer> edges = new ArrayList<>();

    public void dfs(int from, int to, int level) {
        if (to - from <= 3 || level == 1) {
            if (level >= 2) {
                throw new RuntimeException("we could not make it");
            }
            for (int h = from ; h <= to-2 ; h++) {
                edges.add(h);
                edges.add(h+1);
            }
            return;
        }
        int med = (to + from) / 2;
        dfs(from, med, level-1);
        dfs(med, to, level-1);

        debug(from, med, level);
        edges.add(from);
        edges.add(med);
    }

    public static void main(String[] args) {
        BearSpans spans = new BearSpans();
        debug(spans.findAnyGraph(9, 12, 3));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
