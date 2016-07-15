package codeforces.cf3xx.cf357.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/07/14.
 */
public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        long x0 = in.nextLong();
        long y0 = in.nextLong();
        double A = in.nextLong() * in.nextLong();

        int n = in.nextInt();
        long[][] shadow = new long[n][3];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 3 ; j++) {
                shadow[i][j] = in.nextLong();
            }
            shadow[i][0] -= x0;
            shadow[i][1] -= y0;
        }

        boolean all = false;
        List<double[]> range = new ArrayList<>();
        for (int i = 0; i < n ; i++) {
            if (d2(shadow[i][0], shadow[i][1]) <= shadow[i][2]*shadow[i][2]) {
                all = true;
                break;
            }

            long dist = d2(shadow[i][0], shadow[i][1]);
            double rrLong = (A+shadow[i][2])*(A+shadow[i][2]);
            if (dist >= rrLong) {
                continue;
            }
            double rad = Math.atan2(shadow[i][1], shadow[i][0]) + Math.PI;
            double R = Math.sqrt(dist);
            if (dist <= A*A) {
                double r = shadow[i][2];
                double subRad = Math.asin(r/R);
                range.add(new double[]{rad-subRad, rad+subRad});
            } else  {
                double a = (A*A - shadow[i][2]*shadow[i][2] + dist) / (2 * R);
                double h = Math.sqrt(A*A - a*a);
                double p2x = a * shadow[i][0] / R;
                double p2y = a * shadow[i][1] / R;
                double p3x = p2x + h * shadow[i][1] / R;
                double p3y = p2y - h * shadow[i][0] / R;
                double p4x = p2x - h * shadow[i][1] / R;
                double p4y = p2y + h * shadow[i][0] / R;

                double subRad0 = Math.atan2(p3y, p3x) + Math.PI;
                double subRad1 = Math.atan2(p4y, p4x) + Math.PI;
                if (subRad0 > subRad1) {
                    double t = subRad0;
                    subRad0 = subRad1;
                    subRad1 = t;
                }
                if (subRad1 - subRad0 > Math.PI) {
                    double t = subRad0;
                    subRad0 = subRad1;
                    subRad1 = t;
                    subRad1 += Math.PI*2;
                }
                range.add(new double[]{ Math.min(subRad0,subRad1), Math.max(subRad0,subRad1)});
            }
        }

        if (all) {
            out.println(1);
        } else {
            List<double[]> zero2PI = new ArrayList<>();
            for (double[] r : range) {
                if (r[1] < 0) {
                    zero2PI.add(new double[]{r[0]+2*Math.PI, r[1]+2*Math.PI});
                } else if (r[0] < 0) {
                    zero2PI.add(new double[]{r[0]+2*Math.PI, 2*Math.PI});
                    zero2PI.add(new double[]{0, r[1]});
                } else if (r[1] < 2*Math.PI) {
                    zero2PI.add(new double[]{r[0], r[1]});
                } else if (r[0] < 2*Math.PI) {
                    zero2PI.add(new double[]{r[0], 2*Math.PI});
                    zero2PI.add(new double[]{0, r[1]-2*Math.PI});
                } else {
                    zero2PI.add(new double[]{r[0]-2*Math.PI, r[1]-2*Math.PI});
                }
            }
            Collections.sort(zero2PI, (r0, r1) -> Double.compare(r0[0], r1[0]));

            double wrongSum = 0;
            double covered = 0;
            for (double[] to : zero2PI) {
                wrongSum += Math.max(0, to[0]-covered);
                covered = Math.max(covered, to[1]);

            }
            wrongSum += Math.max(0, Math.PI*2-covered);
            out.println(String.format("%.9f", (Math.PI*2-wrongSum)/(Math.PI*2)));
        }

        out.flush();
    }

    // c[0]: x, c[1]: y, c[2]: r
    static double[][] intersectionOfCircle(double[] c1, double[] c2) {
        double R2 = (c1[0]-c2[0])*(c1[0]-c2[0])+(c1[1]-c2[1])*(c1[1]-c2[1]);
        double R = Math.sqrt(R2);
        double a = (c1[2]*c1[2] - c2[2]*c2[2] + R2) / (2 * R);
        double h = Math.sqrt(c1[2]*c1[2] - a*a);
        double dx = c2[0]-c1[0];
        double dy = c2[1]-c1[1];
        double p2x = a * dx / R;
        double p2y = a * dy / R;
        double p3x = p2x + h * dy / R;
        double p3y = p2y - h * dx / R;
        double p4x = p2x - h * dy / R;
        double p4y = p2y + h * dx / R;

        return new double[][]{
            { p3x+dx, p3y+dy },
            { p4x+dx, p4y+dy }
        };
    }

    static long d2(long x, long y) {
        return x*x+y*y;
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
