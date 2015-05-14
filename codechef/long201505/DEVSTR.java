package codechef.long201505;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by hama_du on 15/05/09.
 */
public class DEVSTR {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int T = in.nextInt();
        while (--T >= 0) {
            int n = in.nextInt();
            int k = in.nextInt() + 1;
            char[] s = in.nextToken().toCharArray();
            out.println(solve(s, k));
            out.println(String.valueOf(s));
        }
        out.flush();
    }

    private static int solve(char[] s, int k) {
        if (k == 2) {
            return solve2(s);
        }

        int cnt = 0;
        int n = s.length;
        for (int i = 0 ; i < n ;) {
            int fr = i;
            int to = i;
            while (to < n && s[fr] == s[to]) {
                to++;
            }
            int fi = fr + k - 1;
            while (fi < to) {
                s[fi] = flip(s[fi]);
                fi += k;
                cnt++;
            }
            if (to != n && (to - fr) % k == 0) {
                s[to-2] = flip(s[to-2]);
                s[to-1] = flip(s[to-1]);
            }
            i = to;
        }
        return cnt;
    }

    private static char flip(char s) {
        return s == '0' ? '1' : '0';
    }

    private static int solve2(char[] s) {
        int n = s.length;
        int zero = 0;
        int one = 0;
        for (int i = 0 ; i < n ; i++) {
            if (s[i] == ('0' + (i % 2))) {
                zero++;
            } else {
                one++;
            }
        }

        int d = (zero < one) ? 1 : 0;
        for (int i = 0 ; i < n ; i++) {
            s[i] = (char)('0' + ((i + d) % 2));
        }
        return Math.min(zero, one);
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
}
