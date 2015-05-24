package bestcoder.br042;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by dhamada on 15/05/23.
 */
public class C {

    // 4
    // 1000037 2000074 1000037 2000074
    // 2000074 2000074 1000037 1000037


    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            String l = in.readLine();
            if (l == null) {
                break;
            }
            String[] nmk = l.split(" ");
            int n = Integer.valueOf(nmk[0]);
            int m = Integer.valueOf(nmk[1]);
            int K = Integer.valueOf(nmk[2]);

            int[][] map = new int[n][m];
            for (int i = 0; i < n; i++) {
                String[] si = in.readLine().split(" ");
                for (int j = 0; j < m; j++) {
                    map[i][j] = Integer.valueOf(si[j]);
                }
            }

            int[][][] dp = new int[n][m][101];

            dp[0][0][0] = dp[0][0][map[0][0]] = 1;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    for (int k = 0; k <= 100 ; k++) {
                        if (dp[i][j][k] != 1) {
                            continue;
                        }
                        if (i < n-1) {
                            dp[i+1][j][k] = 1;
                            int tk = k + map[i+1][j];
                            if (tk <= K) {
                                dp[i+1][j][tk] = 1;
                            }
                        }
                        if (j < m-1) {
                            dp[i][j+1][k] = 1;
                            int tk = k + map[i][j+1];
                            if (tk <= K) {
                                dp[i][j+1][tk] = 1;
                            }
                        }
                    }
                }
            }

            int best = 0;
            for (int k = K ; k >= 0 ; k--) {
                if (dp[n-1][m-1][k] == 1) {
                    best = k;
                    break;
                }
            }
            out.println(best);
        }
        out.flush();
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
