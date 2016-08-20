package codeforces.cf3xx.cf330.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/08/20.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        Magnet[] mag = new Magnet[n];
        for (int i = 0; i < n ; i++) {
            mag[i] = new Magnet(i, in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt());
        }

        if (n - k <= 1) {
            out.println(1);
            out.flush();
            return;
        }

        Integer[] xOrder = new Integer[n];
        Integer[] yOrder = new Integer[n];
        for (int i = 0; i < n ; i++) {
            xOrder[i] = i;
            yOrder[i] = i;
        }
        Arrays.sort(xOrder, (x0, x1) -> Long.compare(mag[x0].cx, mag[x1].cx));
        Arrays.sort(yOrder, (x0, x1) -> Long.compare(mag[x0].cy, mag[x1].cy));

        TreeSet<Magnet> lr = new TreeSet<>((m0, m1) -> (m0.cx == m1.cx) ? m0.id - m1.id : Long.compare(m0.cx, m1.cx));
        TreeSet<Magnet> tb = new TreeSet<>((m0, m1) -> (m0.cy == m1.cy) ? m0.id - m1.id : Long.compare(m0.cy, m1.cy));
        for (Magnet m : mag) {
            lr.add(m);
            tb.add(m);
        }

        long min = Long.MAX_VALUE;

        boolean[] removed = new boolean[n];
        for (int top = 0 ; top <= k ; top++) {
            for (int bottom = 0 ; top + bottom <= k ; bottom++) {
                for (int left = 0 ; left + top + bottom <= k ; left++) {
                    int right = k - top - bottom - left;

                    int[] tblr = new int[]{top, bottom, left, right};
                    List<Magnet> rm = new ArrayList<>();
                    for (int d = 0 ; d < 4 ; d++) {
                        Integer[] ptr = (d <= 1) ? yOrder : xOrder;
                        int pidx = (d & 1) == 0 ? 0 : n-1;
                        int pdiff = (d & 1) == 0 ? 1 : -1;

                        while (tblr[d] >= 1 && pidx >= 0 && pidx <= n-1) {
                            if (!removed[ptr[pidx]]) {
                                removed[ptr[pidx]] = true;

                                Magnet theMag = mag[ptr[pidx]];
                                rm.add(theMag);
                                lr.remove(theMag);
                                tb.remove(theMag);

                                tblr[d]--;
                            }
                            pidx += pdiff;
                        }
                    }

                    long l = lr.first().cx;
                    long r = lr.last().cx;
                    long t = tb.first().cy;
                    long b = tb.last().cy;

                    if (r % 2 != l % 2) {
                        l--;
                    }
                    if (t % 2 != b % 2) {
                        t--;
                    }
                    if (l == r) {
                        r = l+2;
                    }
                    if (t == b) {
                        b = t+2;
                    }
                    min = Math.min(min, (r-l)*(b-t)/4);

                    lr.addAll(rm);
                    tb.addAll(rm);
                    for (Magnet m : rm) {
                        removed[m.id] = false;
                    }
                }
            }
        }

        out.println(min);
        out.flush();
    }

    static class Magnet {
        int id;
        long x1, y1, x2, y2;
        long cx, cy;

        public Magnet(int i, long a, long b, long c, long d) {
            id = i;
            x1 = a;
            y1 = b;
            x2 = c;
            y2 = d;
            cx = (x1 + x2);
            cy = (y1 + y2);

        }
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
