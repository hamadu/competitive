package atcoder.other2016.s8pc002;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 4/25/16.
 */
public class G {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        Point[] factories = new Point[n];
        for (int i = 0; i < n ; i++) {
            factories[i] = new Point(in.nextInt(), in.nextInt());
        }
        List<Point> hull = convexHull(factories);
        long outerPoints = hull.size();
        long innerPoints = n - outerPoints;
        out.println(outerPoints * 2 + (innerPoints - 1) * 3);
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
            return res * sgn;
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
            return res * sgn;
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
