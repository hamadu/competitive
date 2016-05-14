package topcoder.srm6xx.srm688.div1;

import java.util.*;

/**
 * Created by hama_du on 4/15/16.
 */
public class ParenthesesDiv1Medium {

    public int minSwaps(String s, int[] L, int[] R) {
        int n = s.length();
        char[] c = s.toCharArray();
        int m = L.length;
        Map<Set<Integer>,Integer> plMap = new HashMap<>();
        Set<Integer>[] table = new Set[n];
        for (int i = 0; i < n ; i++) {
            table[i] = new HashSet<>();
        }
        for (int i = 0; i < m ; i++) {
            for (int l = L[i] ; l <= R[i]; l++) {
                table[l].add(i);
            }
        }
        for (int i = 0; i < n ; i++) {
            if (!plMap.containsKey(table[i])) {
                plMap.put(table[i], plMap.size());
            }
        }
        int[][] types = new int[n][n];
        int[] tn = new int[n];
        int modOpen = 0;
        int modClose = 0;

        for (int i = 0 ; i < n ; i++) {
            if (table[i].size() >= 1) {
                int idx = plMap.get(table[i]);
                types[idx][tn[idx]++] = i;
            } else {
                if (c[i] == '(') {
                    modOpen++;
                } else {
                    modClose++;
                }
            }
        }

        int localSwaps = 0;
        int totalDeg = 0;
        int needOuterClose = 0;
        int needOuterOpen = 0;
        for (int i = 0; i < n ; i++) {
            int len = tn[i];
            if (len == 0) {
                continue;
            }
            if (len % 2 == 1) {
                return -1;
            }
            int deg = 0;
            int balance = 0;
            for (int k = 0 ; k < len ; k++) {
                if (c[types[i][k]] == '(') {
                    deg++;
                } else {
                    deg--;
                }
                balance = Math.min(balance, deg);
            }
            totalDeg += deg / 2;
            if (deg < 0) {
                int outside = Math.abs(deg) / 2;
                balance = Math.min(0, balance + 2 * outside);
                needOuterOpen += outside;
            } else {
                needOuterClose += deg / 2;
            }
            localSwaps += (-balance+1) / 2;
        }

        int insideSwaps = Math.min(needOuterOpen, needOuterClose);
        int outsideSwaps = Math.abs(totalDeg);
        if (totalDeg != 0) {
            if (totalDeg < 0) {
                if (-totalDeg > modOpen) {
                    // we lack of '(' !!!
                    return -1;
                }
            } else {
                if (totalDeg > modClose) {
                    // we lack of ')' !!!
                    return -1;
                }
            }
        }
        return localSwaps + insideSwaps + outsideSwaps;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
