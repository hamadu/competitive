package atcoder.arc041;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/06.
 */
public class C {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int L = in.nextInt();
        int[][] r = new int[n][2];
        for (int i = 0; i < n ; i++) {
            r[i][0] = in.nextInt();
            r[i][1] = in.nextChar() == 'L' ? -1 : 1;
        }

        int left = 0;
        while (left < n && r[left][1] == -1) {
            left++;
        }
        int right = n-1;
        while (right >= 0 && r[right][1] == 1) {
            right--;
        }
        long ans = solveLeft(left, r) + solveRight(L, right, r);

        for (int i = left ; i <= right ; ) {
            int lfr = i;
            int lto = i;
            while (lto <= right && r[lto][1] == 1) {
                lto++;
            }
            int rfr = lto;
            int rto = rfr;
            while (rto <= right && r[rto][1] == -1) {
                rto++;
            }
            i = rto;
            ans += solveCenter(lfr, lto-1, rfr, rto-1, r);
        }
        out.println(ans);
        out.flush();
    }


    private static long solveCenter(int lfr, int lto, int rfr, int rto, int[][] r) {
        long jump = 0;
        int L = r[lto][0];
        for (int i = lto ; i >= lfr ; i--) {
            jump += L - r[i][0];
            L--;
        }
        int R = r[rfr][0];
        for (int i = rfr ; i <= rto ; i++) {
            jump += r[i][0] - R;
            R++;
        }
        long gap = r[rfr][0] - r[lto][0] - 1;
        return jump + gap * Math.max(lto - lfr + 1, rto - rfr + 1);
    }

    static long solveLeft(int to, int[][] r) {
        int l = 1;
        long jump = 0;
        for (int i = 0 ; i < to ; i++) {
            jump += r[i][0] - l;
            l++;
        }
        return jump;
    }


    static long solveRight(int L, int to, int[][] r) {
        int l = L;
        long jump = 0;
        for (int i = r.length-1 ; i > to ; i--) {
            jump += l - r[i][0];
            l--;
        }
        return jump;
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
