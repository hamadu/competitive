package topcoder.srm5xx.srm533;

/**
 * Created by hama_du on 15/08/23.
 */
public class MagicBoard {
    private static final String YES = "YES";
    private static final String NO = "NO";

    public String ableToUnlock(String[] board) {
        int n = board.length;
        int m = board[0].length();

        char[][] map = new char[n][m];
        for (int i = 0; i < n ; i++) {
            map[i] = board[i].toCharArray();
        }

        UnionFind uf = new UnionFind(n*m);
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (map[i][j] == '#') {
                    for (int k = 0; k < n; k++) {
                        if (map[k][j] == '#') {
                            uf.unite(i*m+j, k*m+j);
                        }
                    }
                    for (int k = 0; k < m; k++) {
                        if (map[i][k] == '#') {
                            uf.unite(i*m+j, i*m+k);
                        }
                    }
                }
            }
        }

        int theID = -1;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (map[i][j] == '#') {
                    int id = uf.find(i*m+j);
                    if (theID != -1 && theID != id) {
                        return NO;
                    }
                    theID = id;
                }
            }
        }

        int beginOrEnd = 0;
        for (int i = 0; i < n ; i++) {
            int rowCnt = 0;
            for (int j = 0; j < m; j++) {
                if (map[i][j] == '#') {
                    rowCnt++;
                }
            }
            beginOrEnd += rowCnt % 2;
        }

        boolean isOK = false;
        for (int j = 0; j < m; j++) {
            int rowCnt = 0;
            for (int i = 0; i < n ; i++) {
                if (map[i][j] == '#') {
                    rowCnt++;
                }
            }
            beginOrEnd += rowCnt % 2;
            if (rowCnt % 2 == 1) {
                isOK = true;
            }
        }
        if (beginOrEnd >= 3) {
            return NO;
        }
        if (beginOrEnd == 2 && !isOK) {
            return NO;
        }
        return YES;
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

