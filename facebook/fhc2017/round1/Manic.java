package facebook.fhc2017.round1;

import java.io.PrintWriter;
import java.util.*;

public class Manic {
    private static final long INF = 1_000_000_000_000_000L;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = in.nextInt();
        for (int c = 1 ; c <= T ; c++) {
            int n = in.nextInt();
            int m = in.nextInt();
            int k = in.nextInt();
            long[][] graph = new long[n][n];
            for (int i = 0; i < n ; i++) {
                Arrays.fill(graph[i], INF);
            }
            for (int i = 0; i < n ; i++) {
                graph[i][i] = 0;
            }
            for (int i = 0; i < m; i++) {
                int a = in.nextInt()-1;
                int b = in.nextInt()-1;
                int g = in.nextInt();
                graph[a][b] = Math.min(graph[a][b], g);
                graph[b][a] = Math.min(graph[b][a], g);
            }

            int[][] sd = new int[k][2];
            for (int i = 0; i < k ; i++) {
                for (int j = 0; j < 2 ; j++) {
                    sd[i][j] = in.nextInt()-1;
                }
            }

            out.println(String.format("Case #%d: %d", c, solve(graph, sd)));
        }
        out.flush();
    }

    private static long solve(long[][] graph, int[][] queries) {
        int n = graph.length;
        for (int k = 0; k < n ; k++) {
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < n ; j++) {
                    graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
                }
            }
        }

        int Q = queries.length;
        long[][][] dp = new long[Q+1][3][2];
        for (int i = 0; i <= Q ; i++) {
            for (int j = 0; j < 3 ; j++) {
                Arrays.fill(dp[i][j], INF);
            }
        }
        dp[0][1][1] = graph[0][queries[0][0]];
        Queue<State> q = new PriorityQueue<>();
        q.add(new State(0, 1, 1, dp[0][1][1]));

        while (q.size() >= 1) {
            State st = q.poll();
            if (dp[st.k][st.load][st.which] < st.time) {
                continue;
            }

            int now = (st.which == 0) ? queries[st.k-1][1] : queries[st.k+st.load-1][0];

            if (st.load >= 1) {
                long to = st.time + graph[now][queries[st.k][1]];
                if (dp[st.k+1][st.load-1][0] > to) {
                    dp[st.k+1][st.load-1][0] = to;
                    q.add(new State(st.k+1, st.load-1, 0, to));
                }
            }
            if (st.load <= 1) {
                int nextK = st.k + st.load;
                if (nextK < Q) {
                    long to = st.time + graph[now][queries[nextK][0]];
                    if (dp[st.k][st.load+1][1] > to) {
                        dp[st.k][st.load+1][1] = to;
                        q.add(new State(st.k, st.load+1, 1, to));
                    }
                }
            }
        }

        return dp[Q][0][0] >= INF ? -1 : dp[Q][0][0];
    }

    static class State implements Comparable<State> {
        int k;
        int load;
        int which;
        long time;

        State(int a, int b, int c, long d) {
            k = a;
            load = b;
            which = c;
            time = d;
        }

        @Override
        public int compareTo(State o) {
            return Long.compare(time, o.time);
        }
    }
}
