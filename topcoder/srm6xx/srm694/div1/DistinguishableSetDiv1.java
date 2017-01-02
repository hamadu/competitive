package topcoder.srm6xx.srm694.div1;

import java.io.*;
import java.util.*;

public class DistinguishableSetDiv1 {
    public int count(String[] answer) {
        n = answer.length;
        int m = answer[0].length();
        map = new char[n][m];
        for (int i = 0; i < n ; i++) {
            map[i] = answer[i].toCharArray();
        }

        boolean[] ng = new boolean[1<<m];
        for (int i = 0; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                int ngptn = 0;
                for (int k = 0; k < m ; k++) {
                    if (map[i][k] == map[j][k]) {
                        ngptn |= 1<<k;
                    }
                }
                ng[ngptn] = true;
            }
        }


        int ngcnt = 0;
        for (int i = (1<<m)-1 ; i >= 0 ; i--) {
            if (ng[i]) {
                ngcnt++;
                for (int j = 0; j < m ; j++) {
                    if ((i & (1<<j)) >= 1) {
                        ng[i^(1<<j)] = true;
                    }
                }
            }
        }
        return (1<<m)-ngcnt;
    }


    int n;
    char[][] map;

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
