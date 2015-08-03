package codeforces.cr55.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/08/03.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        
        int k = in.nextInt();
        char[] c = in.nextToken().toCharArray();
        
        out.println(solve(k, c));
        out.flush();
    }

    private static String solve(int k, char[] c) {
        int n = c.length;
        int need = (1<<k)-1;
        int used = 0;
        for (int i = 0; i < n / 2; i++) {
            if (c[i] != '?' && c[n-1-i] != '?' && c[i] != c[n-1-i]) {
                return "IMPOSSIBLE";
            }
        }

        for (int i = 0; i < n; i++) {
            if (c[i] != '?') {
                used |= 1<<(c[i]-'a');
            }
        }

        for (int i = 0; i <= (n-1) / 2; i++) {
            if (c[i] == '?' && c[n-1-i] == '?') {
                int fill = k - Integer.bitCount(used);
                for (int j = i; j <= (n-1) / 2; j++) {
                    if (c[j] == '?' && c[n-1-j] == '?') {
                        fill--;
                    }
                }
                char us = 'a';
                if (fill >= 0) {
                    for (int j = 0; j < k; j++) {
                        if ((used & (1<<j)) == 0) {
                            used |= 1<<j;
                            us = (char)('a' + j);
                            break;
                        }
                    }
                }
                c[i] = c[n-1-i] = us;
            } else if (c[i] == '?') {
                c[i] = c[n-1-i];
            } else {
                c[n-1-i] = c[i];
            }
        }
        return (need == used) ? String.valueOf(c) : "IMPOSSIBLE";
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
