package topcoder.srm5xx.srm522;

/**
 * Created by hama_du on 15/08/17.
 */
public class RowAndCoins {
    public String getWinner(String cells) {
        if (cells.charAt(0) == 'A' || cells.charAt(cells.length()-1) == 'A') {
            return "Alice";
        }
        return "Bob";
    }
}
