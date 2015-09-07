package topcoder.srm5xx.srm546;

/**
 * Created by hama_du on 15/08/27.
 */
public class KleofasTail {
    public long countGoodSequences(long K, long A, long B) {
        if (K == 0) {
            return B - A + 1;
        }
        long sum = doit(K, A, B);
        if (K % 2 == 0) {
            sum += doit(K+1, A, B);
        }
        return sum;
    }

    public long doit(long K, long A, long B) {
        long sum = 0;
        long free = 1;
        for (long l = 0; l <= 63; l++) {
            long from = K;
            long to = K + free - 1;
            if (to < A) {
            } else if (B < from) {
                break;
            } else {
                sum += Math.min(B, to) - Math.max(A, from) + 1;
            }
            K *= 2;
            free *= 2;
        }
        return sum;
    }
}
