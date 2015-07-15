package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/14.
 */
public class P1348 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int D = in.nextInt();
        int n = in.nextInt();
        int b = in.nextInt();

        int[][] obstacles = new int[n][2];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 2 ; j++) {
                obstacles[i][j] = in.nextInt();
            }
        }

        double minV = Double.MAX_VALUE;
        for (int bi = 0 ; bi <= b ; bi++) {
            double len = D * 1.0d / (bi + 1);
            double vxvy = len / 2;

            double minVy = 0.0d;
            double maxVy = 10000000.0d;
            for (int cur = 0 ; cur < 100 ; cur++) {
                double vy = (minVy + maxVy) / 2;
                double vx = vxvy / vy;
                boolean hit = false;
                for (int i = 0 ; i < n ; i++) {
                    int ct = (int)Math.floor(obstacles[i][0] / len);
                    double dx = obstacles[i][0] - len * ct;
                    double dy = - (dx * dx) / (2 * vx * vx) + (vy / vx) * dx;
                    if (dy <= obstacles[i][1]) {
                        hit = true;
                        break;
                    }
                }
                if (hit) {
                    minVy = vy;
                } else {
                    maxVy = vy;
                }
            }


            minVy = maxVy;
            maxVy = 10000000.0d;
            for (int cur = 0 ; cur < 300 ; cur++) {
                double vy1 = (minVy * 2 + maxVy) / 3;
                double vx1 = vxvy / vy1;
                double v1 = Math.sqrt(vy1 * vy1 + vx1 * vx1);

                double vy2 = (minVy + maxVy * 2) / 3;
                double vx2 = vxvy / vy2;
                double v2 = Math.sqrt(vy2 * vy2 + vx2 * vx2);
                if (v1 < v2) {
                    maxVy = vy2;
                } else {
                    minVy = vy1;
                }
            }

            double bestVy = maxVy;
            double bestVx = vxvy / bestVy;
            double v = Math.sqrt(bestVx * bestVx + bestVy * bestVy);
            minV = Math.min(minV, v);
        }
        out.println(minV);
        out.flush();
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
