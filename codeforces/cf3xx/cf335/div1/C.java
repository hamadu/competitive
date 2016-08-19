package codeforces.cf3xx.cf335.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 2016/08/19.
 */
public class C {
    private static final double EPS = 1e-9;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        double p = in.nextDouble();
        double q = in.nextDouble();

        Point[] po = new Point[n+3];
        long maxX = 0;
        long maxY = 0;
        for (int i = 0; i < n ; i++) {
            po[i] = new Point(in.nextInt(), in.nextInt());
            maxX = Math.max(maxX, po[i].x);
            maxY = Math.max(maxY, po[i].y);
        }
        po[n] = new Point(0, 0);
        po[n+1] = new Point(maxX, 0);
        po[n+2] = new Point(0, maxY);
        List<Point> hull = convexHull(po);

        double A = q / p;

        int hn = hull.size();
        double crossX = -1;
        double crossY = -1;
        for (int i = 0 ; i < hn ; i++) {
            Point p0 = hull.get(i);
            Point p1 = hull.get((i+1)%hn);
            if (p0.x + p0.y == 0 || p1.x + p1.y == 0) {
                continue;
            }
            if (p0.x == p1.x) {
                double Y = p0.x * A;
                if (Math.min(p0.y, p1.y) - EPS < Y && Y < Math.max(p0.y, p1.y) + EPS) {
                    // ok.
                    crossX = p0.x;
                    crossY = Y;
                    break;
                }
            } else {
                double a = (p1.y - p0.y) * 1.0d / (p1.x - p0.x);
                if (Math.min(a, A) + EPS < Math.max(a, A)) {
                    double x = (p0.y - a * p0.x) / (A - a);
                    if (Math.min(p0.x, p1.x) - EPS < x && x < Math.max(p0.x, p1.x) + EPS) {
                        crossX = x;
                        crossY = x * A;
                        break;
                    }
                }
            }
        }
        if (crossX == -1) {
            throw new RuntimeException("arien");
        }

        double d1 = Math.hypot(crossX, crossY);
        double d2 = Math.hypot(p, q);

        out.println(String.format("%.9f", d2 / d1));
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

        public long det(Point other) {
            return x * other.y - y * other.x;
        }

        public String toString() {
            return "(" + x + "," + y + ")";
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

        private int[] nextInts(int n) {
            int[] ret = new int[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextInt();
            }
            return ret;
        }


        private int[][] nextIntTable(int n, int m) {
            int[][] ret = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextInt();
                }
            }
            return ret;
        }

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
            }
            return ret;
        }

        private long[][] nextLongTable(int n, int m) {
            long[][] ret = new long[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextLong();
                }
            }
            return ret;
        }

        private double[] nextDoubles(int n) {
            double[] ret = new double[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextDouble();
            }
            return ret;
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

        public double nextDouble() {
            return Double.valueOf(nextToken());
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
