package codeforces.cf3xx.cr345.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/03/08.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int a = in.nextInt();
        int b = in.nextInt();
        int T = in.nextInt();
        char[] wh = in.nextToken().toCharArray();

        long requireFirst = ((wh[0] == 'w') ? b : 0) + 1;
        long[] right = new long[n];
        for (int i = 1 ; i < n ; i++) {
            right[i] = right[i-1] + a;
            if (wh[i] == 'w') {
                right[i] += b;
            }
            right[i]++;
        }

        long[] left = new long[n];
        for (int i = 1 ; i < n ; i++) {
            int idx = n-i;
            left[i] = left[i-1] + a;
            if (wh[idx] == 'w') {
                left[i] += b;
            }
            left[i]++;
        }

        int ans = Math.max(doit(left, right, a, T-requireFirst), doit(right, left, a, T-requireFirst));
        out.println(ans);
        out.flush();
    }

    private static int doit(long[] left, long[] right, int a, long time) {
        if (time < 0) {
            return 0;
        }
        int n = right.length;
        if (left[n-1] <= time && right[n-1] <= time) {
            return n;
        }

        int ridx = 0;
        while (ridx < n-1 && right[ridx+1] <= time) {
            ridx++;
        }
        int lidx = 0;
        int max = ridx+1;
        do {
            while (lidx < n-1 && right[ridx] + left[lidx+1] + (lidx+1) * a <= time) {
                lidx++;
                max = Math.max(max, ridx+lidx+1);
            }
        } while (--ridx >= 0);
        return max;
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
