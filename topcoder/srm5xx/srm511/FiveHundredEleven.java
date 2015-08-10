package topcoder.srm5xx.srm511;

/**
 * Created by hama_du on 15/08/07.
 */
public class FiveHundredEleven {
    public String theWinner(int[] cards) {
        return game(0, 0, cards) == 1 ? "Fox Ciel" : "Toastman";
    }

    int[][] memo = new int[512][101];

    public int game(int memory, int cnt, int[] cards) {
        if (memo[memory][cnt] != 0) {
            return memo[memory][cnt];
        }
        int ct = 0;
        for (int i = 0; i < cards.length ; i++) {
            if ((memory | cards[i]) == memory) {
                ct++;
            }
        }
        int left = ct - cnt;
        int stat = -1;
        if (left >= 1) {
            if (game(memory, cnt+1, cards) == -1) {
                stat = 1;
            }
        }
        for (int i = 0; i < cards.length ; i++) {
            int tm = memory | cards[i];
            if (tm != memory && tm != 511) {
                if (game(tm, cnt+1, cards) == -1) {
                    stat = 1;
                }
            }
        }
        memo[memory][cnt] = stat;
        return stat;
    }
}
