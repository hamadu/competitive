package csacademy.round001;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/08/14.
 */
public class F {
    private static final int INF = (int)1e9;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[][] platform = in.nextIntTable(n, 2);
        int[] ball = in.nextInts(m);
        shuffleAndSort(ball);

        Arrays.sort(platform, (p0, p1) -> (p1[1]-p1[0]) - (p0[1]-p0[0]));
        List<Gap> gaps = new ArrayList<>();
        gaps.add(new Gap(gaps.size(), -INF, ball[0]));
        for (int i = 0 ; i+1 < m ; i++) {
            if (ball[i] != ball[i+1]) {
                gaps.add(new Gap(gaps.size(), ball[i], ball[i+1]));
            }
        }
        gaps.add(new Gap(gaps.size(), ball[m-1], INF));
        Collections.sort(gaps, (g0, g1) -> g1.length - g0.length);


        long total = 0;
        int gi = 0;
        TreeSet<Gap> gleft = new TreeSet<>((g0, g1) -> (g0.l == g1.l) ? g0.idx - g1.idx : g0.l - g1.l);
        TreeSet<Gap> gright = new TreeSet<>((g0, g1) -> (g0.l == g1.l) ? g0.idx - g1.idx : g0.l - g1.l);
        for (int i = 0 ; i < n ; i++) {
            int req = platform[i][1] - platform[i][0];
            while (gi < gaps.size()  && gaps.get(gi).length >= req) {
                gleft.add(gaps.get(gi));
                gright.add(gaps.get(gi));
                gi++;
            }
            Gap l = gleft.lower(new Gap(INF, platform[i][0], platform[i][0]));
            if (l.l <= platform[i][0] && platform[i][1] <= l.r) {
                continue;
            }

            Gap ll = gleft.higher(new Gap(INF, platform[i][0], platform[i][0]));
            Gap rr = gright.lower(new Gap(-INF, platform[i][1], platform[i][1]));

            total += Math.min(Math.abs(platform[i][0]-ll.l), Math.abs(platform[i][1]-rr.r));
        }

        out.println(total);
        out.flush();
    }

    static class Gap {
        public int idx;
        public int l;
        public int r;
        public int length;
        Gap(int i, int a, int b) {
            idx = i;
            l = a;
            r = b;
            length = b-a;
        }
    }

    // against for quick-sort killer
    static void shuffleAndSort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n ; i++) {
            int idx = (int)(Math.random() * i);
            int tmp = a[idx];
            a[idx] = a[i];
            a[i] = tmp;
        }
        Arrays.sort(a);
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
