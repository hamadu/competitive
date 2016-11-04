package topcoder.srm6xx.srm699.div1;

import java.io.*;
import java.util.*;

public class OthersXor {
    private static final long INF = 114514;

    public long minSum(int[] x) {
        int free = 0;
        for (int i = 0; i < x.length ; i++) {
            if (x[i] == -1) {
                free++;
            }
        }

        long must = 0;
        if (x.length % 2 == 0) {
            if (free >= 1) {
                must = -1;
            } else {
                for (int i = 0; i < x.length; i++) {
                    must ^= x[i];
                }
            }
        }

        long ans = 0;
        for (int idx = 29 ; idx >= 0 ; idx--) {
            int one = 0;
            for (int i = 0 ; i < x.length ; i++) {
                if (x[i] != -1) {
                    if ((x[i]&(1<<idx)) >= 1) {
                        one++;
                    }
                }
            }
            long req = INF;
            for (int ol = 0 ; ol <= free ; ol++) {
                int tone = one + ol;
                int tzero = x.length - tone;
                if (must != -1 && (must & (1<<idx)) >= 1 != (tone % 2 == 1)) {
                    continue;
                }
                if (x.length % 2 == 0) {
                    if (tone%2 == 0) {
                        req = Math.min(req, tone);
                    } else {
                        req = Math.min(req, tzero);
                    }
                } else {
                    req = Math.min(req, Math.min(tone, tzero));
                }
            }
            if (req == INF) {
                return -1;
            }
            ans += (1L<<idx)*req;
        }

        return ans;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
