package topcoder.srm7xx.srm708.div1;

import java.util.*;

public class BalancedStrings {
    public String[] findAny(int N) {
        if (N == 1) {
            return new String[]{"a"};
        }

        String[] ret = new String[N];
        for (int u = 1 ; u <= 5 ; u++) {
            int canUse = 26 - u * 2;
            for (int i = 0; i < N; i++) {
                char c = (char) ('a' + (i % canUse));
                ret[i] = "" + c;
            }
            int pa = countPair(ret);
            if (pa <= 99 * u) {
                if (pa == 99) {
                    debug(N);
                }
                for (int t = 0 ; t < u ; t++) {
                    while (pa >= 1 && ret[t].length() < 100) {
                        pa--;
                        ret[t] += (char)(((pa % 2 == 0) ? 'y' : 'z') - (t * 2));
                    }
                }
                for (String aa : ret) {
                    if (aa.length() > 100) {
                        throw new RuntimeException("no");
                    }
                }
                if (countPair(ret) != countIt(ret)) {
                    throw new RuntimeException("no2");
                }
                return ret;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        BalancedStrings bs = new BalancedStrings();
        for (int x = 1 ; x <= 100 ; x++) {
            bs.findAny(x);
        }
    }

    public int countIt(String[] a) {
        int n = a.length;
        int it = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j+1 < a[i].length(); j++) {
                if (a[i].charAt(j) != a[i].charAt(j+1)) {
                    it++;
                }
            }
        }
        return it;
    }

    public int countPair(String[] a) {
        int pa = 0;
        int n = a.length;
        for (int i = 0; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                int N = a[i].length();
                int M = a[j].length();
                for (int k = 0; k < N ; k++) {
                    for (int l = 0; l < M ; l++) {
                        if (a[i].charAt(k) == a[j].charAt(l)) {
                            pa++;
                        }
                    }
                }
            }
        }
        return pa;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
