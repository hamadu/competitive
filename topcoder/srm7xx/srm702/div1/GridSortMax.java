package topcoder.srm7xx.srm702.div1;

public class GridSortMax {
    public String findMax(int n, int m, int[] grid) {
        int[][] g = new int[n][m];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                g[i][j] = grid[i*m+j]-1;
            }
        }

        int[] colMap = new int[m];
        int[] rowMap = new int[n];
        for (int i = 0; i < m ; i++) {
            colMap[i] = i;
        }
        for (int i = 0; i < n ; i++) {
            rowMap[i] = i;
        }

        // find first row
        fixedRow = new boolean[n];
        fixedCol = new boolean[m];
        for (int row = 0 ; row < n ; row++) {
            for (int col = 0 ; col < m ; col++) {
                int find = row * m + col;
                sch: for (int i = 0; i < n; i++) {
                    for (int j = 0; j < m; j++) {
                        if (g[rowMap[i]][colMap[j]] == find && canSwapRow(i, row) && canSwapCol(j, col)) {
                            fixedRow[row] = true;
                            fixedCol[col] = true;
                            swap(rowMap, i, row);
                            swap(colMap, j, col);
                            break sch;
                        }
                    }
                }
            }
        }

        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (g[rowMap[i]][colMap[j]] == i*m+j) {
                    ret.append('1');
                } else {
                    ret.append('0');
                }
            }
        }
        return ret.toString();
    }

    boolean[] fixedRow;
    boolean[] fixedCol;

    boolean canSwapCol(int a, int b) {
        return (a == b) || (!fixedCol[a] && !fixedCol[b]);
    }

    boolean canSwapRow(int a, int b) {
        return (a == b) || (!fixedRow[a] && !fixedRow[b]);
    }

    void swap(int[] g, int a, int b) {
        int tmp = g[a];
        g[a] = g[b];
        g[b] = tmp;
    }
}
