package topcoder.srm5xx.srm517;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hama_du on 15/08/14.
 */
public class ColorfulBoard {
    private static final int INF = 114514;

    public int theMin(String[] board) {
        int n = board.length;
        int m = board[0].length();
        char[][] map = new char[n][m];
        for (int i = 0; i < n ; i++) {
            map[i] = board[i].toCharArray();
        }
        char[][] rmap = new char[m][n];
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < n; j++) {
                rmap[i][j] = map[j][i];
            }
        }

        int r = Math.min(solve(map), solve(rmap));
        return r == INF ? -1 : r;
    }

    private int solve(char[][] map) {
        int n = map.length;
        int ret = INF;
        for (int i = 0; i < n ; i++) {
            ret = Math.min(ret, solve(map, i));
        }
        return ret;
    }

    private int solve(char[][] map, int pos) {
        int n = map.length;
        int m = map[0].length;

        List<Integer>[] graph = new List[m];
        for (int i = 0; i < m ; i++) {
            graph[i] = new ArrayList<>();
        }
        boolean[][] edge = new boolean[m][m];

        for (int i = 0; i < n ; i++) {
            if (i == pos) {
                continue;
            }
            char rowPaint = ' ';
            int[] ord = new int[m];
            for (int j = 0; j < m ; j++) {
                if (map[i][j] != map[pos][j]) {
                    ord[j] = -1;
                    if (rowPaint != ' ' && rowPaint != map[i][j]) {
                        return INF;
                    }
                    rowPaint = map[i][j];
                }
            }
            for (int j = 0; j < m ; j++) {
                if (map[i][j] == map[pos][j] && map[i][j] != rowPaint) {
                    ord[j] = 1;
                }
            }

            for (int j = 0; j < m ; j++) {
                for (int k = 0; k < m ; k++) {
                    if (ord[j] == -1 && ord[k] == 1 && !edge[j][k]) {
                        edge[j][k] = true;
                        graph[j].add(k);
                    }
                }
            }
        }
        if (toposort(graph) == null) {
            return INF;
        }
        int ct = m;
        String line = String.valueOf(map[pos]);
        for (int i = 0; i < n ; i++) {
            if (!String.valueOf(map[i]).equals(line)) {
                ct++;
            }
        }
        return ct;
    }

    static int[] toposort(List<Integer>[] graph) {
        int n = graph.length;
        int[] in = new int[n];
        for (int i = 0 ; i < n ; i++) {
            for (int t : graph[i]) {
                in[t]++;
            }
        }

        int[] res = new int[n];
        int idx = 0;
        for (int i = 0 ; i < n ; i++) {
            if (in[i] == 0) {
                res[idx++] = i;
            }
        }
        for (int i = 0 ; i < idx ; i++) {
            for (int t : graph[res[i]]) {
                in[t]--;
                if (in[t] == 0) {
                    res[idx++] = t;
                }
            }
        }
        for (int i = 0 ; i < n ; i++) {
            if (in[i] >= 1) {
                return null;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        ColorfulBoard bd = new ColorfulBoard();
        debug(bd.theMin(new String[]
            {
                "ffffffffff",
                "xfxxoofoxo",
                "ffffffffff",
                "xfxxoofoxo",
                "ffffffffff",
                "ooxxoofoxo",
                "xfxxoofoxo",
                "xfxxoxfxxo",
                "ffxxofffxo",
                "xfxxoxfxxo"
            }
        ));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
