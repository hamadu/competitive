package topcoder.srm6xx.srm693.div1;

import java.io.*;
import java.util.*;

public class BipartiteConstruction {
    private static final long INF = 1000000010L;

    public int[] construct(int K) {
        int[][] edges = new int[20][20];
        for (int i = 1; i <= 18 ; i++) {
            edges[i][i] = 3;
        }
        for (int i = 0; i < 18; i++) {
            edges[i][i+1] = 1;
        }

        long lk = K;
        long[] pow3 = new long[20];
        pow3[0] = 1;
        for (int i = 1 ; i < 20 ; i++) {
            pow3[i] = pow3[i-1] * 3;
        }

        for (int f = 18 ; f >= 0 ; f--) {
            if (lk >= pow3[f] * 2) {
                lk -= pow3[f] * 2;
                edges[18-f][0] = 2;
            } else if (lk >= pow3[f]) {
                lk -= pow3[f];
                edges[18-f][0] = 1;
            }
        }
        
        List<Integer> ans = new ArrayList<>();
        ans.add(19);
        for (int i = 0; i < 20 ; i++) {
            for (int j = 0; j < 20 ; j++) {
                for (int k = 0; k < edges[i][j]; k++) {
                    ans.add(i*19+j);
                }
            }
        }
        int[] ret = new int[ans.size()];
        for (int i = 0; i < ans.size() ; i++) {
            ret[i] = ans.get(i);
        }
        return ret;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
