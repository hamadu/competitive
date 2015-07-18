package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.BitSet;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/17.
 */
public class P1331 {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            int m = in.nextInt();
            int r = in.nextInt();
            if (n + m + r == 0) {
                break;
            }

            int[][] balloons = new int[n][4];
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < 4 ; j++) {
                    balloons[i][j] = in.nextInt();
                }
            }

            int[][] lights = new int[m][4];
            for (int i = 0; i < m ; i++) {
                for (int j = 0; j < 4 ; j++) {
                    lights[i][j] = in.nextInt();
                }
            }

            int[] point = new int[3];
            for (int i = 0; i < 3 ; i++) {
                point[i] = in.nextInt();
            }

            out.println(solve(balloons, lights, point, r));
        }
        out.flush();
    }

    private static double solve(int[][] balloons, int[][] lights, int[] point, int R) {
        int n = balloons.length;
        int m = lights.length;

        int[][] lm = new int[m][n];
        int[] lidx = new int[m];
        for (int li = 0 ; li < m ; li++) {
            int[] l = lights[li];
            for (int i = 0 ; i < n ; i++) {
                int[] b = balloons[i];
                if (lineToSphere(l[0], l[1], l[2], point[0], point[1], point[2], b[0], b[1], b[2], b[3])) {
                    lm[li][lidx[li]++] = i;
                }
            }
        }

        double[] score = new double[m];
        for (int i = 0; i < m ; i++) {
            int[] l = lights[i];
            score[i] = l[3] * 1.0d / d2(l[0], l[1], l[2], point[0], point[1], point[2]);
        }

        double max = 0;

        BitSet bset = new BitSet(n);
        for (int i = 0 ; i < (1<<m) ; i++) {
            double sum = 0;
            bset.clear();
            for (int k = 0; k < m; k++) {
                if ((i & (1<<k)) >= 1) {
                    sum += score[k];
                    for (int li = 0; li < lidx[k]; li++) {
                        bset.set(lm[k][li]);
                    }
                }
            }
            if (bset.cardinality() <= R) {
                max = Math.max(max, sum);
            }
        }
        return max;
    }

    private static boolean lineToSphere(int x0, int y0, int z0, int x1, int y1, int z1, int sx, int sy, int sz, int sr) {
        int r2 = sr * sr;
        int d0 = d2(x0, y0, z0, sx, sy, sz);
        int d1 = d2(x1, y1, z1, sx, sy, sz);
        if (d0 <= r2 && d1 <= r2) {
            return false;
        }
        if (d0 <= r2 || d1 <= r2) {
            return true;
        }

        int Z = cross2(x1 - x0, y1 - y0, z1 - z0, sx - x0, sy - y0, sz - z0);
        int d01 = d2(x0, y0, z0, x1, y1, z1);
        if (Z >= r2 * d01) {
            return false;
        }

        int dot1 = dot(sx - x0, sy - y0, sz - z0, x1 - x0, y1 - y0, z1 - z0);
        int dot2 = dot(sx - x1, sy - y1, sz - z1, x1 - x0, y1 - y0, z1 - z0);
        if (dot1 * dot2 <= 0) {
            return true;
        }
        return false;
    }

    private static int dot(int ax, int ay, int az, int bx, int by, int bz) {
        return ax*bx + ay*by + az*bz;
    }

    private static int cross2(int ax, int ay, int az, int bx, int by, int bz) {
        int yz = ay * bz - by * az;
        int zx = az * bx - bz * ax;
        int xy = ax * by - bx * ay;
        return yz * yz + zx * zx + xy * xy;
    }

    private static int d2(int x0, int y0, int z0, int x1, int y1, int z1) {
        int dx = x0 - x1;
        int dy = y0 - y1;
        int dz = z0 - z1;
        return dx*dx + dy*dy + dz*dz;
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
