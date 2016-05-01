package gcj.gcj2016.round1b;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Created by hama_du on 5/1/16.
 */
public class B {

    private static final long INF = (long) 4e18;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = in.nextInt();
        for (int t = 1 ; t <= T ; t++) {
            char[] c = in.nextToken().toCharArray();
            char[] j = in.nextToken().toCharArray();
            out.println(String.format("Case #%d: %s", t, solve(c, j)));
        }
        out.flush();
    }

    private static String solve(char[] c, char[] j) {
        int n = c.length;
        long[][][] dp = new long[n+1][3][3];
        for (int i = 0; i <= n ; i++) {
            for (int k = 0; k <= 2; k++) {
                Arrays.fill(dp[i][k], INF);
            }
        }

        long[] keta = new long[n];
        keta[n-1] = 1;
        for (int i = n-2 ; i >= 0 ; i--) {
            keta[i] = keta[i+1] * 10;
        }

        dp[0][0] = new long[]{0, 0, 0};
        for (int i = 0; i < n ; i++) {
            for (int f = 0 ; f <= 2 ; f++) {
                if (dp[i][f][0] == INF) {
                    continue;
                }
                long base = dp[i][f][0];
                long baseC = dp[i][f][1];
                long baseJ = dp[i][f][2];
                for (int cd = 0 ; cd <= 9 ; cd++) {
                    for (int jd = 0; jd <= 9 ; jd++) {
                        if (c[i] != '?' && c[i] != (char)('0' + cd)) {
                            continue;
                        }
                        if (j[i] != '?' && j[i] != (char)('0' + jd)) {
                            continue;
                        }
                        long toC = baseC + cd * keta[i];
                        long toJ = baseJ + jd * keta[i];
                        long toValue = toC - toJ;
                        long toAbs = Math.abs(toValue);

                        int tidx = toC == toJ ? 0 : (toC > toJ) ? 1 : 2;
                        long bfAbs = Math.abs(dp[i+1][tidx][0]);
                        boolean upd = false;
                        if (bfAbs > toAbs) {
                            upd = true;
                        } else if (bfAbs == toAbs && dp[i+1][tidx][1] > toC) {
                            upd = true;
                        } else if (bfAbs == toAbs && dp[i+1][tidx][1] == toC && dp[i+1][tidx][2] > toJ) {
                            upd = true;
                        }
                        if (upd) {
                            dp[i+1][tidx][0] = toValue;
                            dp[i+1][tidx][1] = toC;
                            dp[i+1][tidx][2] = toJ;
                        }
                   }
                }
            }
        }

        int fidx = 0;
        for (int k = 1 ; k <= 2;  k++) {
            if (Math.abs(dp[n][fidx][0]) > Math.abs(dp[n][k][0])) {
                fidx = k;
            } else if (Math.abs(dp[n][fidx][0]) == Math.abs(dp[n][k][0]) && dp[n][fidx][1] > dp[n][k][1]) {
                fidx = k;
            } else if (Math.abs(dp[n][fidx][0]) == Math.abs(dp[n][k][0]) && dp[n][fidx][1] == dp[n][k][1] && dp[n][fidx][2] > dp[n][k][2]) {
                fidx = k;
            }
        }

        String fmt = "%0" + n + "d %0" + n + "d";
        return String.format(fmt, dp[n][fidx][1], dp[n][fidx][2]);
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
