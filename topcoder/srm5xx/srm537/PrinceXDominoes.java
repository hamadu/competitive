package topcoder.srm5xx.srm537;

import java.util.*;

/**
 * Created by hama_du on 15/08/24.
 */
public class PrinceXDominoes {
    public int play(String[] dominoes) {
        int n = dominoes.length;

        boolean[][] g = new boolean[n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                g[i][j] = dominoes[i].charAt(j) != '.';
            }
        }
        for (int i = 0; i < n ; i++) {
            g[i][i] = true;
        }
        for (int k = 0; k < n ; k++) {
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < n ; j++) {
                    g[i][j] |= g[i][k] && g[k][j];
                }
            }
        }
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (!g[i][j]) {
                    return -1;
                }
            }
        }

        int[] in = new int[n];
        int[] out = new int[n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                char c = dominoes[j].charAt(i);
                in[i] += c == '.'  ? 0 : (c - 'A' + 1);
                out[j] += c == '.'  ? 0 : (c - 'A' + 1);
            }
        }
        int source = n;
        int sink = source+1;

        init(n+2);
        int flow = 0;
        for (int i = 0; i < n ; i++) {
            if (in[i] < out[i]) {
                edge(source, i, out[i]-in[i], 0);
                flow += out[i]-in[i];
            } else if (in[i] > out[i]) {
                edge(i, sink, in[i]-out[i], 0);
            }
        }
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                char c = dominoes[i].charAt(j);
                if (c != '.') {
                    int d = c - 'A';
                    edge(i, j, d, 1);
                }
            }
        }

        int total = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                char c = dominoes[i].charAt(j);
                total += (c == '.') ? 0 : (c - 'A' + 1);
            }
        }

        int F = (int)min_cost_flow(source, sink, flow);
        if (F == -1) {
            return -1;
        }
        return total - F;
    }

    public static class State implements Comparable<State> {
        int dist;
        int now;
        public State(int _n, int _d) {
            now = _n;
            dist = _d;
        }

        @Override
        public int compareTo(State o) {
            return dist - o.dist;
        }
    }

    public static class Edge {
        int to;
        int cap;
        int rev;
        int cost;
        public Edge(int _to, int _cap, int _cost, int _rev) {
            to = _to;
            cap = _cap;
            rev = _rev;
            cost = _cost;
        }
    }

    public static int INF = 1000000000;
    public static int V;
    public static int[] h;
    public static int[] dist;
    public static int[] prevv, preve;
    public static List<Edge>[] graph;

    @SuppressWarnings("unchecked")
    public static void init(int size) {
        graph = new List[size];
        for (int i = 0 ; i < size ; i++) {
            graph[i] = new ArrayList<Edge>();
        }
        dist = new int[size];
        prevv = new int[size];
        preve = new int[size];
        h = new int[size];
        V = size;
    }

    public static void edge(int from, int to, int cap, int cost) {
        graph[from].add(new Edge(to, cap, cost, graph[to].size()));
        graph[to].add(new Edge(from, 0, -cost, graph[from].size() - 1));
    }


    public static long min_cost_flow(int s, int t, int f) {
        long res = 0;
        Arrays.fill(h, 0);
        Queue<State> q = new PriorityQueue<State>();
        while (f > 0) {
            q.clear();
            Arrays.fill(dist, INF);
            dist[s] = 0;
            q.add(new State(s, 0));
            while (q.size() >= 1) {
                State stat = q.poll();
                int v = stat.now;
                if (dist[v] < stat.dist) {
                    continue;
                }
                for (int i = 0 ; i < graph[v].size(); i++) {
                    Edge e = graph[v].get(i);
                    if (e.cap > 0 && dist[e.to] > dist[v] + e.cost + h[v] - h[e.to]) {
                        dist[e.to] = dist[v] + e.cost + h[v] - h[e.to];
                        prevv[e.to] = v;
                        preve[e.to] = i;
                        q.add(new State(e.to, dist[e.to]));
                    }
                }
            }
            if (dist[t] == INF) {
                return -1;
            }
            for (int v = 0 ; v < V ; v++) {
                h[v] += dist[v];
            }
            long d = f;
            for (int v = t ; v != s ; v = prevv[v]) {
                d = Math.min(d, graph[prevv[v]].get(preve[v]).cap);
            }
            f -= d;
            res += d * h[t];
            for (int v = t ; v != s ; v = prevv[v]) {
                Edge e = graph[prevv[v]].get(preve[v]);
                e.cap -= d;
                Edge rev = graph[v].get(e.rev);
                rev.cap += d;
            }
        }
        return res;
    }
}
