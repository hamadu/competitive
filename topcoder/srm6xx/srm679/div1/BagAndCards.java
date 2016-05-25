package topcoder.srm6xx.srm679.div1;

/**
 * Created by hamada on 2016/05/19.
 */
public class BagAndCards {
    private static final long MOD = (long)1e9+7;

    public int getHash(int n, int m, int x, int a, int b, int c, String isGood) {
        long[][] cards = create(n, m, x, a, b, c);
        return solve(cards, isGood);
    }

    private int solve(long[][] cards, String isGood) {
        int n = cards.length;
        int m = cards[0].length;

        // [bag][opponent card]
        char[] cl = isGood.toCharArray();
        long[][] tmpSum = new long[n][m];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                for (int k = 0; k < m ; k++) {
                    if (cl[j+k] == 'Y') {
                        tmpSum[i][j] += cards[i][k];
                        tmpSum[i][j] -= (tmpSum[i][j] >= MOD) ? MOD : 0;
                    }
                }
            }
        }

        long ans = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                long sum = 0;
                for (int k = 0; k < m ; k++) {
                    sum += tmpSum[i][k] * cards[j][k] % MOD;
                }
                sum %= MOD;
                ans ^= sum;
            }
        }
        return (int)ans;
    }

    public long[][] create(int n, int m, long x, long a, long b, long c) {
        long[][] ret = new long[n][m];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                ret[i][j] = x;
                x = (((x * a) + b) ^ c) % MOD;
            }
        }
        return ret;
    }
}