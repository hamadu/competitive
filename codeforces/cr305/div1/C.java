package codeforces.cr305.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by dhamada on 15/05/28.
 */
public class C {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int q = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt();
        }

        Object[] ispret = generatePrimes(500010);
        boolean[] isp = (boolean[]) ispret[0];
        int[] primes = (int[]) ispret[1];

        int[][] ap = new int[n][10];
        int[] pn = new int[n];
        for (int i = 0; i < n ; i++) {
            int A = a[i];
            for (int pi : primes) {
                if (isp[A] || A == 1) {
                    break;
                }
                boolean div = false;
                while (A % pi == 0) {
                    div = true;
                    A /= pi;
                }
                if (div) {
                    ap[i][pn[i]++] = pi;
                }
            }
            if (A >= 2) {
                ap[i][pn[i]++] = A;
            }
        }

        long cnt = 0;
        int[] divisors = new int[500010];

        int[] have = new int[n];
        for (int i = 0; i < q ; i++) {
            int idx = in.nextInt()-1;
            int num = 0;
            int diff = 1 - have[idx] * 2;

            int ptn = 1<<pn[idx];
            for (int k = 0 ; k < ptn ; k++) {
                int m = 1;
                for (int pi = 0 ; pi < pn[idx] ; pi++) {
                    if ((k & (1<<pi)) >= 1) {
                        m *= ap[idx][pi];
                    }
                }
                int sign = 1 - (Integer.bitCount(k) % 2) * 2;
                if (diff == 1) {
                    num += sign * divisors[m];
                    divisors[m] += diff;
                } else {
                    divisors[m] += diff;
                    num += sign * divisors[m];
                }
            }
            have[idx] = 1 - have[idx];
            cnt += diff * num;
            out.println(cnt);
        }

        out.flush();
    }

    static Object[] generatePrimes(int upto) {
        boolean[] isp = new boolean[upto];
        Arrays.fill(isp, true);

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

        return new Object[]{ isp, ret };
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
                return (char)c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char)c;
            }
            throw new InputMismatchException();
        }

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char)c);
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
