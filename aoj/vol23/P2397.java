package aoj.vol23;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/15.
 */
public class P2397 {
    static final long MOD = 1000000009;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int cn = 0;
        while (true) {
            cn++;
            int w = in.nextInt();
            long h = in.nextLong();
            int n = in.nextInt();
            if (w + h + n == 0) {
                break;
            }
            long[][] obj = new long[n][2];
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < 2; j++) {
                    obj[i][j] = in.nextLong()-1;
                }
            }
            out.println(String.format("Case %d: %d", cn, solve(w, h, obj)));
        }


        out.flush();
    }

    private static long solve(int w, long h, long[][] obj) {
        Arrays.sort(obj, new Comparator<long[]>() {
            @Override
            public int compare(long[] o1, long[] o2) {
                return Long.signum(o1[1] - o2[1]);
            }
        });

        int n = obj.length;
        long[] nowCur = new long[w];
        nowCur[0] = 1;
        long[][] mat = new long[w][w];
        for (int i = 0; i < w ; i++) {
            for (int j = i-1 ; j <= i+1 ; j++) {
                if (j >= 0 && j < w) {
                    mat[i][j] = 1;
                }
            }
        }
        memo(mat, MOD);

        long nowRow = 0;
        for (int i = 0 ; i < n ;) {
            int head = i;
            int tail = i;
            while (tail < n && obj[head][1] == obj[tail][1]) {
                tail++;
            }
            long jumpTo = obj[head][1];
            nowCur = jump(mat, jumpTo - nowRow, nowCur);
            for (int k = head ; k < tail ; k++) {
                int col = (int)obj[k][0];
                nowCur[col] = 0;
            }
            nowRow = jumpTo;
            i = tail;
        }
        long finalJump = h - 1 - nowRow;
        long[] res = jump(mat, finalJump, nowCur);
        return res[w-1];
    }

    static long[] jump(long[][] mat, long length, long[] row) {
        long[][] A = pow(mat, length, MOD);
        int w = row.length;
        long[] toCur = new long[w];
        for (int k = 0; k < w ; k++) {
            for (int j = 0; j < w ; j++) {
                toCur[k] += (A[k][j] * row[j]) % MOD;
            }
            toCur[k] %= MOD;
        }
        return toCur;
    }

    static long[][][] memo;

    static void memo(long[][] mat, long mod) {
        int w = mat.length;
        memo = new long[61][][];
        memo[0] = E(w);
        memo[1] = mul(memo[0], mat, mod);
        for (int i = 2 ; i <= 60 ; i++) {
            memo[i] = mul(memo[i-1], memo[i-1], mod);
        }
    }

    public static long[][] pow(long[][] a, long n, long mod) {
        long i = 1;
        long[][] res = E(a.length);
        int cur = 0;
        while (i <= n) {
            cur++;
            if ((n & i) >= 1) {
                res = mul(res, memo[cur], mod);
            }
            i *= 2;
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
