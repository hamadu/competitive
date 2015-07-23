package aoj.vol25;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/23.
 */
public class P2592 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            if (n == 0) {
                break;
            }
            int pw = in.nextInt();
            int[][] plants = new int[n][4];
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < 4 ; j++) {
                    plants[i][j] = in.nextInt();
                }
            }
            out.println(String.format("%.9f", solve(pw, plants)));
        }
        out.flush();
    }

    private static double solve(int pw, int[][] plants) {
        double min = 0;
        double max = 10000;
        double minCost = Double.MAX_VALUE;
        for (int i = 0; i < 80 ; i++) {
            double wleft = (min * 2 + max) / 3.0d;
            double wright = (min + max * 2) / 3.0d;
            double cost1 = doit(pw, plants, wleft);
            double cost2 = doit(pw, plants, wright);
            minCost = Math.min(minCost, cost1);
            minCost = Math.min(minCost, cost2);
            if (cost1 < cost2) {
                max = wright;
            } else {
                min = wleft;
            }
        }
        return minCost;
    }

    private static double doit(int pw, int[][] plants, double water) {
        double cost = pw * water;
        int n = plants.length;
        for (int i = 0; i < n ; i++) {
            double height = plants[i][0] * water;
            double needHeight = plants[i][3] - height;
            if (needHeight > 0) {
                cost += plants[i][1] * needHeight / plants[i][2];
            }
        }
        return cost;
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
