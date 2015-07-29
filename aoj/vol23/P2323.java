package aoj.vol23;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/28.
 */
public class P2323 {

    static long INF = 1000000000000000000L;

    public static void main(String[] args) {
//        check("0");
//        check("00");
//        check("000");
//        check("00100");

        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        
        while (true) {
            char[] c = in.nextToken().toCharArray();
            if (c[0] == '#') {
                break;
            }
            out.println(solve(c));
        }

        out.flush();
    }

    private static void check(String c) {
        StringBuilder line = new StringBuilder();
        for (int i = 1; i <= 10000 ; i++) {
            line.append(i);
        }
        String to = line.toString();
        debug(to.indexOf(c)+1);
    }

    private static long solve(char[] c) {
        int sum = 0;
        for (int i = 0; i < c.length ; i++) {
            sum += c[i] - '0';
        }
        if (sum == 0) {
            return computeIndex(Long.valueOf("1" + String.valueOf(c))) + 2;
        }

        int n = c.length;
        long ret = INF;
        for (int i = 0; i < n ; i++) {
            for (int j = i+1; j <= Math.min(i+16, n); j++) {
                if (c[i] != '0') {
                    long base = 0;
                    for (int k = i; k < j; k++) {
                        base *= 10;
                        base += c[k] - '0';
                    }
                    ret = Math.min(ret, doit(i, j, c, base));
                }

                if (i == 0) {
                    for (int l = j+1 ; l <= n ; l++) {
                        // [j,l)
                        long base = 0;
                        for (int k = j; k < l; k++) {
                            base *= 10;
                            base += c[k] - '0';
                        }
                        for (int dn = 0; dn <= 1; dn++) {
                            long prec = base+dn;
                            for (int k = i; k < j; k++) {
                                prec *= 10;
                                prec += c[k] - '0';
                            }
                            ret = Math.min(ret, doit(i, j, c, prec));
                        }
                    }
                }
            }
        }
        return ret + 1;
    }

    private static long computeIndex(long base) {
        long idx = 0;
        int k = 1;
        for (long i = 1; i <= base ; i *= 10) {
            long to = Math.min(i*10, base);
            long cnt = to - i;
            idx += cnt * k;
            k++;
        }
        return idx;
    }

    private static long doit(int fr, int to, char[] c, long base) {
        StringBuilder line = new StringBuilder();
        long left = base;
        long right = base;
        line.append(String.valueOf(base));
        while (line.length() < Math.max(c.length * 3, 100)) {
            if (--left >= 1) {
                line.insert(0, String.valueOf(left));
            }
            line.append(String.valueOf(++right));
        }

        int idx = line.toString().indexOf(String.valueOf(c));
        if (idx >= 0) {
            return computeIndex(left) + idx;
        }
        return INF;
    }

    private static char digit(long base, int k) {
        return String.valueOf(base).toCharArray()[k];
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
