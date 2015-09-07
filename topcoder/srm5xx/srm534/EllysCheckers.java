package topcoder.srm5xx.srm534;

/**
 * Created by hama_du on 15/08/23.
 */
public class EllysCheckers {
    public String getWinner(String board) {
        int n = board.length();
        int md = 0;
        for (int i = 0; i < n-1 ; i++) {
            if (board.charAt(i) == 'o') {
                md += (n-1)-i;
            }
        }
        return md % 2 == 1 ? "YES" : "NO";
    }
}
