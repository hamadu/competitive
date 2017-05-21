package codeforces.cf4xx.cf412.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        Map<Long,Integer> p = new HashMap<>();
        for (int i = 0; i < 50; i++) {
            p.put(1L<<i, i);
        }
        long[] a = in.nextLongs(n);
        int[] powCnt = new int[42];
        int[] between = new int[42];
        for (int i = 0; i < a.length; i++) {
            if (p.containsKey(a[i])) {
                powCnt[p.get(a[i])]++;
            } else {
                for (int l = 0 ; l < 41 ; l++) {
                    if ((1L<<l) < a[i] && a[i] < (1L<<(l+1))) {
                        between[l]++;
                        break;
                    }
                }
            }
        }

        int[] heights = new int[42];
        while (powCnt[0] >= 1) {
            for (int i = 0 ; i < 41 ; i++) {
                if (powCnt[i] >= 1) {
                    powCnt[i]--;
                } else {
                    heights[i-1]++;
                    break;
                }
            }
        }
        for (int i = 0 ; i < 41 ; i++) {
            between[i] += powCnt[i];
        }

        assert between[0] == 0;

        int ok = 0;
        int ng = n + 100;
        while (ng - ok > 1) {
            int med = (ng + ok) / 2;
            if (doit(heights.clone(), between.clone(), med) >= 0) {
                ok = med;
            } else {
                ng = med;
            }
        }


        int maxCount = doit(heights.clone(), between.clone(), 0);
        int minCount = doit(heights.clone(), between.clone(), ok);
        if (maxCount == -1) {
            out.println(-1);
        } else {
            StringBuilder line = new StringBuilder();
            for (int m = minCount ; m <= maxCount; m++) {
                line.append(' ').append(m);
            }
            out.println(line.substring(1));
        }
        out.flush();
    }

    private static int doit(int[] heights, int[] between, int dec) {
        for (int d = 0 ; d < heights.length ; d++) {
            if (heights[d] >= 1) {
                int de = Math.min(dec / (d+1), heights[d]);
                heights[d] -= de;
                for (int x = 0 ; x <= d ; x++) {
                    between[x] += de;
                }
                dec -= de * (d+1);

                if (heights[d] >= 1 && dec >= 1) {
                    heights[d]--;
                    int lastX = 1;
                    for (int x = d ; x >= 0 && dec >= 1 ; x--) {
                        dec--;
                        between[x]++;
                        lastX = x;
                    }
                    if (lastX >= 1) {
                        heights[lastX-1]++;
                    }
                }
            }
        }
        if (dec >= 1) {
            return -1;
        }

        int hoge = 0;
        for (int i = 0 ; i < heights.length ; i++) {
            hoge += heights[i];
        }

        for (int i = 0 ; i < 41 ; i++) {
            int have = between[i];
            for (int j = i ; j < 41 ; j++) {
                if (heights[j] >= 1) {
                    int de = Math.min(heights[j], have);
                    have -= de;
                    heights[j] -= de;
                }
            }
            if (have >= 1) {
                return -1;
            }
        }
        return hoge;
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
