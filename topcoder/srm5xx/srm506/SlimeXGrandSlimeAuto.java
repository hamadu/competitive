package topcoder.srm5xx.srm506;

import java.util.*;

/**
 * Created by hama_du on 15/08/05.
 */
public class SlimeXGrandSlimeAuto {

    public static int DINF = 10000000;

    public int travel(int[] cars, int[] districts, String[] roads, int inverseWalkSpeed, int inverseDriveSpeed) {
        int on = districts.length;
        int n = roads.length;

        int[][] graph = new int[n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                char c = roads[i].charAt(j);
                if (c == '.') {
                    graph[i][j] = DINF;
                } else if ('0' <= c && c <= '9') {
                    graph[i][j] = (c-'0')+1;
                } else if ('a' <= c && c <= 'z') {
                    graph[i][j] = (c-'a')+11;
                } else {
                    graph[i][j] = (c-'A')+37;
                }
            }
        }
        for (int i = 0; i < n ; i++) {
            graph[i][i] = 0;
        }

        for (int k = 0; k < n ; k++) {
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < n ; j++) {
                    graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
                }
            }
        }

        init(on+n+2);

        int[] carCount = new int[n];
        for (int ci : cars) {
            carCount[ci]++;
        }

        int source = on+n;
        int sink = source+1;
        for (int i = 0; i < on ; i++) {
            edge(source, i, 1, 0);
        }
        for (int i = 0; i < n ; i++) {
            edge(on+i, sink, carCount[i], 0);
        }
        for (int i = 0; i < on ; i++) {
            int prev = (i >= 1) ? districts[i-1] : 0;
            int go = districts[i];
            int walkCost = graph[prev][go] * inverseWalkSpeed;
            edge(i, sink, 1, walkCost);
            for (int k = 0; k < n ; k++) {
                if (carCount[k] >= 1) {
                    int driveCost = graph[prev][k] * inverseWalkSpeed + graph[k][go] * inverseDriveSpeed;
                    edge(i, on+k, 1, driveCost);
                }
            }
        }
        return (int)min_cost_flow(source, sink, on);
    }


    public static class State implements Comparable<State> {
        long dist;
        int now;
        public State(int _n, long _d) {
            now = _n;
            dist = _d;
        }

        @Override
        public int compareTo(State o) {
            return Long.signum(dist - o.dist);
        }
    }

    public static class Edge {
        int to;
        int cap;
        int rev;
        long cost;
        public Edge(int _to, int _cap, long _cost, int _rev) {
            to = _to;
            cap = _cap;
            rev = _rev;
            cost = _cost;
        }
    }

    public static long INF = 10000000000000L;
    public static int V;
    public static long[] h;
    public static long[] dist;
    public static int[] prevv, preve;
    public static List<Edge>[] graph;

    @SuppressWarnings("unchecked")
    public static void init(int size) {
        graph = new List[size];
        for (int i = 0 ; i < size ; i++) {
            graph[i] = new ArrayList<Edge>();
        }
        dist = new long[size];
        prevv = new int[size];
        preve = new int[size];
        h = new long[size];
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
                return res;
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
