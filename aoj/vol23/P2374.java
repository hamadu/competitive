package aoj.vol23;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/20.
 */
public class P2374 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int M = in.nextInt();
        int N = in.nextInt();
        int m0 = in.nextInt();
        int md = in.nextInt();
        int n0 = in.nextInt();
        int nd = in.nextInt();

        int[] carr = make(M, m0, md, N+1);
        int[] kiwi = make(N, n0, nd, M+1);
        Arrays.sort(carr);
        Arrays.sort(kiwi);
        for (int i = 0; i < N / 2 ; i++) {
            int ri = N-i-1;
            int t = kiwi[i];
            kiwi[i] = kiwi[ri];
            kiwi[ri] = t;
        }

        out.println(solve(kiwi, carr));
        out.flush();
    }

    static long solve(int[] kiwi, int[] carr) {
        int N = kiwi.length;
        int M = carr.length;
        long[] inc = new long[N+1];
        long[] dec = new long[N+1];
        for (int i = 0; i < M ; i++) {
            int to = Math.min(N, carr[i]);
            inc[0]++;
            dec[to]++;
        }

        long sum = 0;
        long mod = 0;

        long val = 0;
        for (int i = 0; i < N ; i++) {
            val += inc[i];
            val -= dec[i];
            if (mod >= 1) {
                long sup = Math.min(dec[i], mod);
                val += sup;
                mod -= sup;
                dec[i+1] += sup;
            }
            if (val > kiwi[i]) {
                sum += kiwi[i];
                mod += val - kiwi[i];
            } else {
                sum += val;
            }
        }
        return sum;
    }

    static int[] make(int M, int m0, int md, int mod) {
        int[] r = new int[M];
        r[0] = m0;
        for (int i = 1; i < M ; i++) {
            r[i] = (r[i-1] * 58 + md) % mod;
        }
        return r;
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
