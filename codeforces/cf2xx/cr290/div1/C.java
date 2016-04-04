package codeforces.cf2xx.cr290.div1;

import java.io.PrintWriter;
import java.util.*;

public class C {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt();
        }
        boolean[] p = gp(32000);

        int e = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] % 2 == 0) {
                e++;
            }
        }
        if (e != n - e) {
            out.println("Impossible");
            out.flush();
            return;
        }

        MaxFlow flow = new MaxFlow();
        flow.init(n+2);
        for (int i = 0; i < n; i++) {
            if (a[i] % 2 == 0) {
                flow.edge(i, n+1, 2);
            } else {
                flow.edge(n, i, 2);
            }
        }
        for (int i = 0; i < n; i++) {
            if (a[i] % 2 == 1) {
                for (int j = 0 ; j < n ; j++) {
                    if (p[a[i]+a[j]]) {
                        flow.edge(i, j, 1);
                    }
                }
            }
        }
        int ma = flow.max_flow(n, n+1);
        if (ma != n) {
            out.println("Impossible");
            out.flush();
            return;
        }

        int[][] graph = new int[n][n];
        for (int i = 0 ; i < n ; i++) {
            if (a[i] % 2 == 1) {
                for (MaxFlow.Edge ed : flow.graph.get(i)) {
                    if (ed.to < n && ed.cap == 0) {
                        graph[i][ed.to] = graph[ed.to][i] = 1;
                    }
                }
            }
        }

        boolean[] done = new boolean[n];

        List<List<Integer>> group = new ArrayList<>();
        for (int i = 0 ; i < n ; i++) {
            if (!done[i]) {
                List<Integer> l = new ArrayList<>();
                dfs(graph, l, done, i, -1);
                group.add(l);
            }
        }

        out.println(group.size());
        for (List<Integer> l : group) {
            StringBuilder sb = new StringBuilder().append(l.size());
            for (int li : l) {
                sb.append(' ').append(li+1);
            }
            out.println(sb.toString());
        }
        out.flush();
    }

    private static void dfs(int[][] graph, List<Integer> l, boolean[] done, int v, int p) {
        if (done[v]) {
            return;
        }
        done[v] = true;
        l.add(v);
        for (int i = 0 ; i < graph.length ; i++) {
            if (graph[v][i] == 1 && i != p) {
                dfs(graph, l, done, i, v);
                return;
            }
        }
    }

    static boolean[] gp(int max) {
        boolean[] isp = new boolean[max+1];
        Arrays.fill(isp, true);
        isp[1] = false;

        int cnt = 0;
        for (int i = 2 ; i <= max ; i++) {
            if (isp[i]) {
                cnt++;
                for (int j = i*2; j <= max ; j += i) {
                    isp[j] = false;
                }
            }
        }
        return isp;
    }



    static class MaxFlow {
        public class Edge {
            int to;
            int cap;
            int rev;
            public Edge(int _to, int _cap, int _rev) {
                to = _to;
                cap = _cap;
                rev = _rev;
            }
        }
        public Map<Integer, List<Edge>> graph = new HashMap<Integer, List<Edge>>();
        public boolean[] used;
        public void init(int size) {
            for (int i = 0 ; i < size ; i++) {
                graph.put(i, new ArrayList<Edge>());
            }
            used = new boolean[size];
        }
        public void edge(int from, int to, int cap) {
            graph.get(from).add(new Edge(to, cap, graph.get(to).size()));
            graph.get(to).add(new Edge(from, 0, graph.get(from).size() - 1));
        }
        public int dfs(int v, int t, int f) {
            if (v == t) return f;
            used[v] = true;
            for (int i = 0 ; i < graph.get(v).size() ; i++) {
                Edge e = graph.get(v).get(i);
                if (!used[e.to] && e.cap > 0) {
                    int d = dfs(e.to, t, Math.min(f, e.cap));
                    if (d > 0) {
                        e.cap -= d;
                        graph.get(e.to).get(e.rev).cap += d;
                        return d;
                    }
                }
            }
            return 0;
        }
        public int max_flow(int s, int t) {
            int flow = 0;
            while (true) {
                used = new boolean[graph.size()];
                int f = dfs(s, t, Integer.MAX_VALUE);
                if (f == 0) {
                    break;
                }
                flow += f;
            }
            return flow;
        }
        }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



