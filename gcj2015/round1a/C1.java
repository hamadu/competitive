package gcj2015.round1a;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class C1 {


    static class Point implements Comparable<Point> {
        int idx;
        long x;
        long y;

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

    public static List<Point> convexHull(Point[] points) {
        int n = points.length;
        Arrays.sort(points);
        Point[] candidate = new Point[n*2];
        int k = 0;

        // downer
        for (int i = 0 ; i < n ; i++) {
            while (k > 1) {
                Point a = new Point(candidate[k-2], candidate[k-1]);
                Point b = new Point(candidate[k-1], points[i]);
                if (a.det(b) < 0) {
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
                if (a.det(b) < 0) {
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


    private static int[] solve(Point[] points) {
        int n = points.length;
        int[] needToCut = new int[n];
        Arrays.fill(needToCut, n);

        for (int p = 1 ; p < (1<<n) ; p++) {
            int bc = Integer.bitCount(p);
            int cut = n - bc;
            int idx = 0;
            Point[] subtree = new Point[bc];
            for (int i = 0 ; i < n ; i++) {
                if ((p & (1<<i)) >= 1) {
                    subtree[idx++] = points[i];
                }
            }
            List<Point> left = convexHull(subtree);
            for (Point pt : left) {
                needToCut[pt.idx] = Math.min(needToCut[pt.idx], cut);
            }
        }
        return needToCut;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



