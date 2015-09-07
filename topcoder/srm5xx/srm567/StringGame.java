package topcoder.srm5xx.srm567;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/04.
 */
public class StringGame {
    public int[] getWinningStrings(String[] S) {
        int n = S.length;
        int[] ord = new int[n];
        int oi = 0;
        for (int i = 0; i < n ; i++) {
            if (canWin(i, S)) {
                ord[oi++] = i;
            }
        }
        return Arrays.copyOf(ord, oi);
    }

    private boolean canWin(int choose, String[] s) {
        int n = s.length;
        long alive = ((1L<<n)-1)^(1L<<choose);
        boolean[] done = new boolean[26];
        int[][] deg = new int[n][26];
        for (int i = 0; i < n ; i++) {
            for (char c : s[i].toCharArray()) {
                deg[i][c-'a']++;
            }
        }

        boolean upd = true;
        while (upd) {
            upd = false;
            for (int k = 0; k < 26 ; k++) {
                if (done[k]) {
                    continue;
                }
                boolean isValid = true;
                for (int x = 0; x < n ; x++) {
                    if ((alive & (1L<<x)) >= 1 && deg[choose][k] < deg[x][k]) {
                        isValid = false;
                        break;
                    }
                }
                if (isValid) {
                    done[k] = true;
                    for (int x = 0; x < n; x++) {
                        if ((alive & (1L<<x)) >= 1 && deg[choose][k] > deg[x][k]) {
                            alive ^= 1L<<x;
                            upd = true;
                        }
                    }
                }
            }
            if (!upd) {
                break;
            }
        }
        return Long.bitCount(alive) == 0;
    }
}
