package csacademy.round009;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/08/11.
 */
public class F {
    private static final long MOD = (long)1e9+7;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();

        char[] c = in.nextToken().toCharArray();
        d = new int[n];
        for (int i = 0; i < n ; i++) {
            d[i] = c[i]-'0';
        }

        imos = new int[10][n+1];
        for (int i = 0; i < 10 ; i++) {
            for (int j = 0; j < n; j++) {
                imos[i][j+1] += imos[i][j];
                if (d[j] == i) {
                    imos[i][j+1] += 1;
                }
            }
        }

        memo = new long[2][n+1][n+1][11][11];
        for (int f = 0; f <= 1; f++) {
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= n; j++) {
                    for (int k = 0; k < 11; k++) {
                        Arrays.fill(memo[f][i][j][k],-1);
                    }
                }
            }
        }

        long ans = dfs(1, 0, n-1, 10, 10);

        out.println(ans % MOD);
        out.flush();
    }

    static int[][] imos;
    static long[][][][][] memo;

    static long dfs(int flg, int l, int r, int last1, int last2) {
        if (memo[flg][l][r][last1][last2] != -1) {
            return memo[flg][l][r][last1][last2];
        }
        long ret = 0;
        if (l > r) {
        } else {
            if (flg == 1) {
                // match one
                if (last2 == 10) {
                    ret += imos[0][r+1]-imos[0][l];
                } else if (last2*2 < 10) {
                    ret += imos[last2*2][r+1]-imos[last2*2][l];
                }

                // match two
                {
                    int want = ((last1==10) ? 0 : last1)+((last2==10) ? 0 : last2);
                    if (want <= 9) {
                        int cnt = imos[want][r+1]-imos[want][l];
                        ret += cnt*(cnt-1)/2;
                        ret %= MOD;
                    }
                }
            }

            // dont use l
            ret += dfs(0, l+1, r, last1, last2);

            // use l
            int tr = r;
            while (l < tr) {
                if (d[l] == d[tr]) {
                    if (last2 == 10) {
                        ret += dfs(1, l+1, tr-1, d[l], last1);
                    } else if (last2 <= d[l]){
                        ret += dfs(1, l+1, tr-1, d[l]-last2, last1);
                    }
                }
                tr--;
            }
        }
        ret %= MOD;
        memo[flg][l][r][last1][last2] = ret;
        return ret;
    }


    static int[] d;

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
