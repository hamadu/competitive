package topcoder.srm6xx.srm672.div1;

import java.util.*;

/**
 * Created by hama_du on 15/10/22.
 */
public class Procrastination {
    private static final long INF = 1000000000000000000L;

    public long upd(long bound, long a, long b) {
        if (bound <= b) {
            return Math.min(a, b);
        }
        return a;
    }

    public Map<Long,List<Long>> ymap = new HashMap<>();

    public List<Long> findYaku(long a) {
        if (ymap.containsKey(a)) {
            return ymap.get(a);
        }
        List<Long> y = new ArrayList<>();
        for (long x = 2 ; x * x <= a ; x++) {
            if (a % x == 0) {
                y.add(x);
                if (x != a/x) {
                    y.add(a / x);
                }
            }
        }
        y.add(INF);
        Collections.sort(y);
        ymap.put(a, y);
        return y;
    }

    public long findNextYaku(long lb, long a) {
        List<Long> ys = findYaku(a);
        int idx = Collections.binarySearch(ys, lb);
        if (idx < 0) {
            idx = -idx-1;
        }
        return ys.get(idx);
    }

    public long findFinalAssignee(long n) {
        long now = n;
        long hour = 2;
        while (true) {
            if (now < hour * 2) {
                break;
            }
            long yplus = findNextYaku(hour, now);
            long yminus = findNextYaku(hour, now-1);
            if (yplus == yminus) {
                break;
            }
            if (yplus < yminus) {
                now++;
            } else {
                now--;
            }
            hour = Math.min(yplus, yminus)+1;
        }
        return now;
    }

    public static void main(String[] args) {
        Procrastination p = new Procrastination();
        debug(p.findFinalAssignee(196248));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
