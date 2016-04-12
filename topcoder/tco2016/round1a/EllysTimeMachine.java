package topcoder.tco2016.round1a;

import java.util.Arrays;

/**
 * Created by hama_du on 4/12/16.
 */
public class EllysTimeMachine {
    public String getTime(String time) {
        String[] hm = time.split(":");
        int hour = Integer.valueOf(hm[0]);
        int minute = Integer.valueOf(hm[1]);
        int newHour = minute == 0 ? 12 : minute / 5;
        int newMinute = (hour * 5) % 60;
        return String.format("%02d:%02d", newHour, newMinute);
    }

    public static void main(String[] args) {
        EllysTimeMachine solution = new EllysTimeMachine();
        // debug(solution.someMethod());
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
