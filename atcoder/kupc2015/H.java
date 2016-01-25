package atcoder.kupc2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/10/24.
 */
public class H {
    private static final long INF = (long)1000000000000000000L;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        // check();

        int T = in.nextInt();
        while (--T >= 0) {
            long n = in.nextLong();
            out.println(solveDP(n));
        }

        out.flush();
    }

    static void check() {
        for (int i = 1; i <= 2000 ; i++) {
            long a1 = solveDP(i);
            long a2 = 0;
            for (int j = 1 ; j <= 6000; j++) {
                if (Long.bitCount(j) == Long.bitCount(i+j)) {
                    a2 = j;
                    break;
                }
            }
            if (a1 != a2) {
                debug(i, a1, a2);
            }
        }
    }

    private static long solveDP(long n) {
        long[][][] way = new long[65][128][2];
        for (int i = 0; i < 64 ; i++) {
            for (int j = 0; j < 128; j++) {
                Arrays.fill(way[i][j], INF);
            }
        }
        int CAP = 128;
        int GETA = 64;
        way[0][GETA][0] = 0;
        for (int i = 0; i < 60; i++) {
            int bit = (n & (1L<<i)) >= 1 ? 1 : 0;
            for (int j = 0; j < 128; j++) {
                for (int f = 0; f <= 1; f++) {
                    if (way[i][j][f] == INF) {
                        continue;
                    }
                    long best = way[i][j][f];

                    // zero
                    {
                        int val = f + bit;
                        int tf = val >= 2 ? 1 : 0;
                        val %= 2;
                        int tj = j + val;
                        if (tj >= 0 && tj < CAP && way[i+1][tj][tf] > best) {
                            way[i+1][tj][tf] = best;
                        }
                    }

                    // one
                    {
                        int val = f + bit + 1;
                        int tf = val >= 2 ? 1 : 0;
                        val %= 2;
                        int tj = j + val - 1;
                        long tv = best + (1L<<i);
                        if (tj >= 0 && tj < CAP && way[i+1][tj][tf] > tv) {
                            way[i+1][tj][tf] = tv;
                        }
                    }
                }
            }
        }

        return way[60][GETA][0];
    }

    private static long solve(long n) {
        long N = n;
        int flg = 0;
        int[] pos = new int[128];
        int[] len = new int[128];
        int P = 0;

        int idx = 0;
        while (n >= 1) {
            if (n % 2 == 1) {
                if (flg == 0) {
                    pos[P] = idx;
                    flg = 1;
                }
            } else {
                if (flg == 1) {
                    len[P] = idx - pos[P];
                    P++;
                }
                flg = 0;
            }
            n /= 2;
            idx++;
        }
        if (flg == 1) {
            len[P] = idx - pos[P];
            P++;
        }


        long ans = 0;
        int left = 0;
        for (int ip = P-1; ip >= left ; ip--) {
            if (Long.bitCount(ans) == Long.bitCount(ans+N)) {
                break;
            }
            int need = len[ip];
            int canUse = (ip - left);
            if (need > canUse) {
                ans |= 1L<<pos[ip];
            } else {
                for (int i = 0 ; i < need ; i++) {
                    ans |= 1L<<pos[left+i];
                    ans |= 1L<<(pos[left+i]+len[left+i]);
                }
                left += need;
            }
        }

        if (Long.bitCount(ans)!=Long.bitCount(ans+N)) {
            debug(Long.bitCount(ans),Long.bitCount(ans+N));

        }

        return ans;
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
