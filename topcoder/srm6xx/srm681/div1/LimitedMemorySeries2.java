package topcoder.srm6xx.srm681.div1;

/**
 * Created by hamada on 2016/05/17.
 */
public class LimitedMemorySeries2 {
    long A;
    long B;
    long MASK = (1L<<50)-1;

    public int getSum(int n, long x0, long a, long b) {
        A = a;
        B = b;
        long head = x0;
        long res = 0;
        for (int i = 0; i < n ; i++) {
            int l = i;
            int r = i;
            int d = 0;
            long left = head;
            long right = head;
            while (l >= 1 && r <= n-2) {
                l--;
                r++;
                left = prev(left);
                right = next(right);
                if (left < head && head > right) {
                    d++;
                } else {
                    break;
                }
            }
            res += d;
            head = next(head);
        }
        return (int)(res % (long)(1e9+7));
    }

    public long next(long x) {
        return ((x ^ A) + B) & MASK;
    }

    public long prev(long x) {
        return ((x + MASK + 1 - B) ^ A) & MASK;
    }
}