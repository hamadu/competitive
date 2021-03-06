package atcoder.arc.arc020;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/03/19.
 */
public class D {
    private static final int MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        int[] d = new int[n-1];
        for (int i = 0; i < n-1 ; i++) {
            d[i] = in.nextInt();
        }
        int[][][] dp = new int[n][1<<k][m];
        dp[0][0][0] = 1;
        for (int i = 0; i < n-1 ; i++) {
            for (int ptn = 0; ptn < (1 << k); ptn++) {
                for (int mod = 0; mod < m ; mod++) {
                    if (dp[i][ptn][mod] == 0) {
                        continue;
                    }
                    int base = dp[i][ptn][mod];
                    for (int l = 0 ; l < k ; l++) {
                        if ((ptn & (1<<l)) == 0) {
                            int tptn = ptn | (1<<l);
                            int tmod = (mod + compute(tptn, k) * d[i]) % m;
                            dp[i+1][tptn][tmod] += base;
                            dp[i+1][tptn][tmod] %= MOD;
                        }
                    }

                    int tmod = (mod + compute(ptn, k) * d[i]) % m;
                    dp[i+1][ptn][tmod] += base;
                    dp[i+1][ptn][tmod] %= MOD;
                }
            }
        }

        long ans = 0;
        for (int p = 0; p < (1<<k) ; p++) {
            if (Integer.bitCount(p) >= k-1) {
                ans += dp[n-1][p][0];
            }
        }
        out.println(ans % MOD);
        out.flush();
    }

    private static int compute(int tptn, int k) {
        int now = tptn&1;
        int ctr = 0;
        for (int i = 1; i < k ; i++) {
            int to = (tptn>>i)&1;
            if (to != now) {
                ctr++;
            }
            now = to;
        }
        // debug(tptn,k,ctr);
        return ctr;
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
