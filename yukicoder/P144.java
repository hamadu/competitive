package yukicoder;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by dhamada on 15/05/12.
 */
public class P144 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        double p = in.nextDouble();

        double[] dp = new double[n+1];
        Arrays.fill(dp, 1.0d);
        for (int i = 2 ; i <= n ; i++) {
            for (int ii = i*2 ; ii <= n ; ii += i) {
                dp[ii] *= (1.0 - p);
            }
        }

        double sum = 0;
        for (int i = 2 ; i <= n ; i++) {
            sum += dp[i];
        }
        System.out.println(sum);
    }
}
