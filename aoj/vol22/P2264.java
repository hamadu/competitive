package aoj.vol22;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/31.
 */
public class P2264 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        if (n / 2 < k) {
            out.println(-1);
            out.flush();
            return;
        }

        int[] left = new int[200010];
        int[] right = new int[200010];
        int lh = left.length/2;
        int rh = right.length/2;
        int lt = left.length/2;
        int rt = right.length/2;

        for (int i = 1; i <= n ; i++) {
            if (i % 2 == 1) {
                left[lt++] = i;
            } else {
                right[rt++] = i;
            }
        }

        for (int i = 0; i < k ; i++) {
            if (i >= 1) {
                out.println();
            }
            int ln = lt - lh;
            int rn = rt - rh;
            if (ln == rn) {
                for (int j = 0; j < ln; j++) {
                    out.println(left[lh+j] + " " + right[rh+j]);
                    if (j+1 < ln) {
                        out.println(left[lh+j] + " " + right[rh+j+1]);
                    }
                }
            } else {
                for (int j = 0; j < ln; j++) {
                    if (j >= 1) {
                        out.println(left[lh+j] + " " + right[rh+j-1]);
                    }
                    if (j+1 < ln) {
                        out.println(left[lh+j] + " " + right[rh+j]);
                    }
                }
            }
            right[--rh] = left[lh++];
            left[lt++] = right[--rt];
        }

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
