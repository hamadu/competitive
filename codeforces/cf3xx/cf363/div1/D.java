package codeforces.cf3xx.cf363.div1;

import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 2016/07/19.
 */
public class D {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int k = in.nextInt();
        int n = in.nextInt();
        K = k;
        Point[] stone = new Point[k];
        Point[] mon = new Point[n];
        for (int i = 0; i < k ; i++) {
            stone[i] = P(in.nextLong(), in.nextLong());
        }
        for (int i = 0; i < n ; i++) {
            mon[i] = P(in.nextLong(), in.nextLong());
            mon[i].id = i;
        }

        boolean[][] free = new boolean[k][n];
        next = new int[k][n];
        level = new int[k][n];
        for (int sid = 0; sid < k ; sid++) {
            Arrays.fill(next[sid], -1);
            Arrays.sort(mon, Point.distComparator(stone[sid]));
            Point[] vec = new Point[n];
            for (int j = 0; j < n ; j++) {
                vec[j] = stone[sid].to(mon[j]);
            }

            Map<Point,Integer> map = new HashMap<>();
            int[] deg = new int[n];
            int[][] dirs = new int[n][2];
            for (int j = 0; j < n ; j++) {
                Point dxy = Point.unitize(vec[j]);
                int theid = map.getOrDefault(dxy, map.size());
                map.putIfAbsent(dxy, theid);
                dirs[j][0] = theid;
                dirs[j][1] = j;
                deg[theid]++;
            }

            Arrays.sort(dirs, (o1, o2) -> o1[0] - o2[0]);

            for (int j = 0; j < n ;) {
                int fr = j;
                int to = j;
                while (to < n && dirs[fr][0] == dirs[to][0]) {
                    to++;
                }
                free[sid][mon[dirs[fr][1]].id] = true;
                for (int l = fr+1 ; l < to ; l++) {
                    int frid = mon[dirs[l-1][1]].id;
                    int toid = mon[dirs[l][1]].id;
                    next[sid][toid] = frid;
                    level[sid][toid] = l-fr;
                }
                j = to;
            }
        }

        int counter = 0;
        for (int i = 0; i < n ; i++) {
            Set<Integer> req = new HashSet<>();
            req.add(i);
            if (canHit(req, 0)) {
                counter++;
            }
        }
        out.println(counter);
        out.flush();
    }

    static int K;
    static int[][] next;
    static int[][] level;

    static boolean canHit(Set<Integer> req, int done) {
        if (req.size() > K-Integer.bitCount(done)) {
            return false;
        }
        if (req.size() == 0) {
            return true;
        }

        int left = K - Integer.bitCount(done);
        for (int i = 0 ; i < K ; i++) {
            if ((done & (1<<i)) >= 1) {
                continue;
            }
            for (int r : req) {
                if (level[i][r] + req.size() - 1 > left) {
                    continue;
                }
                Set<Integer> nextReq = new HashSet<>(req);
                nextReq.remove(r);
                int head = next[i][r];

                int lv = nextReq.size();
                while (head != -1 && lv <= left) {
                    nextReq.add(head);
                    head = next[i][head];
                    lv++;
                }
                if (lv <= left) {
                    if (canHit(nextReq, done|(1<<i))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static Point P(long x, long y) {
        return new Point(x, y);
    }

    static class Point implements Comparable<Point> {
        int id;
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

        long dist2(Point o) {
            return dist2(this, o);
        }

        static long dist2(Point a, Point b) {
            long dx = a.x-b.x;
            long dy = a.y-b.y;
            return dx*dx+dy*dy;
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


        static Point unitize(Point p) {
            if (p.x == 0 && p.y == 0) {
                throw new RuntimeException("same point");
            } else if (p.x == 0) {
                return new Point(0, p.y >= 1 ? 1 : -1);
            } else if (p.y == 0) {
                return new Point(p.x >= 1 ? 1 : -1, 0);
            } else {
                long gcd = gcd(Math.abs(p.x), Math.abs(p.y));
                long tx = p.x / gcd;
                long ty = p.y / gcd;
                return new Point(tx, ty);
            }
        }

        static Comparator<Point> distComparator(Point from) {
            return (p1, p2) -> Long.compare(dist2(p1, from), dist2(p2, from));
        }

        @Override
        public int compareTo(Point o) {
            return x == o.x ? Long.compare(y, o.y) : Long.compare(x, o.x);
        }

        @Override
        public String toString() {
            return String.format("(%d,%d)", x, y);
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Point) {
                Point c = (Point)o;
                return x == c.x && y == c.y;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return (int)(x*31+y);
        }
    }

    public static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a%b);
    }


    public static boolean next_permutation(int[] num) {
        int len = num.length;
        int x = len - 2;
        while (x >= 0 && num[x] >= num[x+1]) {
            x--;
        }
        if (x == -1) return false;

        int y = len - 1;
        while (y > x && num[y] <= num[x]) {
            y--;
        }
        int tmp = num[x];
        num[x] = num[y];
        num[y] = tmp;
        Arrays.sort(num, x+1, len);
        return true;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
