package atcoder.tdpc;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/09/01.
 */
public class O {
    private static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int[] deg = new int[26];
        for (int i = 0; i < 26 ; i++) {
            deg[i] = in.nextInt();
        }

        long[][] ncr = new long[1000][1000];
        for (int i = 0; i < ncr.length ; i++) {
            ncr[i][0] = ncr[i][i] = 1;
            for (int j = 1 ; j < i ; j++) {
                ncr[i][j] = (ncr[i-1][j-1] + ncr[i-1][j]) % MOD;
            }
        }

        long[][][] subdp = new long[11][11][11];
        subdp[0][0][0] = 1;
        for (int i = 0; i < 11 ; i++) {
            for (int u = 0; u < 11 ; u++) {
                for (int b = 0; b < 11 ; b++) {
                    if (subdp[i][u][b] == 0) {
                        continue;
                    }
                    long base = subdp[i][u][b];
                    for (int dz = 1 ; i+dz <= 10 ; dz++) {
                        int ti = i+dz;
                        int tu = u+1;
                        int tb = b+dz-1;
                        subdp[ti][tu][tb] += base;
                        subdp[ti][tu][tb] %= MOD;
                    }
                }
            }
        }

        // ua,ub subdp[a][ua][b1]
        for (int b1 = 0; b1 <= 10; b1++) {
            for (int b2 = 0; b2 <= 10 ; b2++) {
            }
        }



        long[][] dp = new long[27][270];
        dp[0][0] = 1;

        int length = 0;
        for (int i = 0; i < 26; i++) {
            for (int k = 0; k < 270; k++) {
                if (dp[i][k] == 0) {
                    continue;
                }
                long base = dp[i][k];
                int same = k;
                int diff = length - same + 1;

                for (int a = 0; a <= deg[i] ; a++) {
                    int b = deg[i]-a;
                    for (int ua = 0; ua <= a; ua++) {
                        for (int ub = 0; ub <= b; ub++) {
                            long pick = ncr[same][ua] * ncr[diff][ub] % MOD;
                            if (pick == 0) {
                                continue;
                            }
                            for (int b1 = 0; b1 <= deg[i]; b1++) {
                                for (int b2 = 0; b2 <= deg[i]; b2++) {
                                    int tk = k+b1+b2-ua;
                                    if (tk < 0 || subdp[a][ua][b1] == 0 || subdp[b][ub][b2] == 0) {
                                        continue;
                                    }
                                    long add = pick * subdp[a][ua][b1] % MOD * subdp[b][ub][b2] % MOD;
                                    dp[i+1][tk] += base * add % MOD;
                                    dp[i+1][tk] %= MOD;
                                }
                            }
                        }
                    }
                }
            }
            length += deg[i];
        }
        out.println(dp[26][0]);
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
