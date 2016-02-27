package atcoder.other2015.kupc2015;

import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Created by hama_du on 15/10/24.
 */
public class B {

    static int[] dx = {-2, 0, 2, -1, 0, 1, -2, -1, 0, 1, 2, -1, 0, 1, -2, 0, 2};
    static int[] dy = {-2, -2, -2, -1, -1, -1, 0, 0, 0, 0, 0, 1, 1, 1, 2, 2, 2};

    public static void main(String[] args) {
        PrintWriter out = new PrintWriter(System.out);


        char[][] ans = new char[10][10];
        for (int i = 0; i < 10 ; i++) {
            Arrays.fill(ans[i], '.');
        }

        int[][] map = new int[10][10];
        sch: for (int a = 0; a < 100 ; a++) {
            debug(a);
            for (int b = a+1; b < 100 ; b++) {
                for (int c = b+1 ; c < 100 ; c++) {
                    for (int d = c+1 ; d < 100 ; d++) {
                        int[] abc = new int[]{a, b, c, d};
                        for (int i = 0; i < 10 ; i++) {
                            Arrays.fill(map[i], 0);
                        }
                        int[][] v = new int[4][2];
                        for (int i = 0; i < 4 ; i++) {
                            v[i][0] = abc[i] / 10;
                            v[i][1] = abc[i] % 10;
                            for (int e = 0; e < dx.length ; e++) {
                                int ty = v[i][0] + dy[e];
                                int tx = v[i][1] + dx[e];
                                if (ty < 0 || tx < 0 || ty >= 10 || tx >= 10) {
                                    continue;
                                }
                                map[ty][tx] = 1;
                            }
                        }

                        int[][] dp = new int[10][10];
                        for (int i = 0; i < 10 ; i++) {
                            if (map[i][0] == 0) {
                                dp[i][0] = 1;
                            }
                        }
                        for (int j = 1; j < 10 ; j++) {
                            for (int i = 0; i < 10 ; i++) {
                                if (map[i][j] == 1) {
                                    continue;
                                }
                                for (int e = -1 ; e <= 1; e++) {
                                    if (i+e < 0 || i+e >= 10) {
                                        continue;
                                    }

                                    dp[i][j] = Math.min(2, dp[i][j]+dp[i+e][j-1]);
                                }
                            }
                        }
                        int ret = 0;
                        for (int i = 0; i < 10 ; i++) {
                            ret += dp[i][9];
                        }
                        if (ret == 1) {
                            for (int e = 0; e < 4 ; e++) {
                                ans[v[e][0]][v[e][1]] = 'C';
                            }
                            break sch;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 10 ; i++) {
            out.println(String.valueOf(ans[i]));
        }
        out.println();
        for (int i = 0; i < 10 ; i++) {
            for (int j = 0; j < 10 ; j++) {
                out.print(map[i][j]);
            }
            out.println();
        }
        out.flush();
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
