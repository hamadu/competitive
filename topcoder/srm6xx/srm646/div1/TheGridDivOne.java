package topcoder.srm6xx.srm646.div1;

/**
 * Created by hama_du
 */
import java.io.*;
import java.util.*;

public class TheGridDivOne {
    static int[] DX = new int[]{1, 0, -1, 0};
    static int[] DY = new int[]{0, 1, 0, -1};

    public int find(int[] x, int[] y, int k) {
        long[] xs = compress(x);
        long[] ys = compress(y);
        Map<Long,Integer> xmap = rmap(xs);
        Map<Long,Integer> ymap = rmap(ys);
        int xn = xs.length;
        int yn = ys.length;
        boolean[][] blocked = new boolean[xn][yn];
        for (int i = 0; i < x.length ; i++) {
            blocked[xmap.get(x[i]+0L)][ymap.get(y[i]+0L)] = true;
        }

        int[][][] graph = new int[xn][yn][4];
        for (int i = 0; i < xn; i++) {
            for (int j = 0; j < yn ; j++) {
                if (blocked[i][j]) {
                    Arrays.fill(graph[i][j], -1);
                    continue;
                }

                for (int d = 0 ; d <= 3 ; d++) {
                    int ti = i+DX[d];
                    int tj = j+DY[d];
                    if (ti < 0 || ti >= xn || tj < 0 || tj >= yn) {
                        graph[i][j][d] = -1;
                    } else {
                        if (blocked[ti][tj]) {
                            graph[i][j][d] = -1;
                        }
                    }
                }
            }
        }

        Dijkstra dijkstra = new Dijkstra(graph);
        dijkstra.xs = xs;
        dijkstra.ys = ys;

        long[][] ret = dijkstra.doit(xmap.get(0L), ymap.get(0L));
        long best = 0;
        for (int i = 0; i < xn ; i++) {
            for (int j = 0; j < yn ; j++) {
                if (ret[i][j] <= k && !blocked[i][j]) {
                    long cango = k - ret[i][j];
                    if (i == xn-1) {
                        best = Math.max(best, xs[i] + cango);
                        continue;
                    }
                    long till = xs[i+1] - xs[i];
                    if (blocked[i+1][j]) {
                        best = Math.max(best, xs[i]);
                    } else {
                        best = Math.max(best, xs[i]+Math.min(till, cango));
                    }
                }
            }
        }
        return (int)best;
    }

    static class Dijkstra {
        int[][][] graph;
        long[] xs;
        long[] ys;

        class State implements Comparable<State> {
            int nowX;
            int nowY;
            long time;

            State(int x, int y, long t) {
                nowX = x;
                nowY = y;
                time = t;
            }

            @Override
            public int compareTo(State o) {
                return Long.compare(time, o.time);
            }
        }

        public Dijkstra(int[][][] graph) {
            this.graph = graph;
        }

        long[][] doit(int fromX, int fromY) {
            int xn = graph.length;
            int yn = graph[0].length;
            long[][] dp = new long[xn][yn];
            for (int i = 0; i < xn ; i++) {
                Arrays.fill(dp[i], Long.MAX_VALUE);
            }
            Queue<State> q = new PriorityQueue<>();
            q.add(new State(fromX, fromY, 0));
            dp[fromX][fromY] = 0;
            while (q.size() >= 1) {
                State st = q.poll();
                for (int d = 0 ; d < 4 ; d++) {
                    if (graph[st.nowX][st.nowY][d] == -1) {
                        continue;
                    }
                    int toX = st.nowX+DX[d];
                    int toY = st.nowY+DY[d];
                    long time = st.time+Math.abs(xs[st.nowX]-xs[toX])+Math.abs(ys[st.nowY]-ys[toY]);
                    if (dp[toX][toY] > time) {
                        dp[toX][toY] = time;
                        q.add(new State(toX, toY, time));
                    }
                }
            }
            return dp;
        }
    }

    static Map<Long,Integer> rmap(long[] xs) {
        Map<Long,Integer> mp = new HashMap<>();
        for (int i = 0; i < xs.length; i++) {
            mp.put(xs[i], i);
        }
        return mp;
    }

    static long[] compress(int[] x) {
        List<Long> xl = new ArrayList<>();
        xl.add(0L);
        for (int i = 0; i < x.length ; i++) {
            for (int d = -1 ; d <= 1; d++) {
                xl.add(x[i]+d+0L);
            }
        }
        Collections.sort(xl);
        int xc = 1;
        for (int i = 1; i < xl.size(); i++) {
            if (xl.get(i) - xl.get(i-1) != 0) {
                xc++;
            }
        }
        long[] xs = new long[xc];
        int xi = 0;
        for (int i = 0; i < xl.size() ; i++) {
            if (xi == 0 || xs[xi-1] != xl.get(i)) {
                xs[xi++] = xl.get(i);
            }
        }
        return xs;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
