package topcoder.srm5xx.srm587;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/26.
 */
public class ThreeColorability {
    public String[] lexSmallest(String[] cells) {
        int n = cells.length;
        int m = cells[0].length();
        char[][] map = new char[n][m];
        for (int i = 0; i < n ; i++) {
            map[i] = cells[i].toCharArray();
        }

        char[][] ret = solve(map);
        if (ret == null) {
            return new String[]{};
        }
        String[] ls = new String[n];
        for (int i = 0; i < n ; i++) {
            ls[i] = String.valueOf(ret[i]);
        }
        debug(ls);
        return ls;
    }

    private char[][] solve(char[][] map) {
        int n = map.length;
        int m = map[0].length;
        char[][] ret = new char[n][m];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m; j++) {
                ret[i][j] = map[i][j];
            }
        }

        if (!isOK(ret) || !isOK(flip(ret))) {
            return null;
        }

        for (int j = 0; j < m ; j++) {
            if (ret[0][j] == '?') {
                ret[0][j] = 'N';
                if (!isOK(ret) || !isOK(flip(ret))) {
                    ret[0][j] = 'Z';
                }
            }
        }

        for (int i = 1 ; i < n ; i++) {
            boolean same = false;
            boolean diff = false;
            for (int j = 0; j < m ; j++) {
                if (ret[i][j] != '?') {
                    if (ret[0][j] == ret[i][j]) {
                        same = true;
                    } else {
                        diff = true;
                    }
                }
            }
            if (same && diff) {
                return null;
            } else if (!same && !diff) {
                if (ret[0][0] == 'N') {
                    same = true;
                }
            }
            for (int j = 0; j < m ; j++) {
                ret[i][j] = (same ^ ret[0][j] == 'Z') ? 'N' : 'Z';
            }
        }
        return ret;
    }

    private char[][] flip(char[][] ret) {
        int n = ret.length;
        int m = ret[0].length;
        char[][] r = new char[m][n];
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < n ; j++) {
                r[i][j] = ret[j][i];
            }
        }
        return r;
    }


    private boolean isOK(char[][] map) {
        int n = map.length;
        int m = map[0].length;

        int[][] rel = new int[m][m];
        for (int k = 0; k < n ; k++) {
            for (int i = 0; i < m ; i++) {
                for (int j = i+1 ; j < m; j++) {
                    if (map[k][i] != '?' && map[k][j] != '?') {
                        if (map[k][i] == map[k][j]) {
                            if (rel[i][j] == -1) {
                                return false;
                            }
                            rel[i][j] = rel[j][i] = 1;
                        } else {
                            if (rel[i][j] == 1) {
                                return false;
                            }
                            rel[i][j] = rel[j][i] = -1;
                        }
                    }
                }
            }
        }
        boolean isOK = true;
        int[] col = new int[m];
        for (int i = 0; i < m ; i++) {
            if (col[i] == 0) {
                isOK &= dfs(0, 1, rel, col);
            }
        }
        return isOK;
    }

    private boolean dfs(int now, int paint, int[][] rel, int[] col) {
        if (col[now] != 0) {
            return col[now] == paint;
        }
        col[now] = paint;

        boolean ok = true;
        for (int to = 0; to < rel.length; to++) {
            if (to != now && rel[now][to] != 0) {
                ok &= dfs(to, paint * rel[now][to], rel, col);
            }
        }
        return ok;
    }


    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    static class UnionFind {
        int[] parent, rank;
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0 ; i < n ; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        int find(int x) {
            if (parent[x] == x) {
                return x;
            }
            parent[x] = find(parent[x]);
            return parent[x];
        }

        void unite(int x, int y) {
            x = find(x);
            y = find(y);
            if (x == y) {
                return;
            }
            if (rank[x] < rank[y]) {
                parent[x] = y;
            } else {
                parent[y] = x;
                if (rank[x] == rank[y]) {
                    rank[x]++;
                }
            }
        }
        boolean issame(int x, int y) {
            return (find(x) == find(y));
        }
    }
}
