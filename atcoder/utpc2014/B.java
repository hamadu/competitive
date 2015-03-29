package atcoder.utpc2014;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class B {

    static int dbl(String x) {
        String pt = x.substring(x.length() - 3);
        boolean isminus = false;
        String xf = x;
        if (x.charAt(0) == '-') {
            isminus = true;
            xf = x.substring(1);
        }
        xf = xf.substring(0, xf.length() - 4);
        return (Integer.valueOf(xf) * 1000 + Integer.valueOf(pt)) * (isminus ? -1 : 1);
    }

    static boolean check() {
        for (int x = -999 ; x <= 999 ; x++) {
            for (int y = 0 ; y <= 999 ; y++) {
                String l = Math.abs(x) + "." + String.format("%03d", y);
                if (x < 0) {
                    l = "-" + l;
                }
                int nx = dbl(l);
//                double f = Double.parseDouble(l)+1e-9;
//                int nx = (int)(f * 1000);
                int ax = nx / 1000;
                int dx = Math.abs(nx % 1000);
                if (ax != x || y != dx) {
                    debug("err!", x, y, ax, dx);
                }
            }
        }
        return true;
    }


    static void pr(PrintWriter out, int ax, int ay, int bx, int by) {
        out.println(ax + " " + ay + " " + bx + " " + by);
    }

    public static void main(String[] args) {
        // debug(dbl("-1.300"));
        // debug(check());

        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        String[] pair = in.nextLine().split(" ");
        // double x = in.nextDouble();
        // double y = in.nextDouble();
        boolean rx = false;
        boolean ry = false;
        int nx = dbl(pair[0]);
        int ny = dbl(pair[1]);
        if (nx < 0) {
            rx = true;
            nx = Math.abs(nx);
        }
        if (ny < 0) {
            ry = true;
            ny = Math.abs(ny);
        }


        int ax = nx / 1000;
        int ay = ny / 1000;
        int dx = nx % 1000;
        int dy = ny % 1000;
        if (dx == 0 && dy == 0) {
            pr(out, ax-1, ay, ax+1, ay);
            pr(out, ax, ay - 1, ax, ay + 1);
            out.flush();
            return;
        }


        int[][] line = new int[2][4];
        line[0] = new int[]{0, 0, dx, dy};
        line[1] = new int[]{0, 1, dx, 1-(1000-dy)};
        if (dx == 0) {
            line[1] = new int[]{1, 1, 1-(1000-dx), 1-(1000-dy)};
        }

        for (int i = 0 ; i <= 1 ; i++) {
            line[i][0] += ax;
            line[i][2] += ax;
            line[i][1] += ay;
            line[i][3] += ay;
        }

        if (rx) {
            for (int i = 0 ; i <= 1 ; i++) {
                line[i][0] *= -1;
                line[i][2] *= -1;
            }
        }
        if (ry) {
            for (int i = 0 ; i <= 1 ; i++) {
                line[i][1] *= -1;
                line[i][3] *= -1;
            }
        }

        for (int i = 0 ; i <= 1 ; i++) {
            pr(out, line[i][0], line[i][1], line[i][2], line[i][3]);
        }
        out.flush();
    }

    private static int[] parse(String s) {
        String[] f = s.split("\\.");
        int a = Integer.valueOf(f[0]);
        int b = Integer.valueOf(f[1]);
        return new int[]{a, b};
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
                res += c - '0';
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
                res += c - '0';
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



