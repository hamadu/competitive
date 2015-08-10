package topcoder.srm5xx.srm512;

/**
 * Created by hama_du on 15/08/07.
 */
public class MysteriousRestaurant {
    public int maxDays(String[] prices, int budget) {
        int day = prices.length;
        int dish = prices[0].length();
        int[][] price = new int[day][dish];
        for (int i = 0; i < day ; i++) {
            for (int j = 0; j < dish ; j++) {
                char c = prices[i].charAt(j);
                if ('0' <= c && c <= '9') {
                    price[i][j] = c - '0';
                } else if ('A' <= c && c <= 'Z') {
                    price[i][j] = c - 'A' + 10;
                } else {
                    price[i][j] = c - 'a' + 36;
                }
            }
        }

        for (int last = day-1 ; last >= 0 ; last--) {
            int total = 0;
            for (int i = 0; i < 7 ; i++) {
                int best = Integer.MAX_VALUE;
                for (int di = 0; di < dish ; di++) {
                    int sum = 0;
                    for (int fi = i ; fi <= last ; fi += 7) {
                        sum += price[fi][di];
                    }
                    best = Math.min(best, sum);
                }
                total += best;
            }
            if (total <= budget) {
                return last+1;
            }
        }
        return 0;
    }
}
