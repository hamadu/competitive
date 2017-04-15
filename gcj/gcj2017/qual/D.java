package gcj.gcj2017.qual;

import java.io.PrintWriter;
import java.util.*;

public class D {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            int n = in.nextInt();
            int m = in.nextInt();
            int[][] tbl = new int[n][n];
            for (int i = 0; i < m ; i++) {
                int c = in.next().toCharArray()[0];
                int y = in.nextInt()-1;
                int x = in.nextInt()-1;
                tbl[y][x] = c;
            }
            out.println(String.format("Case #%d: %s", cs, solve(n, tbl)));
        }
        out.flush();
    }

    private static String solve(int n, int[][] tbl) {
        List<int[]> cross = solveCross(n, tbl);
        List<int[]> plus = solvePlus(n, tbl);

        int[][] ansTbl = new int[n][n];
        for (int i = 0; i < n ; i++) {
            ansTbl[i] = tbl[i].clone();
        }

        for (int[] x : cross) {
            if (ansTbl[x[0]][x[1]] == '+') {
                ansTbl[x[0]][x[1]] = 'o';
            } else {
                ansTbl[x[0]][x[1]] = 'x';
            }
        }
        for (int[] x : plus) {
            if (ansTbl[x[0]][x[1]] == 'x') {
                ansTbl[x[0]][x[1]] = 'o';
            } else {
                ansTbl[x[0]][x[1]] = '+';
            }
        }

        StringBuilder line = new StringBuilder();
        int addModel = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (tbl[i][j] != ansTbl[i][j]) {
                    addModel++;
                    line.append("\n").append((char)ansTbl[i][j]).append(' ').append(i+1).append(' ').append(j+1);
                }
            }
        }

        int finalScore = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n ; j++) {
                if (ansTbl[i][j] == 'o') {
                    finalScore += 2;
                } else if (ansTbl[i][j] == 'x' || ansTbl[i][j] == '+') {
                    finalScore += 1;
                }
            }
        }
        return String.format("%d %d%s", finalScore, addModel, line.toString());
    }

    private static List<int[]> solveCross(int n, int[][] tbl) {
        MaxFlowDinic flowX = new MaxFlowDinic();
        flowX.init(2*n+2);

        int source = 2*n;
        int sink = 2*n+1;
        for (int i = 0; i < n ; i++) {
            flowX.edge(source, i, 1);
            flowX.edge(n+i, sink, 1);
        }
        int[][] has = new int[n][n];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(has[i], 1);
        }
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (tbl[i][j] == 'x' || tbl[i][j] == 'o') {
                    for (int k = 0; k < n ; k++) {
                        has[i][k] = has[k][j] = 0;
                    }
                }
            }
        }
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (has[i][j] == 1) {
                    flowX.edge(i, n + j, 1);
                }
            }
        }
        flowX.max_flow(source, sink);


        List<int[]> addXs= new ArrayList<>();
        for (int i = 0 ; i < n ; i++) {
            for (int[] e : flowX.graph[i]) {
                if (e[0] >= n && e[0] < 2*n && e[1] == 0) {
                    addXs.add(new int[]{i, e[0]-n});
                }
            }
        }
        return addXs;
    }


    private static List<int[]> solvePlus(int n, int[][] tbl) {

        int rn = n*2+1;
        int[][] rotTbl = new int[rn][rn];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                int ti = i+j;
                int tj = i-j+n;
                rotTbl[ti][tj] = tbl[i][j];
            }
        }

        MaxFlowDinic flow = new MaxFlowDinic();
        flow.init(2*rn+2);
        int source = 2*rn;
        int sink = 2*rn+1;
        for (int i = 0; i < rn ; i++) {
            flow.edge(source, i, 1);
            flow.edge(rn+i, sink, 1);
        }
        int[][] has = new int[rn][rn];
        for (int i = 0; i < rn ; i++) {
            Arrays.fill(has[i], 1);
        }
        for (int i = 0; i < rn ; i++) {
            for (int j = 0; j < rn ; j++) {
                if (rotTbl[i][j] == '+' || rotTbl[i][j] == 'o') {
                    for (int k = 0; k < rn ; k++) {
                        has[i][k] = has[k][j] = 0;
                    }
                }
            }
        }
        for (int i = 0; i < rn ; i++) {
            for (int j = 0; j < rn ; j++) {
                int baseI = i+j-n;
                int baseJ = i-j+n;
                if (has[i][j] == 1 && baseI >= 0 && baseI <= 2*n-2 && baseI % 2 == 0 && baseJ >= 0 && baseJ <= 2*n-2 && baseJ % 2 == 0) {
                    flow.edge(i, rn + j, 1);
                }
            }
        }
        flow.max_flow(source, sink);


        List<int[]> addXs= new ArrayList<>();
        for (int i = 0 ; i < rn ; i++) {
            for (int[] e : flow.graph[i]) {
                if (e[0] >= rn && e[0] < 2*rn && e[1] == 0) {
                    int j = e[0]-rn;
                    int baseI = i+j-n;
                    int baseJ = i-j+n;
                    addXs.add(new int[]{baseI / 2, baseJ / 2});
                }
            }
        }
        return addXs;
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

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
