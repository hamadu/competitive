package topcoder.srm6xx.srm650.div1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hama_du on 2016/06/21.
 */
public class TheKingsRoadsDiv1 {
    public String getAnswer(int h, int[] a, int[] b) {
        limit = System.currentTimeMillis() + 1900;
        return solve(h, a, b) ? "Correct" : "Incorrect";
    }

    long limit;

    int H;
    private boolean solve(int h, int[] a, int[] b) {
        H = h;
        int n = (1<<h)-1;
        boolean[][] vv = new boolean[n][n];
        for (int i = 0; i < n ; i++) {
            vv[i][i] = true;
        }
        for (int i = 0; i < a.length ; i++) {
            a[i]--;
            b[i]--;
        }

        int yobun = 0;
        List<int[]> validEdge = new ArrayList<>();
        for (int i = 0; i < a.length ; i++) {
            if (vv[a[i]][b[i]]) {
                yobun++;
            } else {
                validEdge.add(new int[]{a[i], b[i]});
            }
            vv[a[i]][b[i]] = vv[b[i]][a[i]] = true;
        }
        if (yobun > 3) {
            return false;
        }
        graph = buildGraph(n, validEdge);
        loop = findLoopEdges(graph);
        taken = new boolean[n][n];
        Collections.shuffle(loop);

        int shouldRemove = 3 - yobun;
        int[][] e = new int[shouldRemove][2];

        int[] deg = new int[n];
        for (int i = 0; i < n ; i++) {
            deg[i] = graph[i].length;
        }
        try {
            dfs(0, 0, deg, e, shouldRemove);
        } catch (FoundException fe) {
            return true;
        } catch (TLEException te) {
            return false;
        }
        return false;
    }

    int[][] graph;
    List<int[]> loop;
    boolean[][] taken;

    private void dfs(int tn, int ei, int[] deg, int[][] e, int maxTake) {
        if (System.currentTimeMillis() > limit) {
            throw new TLEException();
        }
        if (tn == maxTake) {
            if (isValid(deg)) {
                throw new FoundException();
            }
            return;
        }
        if (ei >= loop.size()) {
            return;
        }
        dfs(tn, ei+1, deg, e, maxTake);

        int[] ab = loop.get(ei);
        deg[ab[0]]--;
        deg[ab[1]]--;
        taken[ab[0]][ab[1]] = taken[ab[1]][ab[0]] = true;
        dfs(tn+1, ei+1, deg, e, maxTake);
        taken[ab[0]][ab[1]] = taken[ab[1]][ab[0]] = false;
        deg[ab[0]]++;
        deg[ab[1]]++;
    }

    private boolean isValid(int[] deg) {
        int n = graph.length;
        int[] rt = new int[4];
        int root = -1;
        for (int i = 0; i < n ; i++) {
            if (deg[i] < 0 || deg[i] >= 4) {
                return false;
            }
            rt[deg[i]]++;
            if (deg[i] == 2) {
                root = i;
            }
        }
        if (rt[0] >= 1 || rt[2] != 1 || rt[1] != (1<<(H-1))) {
            return false;
        }

        boolean[] used = new boolean[n];
        int[] que = new int[n];
        int qh = 0;
        int qt = 0;
        used[root] = true;
        que[qh++] = root;

        for (int i = 0; i < H ; i++) {
            if (qh - qt != (1<<i)) {
                return false;
            }
            int tqh = qh;
            while (qt < tqh) {
                int now = que[qt++];
                for (int to : graph[now]) {
                    if (taken[now][to]) {
                        continue;
                    }
                    if (used[to]) {
                        continue;
                    }
                    used[to] = true;
                    que[qh++] = to;
                }
            }
        }
        return true;
    }

    class FoundException extends RuntimeException {
    }

    class TLEException extends RuntimeException {
    }

    static List<int[]> findLoopEdges(int[][] graph) {
        int n = graph.length;
        boolean[][] cut = new boolean[n][n];
        int[] que = new int[2*n];
        int[] deg = new int[n];
        int qh = 0;
        int qt = 0;
        boolean[] done = new boolean[n];
        for (int i = 0; i < n ; i++) {
            deg[i] = graph[i].length;
            if (deg[i] == 1) {
                que[qh++] = i;
                done[i] = true;
            }
        }
        while (qt < qh) {
            int now = que[qt++];
            for (int to : graph[now]) {
                if (cut[now][to]) {
                    continue;
                }
                cut[now][to] = cut[to][now] = true;
                deg[to]--;
                if (deg[to] <= 1 && !done[to]) {
                    que[qh++] = to;
                    done[to] = true;
                }
            }
        }
        List<int[]> loopEdges = new ArrayList<>();
        for (int i = 0; i < n ; i++) {
            for (int j : graph[i]) {
                if (!cut[i][j] && i < j) {
                    loopEdges.add(new int[]{i, j});
                }
            }
        }
        return loopEdges;
    }

    static int[][] buildGraph(int n, List<int[]> edges) {
        int m = edges.size();
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = edges.get(i)[0];
            int b = edges.get(i)[1];
            deg[a]++;
            deg[b]++;
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges.get(i)[0];
            int b = edges.get(i)[1];
            graph[a][--deg[a]] = b;
            graph[b][--deg[b]] = a;
        }
        return graph;
    }
}
