package topcoder.srm5xx.srm527;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hama_du on 15/08/18.
 */
public class P8XMatrixRecovery {
    public String[] solve(String[] rows, String[] columns) {
        int n = rows.length;
        int m = columns.length;
        char[][] row = new char[n][m];
        char[][] col = new char[m][n];
        for (int i = 0; i < n ; i++) {
            row[i] = rows[i].toCharArray();
        }
        for (int i = 0; i < m ; i++) {
            col[i] = columns[i].toCharArray();
        }

        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (row[i][j] == '?') {
                    row[i][j] = '0';
                    if (isOKCol(col, row)) {
                    } else {
                        row[i][j] = '1';
                    }
                }
            }
        }
        String[] ret = new String[n];
        for (int i = 0; i < n ; i++) {
            ret[i] = String.valueOf(row[i]);
        }
        debug(ret);
        return ret;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }


    private boolean isOKCol(char[][] col, char[][] ans) {
        int M = col.length;
        int n = col[0].length;

        MaxFlowDinic dinic = new MaxFlowDinic();
        dinic.init(2*M+2);
        int source = 2*M;
        int sink = source+1;
        for (int i = 0; i < M ; i++) {
            dinic.edge(source, i, 1);
            dinic.edge(M+i, sink, 1);
        }
        for (int i = 0; i < M ; i++) {
            for (int j = 0; j < M ; j++) {
                boolean match = true;
                for (int k = 0; k < n ; k++) {
                    if (ans[k][i] != '?' && col[j][k] != '?' && ans[k][i] != col[j][k]) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    dinic.edge(i, M+j, 1);
                }
            }
        }
        return dinic.max_flow(source, sink) == M;
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
