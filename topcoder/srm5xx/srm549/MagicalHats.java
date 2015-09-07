package topcoder.srm5xx.srm549;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/30.
 */
public class MagicalHats {
    private static final int INF = 114514;

    public int findMaximumReward(String[] board, int[] coins, int numGuesses) {
        int n = board.length;
        int m = board[0].length();
        int[][] hats = hats(board);
        hn = hats.length;
        cn = coins.length;
        gn = numGuesses;
        prec(hats, cn, n, m);

        pw3 = new int[14];
        pw3[0] = 1;
        for (int i = 1; i < 14 ; i++) {
            pw3[i] = pw3[i-1]*3;
        }

        memo = new int[2000000];
        Arrays.fill(memo, -1);
        int cnt = dfs(0, 0, 0);
        int total = 0;
        Arrays.sort(coins);
        for (int i = 0; i < cnt ; i++) {
            total += coins[i];
        }
        return (cnt <= -1) ? -1 : total;
    }

    boolean[] isOK;
    int hn;
    int cn;
    int gn;
    int[] memo;
    int[] pw3;

    public int dfs(int done, int hit, int code) {
        if (done == (1<<hn)-1) {
            return isOK[hit] ? 0 : -INF;
        }
        if (memo[code] != -1) {
            return memo[code];
        }
        int ret = -INF;
        for (int i = 0; i < hn ; i++) {
            if ((done & (1<<i)) >= 1) {
                continue;
            }
            int r1 = -INF;
            if (Integer.bitCount(hit) < cn) {
                r1 = dfs(done|(1<<i), hit|(1<<i), code+pw3[i]*2) + (Integer.bitCount(done) < gn ? 1 : 0);
            }
            int r2 = dfs(done|(1<<i), hit, code+pw3[i]);
            if (r1 <= -1) {
                ret = Math.max(ret, r2);
            } else if (r2 <= -1) {
                ret = Math.max(ret, r1);
            } else {
                ret = Math.max(ret, Math.min(r1, r2));
            }
        }
        memo[code] = ret;
        return ret;
    }

    public void prec(int[][] hats, int cn, int n, int m) {
        int hn = hats.length;
        isOK = new boolean[1<<hn];
        int[] parityH = new int[n];
        int[] parityW = new int[m];
        for (int i = 0; i < hn ; i++) {
            parityH[hats[i][0]] ^= 1;
            parityW[hats[i][1]] ^= 1;
        }

        for (int ptn = 0; ptn < 1<<hn ; ptn++) {
            if (Integer.bitCount(ptn) != cn) {
                continue;
            }
            int[] ph = parityH.clone();
            int[] pw = parityW.clone();
            for (int i = 0; i < hn ; i++) {
                if ((ptn & (1<<i)) >= 1) {
                    ph[hats[i][0]] ^= 1;
                    pw[hats[i][1]] ^= 1;
                }
            }
            boolean possible = true;
            for (int i = 0; i < n ; i++) {
                if (ph[i] != 0) {
                    possible = false;
                }
            }
            for (int j = 0; j < m ; j++) {
                if (pw[j] != 0) {
                    possible = false;
                }
            }
            isOK[ptn] = possible;
        }
    }

    public int[][] hats(String[] board) {
        int n = board.length;
        int m = board[0].length();
        int hn = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (board[i].charAt(j) == 'H') {
                    hn++;
                }
            }
        }

        int[][] hats = new int[hn][2];
        int hi = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (board[i].charAt(j) == 'H') {
                    hats[hi][0] = i;
                    hats[hi][1] = j;
                    hi++;
                }
            }
        }
        return hats;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    public static void main(String[] args) {
        MagicalHats hats = new MagicalHats();
        debug(hats.findMaximumReward(new String[]{"HHH",
                        "HHH",
                        "H.H"}
                , new int[]{
                        33,337,1007,2403
                },
                3));
    }
}
