package topcoder.srm5xx.srm530;

/**
 * Created by hama_du on 15/08/19.
 */
public class GogoXCake {
    public String solve(String[] cake, String[] cutter) {
        int n = cake.length;
        int m = cake[0].length();
        char[][] cmap = new char[n][m];
        for (int i = 0; i < n; i++) {
            cmap[i] = cake[i].toCharArray();
        }
        int k = cutter.length;
        int l = cutter[0].length();
        char[][] cut = new char[k][l];
        for (int i = 0; i < k ; i++) {
            cut[i] = cutter[i].toCharArray();
        }
        return isPossible(cmap, cut) ? "YES" : "NO";
    }

    private boolean isPossible(char[][] cake, char[][] cutter) {
        int n = cake.length;
        int m = cake[0].length;
        int cn = cutter.length;
        int cm = cutter[0].length;
        for (int i = 0; i+cn <= n ; i++) {
            for (int j = 0; j+cm <= m ; j++) {
                boolean isPossible = true;
                sch: for (int k = 0; k < cn; k++) {
                    for (int l = 0; l < cm; l++) {
                        if (cutter[k][l] == '.' && cake[i+k][j+l] == 'X') {
                            isPossible = false;
                            break sch;
                        }


                    }
                }
                if (isPossible) {
                    for (int k = 0; k < cn; k++) {
                        for (int l = 0; l < cm; l++) {
                            if (cutter[k][l] == '.') {
                                cake[i+k][j+l] = 'X';
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (cake[i][j] == '.') {
                    return false;
                }
            }
        }
        return true;
    }
}
