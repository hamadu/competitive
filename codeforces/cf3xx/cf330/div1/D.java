package codeforces.cf3xx.cf330.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 2016/08/20.
 */
public class D {
    private static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = in.nextInts(n);
        int q = in.nextInt();
        int[][] lr = new int[q][3];
        for (int i = 0; i < q ; i++) {
            lr[i][0] = in.nextInt()-1;
            lr[i][1] = in.nextInt()-1;
            lr[i][2] = i;
        }
        Arrays.sort(lr, (q0, q1) -> q0[1] - q1[1]);

        int[] po = generatePrimes(1000010);
        int[] pidx = new int[1000010];
        Arrays.fill(pidx, -1);
        long[] pmul = new long[po.length];
        long[] pinv = new long[po.length];
        for (int i = 0 ; i < po.length ; i++) {
            pidx[po[i]] = i;
            pmul[i] = (po[i] - 1) * inv(po[i]) % MOD;
            pinv[i] = inv(pmul[i]);
        }

        long[] ans = new long[q];
        int[] last = new int[po.length];
        Arrays.fill(last, -1);

        int qi = 0;
        BIT bit = new BIT(n+10);

        int[] primes = new int[100];
        int pi = 0;
        for (int i = 0 ; i < n ; i++) {
            pi = 0;
            int x = a[i];
            for (int p : po) {
                if (x < p) {
                    break;
                }
                if (isp[x]) {
                    primes[pi++] = pidx[x];
                    break;
                }
                if (x % p == 0) {
                    primes[pi++] = pidx[p];
                    while (x % p == 0) {
                        x /= p;
                    }
                }
            }
            for (int k = 0 ; k < pi ; k++) {
                int p = primes[k];
                if (last[p] != -1) {
                    bit.mul(last[p], pinv[p]);
                }
                last[p] = i+1;
                bit.mul(last[p], pmul[p]);
            }

            while (qi < q && lr[qi][1] <= i) {
                ans[lr[qi][2]] = bit.range(lr[qi][0]+1, lr[qi][1]+1);
                qi++;
            }
        }

        long[] imos = new long[n+1];
        imos[0] = 1;
        for (int i = 0; i < n ; i++) {
            imos[i+1] = imos[i] * a[i] % MOD;
        }
        for (int i = 0 ; i < q ; i++) {
            int l = lr[i][0];
            int r = lr[i][1]+1;
            int qq = lr[i][2];
            ans[qq] = ans[qq] * imos[r] % MOD * inv(imos[l]) % MOD;
        }
        for (int i = 0; i < q ; i++) {
            out.println(ans[i]);
        }
        out.flush();
    }

    static class BIT {
        long N;
        long[] data;
        BIT(int n) {
            N = n;
            data = new long[n+1];
            Arrays.fill(data, 1);
        }

        long sum(int i) {
            long s = 1;
            while (i > 0) {
                s = s * data[i] % MOD;
                i -= i & (-i);
            }
            return s;
        }

        long range(int i, int j) {
            return sum(j) * inv(sum(i-1)) % MOD;
        }

        void mul(int i, long x) {
            while (i <= N) {
                data[i] = data[i] * x % MOD;
                i += i & (-i);
            }
        }
    }

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

    static boolean[] isp;

    static int[] generatePrimes(int upto) {
        isp = new boolean[upto];
        Arrays.fill(isp, true);
        isp[0] = isp[1] = false;

        int pi = 0;
        for (int i = 2; i < upto ; i++) {
            if (isp[i]) {
                pi++;
                for (int j = i * 2; j < upto; j += i) {
                    isp[j] = false;
                }
            }
        }

        int[] ret = new int[pi];
        int ri = 0;
        for (int i = 2 ; i < upto ; i++) {
            if (isp[i]) {
                ret[ri++] = i;
            }
        }
        return ret;
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
