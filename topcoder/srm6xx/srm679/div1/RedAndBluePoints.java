package topcoder.srm6xx.srm679.div1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by hama_du on 2016/01/20.
 */
public class RedAndBluePoints {
    static class Point implements Comparable<Point> {
        long x;
        long y;

        public Point(long _x, long _y) {
            x = _x;
            y = _y;
        }

        long dot(Point o) {
            return x * o.x + y * o.y;
        }

        long cross(Point o) {
            return x * o.y - y * o.x;
        }

        Point to(Point o) {
            return new Point(o.x - x, o.y - y);
        }

        static boolean innerTriangle(Point x1, Point x2, Point x3, Point y) {
            Point v1 = x1.to(x2);
            Point v2 = x2.to(x3);
            Point v3 = x3.to(x1);
            Point u1 = x1.to(y);
            Point u2 = x2.to(y);
            Point u3 = x3.to(y);
            boolean c1 = v1.cross(u1) > 0;
            boolean c2 = v2.cross(u2) > 0;
            boolean c3 = v3.cross(u3) > 0;
            return c1 == c2 && c2 == c3;
        }

        @Override
        public int compareTo(Point o) {
            return x == o.x ? Long.compare(y, o.y) : Long.compare(x, o.x);
        }

        @Override
        public String toString() {
            return String.format("(%d,%d)", x, y);
        }
    }


    public int find(int[] blueX, int[] blueY, int[] redX, int[] redY) {
        int bn = blueX.length;
        List<Point> blues = new ArrayList<>();
        for (int i = 0; i < bn ; i++) {
            blues.add(new Point(blueX[i], blueY[i]));
        }
        Collections.sort(blues);

        int rn = redX.length;
        List<Point> reds = new ArrayList<>();
        for (int i = 0; i < rn ; i++) {
            reds.add(new Point(redX[i], redY[i]));
        }
        Collections.sort(reds);

        int[][][] inner = new int[bn][bn][bn];
        for (int i = 0; i < bn ; i++) {
            for (int j = 0; j < bn ; j++) {
                for (int k = 0; k < bn ; k++) {
                    if (i == j || j == k || i == k) {
                        continue;
                    }
                    int cnt = 0;
                    Point bi = blues.get(i);
                    Point bj = blues.get(j);
                    Point bk = blues.get(k);
                    for (int l = 0; l < bn ; l++) {
                        if (l == i || j == l || k == l) {
                            continue;
                        }
                        cnt += Point.innerTriangle(bi, bj, bk, blues.get(l)) ? 1 : 0;
                    }
                    for (int l = 0; l < rn ; l++) {
                        cnt -= Point.innerTriangle(bi, bj, bk, reds.get(l)) ? 114514 : 0;
                    }
                    inner[i][j][k] = cnt;
                }
            }
        }


        // [first][last][head]
        int[][][] upper = new int[bn][bn][bn];
        int[][][] downer = new int[bn][bn][bn];
        for (int i = 0; i < bn ; i++) {
            for (int j = 0; j < bn ; j++) {
                Arrays.fill(upper[i][j], -1);
                Arrays.fill(downer[i][j], -1);
            }
        }
        for (int i = 0; i < bn ; i++) {
            upper[i][i][i] = 1;
            downer[i][i][i] = 1;
        }
        for (int i = 0; i < bn ; i++) {
            for (int j = 0; j < bn ; j++) {
                for (int k = 0; k < bn ; k++) {
                    if (upper[i][j][k] < 0) {
                        continue;
                    }
                    int base = upper[i][j][k];
                    Point last = blues.get(j);
                    Point head = blues.get(k);
                    Point v1 = last.to(head);
                    for (int l = 0 ; l < bn ; l++) {
                        Point next = blues.get(l);
                        Point v2 = head.to(next);
                        if (head.x > next.x || (head.x == next.x && head.y >= next.y)) {
                            continue;
                        }
                        if (i != k && v1.y * v2.x <= v2.y * v1.x) {
                            continue;
                        }
                        int to = base + inner[i][k][l] + 1;
                        upper[i][k][l] = Math.max(upper[i][k][l], to);
                    }
                }
            }
        }
        for (int i = 0; i < bn ; i++) {
            for (int j = 0; j < bn ; j++) {
                for (int k = 0; k < bn ; k++) {
                    if (downer[i][j][k] < 0) {
                        continue;
                    }
                    int base = downer[i][j][k];
                    Point last = blues.get(j);
                    Point head = blues.get(k);
                    Point v1 = last.to(head);
                    for (int l = 0 ; l < bn ; l++) {
                        Point next = blues.get(l);
                        Point v2 = head.to(next);
                        if (head.x > next.x || (head.x == next.x && head.y >= next.y)) {
                            continue;
                        }
                        if (i != k && v1.y * v2.x >= v2.y * v1.x) {
                            continue;
                        }
                        int to = base + inner[i][k][l] + 1;
                        downer[i][k][l] = Math.max(downer[i][k][l], to);
                    }
                }
            }
        }

        int max = Math.min(2, bn);
        for (int i = 0; i < bn ; i++) {
            for (int j = 0; j < bn ; j++) {
                for (int k = 0; k < bn ; k++) {
                    for (int l = 0; l < bn ; l++) {
                        max = Math.max(max, upper[i][k][j] + downer[i][l][j] - 2);
                    }
                }
            }
        }
        return max;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }


    public static void main(String[] args) {
        RedAndBluePoints points = new RedAndBluePoints();
        debug(points.find(
                new int[]{0,0,10,10},
                new int[]{0,10,0,10},
                new int[]{12},
                new int[]{12}
        ));
    }
}