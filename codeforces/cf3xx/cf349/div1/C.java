package codeforces.cf3xx.cf349.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/08/06.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        prec(200000);

        int n = in.nextInt();
        int len = in.nextToken().toCharArray().length;
        List<int[]> qinfo = new ArrayList<>();
        for (int i = 0; i < n ; i++) {
            int type = in.nextInt();
            if (type == 1) {
                len = in.nextToken().toCharArray().length;
            } else {
                qinfo.add(new int[]{len, in.nextInt(), i});
            }
        }
        long[] ans = new long[n];
        Arrays.fill(ans, -1);
        Collections.sort(qinfo, (q0, q1) -> {
            int cidx = q0[0] == q1[0] ? 1: 0;
            return q0[cidx] - q1[cidx];
        });

        long[] tbl25 = new long[200000];
        tbl25[0] = 1;
        for (int i = 1 ; i < tbl25.length; i++) {
            tbl25[i] = (tbl25[i-1] * 25) % MOD;
        }

        long[] zoy = new long[200000];
        for (int i = 0; i < qinfo.size() ; ) {
            int fr = i;
            int to = fr;
            while (to < qinfo.size() && qinfo.get(fr)[0] == qinfo.get(to)[0]) {
                to++;
            }
            int k = qinfo.get(fr)[0];
            int head = k;
            zoy[head] = 1;
            for (int j = fr ; j < to ; j++) {
                int[] q = qinfo.get(j);
                while (head < q[1]) {
                    zoy[head+1] = (zoy[head] * 26 + comb(head, k-1) * tbl25[head-k+1] % MOD) % MOD;
                    head++;
                }
                if (q[1] < k) {
                    ans[q[2]] = 0;
                } else {
                    ans[q[2]] = zoy[q[1]];
                }
            }
            i = to;
        }

        for (int i = 0; i < n ; i++) {
            if (ans[i] >= 0) {
                out.println(ans[i]);
            }
        }
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
        }
        _invfact[n] = inv(_fact[n]);
        for (int i = n ; i >= 2 ; i--) {
            _invfact[i-1] = (_invfact[i] * i) % MOD;
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
