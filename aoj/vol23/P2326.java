package aoj.vol23;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/28.
 */
public class P2326 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int a = in.nextInt();
            int b = in.nextInt();
            long P = in.nextLong();
            if (P + a + b == 0) {
                break;
            }
            out.println(solve(a, b, P));
        }

        out.flush();
    }

    private static long solve(long a, long b, long p) {
        List<String> x = new ArrayList<String>();
        for (long l = a ; l <= b ; l++) {
            x.add(String.valueOf(l));
        }
        Collections.sort(x);
        oi = 0;
        for (String num : x) {
            ord[oi++] = Long.valueOf(num);
        }
        bit.MOD = p;
        Arrays.fill(bit.data, 0);

        for (int i = 0; i < oi ; i++) {
            int idx = (int)(ord[i]-(a-1));
            long add = 1 + bit.range(1, idx-1);
            bit.add(idx, add);
        }

        return bit.range(1, 100005);
    }

    static long[] ord = new long[100010];
    static int oi;
    static BIT bit = new BIT(100010, 1);

    // BIT, 1-indexed, range : [a,b]
    static class BIT {
        long MOD;
        long N;
        long[] data;
        BIT(int n, long P) {
            MOD = P;
            N = n;
            data = new long[n+1];
        }


        long sum(int i) {
            long s = 0;
            while (i > 0) {
                s += data[i];
                s %= MOD;
                i -= i & (-i);
            }
            return s;
        }

        long range(int i, int j) {
            return (sum(j) - sum(i-1) + MOD) % MOD;
        }

        void add(int i, long x) {
            while (i <= N) {
                data[i] += x;
                data[i] %= MOD;
                i += i & (-i);
            }
        }
    }

    private static void dfs(long idx, long a, long b) {
        if (idx > b) {
            return;
        }
        if (idx >= a) {
            ord[oi++] = idx;
        }
        for (int f = (idx == 0) ? 1 : 0 ; f <= 9 ; f++) {
            dfs(idx*10+f, a, b);
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
