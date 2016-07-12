package codeforces.cf3xx.cf358.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 2016/07/12.
 */
public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        in.nextLong();
        Point[] pt = new Point[n];
        for (int i = 0; i < n ; i++) {
            pt[i] = new Point(in.nextLong(), in.nextLong());
        }
        List<Point> hull = convexHull(pt);
        Point[] abc = null;
        long bestArea = -1;
        int hn = hull.size();
        for (int i = 0; i < hn ; i++) {
            Point a = hull.get(i);
            int k = -1;
            for (int j = i+1 ; j < hn ; j++) {
                Point b = hull.get(j);
                if (k <= j) {
                    k = j+1;
                }
                while (k+1 < hn && Point.area(a, b, hull.get(k+1)) >= Point.area(a, b, hull.get(k))) {
                    k++;
                }
                if (k < hn) {
                    Point c = hull.get(k);
                    long area = Point.area(a, b, c);
                    if (bestArea < area) {
                        bestArea = area;
                        abc = new Point[]{a, b, c};
                    }
                }
            }
        }
        if (abc == null) {
            throw new RuntimeException("wtf");
        }

        for (int i = 0; i < 3 ; i++) {
            int a = i;
            int b = (i+1)%3;
            int c = (i+2)%3;
            long x = abc[a].x + abc[b].x - abc[c].x;
            long y = abc[a].y + abc[b].y - abc[c].y;
            out.println(x + " " + y);
        }
        out.flush();
    }


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

        public long dist2(Point to) {
            return (x-to.x)*(x-to.x)+(y-to.y)*(y-to.y);
        }


        public long det(Point other) {
            return x * other.y - y * other.x;
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }

        public static long area(Point a, Point b, Point c) {
            long dx1 = c.x - a.x;
            long dy1 = c.y - a.y;
            long dx2 = b.x - a.x;
            long dy2 = b.y - a.y;
            return Math.abs(dx1*dy2-dy1*dx2)/2;
        }
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
        for (int i = 0 ; i < k - 1 ; i++) {
            ret.add(candidate[i]);
        }
        return ret;
    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int next() {
            if (numChars == -1)
                throw new InputMismatchException();
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public char nextChar() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            if ('a' <= c && c <= 'z') {
                return (char) c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char) c;
            }
            throw new InputMismatchException();
        }

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char) c);
                c = next();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public int nextInt() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = next();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c-'0';
                c = next();
            } while (!isSpaceChar(c));
            return res*sgn;
        }

        public long nextLong() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            long sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = next();
            }
            long res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c-'0';
                c = next();
            } while (!isSpaceChar(c));
            return res*sgn;
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
        }
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
