package topcoder.srm5xx.srm524;

import java.math.BigInteger;

/**
 * Created by hama_du on 15/08/17.
 */
public class MagicDiamonds {
    public long minimalTransfer(long n) {
        if (n <= 3) {
            return n;
        }
        return (BigInteger.valueOf(n).isProbablePrime(32)) ? 2 : 1;
    }
}
