package topcoder.srm5xx.srm557;

/**
 * Created by hama_du on 15/09/01.
 */
public class FoxAndMountainEasy {
    public String possible(int n, int h0, int hn, String history) {
        int h = history.length();
        
        int left = 0;
        int right = 0;
        int minHeight = 0;
        int height = h0;
        for (int i = 0; i < h ; i++) {
            if (history.charAt(i) == 'U') {
                height++;
            } else {
                height--;
            }
            minHeight = Math.min(minHeight, height);
        }
        if (minHeight <= -1) {
            // append up
            left = -minHeight;
        }
        right = Math.abs(height+left - hn);
        if (left + h + right > n || (n - (left + h + right)) % 2 == 1) {
            return "NO";
        }
        return "YES";
    }
}
