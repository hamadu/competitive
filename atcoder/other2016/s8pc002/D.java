package atcoder.other2016.s8pc002;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 4/26/16.
 */
public class D {


    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        memo = new long[100000];
        Arrays.fill(memo, Long.MAX_VALUE);
        dfs(1, 1, 0, 1_000_000_000_000_000_00L);

        List<long[]> pair = new ArrayList<>();
        for (int i = 0 ; i < memo.length ; i++) {
            if (memo[i] != Long.MAX_VALUE) {
                pair.add(new long[]{memo[i], i});
            }
        }
        Collections.sort(pair, (p0, p1) -> Long.compare(p0[0], p1[0]));

        TreeMap<Long,Long> magicMap = new TreeMap<>();
        long last = -1;
        for (long[] p : pair) {
            if (last > p[1]) {
                continue;
            }
            last = p[1];
            magicMap.put(p[0], p[1]);
        }

        int q = in.nextInt();
        while (--q >= 0) {
            long n = in.nextLong();
            Map.Entry<Long,Long> e = magicMap.floorEntry(n);
            out.println(String.format("%d %d", e.getValue(), e.getKey()));
        }
        out.flush();
    }

    static final long[] primes = new long[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
    static final long[] limit = new long[]{10, 8, 6, 4, 2,  1,  1,  1,  1,  1,  1,  1 };
    static long[] memo;

    private static void dfs(long now, int val, int idx, long n) {
        if (now > n) {
            return;
        }
        memo[val] = Math.min(memo[val], now);
        if (idx >= primes.length) {
            return;
        }
        dfs(now, val, idx+1, n);
        int cur = 0;
        long upto = n / primes[idx];
        while (now <= upto && cur <= limit[idx]) {
            now *= primes[idx];
            cur++;
            dfs(now, val*(1+cur), idx+1, n);
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
