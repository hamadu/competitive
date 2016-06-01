package topcoder.srm6xx.srm691.div1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by hama_du on 2016/05/30.
 */
public class Moneymanager {
    public int getbest(int[] a, int[] b, int X) {
        List<int[]> w = new ArrayList<>();
        int n = a.length;
        for (int i = 0; i < n ; i++) {
            w.add(new int[]{a[i], b[i]});
        }
        Collections.sort(w, (o1, o2) -> (o2[0] * o1[1]) - (o1[0] * o2[1]));

        int bsum = w.stream().mapToInt(wi -> wi[1]).sum();
        int best = 0;
        for (int bs = n / 2 ; bs <= 10 * n / 2 ; bs++) {
            int[][][] dp = new int[n+1][n/2+1][n*10+1];
            for (int i = 0; i <= n ; i++) {
                for (int j = 0; j <= n/2; j++) {
                    Arrays.fill(dp[i][j], -1);
                }
            }
            dp[0][0][0] = 0;

            int partSum = 0;
            for (int i = 0; i < n ; i++) {
                int A = w.get(i)[0];
                int B = w.get(i)[1];
                for (int c = 0; c <= n / 2 ; c++) {
                    for (int lb = 0; lb <= bs ; lb++) {
                        if (dp[i][c][lb] < 0) {
                            continue;
                        }
                        int base = dp[i][c][lb];
                        if (c < n / 2 && lb+B <= bs) {
                            dp[i+1][c+1][lb+B] = Math.max(dp[i+1][c+1][lb+B], base + (bsum - lb) * A);
                        }

                        int rb = partSum - lb;
                        dp[i+1][c][lb] = Math.max(dp[i+1][c][lb], base + (bsum - bs - rb) * A + X * B);
                    }
                }
                partSum += B;
            }
            best = Math.max(best, dp[n][n/2][bs]);
        }
        return best;
    }


    public static void main(String[] args) {
        Moneymanager manager = new Moneymanager();
        debug(manager.getbest(
                new int[]{30,13,28,59,26,62,48,75,6,69,94,51},
                new int[]{4,6,4,5,4,3,1,5,6,5,2,2},
                62
        ));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}

