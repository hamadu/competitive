package topcoder.srm5xx.srm538;

/**
 * Created by hama_du on 15/08/24.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TurtleSpy {

    public double maxDistance(String[] commands) {
        double forward = 0;
        double backward = 0;

        List<Integer> rcmd = new ArrayList<Integer>();
        for (String s : commands) {
            String[] data = s.split(" ");
            int op = Integer.valueOf(data[1]);
            if ("forward".equals(data[0])) {
                forward += op;
            } else if ("backward".equals(data[0])) {
                backward += op;
            } else {
                if ("right".equals(data[0])) {
                    op = (360 - op);
                }
                rcmd.add(op);
            }
        }

        boolean[][] roll = new boolean[rcmd.size()+1][360];
        roll[0][0] = true;
        for (int i = 0; i < rcmd.size(); i++) {
            int rd = rcmd.get(i);
            for (int j = 0; j < 360; j++) {
                if (roll[i][j]) {
                    roll[i+1][j] = true;
                    roll[i+1][(j + rd) % 360] = true;
                }
            }
        }

        double max = Math.abs(forward - backward);
        for (int i = 0 ; i < 360 ; i++) {
            if (roll[rcmd.size()][i]) {
                max = Math.max(max, doit(forward, backward, i));
            }
        }
        return max;
    }

    private double doit(double forward, double backward, double i) {
        double rad = i * Math.PI / 180;

        double x = forward - backward * Math.cos(rad);
        double y = backward * Math.sin(rad);

        return Math.sqrt(x*x+y*y);
    }

    public static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
}