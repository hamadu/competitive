package topcoder.srm5xx.srm562;

/**
 * Created by hama_du on 15/09/03.
 */
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckerFreeness {
    static class Point implements Comparable<Point> {
        long x;
        long y;

        Point(long _x, long _y) {
            x = _x;
            y = _y;
        }

        Point(Point a, Point b) {
            x = b.x - a.x;
            y = b.y - a.y;
        }

        public int compareTo(Point o) {
            if (x != o.x) {
                return Long.signum(x - o.x);
            }
            return Long.signum(y - o.y);
        }

        public long det(Point other) {
            return x * other.y - y * other.x;
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    // 凸包
    public List<Point> convexHull(Point[] points) {
        int n = points.length;
        Arrays.sort(points);
        Point[] candidate = new Point[n*2];
        int k = 0;

        // downer
        for (int i = 0 ; i < n ; i++) {
            while (k > 1) {
                Point a = new Point(candidate[k-2], candidate[k-1]);
                Point b = new Point(candidate[k-1], points[i]);
                if (a.det(b) <= 0) {
                    k--;
                } else {
                    break;
                }
            }
            candidate[k++] = points[i];
        }

        // upper
        int t = k;
        for (int i = n-2 ; i >= 0 ; i--) {
            while (k > t) {
                Point a = new Point(candidate[k-2], candidate[k-1]);
                Point b = new Point(candidate[k-1], points[i]);
                if (a.det(b) <= 0) {
                    k--;
                } else {
                    break;
                }
            }
            candidate[k++] = points[i];
        }
        List<Point> ret = new ArrayList<Point>();
        for (int i = 0 ; i < k ; i++) {
            ret.add(candidate[i]);
        }
        return ret;
    }

    public boolean isBad(Point[] white, Point[] black) {
        List<Point> whiteHull = convexHull(white);
        int wl = whiteHull.size();
        int bl = black.length;
        for (int i = 0 ; i < wl-1 ; i++) {
            int fi = i;
            int ti = i+1;
            long x1 = whiteHull.get(fi).x;
            long y1 = whiteHull.get(fi).y;
            long x2 = whiteHull.get(ti).x;
            long y2 = whiteHull.get(ti).y;
            for (int j = 0 ; j < bl ; j++) {
                for (int k = j+1 ; k < bl ; k++) {
                    long x3 = black[j].x;
                    long y3 = black[j].y;
                    long x4 = black[k].x;
                    long y4 = black[k].y;
                    if (Line2D.Double.linesIntersect(x1, y1, x2, y2, x3, y3, x4, y4)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String isFree(String[] whiteX, String[] whiteY, String[] blackX, String[] blackY) {
        Point[] white = process(whiteX, whiteY);
        Point[] black = process(blackX, blackY);
        if (isBad(white, black) || isBad(black, white)) {
            return "NO";
        }
        return "YES";
    }

    private Point[] process(String[] whiteX, String[] whiteY) {
        long[] x = process(whiteX);
        long[] y = process(whiteY);
        Point[] pt = new Point[x.length];
        for (int i = 0 ; i < x.length ; i++) {
            pt[i] = new Point(x[i], y[i]);
        }
        return pt;
    }

    private long[] process(String[] whiteX) {
        StringBuilder b = new StringBuilder();
        for (String x : whiteX) {
            b.append(x);
        }
        String[] data = b.toString().split(" ");
        long[] ret = new long[data.length];
        for (int i = 0 ; i < data.length ; i++) {
            ret[i] = Long.valueOf(data[i]);
        }
        return ret;
    }

    public static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
}