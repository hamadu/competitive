package csacademy.round018;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class F {
    private static final long MOD = (long)1e9+7;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = in.nextInts(n);
        int[] aa = new int[2*n];
        for (int i = 0; i < 2*n ; i++) {
            aa[i] = a[i%n];
        }
        prepare(aa);

        for (int i = 0; i < n ; i++) {
            if (a[i] == 1) {
                out.println(0);
                out.flush();
                return;
            }
        }
        if (gcdRange(0, n) >= 2) {
            long ans = 1;
            for (int i = 0; i < n ; i++) {
                ans *= 2;
                ans %= MOD;
            }
            ans = (ans - n + MOD) % MOD;
            out.println(ans);
            out.flush();
            return;
        }

        int head = 0;
        int[] canGo = new int[n];
        for (int i = 0; i < n ; i++) {
            head = Math.max(head, i+1);
            int now = gcdRange(i, head);
            while (now >= 2) {
                now = gcd(now, aa[head]);
                head++;
            }
            canGo[i] = head-1;
        }

        int prefixGCD = -1;

        long ans = 0;
        long[] dpsum = new long[2*n+10];
        long[] dp = new long[2*n+10];
        for (int i = n ; i >= 2 ; i--) {
            int toGCD = compute(prefixGCD, a[i%n]);
            if (toGCD == 1) {
                break;
            }
            if (prefixGCD != toGCD) {
                prefixGCD = toGCD;

                int ok = 1;
                int ng = i;
                while (ng - ok > 1) {
                    int med = (ng + ok) / 2;
                    if (compute(prefixGCD, gcdRange(1, med)) >= 2) {
                        ok = med;
                    } else {
                        ng = med;
                    }
                }

                canGo[0] = ok;

                Arrays.fill(dpsum, 0);
                Arrays.fill(dp, 0);
                dp[0] = 1;

                long adding = 0;
                for (int v = 0 ; v <= i ; v++) {
                    adding = (adding+dpsum[v])%MOD;
                    dp[v] = (dp[v]+adding)%MOD;
                    if (v == i) {
                        break;
                    }
                    dpsum[v+1] = (dpsum[v+1]+dp[v])%MOD;
                    dpsum[canGo[v]+1] = (dpsum[canGo[v]+1]-dp[v]+MOD)%MOD;
                }
            }
            ans += dp[i];
            ans %= MOD;
        }

        out.println(ans);
        out.flush();
    }

    static int M;
    static int[] data;

    static int gcdRange(int i, int j) {
        if (j - i <= 0) {
            return -1;
        }
        return gcdRange(i, j, 1, 0, M);
    }

    static int gcdRange(int fr, int to, int idx, int l, int r) {
        if (to <= l || r <= fr) {
            return -1;
        }
        if (fr <= l && r <= to) {
            return data[idx];
        }

        int left = gcdRange(fr, to, idx*2, l, (l+r)>>>1);
        int right = gcdRange(fr, to, idx*2+1, (l+r)>>>1, r);
        return compute(left, right);
    }

    static void prepare(int[] a) {
        int n = a.length;
        M = Math.max(4, Integer.highestOneBit(n-1)<<1);
        data = new int[2*M];

        Arrays.fill(data, -1);
        for (int i = 0 ; i < a.length ; i++) {
            data[M+i] = a[i];
        }
        for (int i = M-1 ; i >= 1 ; i--) {
            data[i] = compute(data[i*2], data[i*2+1]);
        }
    }

    private static int compute(int a, int b) {
        if (a == -1) {
            return b;
        } else if (b == -1) {
            return a;
        } else {
            return gcd(a, b);
        }

    }

    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a%b);
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
