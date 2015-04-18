package gcj2015.round1a;

import java.io.PrintWriter;
import java.util.*;

public class C2 {


    static class Point implements Comparable<Point> {
        int idx;
        long x;
        long y;
        double rad;

        Point(int i, long _x, long _y) {
            idx = i;
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

        // -pi to pi
        public double dir(Point another) {
            long dx = another.x - x;
            long dy = another.y - y;
            return Math.atan2(dy, dx);
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            int n = in.nextInt();
            Point[] p = new Point[n];
            for (int i = 0; i < n ; i++) {
                p[i] = new Point(i, in.nextLong(), in.nextLong());
            }
            int[] ret = solve(p);

            out.println(String.format("Case #%d:", cs));
            for (int r : ret) {
                out.println(r);
            }
        }
        out.flush();
    }

    private static double EPS = 1e-13;


    private static int[] solve(Point[] points) {
        int n = points.length;
        if (n <= 3) {
            return new int[n];
        }

        int[] needToCut = new int[n];
        Arrays.fill(needToCut, n);

        for (int i = 0 ; i < n ; i++) {
            List<Double> pts = new ArrayList<>();
            for (int m = 0 ; m <= 1; m++) {
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        pts.add(points[i].dir(points[j]) + Math.PI * 2 * m);
                    }
                }
            }

            {
                Collections.sort(pts);
                int to = 0;
                for (int j = 0; j < n-1; j++) {
                    double r1 = pts.get(j);
                    double r2 = r1 + Math.PI;
                    while (pts.get(to) + EPS <= r2) {
                        to++;
                    }
                    needToCut[i] = Math.min(needToCut[i], to - j - 1);
                }
            }
        }

        return needToCut;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



