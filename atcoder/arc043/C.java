package atcoder.arc043;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/09/19.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt()-1;
        }
        for (int i = 0; i < n ; i++) {
            b[i] = in.nextInt()-1;
        }

        int[] invA = new int[n];
        for (int i = 0; i < n ; i++) {
            invA[a[i]] = i;
        }
        int[] q = new int[n];
        for (int i = 0; i < n ; i++) {
            q[i] = invA[b[i]];
        }

        long x = inverseCount(q);
        if (x % 2 == 1) {
            out.println(-1);
        } else {
            long want = x / 2;
            int[] arr = build(want, n);
            int[] ans = new int[n];
            for (int i = 0; i < n ; i++) {
                ans[i] = a[arr[i]];
            }
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < n ; i++) {
                line.append(' ').append(ans[i]+1);
            }
            out.println(line.substring(1));
        }
        out.flush();
    }

    private static int[] build(long d, int n) {
        int[] ret = new int[n];
        return ret;
    }

    private static long inverseCount(int[] q) {
        int n = q.length;
        long cnt = 0;
        BIT bit = new BIT(n+10);
        for (int i = 0; i < n ; i++) {
            int num = q[i]+1;
            cnt += bit.range(num+1, n+5);
            bit.add(num, 1L);
        }
        return cnt;
    }


    // BIT, 1-indexed, range : [a,b]
    static class BIT {
        long N;
        long[] data;
        BIT(int n) {
            N = n;
            data = new long[n+1];
        }

        long sum(int i) {
            long s = 0;
            while (i > 0) {
                s += data[i];
                i -= i & (-i);
            }
            return s;
        }

        long range(int i, int j) {
            return sum(j) - sum(i-1);
        }

        void add(int i, long x) {
            while (i <= N) {
                data[i] += x;
                i += i & (-i);
            }
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
