package topcoder.srm6xx.srm675;

import java.util.Arrays;

/**
 * Created by hama_du on 2015/12/10.
 */
public class LimitedMemorySeries1 {
    static final long MOD = 1000000007;

    public long getSum(int n, int x0, int a, int b, int[] query) {
        int q = query.length;

        int[] largeBucket = new int[32000];
        int[] smallBucket = new int[33000];
        int mask = (1<<15)-1;
        long ans = 0;

        {
            long now = x0;
            largeBucket[(int)(now>>15)]++;
            for (int i = 1 ; i < n ; i++) {
                now = (now * a + b) % MOD;
                largeBucket[(int)(now>>15)]++;
            }

            for (int qi = 0 ; qi < q ; qi++) {
                int head = -1;
                int sum = 0;
                for (int f = 0 ; f < 32000 ; f++) {
                    sum += largeBucket[f];
                    if (query[qi] < sum) {
                        sum -= largeBucket[f];
                        head = f;
                        break;
                    }
                }
                now = x0;
                Arrays.fill(smallBucket, 0);
                if ((now>>15) == head) {
                    smallBucket[(int) (now & mask)]++;
                }
                for (int i = 1 ; i < n ; i++) {
                    now = (now * a + b) % MOD;
                    if ((now>>15) == head) {
                        smallBucket[(int)(now & mask)]++;
                    }
                }
                for (int f = 0 ; f < 33000 ; f++) {
                    sum += smallBucket[f];
                    if (query[qi] < sum) {
                        ans += (head<<15)+f;
                        break;
                    }
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        LimitedMemorySeries1 series1 = new LimitedMemorySeries1();
        debug(series1.getSum(5,
                123456789,
                987654321,
                1000000006
                ,new int[]{0,1,2,3}));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
