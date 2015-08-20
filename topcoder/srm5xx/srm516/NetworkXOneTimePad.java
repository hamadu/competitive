package topcoder.srm5xx.srm516;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hama_du on 15/08/13.
 */
public class NetworkXOneTimePad {
    public int crack(String[] plaintexts, String[] ciphertexts) {
        int pn = plaintexts.length;
        int cn = ciphertexts.length;
        int L = plaintexts[0].length();

        Set<Long> pk = new HashSet<>();
        for (int i = 0; i < pn ; i++) {
            long l = 0;
            for (int j = 0; j < L ; j++) {
                long d = plaintexts[i].charAt(j) - '0';
                l |= d<<(L-j-1);
            }
            pk.add(l);
        }

        Set<Long> ck = new HashSet<>();
        for (int i = 0; i < cn ; i++) {
            long l = 0;
            for (int j = 0; j < L ; j++) {
                long d = ciphertexts[i].charAt(j) - '0';
                l |= d<<(L-j-1);
            }
            ck.add(l);
        }

        Set<Long> possibleKeys = new HashSet<>();
        for (long p : pk) {
            for (long c : ck) {
                long key = p ^ c;
                boolean isOK = true;
                for (long c0 : ck) {
                    long rev = c0 ^ key;
                    if (!pk.contains(rev)) {
                        isOK = false;
                        break;
                    }
                }
                if (isOK) {
                    possibleKeys.add(key);
                }
            }
        }
        return possibleKeys.size();
    }
}
