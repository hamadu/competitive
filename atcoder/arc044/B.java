package atcoder.arc044;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/09/19.
 */
public class B {
    private static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        boolean wrong = false;
        int n = in.nextInt();
        int[] cnt = new int[n];
        int max = 0;
        for (int i = 0; i < n ; i++) {
            int d = in.nextInt();
            cnt[d]++;
            if (i == 0 && d != 0) {
                wrong = true;
            }
            max = Math.max(max, d);
        }
        if (cnt[0] >= 2) {
            wrong = true;
        }


        long[] pow2MOD = new long[4*n];
        pow2MOD[0] = 1;
        for (int i = 1; i < pow2MOD.length ; i++) {
            pow2MOD[i] = (pow2MOD[i-1] * 2) % MOD;
        }

        long ptn = 1;
        for (int d = 1 ;  d < n ; d++) {
            int prev = cnt[d-1];
            int next = cnt[d];
            for (int f = 0 ; f < next ; f++) {
                ptn *= (pow2MOD[prev]-1+MOD)%MOD;
                ptn %= MOD;
            }
            if (d == max) {
                break;
            }
        }

        long freeEdges = 0;
        for (int d = 1 ; d < n ; d++) {
            freeEdges += 1L*cnt[d]*(cnt[d]-1L)/2;
        }

        ptn *= pow(2, freeEdges);
        ptn %= MOD;

        if (wrong) {
            ptn = 0;
        }
        out.println(ptn);
        out.flush();
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
