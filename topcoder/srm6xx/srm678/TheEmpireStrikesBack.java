package topcoder.srm6xx.srm678;

import java.util.*;

/**
 * Created by hama_du on 2016/01/26.
 */
public class TheEmpireStrikesBack {
    private static final long MOD = 1000000007;

    public int find(int AX, int BX, int CX, int AY, int BY, int CY, int n, int m) {
        long[][] p = new long[n][2];
        p[0][0] = AX;
        p[0][1] = AY;
        for (int i = 1 ; i < n ; i++) {
            p[i][0] = (p[i-1][0] * BX + CX) % MOD;
            p[i][1] = ((p[i-1][1] * BY) + CY) % MOD;
        }
        return solve(p, m);
    }

    private int solve(long[][] p, int m) {
        Map<Long,Long> pos = new HashMap<>();
        for (int i = 0 ; i < p.length ; i++) {
            long y = p[i][1];
            long x = p[i][0];
            if (pos.getOrDefault(x, -1L) < y) {
                pos.put(x, y);
            }
        }
        int n = pos.size();
        long[][] pl = new long[n][2];
        int pi = 0;
        for (long x : pos.keySet()) {
            pl[pi][0] = x;
            pl[pi][1] = pos.get(x);
            pi++;
        }
        Arrays.sort(pl, (o1, o2) -> Long.compare(o1[0], o2[0]));

        Deque<long[]> de = new ArrayDeque<>();
        for (int i = 0 ; i < n ; i++) {
            while (de.size() >= 1 && de.peekLast()[1] <= pl[i][1]) {
                de.pollLast();
            }
            de.add(pl[i]);
        }

        long[][] pl2 = new long[de.size()][2];
        pi = 0;
        while (de.size() >= 1) {
            pl2[pi++] = de.pollFirst().clone();
        }

        long max = (long)1e11;
        long min = -1;
        while (max - min > 1) {
            long med = (max + min) / 2;
            if (isOK(med, pl2, m)) {
                max = med;
            } else {
                min = med;
            }
        }
        return (int)max;
    }

    private boolean isOK(long T, long[][] pl, int m) {
        int n = pl.length;
        int cnt = 0;

        int[][] range = new int[n][2];
        int left = 0;
        int right = 0;
        for (int i = 0; i < n ; i++) {
            while (left < n && pl[left][1] > pl[i][1] + T) {
                left++;
            }
            while (right < n && pl[i][0] + T >= pl[right][0]) {
                right++;
            }
            range[i][0] = left;
            range[i][1] = right;
        }

        for (int i = 0 ; i < n ;) {
            cnt++;
            int needCover = i;
            int l = i;
            int maxCover = 0;
            while (l < n && range[l][0] <= needCover) {
                maxCover = Math.max(maxCover, range[l][1]);
                l++;
            }
            i = maxCover;
        }
        return cnt <= m;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }


    public static void main(String[] args) {
        TheEmpireStrikesBack empireStrikesBack = new TheEmpireStrikesBack();
        debug(empireStrikesBack.find(
                1,
                3,
                8,
                10000,
                10,
                999910000,
                3,
                1
        ));
    }
}
