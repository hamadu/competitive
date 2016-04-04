package codeforces.cf0xx.cr60.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/08/03.
 */
public class C {
    private static final double INF = 1e12;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        double[][] poly = new double[n+1][3];
        for (int i = 0; i <= n ; i++) {
            for (int j = 0; j < 3 ; j++) {
                poly[i][j] = in.nextLong();
            }
        }
        long vp = in.nextLong();
        long vs = in.nextLong();
        double[] ip = new double[3];
        for (int i = 0; i < 3 ; i++) {
            ip[i] = in.nextLong();
        }

        double best = INF;
        double[] bestPoint = null;

        double time = 0;
        for (int i = 0; i < n ; i++) {
            double min = 0.0d;
            double max = 1.0d;
            for (int cur = 0; cur < 60 ; cur++) {
                double med1 = (min + max) / 2.0d;
                double[] p1 = med(poly[i], poly[i+1], med1);
                double reach1 = dist(ip, p1) / vp;
                double tt1 = time + dist(poly[i], p1) / vs;
                if (reach1 <= tt1) {
                    if (tt1 < best) {
                        best = tt1;
                        bestPoint = p1.clone();
                    }
                    min = med1;
                } else {
                    max = med1;
                }
            }
            time += dist(poly[i], poly[i+1]) / vs;
        }

        if (best == INF) {
            out.println("NO");
        } else {
            out.println("YES");
            out.println(String.format("%.9f", best));
            out.println(String.format("%.9f %.9f %.9f", bestPoint[0], bestPoint[1], bestPoint[2]));
        }
        out.flush();
    }


    static double[] med(double[] p1, double[] p2, double rate) {
        double[] ret = new double[3];
        for (int i = 0; i < 3 ; i++) {
            ret[i] = p1[i] * rate + p2[i] * (1.0d - rate);
        }
        return ret;
    }

    static double dist(double[] p1, double[] p2) {
        return Math.sqrt(dist2(p1, p2));
    }

    static double dist2(double[] p1, double[] p2) {
        double d = 0;
        for (int i = 0; i < 3 ; i++) {
            d += (p1[i] - p2[i]) * (p1[i] - p2[i]);
        }
        return d;
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
