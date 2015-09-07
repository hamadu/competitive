package topcoder.srm5xx.srm554;

/**
 * Created by hama_du on 15/08/31.
 */
public class TheBrickTowerEasyDivOne {
    public int find(int redCount, int redHeight, int blueCount, int blueHeight) {
        int ret = Math.min(redCount, blueCount);
        if (redHeight != blueHeight) {
            ret += Math.min(redCount, blueCount+1);
            ret += Math.min(redCount+1, blueCount);
        } else {
            if (redCount > blueCount) {
                ret += Math.min(redCount, blueCount+1);
            } else {
                ret += Math.min(redCount+1, blueCount);
            }
        }
        return ret;
    }
}
