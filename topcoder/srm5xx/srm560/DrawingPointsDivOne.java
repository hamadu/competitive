package topcoder.srm5xx.srm560;

import java.util.*;

/**
 * Created by hama_du on 15/09/02.
 */
public class DrawingPointsDivOne {
    public int maxSteps(int[] x, int[] y) {
        int n = x.length;
        int[][] points = new int[n][2];
        for (int i = 0; i < n ; i++) {
            points[i][0] = x[i] * 2;
            points[i][1] = y[i] * 2;
        }

        int ok = 0;
        for (int turn = 1 ; turn <= 150 ; turn++) {
            int[][] to = reverse(1, points);
            int[][] back = proceeed(1, to);
            if (!check(back, points)) {
                break;
            }
            debug(turn,to.length);
            ok = turn;
            points = to;
        }
        return ok >= 150 ? -1 : ok;
    }

    int[] dx = new int[]{-1, -1, 1, 1};
    int[] dy = new int[]{-1, 1, -1, 1};

    public boolean check(int[][] a1, int[][] a2) {
        if (a1.length != a2.length) {
            return false;
        }
        return true;
    }

    public int[][] reverse(int num, int[][] point) {
        Map<Integer,Set<Integer>> newPts = new HashMap<>();
        for (int i = 0; i < point.length ; i++) {
            int x = point[i][0];
            int y = point[i][1];
            for (int d = 0; d < 4 ; d++) {
                int tx = x + dx[d];
                int ty = y + dy[d];
                addPoint(newPts, tx, ty);
            }
        }
        int[][] ret = toArray(newPts);
        if (num == 1) {
            return ret;
        }
        return reverse(num-1, ret);
    }

    public int[][] proceeed(int num, int[][] point) {
        Map<Integer,Set<Integer>> pts = toMap(point);
        Map<Integer,Set<Integer>> newPts = new HashMap<>();
        for (int i = 0; i < point.length ; i++) {
            int x = point[i][0];
            int y = point[i][1];
            if (hasFour(pts, x, y)) {
                addPoint(newPts, x+1, y+1);
            }
        }
        int[][] ret = toArray(newPts);
        if (num == 1) {
            return ret;
        }
        return proceeed(num-1, ret);
    }

    public boolean hasFour(Map<Integer,Set<Integer>> pts, int x, int y) {
        if (pts.get(x).contains(y+2)) {
            if (pts.containsKey(x+2) && pts.get(x+2).contains(y) && pts.get(x+2).contains(y+2)) {
                return true;
            }
        }
        return false;
    }

    public void addPoint(Map<Integer,Set<Integer>> pts, int x, int y) {
        if (!pts.containsKey(x)) {
            pts.put(x, new HashSet<>());
        }
        pts.get(x).add(y);
    }

    public Map<Integer,Set<Integer>> toMap(int[][] point) {
        Map<Integer,Set<Integer>> pts = new HashMap<>();
        int n = point.length;
        for (int i = 0; i < n ; i++) {
            addPoint(pts, point[i][0], point[i][1]);
        }
        return pts;
    }

    public int[][] toArray(Map<Integer,Set<Integer>> pts) {
        int cnt = 0;
        for (Set<Integer> ys : pts.values()) {
            cnt += ys.size();
        }
        int[][] point = new int[cnt][2];
        int ci = 0;
        for (int x : pts.keySet()) {
            for (int y : pts.get(x)) {
                point[ci][0] = x;
                point[ci][1] = y;
                ci++;
            }
        }
        return point;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
