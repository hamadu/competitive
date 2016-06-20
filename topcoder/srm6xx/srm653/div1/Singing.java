package topcoder.srm6xx.srm653.div1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hama_du on 2016/06/20.
 */
public class Singing {
    int solve(int N, int low, int high, int[] pitch) {
        int[][] change = new int[N + 1][N + 1];
        for (int i = 0; i < pitch.length - 1; i++) {
            int fr = pitch[i];
            int to = pitch[i + 1];
            change[fr][to]++;
        }

        MaxFlowDinic dinic = new MaxFlowDinic();
        dinic.init(N+3);
        for (int i = 1; i < low; i++) {
            dinic.edge(N+1, i, 1000000);
        }
        for (int i = high+1; i <= N ; i++) {
            dinic.edge(i, N+2, 1000000);
        }
        for (int a = 1 ; a <= N ; a++) {
            for (int b = a+1 ; b <= N ; b++) {
                if (a == b) {
                    continue;
                }
                if (change[a][b] + change[b][a] >= 1) {
                    dinic.edge(a, b, change[a][b] + change[b][a]);
                    dinic.edge(b, a, change[a][b] + change[b][a]);
                }
            }
        }
        return dinic.max_flow(N+1, N+2);
    }

    public static class MaxFlowDinic {
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
