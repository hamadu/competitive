package topcoder.srm5xx.srm541;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/24.
 */
public class AkariDaisukiDiv1 {
    private static final long MOD = 1000000007;

    public int countF(String Waai, String Akari, String Daisuki, String S, String F, int k) {
        long ret = count(S, F);

        String first50 = S;
        String last50 = S;
        long lastDiff = -1;
        for (int i = 0; i < Math.min(100, k) ; i++) {
            if (first50.length() <= 49) {
                String to = Waai + first50 + Akari + first50 + Daisuki;
                long tr = count(to, F);
                lastDiff = tr - ret * 2;
                ret = tr;
                if (to.length() <= 50) {
                    first50 = to;
                    last50 = to;
                } else {
                    first50 = to.substring(0, 50);
                    last50 = to.substring(to.length()-50);
                }
            } else {
                String wx = Waai + first50;
                String xax = last50 + Akari + first50;
                String xd = last50 + Daisuki;
                ret *= 2;
                long diff = count(wx, F, 0, Waai.length())
                        + count(xax, F, 50 - F.length() + 1, (last50 + Akari).length())
                        + count(xd, F, 50 - F.length() + 1, xd.length());
                lastDiff = diff;
                ret += diff;
                ret %= MOD;
                first50 = wx.substring(0, 50);
                last50 = xd.substring(xd.length()-50);
            }
        }
        if (k <= 100) {
            return (int)ret;
        }

        for (int i = 100 ; i < k ; i++) {
            ret = (ret * 2 + lastDiff) % MOD;
        }
        return (int)ret;
    }
    public int count(String haystack, String needle) {
        return count(haystack, needle, 0, haystack.length());
    }

    public int count(String haystack, String needle, int from, int till) {
        int cnt = 0;
        for (int i = from ; i < till ; i++) {
            if (haystack.substring(i).startsWith(needle)) {
                cnt++;
            }
        }
        return cnt;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    public static void main(String[] args) {
        AkariDaisukiDiv1 daisuki = new AkariDaisukiDiv1();
        debug(daisuki.countF("vfsebgjfyfgerkqlr", "ezbiwls", "kjerx", "jbmjvaawoxycfndukrjfg", "bgjfyfgerkqlrvfsebgjfyfgerkqlrvfsebgjfyfgerkqlrvfs", 1575724));
        debug(daisuki.countF("a", "b", "c", "d", "baadbdcbadbdccccbaaaadbdcbadbdccbaadbdcba", 58));
        debug(daisuki.countF("a", "x", "y", "b", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab", 49));
    }
}
