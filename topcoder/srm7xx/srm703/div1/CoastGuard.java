package topcoder.srm7xx.srm703.div1;

import java.awt.geom.Line2D;
import java.io.*;
import java.util.*;

public class CoastGuard {
    private static final long MOD = 1000000007;

    static class Ship implements Comparable<Ship> {
        int x;
        int y;

        public Ship(int a, int b) {
            x = a;
            y = b;
        }

        @Override
        public int compareTo(Ship o) {
            return o.y - y;
        }
    }

    public int count(int[] d, int[] x, int[] y) {
        n = d.length;
        Arrays.sort(d);
        s = new Ship[n];
        for (int i = 0; i < n ; i++) {
            s[i] = new Ship(x[i], y[i]);
        }
        Arrays.sort(s);
        D = d;

        memo = new Map[n+1][n+1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                memo[i][j] = new HashMap<>();
            }
        }

        intersects = new boolean[n][n][n][n];
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                for (int k = 0; k < n ; k++) {
                    for (int l = 0; l < n ; l++) {
                        intersects[i][j][k][l] = Line2D.linesIntersect(d[i], 0, s[j].x, s[j].y, d[k], 0, s[l].x, s[l].y);
                    }
                }
            }
        }

        return (int)(solve(0, n, (1L<<n)-1) % MOD);
    }

    int n;
    int[] D;
    Ship[] s;
    Map<Long,Long>[][] memo;

    boolean[][][][] intersects;

    private long solve(int fr, int to, long l) {
        if (memo[fr][to].containsKey(l)) {
            return memo[fr][to].get(l);
        }
        int len = to - fr;
        if (Long.bitCount(l) != len) {
            memo[fr][to].put(l, 0L);
            return 0;
        }
        if (len <= 1) {
            memo[fr][to].put(l, 1L);
            return 1;
        }

        int idx = 0;
        for (int i = 0 ; i < n ; i++) {
            if ((l & (1L<<i)) >= 1) {
                idx = i;
                break;
            }
        }


        long ans = 0;
        for (int target = fr ; target < to ; target++) {
            long tl = 0L;
            long tr = 0L;
            boolean isPossible = true;
            for (int j = idx+1; j < n; j++) {
                if ((l & (1L << j)) >= 1) {
                    if (target+1 < to && !intersects[target][idx][target+1][j]) {
                        tr |= 1L << j;
                    } else if (target-1 >= fr && !intersects[target][idx][target-1][j]) {
                        tl |= 1L << j;
                    } else {
                        isPossible = false;
                    }
                }
            }
            if (isPossible) {
                ans += solve(fr, target, tl)*solve(target+1, to, tr)%MOD;
            }
        }
        ans %= MOD;
        memo[fr][to].put(l, ans);
        return ans;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
