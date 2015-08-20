package topcoder.srm5xx.srm516;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/13.
 */
public class RowsOrdering {
    private static final long MOD = 1000000007;

    public int order(String[] rows) {
        int n = rows.length;
        int c = rows[0].length();

        C[] col = new C[c];
        for (int i = 0; i < c ; i++) {
            col[i] = new C();
            for (int j = 0; j < n ; j++) {
                col[i].deg[convert(rows[j].charAt(i))]++;
            }
            col[i].sortDeg();
        }
        Arrays.sort(col);

        long ret = 0;
        for (int i = 0; i < c ; i++) {
            long P = 1;
            for (int j = 0; j < c-i-1 ; j++) {
                P *= 50;
                P %= MOD;
            }
            for (int z = 0; z < 50 ; z++) {
                ret += ((col[i].deg[z] * P) % MOD * z) % MOD;
                ret %= MOD;
            }
        }
        ret += n;
        ret %= MOD;
        return (int)ret;
    }

    private static class C implements Comparable<C> {
        int[] deg = new int[50];

        void sortDeg() {
            Arrays.sort(deg);
            for (int i = 0; i < 25; i++) {
                int t = deg[i];
                deg[i] = deg[49-i];
                deg[49-i] = t;
            }
        }

        @Override
        public int compareTo(C o) {
            int nu0 = 0;
            int nu1 = 0;
            for (int i = 0; i < 50 ; i++) {
                nu0 += deg[i] * i;
                nu1 += o.deg[i] * i;
            }
            return nu0 - nu1;
        }
    }

    private int convert(char c) {
        if ('a' <= c && c <= 'z') {
            return c - 'a';
        } else {
            return 26 + c - 'A';
        }
    }
}
