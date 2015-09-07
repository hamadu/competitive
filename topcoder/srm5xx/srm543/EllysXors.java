package topcoder.srm5xx.srm543;

/**
 * Created by hama_du on 15/08/25.
 */
public class EllysXors {
    public long getXor(long L, long R) {
        return xor(L-1) ^ xor(R);
    }

    private long xor(long r) {
        switch ((int)(r % 4)) {
            case 0:
                return r;
            case 1:
                return 1;
            case 2:
                return r+1;
            case 3:
            default:
                return 0;

        }
    }
}
