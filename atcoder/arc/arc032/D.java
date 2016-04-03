// http://arc032.contest.atcoder.jp/submissions/363613
package atcoder.arc.arc032;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class D {
    static final int MAX = 3000;

    static final long MOD = 1000000007;

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

    static long inv(long a) {
        return pow(a, MOD - 2) % MOD;
    }

    static long[] _fact;
    static long[] _invfact;
    static long comb(long ln, long lr) {
        int n = (int)ln;
        int r = (int)lr;
        if (n < 0 || r < 0 || r > n) {
            return 0;
        }
        if (r > n / 2) {
            r = n - r;
        }
        return (((_fact[n] * _invfact[n - r]) % MOD) * _invfact[r]) % MOD;
    }

    static void prec(int n) {
        _fact = new long[n + 1];
        _invfact = new long[n + 1];
        _fact[0] = 1;
        _invfact[0] = 1;
        for (int i = 1; i <= n; i++) {
            _fact[i] = _fact[i - 1] * i % MOD;
            _invfact[i] = inv(_fact[i]);
        }
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        prec(100010);

        int n = in.nextInt();
        int k = in.nextInt();

        int[] atk = new int[n];
        int[] def = new int[n];
        for (int i = 0; i < n; i++) {
            atk[i] = in.nextInt();
            def[i] = in.nextInt();
        }
        int[][] ma = new int[MAX+10][MAX+10];
        for (int i = 0; i < n ; i++) {
            ma[atk[i]][def[i]]++;
        }

        int[][] imos = new int[MAX+10][MAX+10];
        for (int i = 0 ; i <= MAX ; i++) {
            for (int j = 0 ; j <= MAX ; j++) {
                imos[i+1][j+1] = imos[i+1][j] + imos[i][j+1] - imos[i][j] + ma[i][j];
            }
        }

        int max = 3000;
        int min = -1;
        while (max - min > 1) {
            int med = (max + min) / 2;
            if (isPossible(k, med, imos)) {
                max = med;
            } else {
                min = med;
            }
        }


        long ans = 0;
        for (int i = 0 ; i <= MAX; i++) {
            for (int j = 0 ; j <= MAX; j++) {
                int corner = ma[i][j];
                int ti = Math.min(MAX+1, i+max+1);
                int tj = Math.min(MAX+1, j+max+1);
                int cnt = imos[ti][tj] - imos[ti][j] - imos[i][tj] + imos[i][j];

                int fx = imos[ti][tj] - imos[ti][j+1] - imos[i][tj] + imos[i][j+1];
                int fy = imos[ti][tj] - imos[ti][j] - imos[i+1][tj] + imos[i+1][j];
                int fxy = imos[ti][tj] - imos[ti][j+1] - imos[i+1][tj] + imos[i+1][j+1];

                // ptn1
                ans += (comb(cnt, k) - comb(cnt - corner, k) + MOD) % MOD;

                // ptn2
                cnt -= corner;
                ans += (comb(cnt, k) - comb(fx, k) - comb(fy, k) + comb(fxy, k) + MOD * 10) % MOD;
            }
            ans %= MOD;
        }

        out.println(max);
        out.println(ans);
        out.flush();
    }

    private static boolean isPossible(int k, int med, int[][] imos) {
        for (int i = 0 ; i <= MAX - med ; i++) {
            for (int j = 0 ; j <= MAX - med ; j++) {
                if (imos[i+med+1][j+med+1] - imos[i+med+1][j] - imos[i][j+med+1] + imos[i][j] >= k) {
                    return true;
                }
            }
        }
        return false;

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
