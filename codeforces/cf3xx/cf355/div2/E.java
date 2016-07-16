package codeforces.cf3xx.cf355.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/07/16.
 */
public class E {
    private static final int[] DY = {0, 1, 0, -1};
    private static final int[] DX = {1, 0, -1, 0};

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] map2 = new int[n][n];
        int[][] map3 = new int[n][n];
        boolean hasNonZero = false;
        for (int i = 0; i < n ; i++) {
            char[] c = in.nextToken().toCharArray();
            for (int j = 0; j < n ; j++) {
                if (c[j] == '0') {
                    map2[i][j] = map3[i][j] = -1;
                } else {
                    hasNonZero = true;
                    map2[i][j] = c[j] == '2' ? 1 : 0;
                    map3[i][j] = c[j] == '3' ? 1 : 0;
                }
            }
        }

        if (!hasNonZero) {
            out.println(0);
            out.flush();
            return;
        }

        for (int i = 0; i < pow2.length; i++) {
            pow2[i] = log2 * i;
            pow3[i] = log3 * i;
        }

        int[][] ret = new int[3][2];
        ret[0] = doit(spacing(map2), spacing(map3));

        {
            for (int d = 0 ; d <= 1 ; d++) {
                int[][] diag2 = new int[n+4][n+4];
                int[][] diag3 = new int[n+4][n+4];
                for (int i = 0; i < n+4; i++) {
                    Arrays.fill(diag2[i], -1);
                    Arrays.fill(diag3[i], -1);
                }
                for (int i = 0; i+d < n; i++) {
                    diag2[i][n/2+2] = map2[i][i+d];
                    diag3[i][n/2+2] = map3[i][i+d];
                    int ny = n/2+2;
                    for (int j = 1; j < n; j++) {
                        int ti = i-j;
                        int tj = i+j+d;
                        if (ti < 0 || tj < 0 || ti >= n || tj >= n) {
                            break;
                        }
                        diag2[i][ny-j] = map2[ti][tj];
                        diag3[i][ny-j] = map3[ti][tj];
                    }
                    for (int j = 1; j < n; j++) {
                        int ti = i+j;
                        int tj = i-j+d;
                        if (ti < 0 || tj < 0 || ti >= n || tj >= n) {
                            break;
                        }
                        diag2[i][ny+j] = map2[ti][tj];
                        diag3[i][ny+j] = map3[ti][tj];
                    }
                }
                ret[d+1] = doit(spacing(diag2), spacing(diag3));
            }
        }

        int[] be = ret[0].clone();
        for (int i = 1 ; i < 3 ; i++) {
            if (compare(be, ret[i]) < 0) {
                be = ret[i].clone();
            }
        }

        long ans = pow(2, be[0]) * pow(3, be[1]) % MOD;
        out.println(ans);
        out.flush();
    }

    static final int MOD = 1000000007;

    static long pow(long a, long x) {
        long res = 1;
        while (x > 0) {
            if (x % 2 != 0) {
            res = (res * a) % MOD;
            }
            a = (a * a) % MOD;
            x /= 2;
        }
        return res;
    }


    private static int[][] spacing(int[][] map) {
        int n = map.length;
        int[][] ret = new int[n+2][n+2];
        for (int i = 0; i < n+2 ; i++) {
            Arrays.fill(ret[i], -1);
        }
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                ret[i+1][j+1] = map[i][j];
            }
        }
        return ret;
    }

    static int[] doit(int[][] map2, int[][] map3) {
        double best = -1;
        int[] best23 = new int[]{0,0};
        long[][][] imo = go(map2, map3);
        int n = map2.length;
        long MASK = (1L<<16)-1;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                int far = 10000;
                for (int d = 0 ; d < 4 ; d++) {
                    far = Math.min(far, (int)(imo[d][i][j]&MASK));
                }
                if (far == 0) {
                    continue;
                }
                int d2 = 0;
                int d3 = 0;
                for (int d = 0 ; d < 4 ; d++) {
                    int ti = i+DY[d]*far;
                    int tj = j+DX[d]*far;
                    d3 += (int)((imo[d][i][j]>>32) - (imo[d][ti][tj]>>32));
                    d2 += (int)(((imo[d][i][j]>>16)&MASK) - ((imo[d][ti][tj]>>16)&MASK));
                }
                d2 -= map2[i][j] * 3;
                d3 -= map3[i][j] * 3;
                if (best < pow2[d2] + pow3[d3]) {
                    best = pow2[d2] + pow3[d3];
                    best23 = new int[]{d2, d3};
                }
            }
        }
        return best23;
    }


    static long[][][] go(int[][] map2, int[][] map3) {
        int n = map2.length;

        // two, three, step
        long[][][] imo = new long[4][n][n];
        long MASK = (1L<<16)-1;

        for (int d = 0 ; d <= 2 ; d += 2) {
            int last = d == 0 ? n-1 : 0;
            for (int i = 0; i < n; i++) {
                if (map2[i][last] == -1) {
                    imo[d][i][last] = 0;
                } else {
                    imo[d][i][last] = (((long)map3[i][last])<<32L)+(((long)map2[i][last])<<16L)+1;
                }
                int j = d == 0 ? n-2 : 1;
                int dj = d == 0 ? -1 : 1;
                while (0 <= j && j < n) {
                    long prev = imo[d][i][j-dj];
                    if (map2[i][j] == -1) {
                        imo[d][i][j] = 0;
                    } else {
                        long to3 = ((prev>>32)+map3[i][j])<<32L;
                        long to2 = (((prev>>16)&MASK)+map2[i][j])<<16L;
                        long top = (prev&MASK)+1;
                        imo[d][i][j] = to3|to2|top;
                    }
                    j += dj;
                }
            }
        }
        for (int d = 1 ; d <= 3 ; d += 2) {
            int last = d == 1 ? n-1 : 0;
            for (int i = 0; i < n; i++) {
                if (map2[last][i] == -1) {
                    imo[d][last][i] = 0;
                } else {
                    imo[d][last][i] = ((long) map3[last][i]<<32L)+((long) map2[last][i]<<16L)+1;
                }
                int j = d == 1 ? n-2 : 1;
                int dj = d == 1 ? -1 : 1;
                while (0 <= j && j < n) {
                    long prev = imo[d][j-dj][i];
                    if (map2[j][i] == -1) {
                        imo[d][j][i] = 0;
                    } else {
                        long to3 = ((prev>>32)+map3[j][i])<<32;
                        long to2 = (((prev>>16)&MASK)+map2[j][i])<<16;
                        long top = (prev&MASK)+1;
                        imo[d][j][i] = to3|to2|top;
                    }
                    j += dj;
                }
            }
        }
        return imo;
    }

    static void debug(long f) {
        debug(f>>32, f>>16&((1<<16)-1), f&((1<<16)-1));
    }

    static long compare(int[] tt0, int[] tt1) {
        double d0 = pow2[tt0[0]] + pow3[tt0[1]];
        double d1 = pow2[tt1[0]] + pow3[tt1[1]];
        return Double.compare(d0, d1);
    }


    static final double log2 = Math.log(2);
    static final double log3 = Math.log(3);
    static final double[] pow2 = new double[3200];
    static final double[] pow3 = new double[3200];

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
