package codeforces.cf3xx.cf378.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.TreeMap;
import java.util.TreeSet;

public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long[][] a = in.nextLongTable(n, 3);

        long max = 0;
        int x = -1;
        int y = -1;
        for (int i = 0; i < n ; i++) {
            long r = Math.min(Math.min(a[i][0], a[i][1]), a[i][2]);
            if (max < r) {
                max = r;
                x = i;
                y = -1;
            }
        }

        TreeMap<Long,Long> ts = new TreeMap<>();
        TreeMap<Long,Integer> tid = new TreeMap<>();
        for (int i = n-1 ; i >= 0 ; i--) {
            for (int j = 0; j < 3 ; j++) {
                for (int k = j+1 ; k < 3 ; k++) {
                    long id = (a[i][j]<<30)|a[i][k];
                    if (ts.containsKey(id)) {
                        long r = Math.min(Math.min(a[i][j], a[i][k]), a[i][3-j-k] + ts.get(id));
                        if (max < r) {
                            max = r;
                            x = i;
                            y = tid.get(id);
                        }

                    }

                }
            }

            for (int j = 0; j < 3 ; j++) {
                for (int k = 0 ; k < 3 ; k++) {
                    if (j == k) {
                        continue;
                    }
                    long id = (a[i][j]<<30)|a[i][k];
                    long fo = ts.getOrDefault(id, 0L);
                    if (fo < a[i][3-j-k]) {
                        ts.put(id, a[i][3-j-k]);
                        tid.put(id, i);
                    }
                }
            }
        }

        if (x != -1 && y != -1) {
            out.println(2);
            out.println(String.format("%d %d", x+1, y+1));
        } else {
            out.println(1);
            out.println(x+1);
        }
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
