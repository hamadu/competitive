package codeforces.cr76.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 15/09/08.
 */
public class D {
    private static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        long[][] mat = buildMat();
        long[][] emat = extendForSummation(mat);

        int L = in.nextInt();
        int R = in.nextInt();
        long ans = (solveE(R, emat) - solveE(L-1, emat) + MOD) % MOD;
        out.println(ans);
        out.flush();
    }

    static long solveE(int X, long[][] mat) {
        long p1 = solve((X+1)/2, mat);
        long p2 = solve(X, mat);

        return (p1 + p2) * inv(2, MOD) % MOD;
    }

    static long solve(int X, long[][] mat) {
        long ret = 0;
        long[][] add = pow(mat, X+1, MOD);
        int n = mat.length / 2;
        for (int i = 0; i <= 24; i++) {
            ret += add[n+i][24];
        }
        return (ret + MOD - 1) % MOD;
    }

    static long pow(long a, long x, long MOD) {
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

    static long inv(long a, long MOD) {
        return pow(a, MOD - 2, MOD) % MOD;
    }

    static long[][] extendForSummation(long[][] mat) {
        int n = mat.length;
        long[][] ret =  new long[2*n][2*n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n ; j++) {
                ret[i][j] = mat[i][j];
            }
        }
        for (int i = 0; i < n ; i++) {
            ret[n+i][i] = ret[n+i][n+i] = 1;
        }
        return ret;
    }

    public static long[][] pow(long[][] a, long n, long mod) {
        long i = 1;
        long[][] res = E(a.length);
        long[][] ap = mul(E(a.length), a, mod);
        while (i <= n) {
            if ((n & i) >= 1) {
                res = mul(res, ap, mod);
            }
            i *= 2;
            ap = mul(ap, ap, mod);
        }
        return res;
    }

    public static long[][] E(int n) {
        long[][] a = new long[n][n];
        for (int i = 0 ; i < n ; i++) {
            a[i][i] = 1;
        }
        return a;
    }

    public static long[][] mul(long[][] a, long[][] b, long mod) {
        long[][] c = new long[a.length][b[0].length];
        if (a[0].length != b.length) {
            System.err.print("err");
        }
        for (int i = 0 ; i < a.length ; i++) {
            for (int j = 0 ; j < b[0].length ; j++) {
                long sum = 0;
                for (int k = 0 ; k < a[0].length ; k++) {
                    sum = (sum + a[i][k] * b[k][j]) % mod;
                }
                c[i][j] = sum;
            }
        }
        return c;
    }

    static long[][] buildMat() {
        Color[][] forbiddenPatterns = {
                {Color.WHITE, Color.WHITE},
                {Color.BLACK, Color.BLACK},
                {Color.RED, Color.RED},
                {Color.YELLOW, Color.YELLOW},
                {Color.WHITE, Color.YELLOW},
                {Color.YELLOW, Color.WHITE},
                {Color.RED, Color.BLACK},
                {Color.BLACK, Color.RED},
                {Color.BLACK, Color.WHITE, Color.RED},
                {Color.RED, Color.WHITE, Color.BLACK},
        };


        long[][] mat = new long[25][25];
        for (Color c1 : Color.values()) {
            for (Color c2 : Color.values()) {
                for (Color c3 : Color.values()) {
                    if (c3 == Color.NONE) {
                        continue;
                    }
                    if (c2 == Color.NONE && c1 != Color.NONE) {
                        continue;
                    }

                    // c1-c2-c3
                    int from = Color.encode(c1, c2);
                    int to = Color.encode(c2, c3);
                    boolean isOK = true;
                    for (Color[] ngColors : forbiddenPatterns) {
                        int n = ngColors.length;
                        if (n == 2) {
                            if (c1 == ngColors[0] && c2 == ngColors[1]) {
                                isOK = false;
                            }
                            if (c2 == ngColors[0] && c3 == ngColors[1]) {
                                isOK = false;
                            }
                        } else {
                            if (c1 == ngColors[0] && c2 == ngColors[1] && c3 == ngColors[2]) {
                                isOK = false;
                            }
                        }
                    }
                    if (isOK) {
                        mat[to][from]++;
                    }
                }
            }
        }
        return mat;
    }

    static enum Color {
        WHITE(0), BLACK(1), RED(2), YELLOW(3), NONE(4);

        private final int value;

        Color(int value) {
            this.value = value;
        }

        public int toInt() { return value; }

        public static int encode(Color c1, Color c2) {
            return c1.toInt() * 5 + c2.toInt();
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
                res += c-'0';
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
