package codeforces.other2016.intelcodechallenge2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class C {
    private static final long INF = (long)1e18;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int W = in.nextInt();
        int H = in.nextInt();
        int k = in.nextInt();
        int[][] sensors = in.nextIntTable(k, 2);

        Sensor[] xpy = new Sensor[k];
        Sensor[] xmy = new Sensor[k];
        for (int i = 0; i < k ; i++) {
            int x = sensors[i][0];
            int y = sensors[i][1];
            xpy[i] = new Sensor(i, x, y, x+y);
            xmy[i] = new Sensor(i, x, y, x-y);
        }
        Arrays.sort(xpy, (a, b) -> a.value - b.value);
        Arrays.sort(xmy, (a, b) -> a.value - b.value);

        Map<Integer,Integer> firstXPYIndex = new HashMap<>();
        for (int i = 0; i < k ; i++) {
            if (i == 0 || xpy[i-1].value != xpy[i].value) {
                firstXPYIndex.put(xpy[i].value, i);
            }
        }

        Map<Integer,Integer> firstXMYIndex = new HashMap<>();
        for (int i = 0; i < k ; i++) {
            if (i == 0 || xmy[i-1].value != xmy[i].value) {
                firstXMYIndex.put(xmy[i].value, i);
            }
        }


        long[] first = new long[k];
        Arrays.fill(first, INF);

        int nx = 0;
        int ny = 0;
        int dx = 1;
        int dy = 1;
        long time = 0;
        while (true) {
            int toX = (dx == 1) ? W - nx : nx;
            int toY = (dy == 1) ? H - ny : ny;

            // process them
            if (dx == dy) {
                // x-y is const
                int idx = firstXMYIndex.getOrDefault(nx-ny, k);
                while (idx < k && xmy[idx].value == nx-ny) {
                    Sensor sensor = xmy[idx];
                    long tt = time + Math.abs(nx - sensor.x);
                    first[sensor.id] = Math.min(first[sensor.id], tt);
                    idx++;
                }
            } else {
                // x+y is const
                int idx = firstXPYIndex.getOrDefault(nx+ny, k);
                while (idx < k && xpy[idx].value == nx+ny) {
                    Sensor sensor = xpy[idx];
                    long tt = time + Math.abs(nx - sensor.x);
                    first[sensor.id] = Math.min(first[sensor.id], tt);
                    idx++;
                }
            }

            if (toX < toY) {
                time += toX;
                nx += dx * toX;
                ny += dy * toX;
                dx *= -1;
            } else if (toX > toY) {
                time += toY;
                nx += dx * toY;
                ny += dy * toY;
                dy *= -1;
            } else {
                break;
            }
        }

        for (int i = 0; i < k ; i++) {
            out.println(first[i] == INF ? -1 : first[i]);
        }
        out.flush();
    }

    static class Sensor {
        int id;
        int x;
        int y;
        int value;

        public Sensor(int i, int xi, int yi, int v) {
            id = i;
            x = xi;
            y = yi;
            value = v;
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