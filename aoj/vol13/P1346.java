package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/14.
 */
public class P1346 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        char[] c = in.nextToken().toCharArray();
        int ans = in.nextInt();

        int mulFirst = solveMulFirst(c);
        int leftFirst = solveLeftFirst(c);

        if (ans == mulFirst && ans == leftFirst) {
            out.println('U');
        } else if (ans == mulFirst && ans != leftFirst) {
            out.println('M');
        } else if (ans != mulFirst && ans == leftFirst) {
            out.println('L');
        } else {
            out.println('I');
        }
        out.flush();
    }

    private static int solveMulFirst(char[] c) {
        int n = c.length;
        int ans = 0;
        boolean[] touched = new boolean[n];
        for (int i = 0 ; i+1 < n ; i += 2) {
            if (touched[i]) {
                continue;
            }
            if (c[i+1] == '*') {
                int r = c[i] - '0';
                touched[i] = true;
                for (int j = i+2; j < n && c[j-1] == '*' ; j += 2) {
                    touched[j] = true;
                    r *= c[j] - '0';
                }
                ans += r;
            }
        }
        for (int i = 0; i < n ; i += 2) {
            if (!touched[i]) {
                ans += c[i] - '0';
            }
        }
        return ans;
    }

    private static int solveLeftFirst(char[] c) {
        int n = c.length;
        int ans = c[0] - '0';
        for (int i = 1 ; i < n ; i += 2) {
            char op = c[i];
            int d = c[i+1] - '0';
            if (op == '*') {
                ans *= d;
            } else {
                ans += d;
            }
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
