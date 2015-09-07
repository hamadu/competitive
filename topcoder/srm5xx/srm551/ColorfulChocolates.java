package topcoder.srm5xx.srm551;

/**
 * Created by hama_du on 15/08/30.
 */
public class ColorfulChocolates {
    public int maximumSpread(String chocolates, int maxSwaps) {
        char[] c = chocolates.toCharArray();
        int n = c.length;
        int max = 1;
        for (char az = 'A'; az <= 'Z' ; az++) {
            int[] pos = new int[n];
            int pn = 0;
            for (int i = 0; i < n ; i++) {
                if (c[i] == az) {
                    pos[pn++] = i;
                }
            }
            if (pn <= 1) {
                continue;
            }

            for (int firstTo = 0; firstTo < n ; firstTo++) {
                for (int firstFrom = 0; firstFrom < pn ; firstFrom++) {
                    int cost = 0;
                    int from = firstFrom;
                    int to = firstTo;
                    int len = 0;
                    while (from < pn && to < n) {
                        cost += Math.abs(pos[from] - to);
                        from++;
                        to++;
                        len++;
                        if (cost <= maxSwaps) {
                            max = Math.max(max, len);
                        }
                    }
                }
            }
        }
        return max;
    }
}
