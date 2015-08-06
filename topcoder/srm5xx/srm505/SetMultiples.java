package topcoder.srm5xx.srm505;

/**
 * Created by hama_du on 15/08/05.
 */
public class SetMultiples {
    public long smallestSubset(long A, long B, long C, long D) {
        A = Math.max(A, B/2+1);
        C = Math.max(C, D/2+1);

        long ans = (B - A + 1) + (D - C + 1);
        for (long k = Math.max(2, D / B - 1) ; A <= B ; k++) {
            long from = (C+k-1) / k;
            long to = D / k;
            // [A,B] and [from,to]
            if (B < from) {
                continue;
            }
            if (to < A) {
                break;
            }
            long cov = Math.min(to, B) - Math.max(A, from) + 1;
            ans -= cov;
            B = from -1;
        }

        return ans;
    }
}
