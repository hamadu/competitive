package topcoder.srm5xx.srm515;

/**
 * Created by hama_du on 15/08/13.
 */
public class RotatedClock {
    public String getEarliest(int hourHand, int minuteHand) {
        int best = Integer.MAX_VALUE;
        for (int h = 0; h <= 11; h++) {
            int rotated = h * 30;
            int res = getTime((hourHand + rotated) % 360, (minuteHand + rotated) % 360);
            best = Math.min(best, res);
        }
        if (best == Integer.MAX_VALUE) {
            return "";
        }
        return String.format("%02d:%02d", best / 60, best % 60);
    }

    public int getTime(int h, int m) {
        if (m % 12 == 0) {
            if (h % 30 == m / 12) {
                return (h / 30) * 60 + m / 6;
            }
        }
        return Integer.MAX_VALUE;
    }
}
