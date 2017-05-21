package gcj.gcj2017.round2;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class C {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            int n = in.nextInt();
            int m = in.nextInt();

            char[][] table = new char[n][m];
            for (int i = 0; i < n ; i++) {
                table[i] = in.next().toCharArray();
            }

            char[][] response = solve(table);
            out.println(String.format("Case #%d: %s", cs, response == null ? "IMPOSSIBLE" : "POSSIBLE"));
            if (response != null) {
                for (int i = 0; i < n; i++) {
                    out.println(String.valueOf(response[i]));
                }
            }

        }
        out.flush();
    }

    private static char[][] solve(char[][] table) {
        int n = table.length;
        int m = table[0].length;

        int[][] beamID = new int[n][m];

        int bn = 0;
        for (int i = 0; i < n; i++) {
            Arrays.fill(beamID[i], -1);
            for (int j = 0; j < m; j++) {
                if (table[i][j] == '|' || table[i][j] == '-') {
                    beamID[i][j] = bn;
                    bn++;
                }
            }
        }

        int[] flg = new int[bn];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                List<Integer> yoko = new ArrayList<>();
                List<Integer> tate = new ArrayList<>();
                for (int ti = i-1; ti >= 0; ti--) {
                    if (table[ti][j] == '#') {
                        break;
                    } else if (beamID[ti][j] >= 0) {
                        tate.add(beamID[ti][j]);
                    }
                }
                for (int ti = i+1; ti < n; ti++) {
                    if (table[ti][j] == '#') {
                        break;
                    } else if (beamID[ti][j] >= 0) {
                        tate.add(beamID[ti][j]);
                    }
                }
                for (int tj = j-1; tj >= 0; tj--) {
                    if (table[i][tj] == '#') {
                        break;
                    } else if (beamID[i][tj] >= 0) {
                        yoko.add(beamID[i][tj]);
                    }
                }
                for (int tj = j+1; tj < m; tj++) {
                    if (table[i][tj] == '#') {
                        break;
                    } else if (beamID[i][tj] >= 0) {
                        yoko.add(beamID[i][tj]);
                    }
                }
            }
        }

        return null;
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



