// http://arc034.contest.atcoder.jp/submissions/352841
package atcoder.arc034;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class C {
    private static final long MOD = 1_000_000_007;

    static List<Integer> primes(int max) {
        List<Integer> primes = new ArrayList<>();
        boolean[] p = new boolean[max];
        Arrays.fill(p, true);
        p[1] = false;
        for (int i = 2 ; i < max ; i++) {
            if (p[i]) {
                for (int ii = i * 2; ii < max; ii += i) {
                    p[ii] = false;
                }
                primes.add(i);
            }
        }
        return primes;
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int a = in.nextInt();
        int b = in.nextInt();

        List<Integer> primes = primes(50000);
        Map<Integer,Integer> count = new HashMap<>();
        for (int c = b+1 ; c <= a ; c++) {
            int d = c;
            for (int p : primes) {
                int cnt = 0;
                while ((d % p) == 0) {
                    d /= p;
                    cnt++;
                }
                if (cnt >= 1) {
                    if (count.containsKey(p)) {
                        count.put(p, count.get(p)+cnt);
                    } else {
                        count.put(p, cnt);
                    }
                }
            }
            if (d >= 2) {
                if (count.containsKey(d)) {
                    count.put(d, count.get(d) + 1);
                } else {
                    count.put(d, 1);
                }
            }
        }

        long ptn = 1;
        for (int v : count.values()) {
            ptn *= (v + 1);
            ptn %= MOD;
        }
        out.println(ptn);
        out.flush();
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
