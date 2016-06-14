package gcj.gcj2014.round3;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by hama_du on 2016/06/10.
 */
public class B {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            int p = in.nextInt();
            int q = in.nextInt();
            int n = in.nextInt();
            int[][] enemies = new int[n][2];
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < 2 ; j++) {
                    enemies[i][j] = in.nextInt();
                }
            }
            out.println(String.format("Case #%d: %d", cs, solve(enemies, p, q)));
        }
        out.flush();
    }

    private static int solve(int[][] enemies, int p, int q) {
        int n = enemies.length;
        int MAXTAME = 1200;
        int[][][][] dp = new int[2][n+1][201][MAXTAME];
        for (int i = 0; i < 2 ; i++) {
            for (int j = 0; j <= n; j++) {
                for (int k = 0; k <= 200; k++) {
                    Arrays.fill(dp[i][j][k], -1);
                }
            }
        }
        dp[0][0][enemies[0][0]][0] = 0;
        for (int ei = 0; ei < n; ei++) {
            int maxHP = enemies[ei][0];
            int money = enemies[ei][1];
            int nextMax = ei+1 < n ? enemies[ei+1][0] : 0;
            for (int dphp = 0 ; dphp <= maxHP ; dphp++) {
                int hp = dphp == 0 ? maxHP : dphp-1;
                for (int deco = 0; deco < MAXTAME ; deco++) {
                    for (int turn = 0 ; turn <= 1 ; turn++) {
                        if (dp[turn][ei][hp][deco] < 0) {
                            continue;
                        }
                        int base = dp[turn][ei][hp][deco];
                        if (hp == maxHP) {
                            for (int d = 0 ; d <= deco ; d++) {
                                int dec = p * d;
                                if (dec >= maxHP) {
                                    dp[turn][ei+1][nextMax][deco-d] = Math.max(dp[turn][ei+1][nextMax][deco-d], base+money);
                                    break;
                                } else {
                                    dp[turn][ei][dec][deco-d] = Math.max(dp[turn][ei][dec][deco-d], base);
                                }
                            }
                        } else {
                            if (turn == 0) {
                                // my turn

                                // hit head
                                if (hp+p >= maxHP) {
                                    dp[1-turn][ei+1][nextMax][deco] = Math.max(dp[1-turn][ei+1][nextMax][deco], base+money);
                                } else {
                                    dp[1-turn][ei][hp+p][deco] = Math.max(dp[1-turn][ei][hp+p][deco], base);
                                }

                                // hit next
                                if (deco+1 < MAXTAME) {
                                    dp[1-turn][ei][hp][deco+1] = Math.max(dp[1-turn][ei][hp][deco+1], base);
                                }

                                // or do nothing
                                dp[1-turn][ei][hp][deco] = Math.max(dp[1-turn][ei][hp][deco], base);
                            } else {
                                // tower turn
                                if (hp+q >= maxHP) {
                                    dp[1-turn][ei+1][nextMax][deco] = Math.max(dp[1-turn][ei+1][nextMax][deco], base);
                                } else {
                                    dp[1-turn][ei][hp+q][deco] = Math.max(dp[1-turn][ei][hp+q][deco], base);
                                }
                            }
                        }
                    }
                }
            }
        }

        int max = 0;
        for (int t = 0; t <= 1; t++) {
            max = Math.max(max, dp[t][n][0][0]);
        }
        return max;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
