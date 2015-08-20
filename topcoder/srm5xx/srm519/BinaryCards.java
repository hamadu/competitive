package topcoder.srm5xx.srm519;

/**
 * Created by hama_du on 15/08/16.
 */
public class BinaryCards {
    public long largestNumber(long A, long B) {
        if (A == B) {
            return A;
        }
        long ret = 0;
        while (true) {
            long hi = Long.highestOneBit(B);
            if ((hi & A) == 0) {
                return ret | hi | (hi - 1);
            }
            ret += hi;
            B -= hi;
            A -= hi;
        }
    }
}
