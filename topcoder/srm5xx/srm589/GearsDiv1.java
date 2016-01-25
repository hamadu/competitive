package topcoder.srm5xx.srm589;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hama_du on 15/09/30.
 */
public class GearsDiv1 {
    public int getmin(String color, String[] graph) {
        int n = color.length();

        int min = Integer.MAX_VALUE;
        char[] cl = color.toCharArray();
        boolean[][] g = new boolean[n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                g[i][j] = graph[i].charAt(j) == 'Y';
            }
        }

        min = Math.min(min, solve('R', 'G', cl, g));
        min = Math.min(min, solve('G', 'B', cl, g));
        min = Math.min(min, solve('B', 'R', cl, g));

        return min;
    }

    private int solve(char A, char B, char[] cl, boolean[][] g) {
        int n = cl.length;
        MaxFlowDinic d = new MaxFlowDinic();
        d.init(n+2);
        for (int i = 0; i < n ; i++) {
            if (cl[i] == A) {
                d.edge(n, i, 1);
            } else if (cl[i] == B) {
                d.edge(i, n+1, 1);
            }
        }
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (cl[i] == A && cl[j] == B && g[i][j]) {
                    d.edge(i, j, 1);
                }
            }
        }
        return d.max_flow(n, n+1);
    }

    static class MaxFlowDinic {
        public List<int[]>[] graph;
        public int[] deg;

        public int[] level;
        public int[] itr;

        public int[] que;

        @SuppressWarnings("unchecked")
        public void init(int size) {
            graph = new List[size];
            for (int i = 0; i < size ; i++) {
                graph[i] = new ArrayList<int[]>();
            }
            deg = new int[size];
            level = new int[size];
            itr = new int[size];
            que = new int[size+10];
        }
        public void edge(int from, int to, int cap) {
            int fdeg = deg[from];
            int tdeg = deg[to];
            graph[from].add(new int[]{to, cap, tdeg});
            graph[to].add(new int[]{from, 0, fdeg});
            deg[from]++;
            deg[to]++;
        }

        public int dfs(int v, int t, int f) {
            if (v == t) return f;
            for (int i = itr[v] ; i < graph[v].size() ; i++) {
                itr[v] = i;
                int[] e = graph[v].get(i);
                if (e[1] > 0 && level[v] < level[e[0]]) {
                    int d = dfs(e[0], t, Math.min(f, e[1]));
                    if (d > 0) {
                        e[1] -= d;
                        graph[e[0]].get(e[2])[1] += d;
                        return d;
                    }
                }
            }
            return 0;
        }

        public void bfs(int s) {
            Arrays.fill(level, -1);
            int qh = 0;
            int qt = 0;
            level[s] = 0;
            que[qh++] = s;
            while (qt < qh) {
                int v = que[qt++];
                for (int i = 0; i < graph[v].size() ; i++) {
                    int[] e = graph[v].get(i);
                    if (e[1] > 0 && level[e[0]] < 0) {
                        level[e[0]] = level[v] + 1;
                        que[qh++] = e[0];
                    }
                }
            }
        }

        public int max_flow(int s, int t) {
            int flow = 0;
            while (true) {
                bfs(s);
                if (level[t] < 0) {
                    return flow;
                }
                Arrays.fill(itr, 0);
                while (true) {
                    int f = dfs(s, t, Integer.MAX_VALUE);
                    if (f <= 0) {
                        break;
                    }
                    flow += f;
                }
            }
        }
    }
}
