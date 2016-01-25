package topcoder.srm5xx.srm578;

/**
 * Created by hama_du on 15/09/13.
 */
public class WolfInZooDivOne {
    static final int K = 5;
    private static final long MOD = 1000000007;

    public int count(int N, String[] L, String[] R) {
        int[] left = num(L);
        int[] right = num(R);
        int M = left.length;
        long[][] pos = new long[N+2][K];
        for (int i = 0; i < M ; i++) {
            int ai = i / 60;
            long v = 1L<<(i % 60);
            for (int j = left[i]+1 ; j <= right[i]+1 ; j++) {
                pos[j][ai] |= v;
            }
        }
        long[][] dp = new long[N+2][N+2];
        dp[0][0] = 1;
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= i; j++) {
                if (dp[i][j] == 0) {
                    continue;
                }
                long base = dp[i][j];
                long[] vec = pos[i].clone();
                for (int k = 0; k < K ; k++) {
                    vec[k] &= pos[j][k];
                }
                for (int to = i+1 ; to <= N+1 ; to++) {
                    boolean isOK = true;
                    for (int k = 0; k < 5 ; k++) {
                        if ((vec[k] & pos[to][k]) >= 1) {
                            isOK = false;
                        }
                    }
                    if (isOK) {
                        dp[to][i] += base;
                        dp[to][i] %= MOD;
                    }
                }
            }
        }
        long ans = 0;
        for (int i = 0; i <= N; i++) {
            ans += dp[N+1][i];
        }
        return (int)(ans % MOD);
    }

    public int[] num(String[] input) {
        StringBuilder line = new StringBuilder();
        for (String i : input) {
            line.append(i);
        }
        String[] part = line.toString().split(" ");
        int[] ret = new int[part.length];
        for (int i = 0; i < part.length ; i++) {
            ret[i] = Integer.valueOf(part[i]);
        }
        return ret;
    }

}
